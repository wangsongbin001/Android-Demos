package com.wang.csdnapp.okhttp;

import android.support.annotation.Nullable;

import com.wang.csdnapp.util.LogUtil;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * Created by SongbinWang on 2017/7/6.
 */

public class ProgressResponseBody extends ResponseBody{
    private static final String TAG = "ProgressResponseBody";
    //待包装的响应
    private ResponseBody responseBody;
    private ProgressResponseListener responseListener;

    private BufferedSource bufferedSource;

    public ProgressResponseBody(ResponseBody responseBody, ProgressResponseListener responseListener){
        this.responseBody = responseBody;
        this.responseListener = responseListener;
    }

    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if(bufferedSource == null){
            //包装
            bufferedSource = Okio.buffer(source(responseBody.source()));
        }
        return bufferedSource;
    }

    private Source source(Source source){
        return new ForwardingSource(source) {
            long totalByteRead = 0l;
            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long byteRead = super.read(sink, byteCount);
                //增加当前读取的字节，如果读取完成，byteRead = -1
                totalByteRead += (byteRead == -1 ? 0 : byteRead);
                //回调，如果responseBody.contentLength 不知道 返回-1
                LogUtil.i(TAG, "totalByteRead:" + totalByteRead + ",byteRead:" + byteRead
                        + ",contentLength:" + responseBody.contentLength());
                responseListener.onResponseProgress(totalByteRead,
                        responseBody.contentLength(), byteRead == -1);
                return byteRead;
            }
        };
    }
}
