package com.wang.mtoolsdemo.common.rxjava1;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by dell on 2017/11/22.
 */

public class ObserveronMainCallAdapterFactory extends CallAdapter.Factory{

    private Scheduler mScheduler;
    public ObserveronMainCallAdapterFactory(){
        mScheduler = AndroidSchedulers.mainThread();
    }

    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        if(getRawType(returnType) != Observable.class){
            return null;
        }
        final CallAdapter<Object,Observable<?>> callAdapter =
                (CallAdapter<Object, Observable<?>>) retrofit.nextCallAdapter(this, returnType, annotations);
        return new CallAdapter<Object, Object>() {
            @Override
            public Type responseType() {
                return callAdapter.responseType();
            }

            @Override
            public Object adapt(Call<Object> call) {
                Observable o = callAdapter.adapt(call);
                return o.observeOn(mScheduler);
            }
        };
    }
}
