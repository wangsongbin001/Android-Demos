package com.example.songbinwang.littledemo.util;

import android.util.Log;

/**
 * Created by songbinwang on 2017/2/15.
 */

public class LogUtil {

    public static void i(String msg){
        if(!Constant.isPublished){
            Log.i("litleDemo", msg);
        }
    }

    public static void i(String tag, String msg){
        if(!Constant.isPublished){
            Log.i(tag, msg);
        }
    }
}
