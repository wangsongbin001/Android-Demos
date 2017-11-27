package com.wang.mtoolsdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.wang.mtoolsdemo.common.bean.NewVersion;
import com.wang.mtoolsdemo.common.bean.PlatformVersion;
import com.wang.mtoolsdemo.common.bean.ResponseBean;
import com.wang.mtoolsdemo.common.excep.ApiException;
import com.wang.mtoolsdemo.common.rxjava1.RetrofitUtil1;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Subscriber;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;


/**
 * Created by dell on 2017/11/16.
 */

public class FiveActivity extends AppCompatActivity {

    @Bind(R.id.iv_content)
    ImageView ivContent;
    @Bind(R.id.fl_container)
    FrameLayout flContainer;
    private Activity mActivity;

    CompositeSubscription compositeSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_5);
        ButterKnife.bind(this);
        mActivity = this;
        compositeSubscription = new CompositeSubscription();

//        Log.i("wangsongbin", "" + Thread.currentThread());
//        RetrofitUtil1.getService().getNewVersion3()
//                .map(new Func1<NewVersion, PlatformVersion>() {
//                    @Override
//                    public PlatformVersion call(NewVersion newVersion) {
//                        Log.i("wangsongbin", "" + Thread.currentThread());
//                        return newVersion.getAndroid();
//                    }
//                })
//                .subscribe(new Subscriber<PlatformVersion>() {
//                    @Override
//                    public void onCompleted() {
//                        Log.i("wangsongbin", "onCompleted");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.i("wangsongbin", "onError" + e.getMessage());
//                        if (e instanceof ApiException) {
//                        }
//                    }
//
//                    @Override
//                    public void onNext(PlatformVersion responseBean) {
//                        Log.i("wangsongbin", "" + Thread.currentThread());
//                        Log.i("wangsongbin", responseBean.toString());
//                    }
//                });
//        Call<ResponseBean> call = apiService1.getNewVersion2();
//        call.enqueue(new Callback<ResponseBean>() {
//            @Override
//            public void onResponse(Call<ResponseBean> call, Response<ResponseBean> response) {
//                Log.i("wangsongbin", "" + Thread.currentThread());
//                Log.i("wangsongbin", response.toString());
//                Log.i("wangsongbin", response.body().toString());
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBean> call, Throwable t) {
//
//            }
//        });
//        Call<ResponseBean1> call = apiService1.getNewVersion1();
//        call.enqueue(new Callback<ResponseBean1>() {
//            @Override
//            public void onResponse(Call<ResponseBean1> call, Response<ResponseBean1> response) {
//                Log.i("wangsongbin", "" + Thread.currentThread());
//                Log.i("wangsongbin", response.toString());
//                Log.i("wangsongbin", response.body().toString());
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBean1> call, Throwable t) {
//
//            }
//        });
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://10.138.60.131:10000/app/version/newVersion")
                .build();
//        Response response = client.newCall(request).execute();
//        Log.i("wangsongbin", "" + response.body().string());
        Log.i("wangsongbin", "" + Thread.currentThread());
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("wangsongbin", "" + Thread.currentThread());
                Log.i("wangsongbin", response.body().string());
                //body只能被消费一次，多次消费抛异常
//                Log.i("wangsongbin", response.body().string());
            }
        });

        if (true) {
            return;
        }
//        Gson gson = new GsonBuilder()
//                .excludeFieldsWithoutExposeAnnotation()
//                .create();
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://10.138.60.131:10000/")
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .client(client)
//                .build();
//
//        IApiService1 apiService = retrofit.create(IApiService1.class);
//        apiService.getNewVersion().subscribe(new Subscriber<ResponseBean>() {
//            @Override
//            public void onCompleted() {
//                Log.i("wangsongbin", "onCompleted");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.i("wangsongbin", "onError" + e.getMessage());
//            }
//
//            @Override
//            public void onNext(ResponseBean responseBean) {
//                Log.i("wangsongbin", "onNext " + responseBean.toString());
//            }
//        });

//        FragmentManager fm = getSupportFragmentManager();
//        fm.beginTransaction()
//                .replace(0, null)
//                .addToBackStack("")
//                .commit();
        //rxjava测试
        mActivity = mActivity;
//        compositeSubscription.add(Observable.create(new Observable.OnSubscribe<String>() {
//            @Override
//            public void call(Subscriber<? super String> subscriber) {
//                Log.i("wangsongbin", "call");
////                Drawable drawable = ContextCompat.getDrawable(mActivity, R.mipmap.huge);
//                subscriber.onNext("http://h.hiphotos.baidu.com/image/crop%3D0%2C0%2C510%2C446/sign=3690ea2a33dbb6fd3114bf6634148728/80cb39dbb6fd5266b75c62c5a118972bd50736ef.jpg");
//                subscriber.onCompleted();
//            }})
//         .subscribeOn(Schedulers.io())//subscribe执行所在的线程，即call方法所在的线程
//         .map(new Func1<String, Drawable>() {
//                    @Override
//                    public Drawable call(String s) {
//                        try {
//                            Drawable drawable = Drawable.createFromStream(new URL(s).openStream(), "src");
//                            return drawable;
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        return null;
//                    }
//                })
//         .observeOn(AndroidSchedulers.mainThread())
//         .subscribe(new Observer<Drawable>() {
//            @Override
//            public void onCompleted() {
//                Log.i("wangsongbin", "onCompleted");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.i("wangsongbin", "throwable:" + e.getMessage());
//            }
//
//            @Override
//            public void onNext(Drawable drawable) {
//                Log.i("wangsongbin", "onNext");
//                ivContent.setImageDrawable(drawable);
//            }
//        }));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeSubscription.unsubscribe();
    }
}

