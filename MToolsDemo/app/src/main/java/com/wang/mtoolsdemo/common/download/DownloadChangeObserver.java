package com.wang.mtoolsdemo.common.download;

import android.app.DownloadManager;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;

import com.wang.mtoolsdemo.common.bean.ProgressBean;
import com.wang.mtoolsdemo.common.util.LogUtil;
import com.wang.mtoolsdemo.common.util.SPUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by dell on 2017/11/27.
 */

public class DownloadChangeObserver extends ContentObserver{

    private Handler mHandler;
    private ScheduledExecutorService mExecutorService;
    private DownloadManager downloadManager;
    private Context mContext;

    public DownloadChangeObserver(Context context, Handler handler) {
        super(handler);
        this.mHandler = handler;
        mExecutorService = Executors.newSingleThreadScheduledExecutor();
        downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        mContext = context;
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        super.onChange(selfChange, uri);
        LogUtil.i("onChange1");
        //selfChange的值得意义不大，一般情况下，都是返回false;
        //开启线程，检索当前的下载的进度
        mExecutorService.scheduleAtFixedRate(progressRunnable, 0, 2, TimeUnit.SECONDS);
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        LogUtil.i("onChange2");
//        //selfChange的值得意义不大，一般情况下，都是返回false;
//        //开启线程，检索当前的下载的进度
//        mExecutorService.scheduleAtFixedRate(progressRunnable, 0, 2, TimeUnit.SECONDS);
    }

    private Runnable progressRunnable = new Runnable() {
        @Override
        public void run() {
            long downloadId = (long) SPUtil.get(mContext, "downloadid", -1l);
            LogUtil.i("downloadId:" + downloadId);
            if(downloadId != -1){
                getBytesAndStatus(downloadId);
            }
        }
    };

    private int[] getBytesAndStatus(long downloadId) {
        int[] bytesAndStatus = new int[]{
                -1, -1, 0
        };
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadId);
        Cursor cursor = null;
        try {
            cursor = downloadManager.query(query);
            if (cursor != null && cursor.moveToFirst()) {
                //已经下载文件大小
                bytesAndStatus[0] = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                //下载文件的总大小
                bytesAndStatus[1] = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                //下载状态
                bytesAndStatus[2] = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));

                Message msg = mHandler.obtainMessage();
                ProgressBean progressBean = new ProgressBean();
                progressBean.setProgress(bytesAndStatus[0]);
                progressBean.setTotal(bytesAndStatus[1]);
                progressBean.setStatus(bytesAndStatus[2]);
                msg.obj = progressBean;
                mHandler.sendMessage(msg);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return bytesAndStatus;
    }
}
