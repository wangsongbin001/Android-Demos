package com.wang.csdnapp.util;

import android.util.Log;

import com.wang.csdnapp.Constant;

/**
 * Created by songbinwang on 2017/2/25.
 */

public class LogUtil {

    public static void i(String tag, String msg){
        if(Constant.IS_DEBUG){
            Log.i(tag, "" + msg);
        }
    }
}
