package com.wang.mtoolsdemo.common.download;

import android.app.DownloadManager;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Handler;
import android.os.Message;

import com.wang.mtoolsdemo.common.bean.ProgressBean;
import com.wang.mtoolsdemo.common.util.LogUtil;

/**
 * Created by dell on 2017/11/27.
 */

public class DownloadChangeObserver extends ContentObserver{

    private Handler mHandler;
    private DownloadManager downloadManager;
    private long downloadId;

    public DownloadChangeObserver(Context context, Handler handler, long downloadId) {
        super(handler);
        downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        this.mHandler = handler;
        this.downloadId = downloadId;
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        LogUtil.i("wangsongbin", "onChange");
        getBytesAndStatus(downloadId);
    }

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
                msg.what = 1001;
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
