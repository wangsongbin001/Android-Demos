package com.wang.mtoolsdemo.common.rxjava1;

import android.app.Activity;

import com.wang.mtoolsdemo.common.excep.ApiException;
import com.wang.mtoolsdemo.common.util.ToastUtil;

import java.lang.ref.SoftReference;

import rx.functions.Action1;

/**
 * Created by dell on 2017/11/27.
 */

public class MErrorAction implements Action1<Throwable>{

    SoftReference<Activity> mActivity = null;
    public MErrorAction(Activity activity){
        mActivity = new SoftReference<Activity>(activity);
    }

    @Override
    public void call(Throwable e) {
        if(e instanceof ApiException){

        }else{
            if(mActivity.get() != null){
                ToastUtil.showLong(mActivity.get(), "其他错误");
            }
        }
    }
}
