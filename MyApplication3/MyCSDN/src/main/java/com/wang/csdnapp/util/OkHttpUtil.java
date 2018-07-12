package com.wang.csdnapp.util;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Authenticator;
import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

/**
 * Created by SongbinWang on 2017/7/4.
 */

public class OkHttpUtil {

    private static final int READ_TIME_OUT = 30;//default 10
    private static final int WRITE_TIME_OUT = 30;//default 10
    private static final int CONNECTION_TIME_OUT = 30;//default 10
    private static OkHttpClient client = null;
    public static void init(Context context){
        File cacheFile = Util.getCacheFile(context, "okhttp_cache");
        Cache cache = new Cache(cacheFile, 10*1024*1024);//10MB
        client = new OkHttpClient.Builder()
                .cache(cache)
                .authenticator(new Authenticator() {
                    @Nullable
                    @Override
                    public Request authenticate(Route route, Response response) throws IOException {
                        String credential = Credentials.basic("username", "password");
                        return response.request().newBuilder().header("Authorization", credential).build();
                    }
                })
                .build();

//                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
//                .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
//                .connectTimeout(CONNECTION_TIME_OUT, TimeUnit.SECONDS)

    }

    /**
     * 同步任务
     *
     * @param request
     * @return
     * @throws IOException
     */
    public static Response execute(Request request) throws IOException {
        return client.newCall(request).execute();
    }

    /**
     * 异步执行任务
     *
     * @param request
     * @param callback
     */
    public static void enqueue(Request request, Callback callback) {
        client.newCall(request).enqueue(callback);
    }

    /**
     * 获得String 类型的返回值
     * @param url
     * @return
     * @throws IOException
     */
    public static String getStringFromServer(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = execute(request);
        if (response.isSuccessful()) {
            return response.body().string();
        }else{
            throw new IOException("Unexpected-code:" + response);
        }
    }
}
