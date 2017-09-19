package com.wang.xyhua.common.utils;

import android.util.Log;

import com.wang.xyhua.common.global.MConstant;

/**
 * Created by dell on 2017/9/19.
 */

public class LogUtil {
    private static final String DEFAULT_TAG = "Xyhua";

    public static void i(String tag, String msg){
        if(MConstant.isDebug){
            Log.i(tag, msg);
        }
    }

    public static void i(String msg){
        i(DEFAULT_TAG, msg);
    }
}
