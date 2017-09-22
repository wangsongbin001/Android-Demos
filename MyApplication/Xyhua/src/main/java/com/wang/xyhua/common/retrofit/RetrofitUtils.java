package com.wang.xyhua.common.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wang.xyhua.common.volley.RequestLisenter;

import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dell on 2017/9/22.
 */

public class RetrofitUtils {
    private static final String BaseUrl = "http://10.138.60.131:10000/app/";
    private static Retrofit retrofit;

    public static Retrofit getInstance(){
        if(retrofit == null){
            synchronized (RetrofitUtils.class){
                if(retrofit == null){
                    retrofit = buildRetrofit();
                }
            }
        }
        return retrofit;
    }

    private static Retrofit buildRetrofit(){
        Gson gson = new GsonBuilder().excludeFieldsWithModifiers().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit;
    }

}
