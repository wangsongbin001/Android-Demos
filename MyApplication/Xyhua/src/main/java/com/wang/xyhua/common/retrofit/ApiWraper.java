package com.wang.xyhua.common.retrofit;

/**
 * Created by dell on 2017/9/22.
 */

public class ApiWraper {

    private static ApiService mInstance;

    public static ApiService getApiService(){
        if(mInstance == null){
            mInstance = RetrofitUtils.getInstance().create(ApiService.class);
        }
        return mInstance;
    }
}
