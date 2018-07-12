package com.wang.csdnapp.okhttp;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * Created by SongbinWang on 2017/7/6.
 */

public class ProgressRequestBody extends RequestBody{

    private RequestBody requestBody;
    private ProgressRequestListener requestListener;

    private BufferedSink bufferedSink;

    public ProgressRequestBody(RequestBody requestBody, ProgressRequestListener requestListener) {
        this.requestBody = requestBody;
        this.requestListener = requestListener;
    }

    @Override
    public MediaType contentType() {
        return requestBody.contentType();
    }

    @Override
    public long contentLength() throws IOException {
        return requestBody.contentLength();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        if(bufferedSink == null){
            //包装
            bufferedSink = Okio.buffer(sink(sink));
        }
        //写入
        requestBody.writeTo(bufferedSink);
        //必须调用flush 否侧最后一部分，不会写入
        bufferedSink.flush();
    }

    /**
     * 写入包装，回调进度接口
     * @param sink
     * @return
     */
    private Sink sink(Sink sink){
        return new ForwardingSink(sink){
            //当前写入的总字节数
            private long totalWrite = 0l;
            //整个文件的总大小
            private long contentLength = 0l;
            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                super.write(source, byteCount);
                if(contentLength == 0){
                    //获得之后，不再调用
                    contentLength = requestBody.contentLength();
                }
                //增加总写入的字节数
                totalWrite += byteCount;
                //回调
                requestListener.onRequestProgress(totalWrite,
                        contentLength, totalWrite == contentLength);

            }
        };
    }
}
