package com.wang.mtoolsdemo.common.rxjava1;

import com.wang.mtoolsdemo.common.bean.NewVersion;
import com.wang.mtoolsdemo.common.bean.ResponseBean;
import com.wang.mtoolsdemo.common.bean.ResponseBean1;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by dell on 2017/11/21.
 */

public interface IApiService1 {

    @POST("app/version/newVersion")
    Observable<ResponseBean> getNewVersion();

    @GET("app/version/newVersion")
    Call<ResponseBean1> getNewVersion1();

    @GET("app/version/newVersion")
    Call<ResponseBean> getNewVersion2();

    @POST("app/version/newVersion")
    Observable<NewVersion> getNewVersion3();

}
