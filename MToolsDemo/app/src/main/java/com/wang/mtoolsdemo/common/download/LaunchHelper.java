package com.wang.mtoolsdemo.common.download;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import com.wang.mtoolsdemo.BuildConfig;
import com.wang.mtoolsdemo.common.AppConfig;
import com.wang.mtoolsdemo.common.bean.NewVersion;
import com.wang.mtoolsdemo.common.bean.PlatformVersion;
import com.wang.mtoolsdemo.common.bean.ProgressBean;
import com.wang.mtoolsdemo.common.rxjava1.MErrorAction;
import com.wang.mtoolsdemo.common.rxjava1.RetrofitUtil1;
import com.wang.mtoolsdemo.common.util.AppUtil;
import com.wang.mtoolsdemo.common.util.DialogUtil;
import com.wang.mtoolsdemo.common.util.LogUtil;
import com.wang.mtoolsdemo.common.util.NetUtil;
import com.wang.mtoolsdemo.common.util.SDCardUtil;
import com.wang.mtoolsdemo.common.util.ToastUtil;

import java.io.File;

import rx.functions.Action1;
import rx.functions.Func1;

import static android.content.Context.DOWNLOAD_SERVICE;

/**
 * Created by dell on 2017/11/27.
 */

public class LaunchHelper {
    public static final String ACTION_START_DOWNLOAD  = "com.wang.mtoolsdemo.action_start_download";
    LaunchActivity mLauncherActivity;
    DownloadIdBroadcastReceiver mDownloadIdBroadcastReceiver;
    DownloadChangeObserver mDownloadChangeObserver;

    Handler mainHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1001 && msg.obj instanceof ProgressBean){
                ProgressBean bean = (ProgressBean) msg.obj;
//                float percent = bean.getProgress() * 1.0f/bean.getTotal();
//                int progress = (int) (percent * 100);
                mLauncherActivity.updateProgress(bean.getProgress(), bean.getTotal());
            }
        }
    };

    public LaunchHelper(LaunchActivity mLauncherActivity) {
        this.mLauncherActivity = mLauncherActivity;
        register();
    }

    private void register(){
        mDownloadIdBroadcastReceiver = new DownloadIdBroadcastReceiver();
        mLauncherActivity.registerReceiver(mDownloadIdBroadcastReceiver, new IntentFilter("" +
                            ACTION_START_DOWNLOAD));
    }

    /**
     * 检查版本
     */
    public void checkVersion() {
        RetrofitUtil1.getService().getNewVersion3()
                .map(new Func1<NewVersion, PlatformVersion>() {
                    @Override
                    public PlatformVersion call(NewVersion newVersion) {
                        return newVersion.getAndroid();
                    }
                }).subscribe(new Action1<PlatformVersion>() {
            @Override
            public void call(PlatformVersion platformVersion) {
                LogUtil.i("" + platformVersion.toString());
                int oldVersionCode = AppUtil.getAppVersionCode(mLauncherActivity);
                //服务端返回的信息
                int newVersionCode = platformVersion.getVersionCode();
                String downloadUrl = platformVersion.getDownloadUrl();

                if(newVersionCode > oldVersionCode && !TextUtils.isEmpty(downloadUrl)){
                    mLauncherActivity.showUpdateView(platformVersion);//需要更新
                }else{
                    mLauncherActivity.go2MainActivity();
                }
            }
        }, new MErrorAction(mLauncherActivity));
    }

    /**
     * 开始更新
     */
    public void startDownload(PlatformVersion version){
        if(SDCardUtil.isSdcardEnable()){
            if(SDCardUtil.getSdcardAvailableSize() > version.getAppSize()){
                Intent intent = new Intent(mLauncherActivity, DownloadService.class);
                intent.putExtra("url", version.getDownloadUrl());
                intent.putExtra("filename", getApkName(version.getVersionCode()));
                mLauncherActivity.startService(intent);
                mLauncherActivity.showProgressDialog(version);
            }else{
                ToastUtil.showLong(mLauncherActivity, "SD内存空间不足");
            }
        }else{
            ToastUtil.showLong(mLauncherActivity, "SD卡不可用");
        }
    }

    public void startDownloadWithCheckNet(PlatformVersion version){
        if(!checkUpdateNet(version)){
            return;
        }
        startDownload(version);
    }

    /**
     * 判断传入的url,是否正在下载
     * @param url
     * @return
     */
    public boolean isDownloading(String url){
        Cursor c = ((DownloadManager) mLauncherActivity.getSystemService(DOWNLOAD_SERVICE))
                .query(new DownloadManager.Query().setFilterByStatus(DownloadManager.STATUS_RUNNING));
        if (c != null && c.moveToFirst()) {
            String tmpURI = c.getString(c.getColumnIndex(DownloadManager.COLUMN_URI));
            if (tmpURI.equals(url)) {
                if (!c.isClosed()) {
                    c.close();
                }
                return true;
            }
        }
        if (c != null && !c.isClosed()) {
            c.close();
        }
        return false;
    }

    /**
     * 判断verson版本的包，是否已经下载
     * @param versionCode
     * @return
     */
    public boolean isDownloaded(int versionCode){
        File file = new File(getAppLocalPath(versionCode));
        LogUtil.i("wangsongbin", "filePath:" + file.getPath());
        if(file.exists()){
            return true;
        }
        return false;
    }
    /**
     * 根据版本号生成本地包的路径
     * @return
     */
    public String getAppLocalPath(int versionCode){
        return new StringBuffer()
                .append(Environment.getExternalStorageDirectory().getAbsolutePath())
                .append(AppConfig.FILE_PATH)
                .append(File.separator)
                .append(getApkName(versionCode))
                .toString();
    }

    /**
     * 生成本地安装包名称
     * @param versionCode
     * @return
     */
    public String getApkName(int versionCode){
        return new StringBuilder(AppConfig.AppName)
                .append("_v" + versionCode)
                .append(".apk").toString();
    }

    /**
     * 安装更新包
     * 注意兼容Android N系列适配
     */
    public static void installApk(Context context, String localFilePath){
        LogUtil.i("wangsongbin", "安装apk:" + localFilePath);
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
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtil.showLong(context, "打开安装程序失败");
        }
    }

    public boolean checkUpdateNet(final PlatformVersion version){
        //检查下载网络
        LogUtil.i("wangsongbin", "检查网络");
        if(!NetUtil.isConnected(mLauncherActivity)){
            ToastUtil.showLong(mLauncherActivity, "当前网络不可用");
            return false;
        }
        if (!NetUtil.isWifi(mLauncherActivity)) {
            DialogUtil.showDialog(mLauncherActivity, "tips", "当前为数据网络，更新会消耗流量！",
                    "立即更新", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            startDownload(version);
                        }
                    }, "取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            return false;
        }
        return true;
    }

    public void onDestory(){
        if(mLauncherActivity == null){
            return;
        }
        if(mDownloadIdBroadcastReceiver != null){
            mLauncherActivity.unregisterReceiver(mDownloadIdBroadcastReceiver);
        }
        if(mDownloadChangeObserver != null){
            mLauncherActivity.getContentResolver().unregisterContentObserver(mDownloadChangeObserver);
        }
    }

    public class DownloadIdBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent == null){
                return;
            }
            long downloadid = intent.getLongExtra("downloadid", -1l);
            if(downloadid != -1){
                mDownloadChangeObserver = new DownloadChangeObserver(mLauncherActivity, mainHandler, downloadid);
                mLauncherActivity.getContentResolver().registerContentObserver(Uri.parse("content://downloads/"),
                        true, mDownloadChangeObserver);
            }
        }
    }

}
