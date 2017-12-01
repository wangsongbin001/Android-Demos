package com.wang.mtoolsdemo;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.wang.mtoolsdemo.common.bean.ProgressBean;
import com.wang.mtoolsdemo.common.download.DownloadChangeObserver;
import com.wang.mtoolsdemo.common.util.LogUtil;
import com.wang.mtoolsdemo.common.util.PermissionActivity;
import com.wang.mtoolsdemo.common.util.SPUtil;
import com.wang.mtoolsdemo.common.util.ToastUtil;
import com.wang.mtoolsdemo.common.view.MHorizontalProgressDialog;
import com.wang.mtoolsdemo.common.view.NumberProgressBar;

import java.io.File;
import java.io.FileDescriptor;
import java.util.HashMap;

/**
 * Created by dell on 2017/11/24.
 * 检测更新App
 */

public class SixActivity extends PermissionActivity{
    String url = "http://m.creditflower.cn/apk/XinYongHua-V110-TEST.apk";
    String fileName = "XinYongHua-V110-TEST.apk";

    private DownloadManager mDownloadManager;
    private TextView tv_progress;
    private NumberProgressBar pb_content;
    private boolean installed = false;
    private Button btn_install;

    Handler mainHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ProgressBean progressBean = (ProgressBean) msg.obj;
            float percent = progressBean.getProgress() * 1.0f/progressBean.getTotal();
            int progress = (int) (percent * 100);
            tv_progress.setText(progress + "%");
            pb_content.setProgress(progress);
            if(progressBean.getProgress() == progressBean.getTotal() && !installed){
                installed = true;

            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);//统计时长
        MobclickAgent.onPageStart("" + getClass().getSimpleName());//统计界面
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        MobclickAgent.onPageEnd("" + getClass().getSimpleName());
    }

    DownloadChangeObserver mDownloadChangeObserver = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_6);

        MobclickAgent.setCatchUncaughtExceptions(true);

        checkNeedPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE}, null, null);

        btn_install = (Button) findViewById(R.id.btn_install);
        btn_install.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobclickAgent.onEvent(SixActivity.this, "event_install");

                HashMap<String, String> product = new HashMap<String, String>();
                product.put("type", "book");
                product.put("price", "" + 100);
                MobclickAgent.onEvent(SixActivity.this, "event_install", product);
                MobclickAgent.onEventValue(SixActivity.this, "onclik", product, 10);
                MHorizontalProgressDialog mHorizontalProgressDialog
                        = new MHorizontalProgressDialog(SixActivity.this);
                mHorizontalProgressDialog.show();

                throw new NullPointerException();
//                StringBuilder sb = new StringBuilder(Environment.getExternalStorageDirectory().getAbsolutePath());
//                sb.append("/MToolsDemo/file")
//                        .append(File.separator)
//                        .append("app-debug.apk");
//
//                LogUtil.i("wangsongbin", "" + Build.VERSION.SDK_INT + "," + sb.toString());
//                installApk(SixActivity.this, "" + sb.toString());
//                finish();
            }
        });
//        tv_progress = findViewById(R.id.tv_progress);
//        pb_content = findViewById(R.id.pb_content);
//
//        mDownloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
//        //在执行下载任务前设置观察者
//        register();
//        //执行下载任务
//        long downloadId = mDownloadManager.enqueue(createRequest());
//        SPUtil.put(this, "downloadid", downloadId);
    }

    private DownloadManager.Request createRequest(){
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        //设置下载时的网络状态
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI
                | DownloadManager.Request.NETWORK_MOBILE);
        //设置通知栏是否可见
        request.setNotificationVisibility(View.VISIBLE);
        /**设置文件保存路径*/
        File fileDir = Environment.getExternalStoragePublicDirectory("/wangsongbin/file");
        if (fileDir.mkdirs() || fileDir.isDirectory()) {
            request.setDestinationInExternalPublicDir("/wangsongbin/file", "phoenix.apk");
        }
        return request;
    }

    private void register(){
        mDownloadChangeObserver = new DownloadChangeObserver(this, mainHandler, (long)SPUtil.get(this, "downloadid", -1l));
        getContentResolver().registerContentObserver(Uri.parse("content://downloads/"), true, mDownloadChangeObserver);

        registerReceiver(new OnCompleteReceiver(), new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    private class OnCompleteReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            long downloadid = (long) SPUtil.get(SixActivity.this, "downloadid", -1l);
            //通过广播，获取id
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            LogUtil.i("downloadId:" + downloadid + ",id:" + id);
            if(id != downloadid){
                return;
            }
            Cursor cursor = mDownloadManager.query(new DownloadManager.Query().setFilterById(id));
            if(cursor.moveToFirst()){
                int columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
                if (cursor.getInt(columnIndex) == DownloadManager.STATUS_SUCCESSFUL) {
                    String localFileUri = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                    installApk(SixActivity.this, localFileUri.replaceFirst("file://", ""));
                }
            }
            cursor.close();
        }
    }

    private void installApk(Context context, String localFilePath){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        File file = new File(localFilePath);
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            uri = FileProvider.getUriForFile(context,
                    "com.wang.mtoolsdemo.fileprovider", file);
        } else {
            uri = Uri.fromFile(file);
        }
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        try {
            LogUtil.i("wangsongbin", "install");
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.i("wangsongbin", "" + e.getMessage());
            ToastUtil.showLong(context, "打开安装程序失败");
        }
    }
}
