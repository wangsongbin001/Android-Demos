package com.wang.mtoolsdemo.common.rxjava1;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wang.mtoolsdemo.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

/**
 * Created by dell on 2017/11/21.
 */

public class RetrofitUtil1 {

    private static String BASE_URL = "http://10.138.60.131:10000/";

    private static Retrofit mInstance;
    private static IApiService1 mService;

    private RetrofitUtil1() {
    }

    public static IApiService1 getService() {
        if (mService == null) {
            synchronized (RetrofitUtil1.class) {
                if (mService == null) {
                    mService = getRetrofit().create(IApiService1.class);
                }
            }
        }
        return mService;
    }

    public static Retrofit getRetrofit() {
        if (mInstance == null) {
            mInstance = buildRetrofit();
        }
        return mInstance;
    }

    private static Retrofit buildRetrofit() {
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            //Log信息拦截器，DEBUG，打印log信息
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            //设置Log
            okHttpBuilder.addInterceptor(loggingInterceptor);
        }
        OkHttpClient client = okHttpBuilder
                .addInterceptor(new CommonInterceptor())//添加共同的请求头
                .connectTimeout(30, TimeUnit.SECONDS)//设置连接超时
                .readTimeout(60, TimeUnit.SECONDS)
                .build();
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(new ObserveronMainCallAdapterFactory())//serverOn(MainThread)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))//subscribeOn(io)
                .addConverterFactory(GsonDataMapFactory.create(gson))//自定义过滤，
                .addConverterFactory(GsonConverterFactory.create(gson))//使用Gson解析
                .client(client)//okHttpClient
                .build();
    }

}
