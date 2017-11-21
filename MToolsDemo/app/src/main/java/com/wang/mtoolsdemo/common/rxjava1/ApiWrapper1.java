package com.wang.mtoolsdemo.common.rxjava1;

/**
 * Created by dell on 2017/11/21.
 */

public class ApiWrapper1 {
    static IApiService1 mIApiService1;
    public static synchronized IApiService1 getService(){
        if(mIApiService1 == null){
            mIApiService1 = RetrofitUtil1.create(IApiService1.class);
        }
        return mIApiService1;
    }
}
