package com.wang.mtoolsdemo.common.util;

import android.util.Log;

import com.wang.mtoolsdemo.BuildConfig;

/**
 * Created by dell on 2017/10/17.
 */

public class LogUtil {
    private static final String defutlt_tag = "mtoolsdemo";

    public static void i(String msg){
        i(defutlt_tag, msg);
    }

    public static void i(String tag, String msg){
        if(!"release".equals(BuildConfig.BUILD_TYPE)){
            Log.i(tag, msg);
        }
    }

    public static void i(Class clazz, String msg){
        if(clazz == null){
            return;
        }
        String tag = clazz.getSimpleName();
        i(tag, msg);
    }

    public static void d(String msg){
        d(defutlt_tag, msg);
    }

    public static void d(String tag, String msg){
        if(!"release".equals(BuildConfig.BUILD_TYPE)){
            Log.i(tag, msg);
        }
    }

    public static void d(Class clazz, String msg){
        if(clazz == null){
            return;
        }
        String tag = clazz.getSimpleName();
        d(tag, msg);
    }

}
