package com.wang.xyhua;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.wang.xyhua.common.retrofit.ApiService;
import com.wang.xyhua.common.retrofit.ApiWraper;
import com.wang.xyhua.common.utils.LogUtil;
import com.wang.xyhua.common.volley.HttpResult;
import com.wang.xyhua.common.volley.HttpUtils;
import com.wang.xyhua.common.volley.RequestLisenter;
import com.wang.xyhua.common.volley.ResVersionBean;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private CompositeDisposable cd;

    @Bind(R.id.btn_volley)
    Button btnVolley;
    @Bind(R.id.btn_retrofit)
    Button btnRetrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_volley, R.id.btn_retrofit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_volley:
                doPostByVolley();
                break;
            case R.id.btn_retrofit:
                doPostByRetrofit();
                break;
        }
    }

    private void doPostByVolley() {
        Map<String, String> params = new HashMap<>();
        HttpUtils.getInstance(this).doPostByJson("version/newVersion", params, new RequestLisenter() {
            @Override
            public void onSuccess(HttpResult response) {
                LogUtil.i("success:" + response.toString());
                LogUtil.i("success:data:" + response.getData());
                if (response.getData() instanceof ResVersionBean) {
                    ResVersionBean bean = (ResVersionBean) response.getData();
                    LogUtil.i("success:android:" + bean.getAndroid());
                    LogUtil.i("success:ios:" + bean.getIos());
                }
            }

            @Override
            public void onFailure(String error) {
                LogUtil.i("failure:" + error);
            }
        }, ResVersionBean.class);
    }

    private void doPostByRetrofit() {
        ApiService apiService = ApiWraper.getApiService();

        Observable<HttpResult<ResVersionBean>> observable = ApiWraper.getApiService().getVersionInfo2();
        cd.add(
                observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .unsubscribeOn(Schedulers.io())
                        .subscribe(new Consumer<HttpResult>() {
                            @Override
                            public void accept(@NonNull HttpResult result) throws Exception {
                                LogUtil.i("success:" + result.toString());
                                if (result.getData() instanceof ResVersionBean) {
                                    ResVersionBean bean = (ResVersionBean) result.getData();
                                    LogUtil.i("success:android:" + bean.getAndroid());
                                    LogUtil.i("success:ios:" + bean.getIos());
                                }
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(@NonNull Throwable throwable) throws Exception {

                            }
                        }).
        );

//        Call<HttpResult> call = apiService.getVersionInfo();
//        call.enqueue(new Callback<HttpResult>() {
//            @Override
//            public void onResponse(Call<HttpResult> call, Response<HttpResult> response) {
//                LogUtil.i("success:" + response.body().toString());
//            }
//
//            @Override
//            public void onFailure(Call<HttpResult> call, Throwable t) {
//                LogUtil.i("failure:" );
//            }
//        });
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
        HttpUtils.getInstance(this).cancelByTag(this);


    }
}
