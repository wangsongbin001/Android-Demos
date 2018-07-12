package com.example.songbinwang.liveinhand.uitls;

import android.util.Log;

/**
 * Created by songbinwang on 2016/6/17.
 */
public class LogUtils {

    private static final String TAG = "loguitls";

    public static void i(String msg){
        if(AppConstant.isDebug){
            Log.i(TAG, msg);
        }
    }

    public static void i(String tag, String msg){
        if (AppConstant.isDebug){
            Log.i(tag, msg);
        }
    }
}
