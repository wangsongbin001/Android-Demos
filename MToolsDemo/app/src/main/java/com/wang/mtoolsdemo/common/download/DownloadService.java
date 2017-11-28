package com.wang.mtoolsdemo.common.download;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.webkit.MimeTypeMap;

import com.wang.mtoolsdemo.common.AppConfig;
import com.wang.mtoolsdemo.common.util.LogUtil;
import com.wang.mtoolsdemo.common.util.SPUtil;
import com.wang.mtoolsdemo.common.util.ToastUtil;
import com.wang.mtoolsdemo.common.util.VerifyUtil;

import java.io.File;

/**
 * Created by dell on 2017/11/24.
 */

public class DownloadService extends Service {

    DownloadManager mDownloadManager;
    OnCompleteReceiver mOnCompleteReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        mDownloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        mOnCompleteReceiver = new OnCompleteReceiver();
        //注册监听
        registerReceiver(mOnCompleteReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        registerReceiver(onNotificationClick, new IntentFilter(DownloadManager.ACTION_NOTIFICATION_CLICKED));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.i("wangsongbin", "onDestroy");
        //注销监听
        if (mOnCompleteReceiver != null) {
            unregisterReceiver(mOnCompleteReceiver);
        }
        if (onNotificationClick != null) {
            unregisterReceiver(onNotificationClick);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent != null){
            String url = intent.getStringExtra("url");
            String fileName = intent.getStringExtra("filename");
            Environment.getExternalStoragePublicDirectory("" + AppConfig.FILE_PATH).mkdirs();
            download(url, fileName);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public void download(String url, String fileName){
        LogUtil.i("wangsongbin", "开始下载");
        //注册监听
//        mDownloadChangeObserver = new DownloadChangeObserver(this, mainHandler);
//        getContentResolver().registerContentObserver(Uri.parse("content://downloads/"), true, mDownloadChangeObserver);

        //校验下载地址是否正确
        if(!VerifyUtil.isUrl(url)){
            ToastUtil.showLong(getApplicationContext(), "下载地址不正确");
            return;
        }

        //校验下载地址是否正在下载
        if(isDownloading(url)){
            return;
        }

        //正式开始下载
        long downloadId = mDownloadManager.enqueue(createRequest(url, fileName));
        SPUtil.put(getApplicationContext(), "downloadid", downloadId);

        //发送广播注册观察者
        Intent intent = new Intent(LaunchHelper.ACTION_START_DOWNLOAD);
        intent.putExtra("downloadid", downloadId);
        sendBroadcast(intent);
    }

    /**
     * 创建请求对象
     * @param url 下载地址
     * @param fileName 文件名
     * @return
     */
    private DownloadManager.Request createRequest(String url, String fileName){
        Uri uri = Uri.parse(url);
        //getContentResolver().registerContentObserver(uri,true,new DownloadObserver(handler,this,downloadid));
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setAllowedOverRoaming(true);
        // 根据文件后缀设置mime
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        int startIndex = fileName.lastIndexOf(".");
        String tmpMimeString = fileName.substring(startIndex + 1).toLowerCase();
        String mimeString = mimeTypeMap.getMimeTypeFromExtension(tmpMimeString);
        request.setMimeType(mimeString);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setTitle(fileName);
        request.addRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        request.setDescription("更新下载");

        File fileDir = Environment.getExternalStoragePublicDirectory(AppConfig.FILE_PATH);
        if (fileDir.mkdirs() || fileDir.isDirectory()) {
            request.setDestinationInExternalPublicDir(AppConfig.FILE_PATH, fileName);
        }
        return request;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 下载完成
     */
    public class OnCompleteReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            LogUtil.i("wangsongbin", "下载完成");
//            ToastUtil.showLong(context.getApplicationContext(), "下载完成！");
            //安装应用
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1l);
            long downloadId = (long) SPUtil.get(context, "downloadid", -1l);
            if(id != downloadId){
                return;
            }
            Cursor cursor = mDownloadManager.query(new DownloadManager.Query().setFilterById(id));
            if(cursor.moveToFirst()){
                int columIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
                if(cursor.getInt(columIndex) == DownloadManager.STATUS_SUCCESSFUL){
                    String localFileUri = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                    SPUtil.put(context, "downloadid", -1l);
                    //安装
                    LaunchHelper.installApk(context, localFileUri.replaceFirst("file://", ""));
                }
            }
            cursor.close();
            stopSelf();
        }
    }

    /**
     * 下载通知点击的监听器
     */
    BroadcastReceiver onNotificationClick = new BroadcastReceiver() {
        @Override
        public void onReceive(Context ctxt, Intent intent) {
            LogUtil.i("wangsongbin", "点击通知");
            showDownloadManagerView();
        }
    };

    /** 跳转到系统下载界面 */
    private void showDownloadManagerView() {
        Intent intent = new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /** 判断传入的url是否正在下载 */
    private boolean isDownloading(String url) {
        Cursor c = mDownloadManager.query(new DownloadManager.Query().setFilterByStatus(DownloadManager.STATUS_RUNNING));
        if (c != null && c.moveToFirst()) {
            String tmpURI = c.getString(c.getColumnIndex(DownloadManager.COLUMN_URI));
            if (tmpURI.equals(url)){
                if(!c.isClosed()){
                    c.close();
                }
                return true;
            }
        }
        if(c != null && !c.isClosed()){
            c.close();
        }
        return false;
    }
}
