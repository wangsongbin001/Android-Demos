package com.wang.csdnapp.okhttp;

/**
 * 请求进度回调接口，用于文件上传
 * Created by SongbinWang on 2017/7/6.
 */

public interface ProgressRequestListener {
    void onRequestProgress(long bytesWritten, long contentLength, boolean done);
}
