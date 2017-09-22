package com.wang.xyhua.common.retrofit;


import com.wang.xyhua.common.volley.HttpResult;
import com.wang.xyhua.common.volley.ResVersionBean;

import io.reactivex.Observable;
import io.reactivex.internal.operators.observable.ObservableSubscribeOn;
import retrofit2.Call;
import retrofit2.http.POST;

/**
 * Created by dell on 2017/9/22.
 */

public interface ApiService {

    @POST("version/newVersion")
    Call<HttpResult> getVersionInfo();
    @POST("version/newVersion")
    Observable<HttpResult<ResVersionBean>> getVersionInfo2();


}
