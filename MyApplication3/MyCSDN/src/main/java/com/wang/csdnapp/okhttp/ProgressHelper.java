package com.wang.csdnapp.okhttp;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by SongbinWang on 2017/7/6.
 */

public class ProgressHelper {

    /**
     * 包装OkHttpClient用于下载文件
     * @param responseListener
     * @return
     */
    public static OkHttpClient addProgressResponseListener(
            final ProgressResponseListener responseListener){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60*60, TimeUnit.SECONDS)
                .writeTimeout(60*60, TimeUnit.SECONDS)
                //添加拦截器
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        //拦截
                        Response originalResponse = chain.proceed(chain.request());
                        //包装响应体并返回
                        return originalResponse.newBuilder()
                                .body(new ProgressResponseBody(originalResponse.body(), responseListener))
                                .build();
                    }
                })
                .build();
        return okHttpClient;
    }

    /**
     * 包装RequestBody用于上传文件
     * @param body
     * @param requestListener
     * @return
     */
    public static ProgressRequestBody addProgressRequestListener(
            RequestBody body, ProgressRequestListener requestListener){
        return new ProgressRequestBody(body, requestListener);
    }
}
