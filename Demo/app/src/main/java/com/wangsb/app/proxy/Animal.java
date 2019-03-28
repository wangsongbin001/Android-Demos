package com.wangsb.app.proxy;

import android.util.Log;

/**
 * Created by xiaosongshu on 2019/3/24.
 */

public class Animal implements Fly, Run{

    public static final String TAG = "proxy";

    @Override
    public void run() {
        Log.i(TAG, "animal run");
    }

    @Override
    public void fly() {
       Log.i(TAG, "animal fly");
    }
}
