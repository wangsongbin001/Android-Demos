package com.wang.mtoolsdemo.common.download;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.webkit.MimeTypeMap;

import com.wang.mtoolsdemo.common.util.SPUtil;

import java.io.File;

/**
 * Created by dell on 2017/11/24.
 */

public class DownloadService extends Service {

    DownloadManager mDownloadManager;
    OnCompleteReceiver mOnCompleteReceiver;

    private static final String DownLoadPath = "/CSDNApp/apk";

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
            Environment.getExternalStoragePublicDirectory("" + DownLoadPath).mkdirs();
            download(url, fileName);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public void download(String url, String fileName){
        //注册监听
//        getContentResolver().registerContentObserver();

        //正式开始下载
        long downloadId = mDownloadManager.enqueue(createRequest(url, fileName));
        SPUtil.put(getApplicationContext(), "downloadid", downloadId);

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

        File fileDir = Environment.getExternalStoragePublicDirectory(DownLoadPath);
        if (fileDir.mkdirs() || fileDir.isDirectory()) {
            request.setDestinationInExternalPublicDir(DownLoadPath, fileName);
        }
        // request.setDestinationInExternalPublicDir(AppConfig.FILEPATH, fileName);
        return request;
    }
    @Nullable
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

        }
    }

    /**
     * 下载通知点击的监听器
     */
    BroadcastReceiver onNotificationClick = new BroadcastReceiver() {
        @Override
        public void onReceive(Context ctxt, Intent intent) {

        }
    };
}
