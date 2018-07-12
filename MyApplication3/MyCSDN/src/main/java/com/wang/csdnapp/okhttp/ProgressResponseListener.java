package com.wang.csdnapp.okhttp;

/**
 * 响应进度回调接口，用于文件下载
 * Created by SongbinWang on 2017/7/6.
 */

public interface ProgressResponseListener {
    void onResponseProgress(long bytesRead, long contentLength, boolean done);
}
