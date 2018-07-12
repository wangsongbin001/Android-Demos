package com.song.myweibo.util;

import android.util.Log;

import com.song.myweibo.data.AppConstant;

/**
 * Created by songbinwang on 2016/10/25.
 */

public class LogUtil {

    public static void i(String tag, String content) {
        if (AppConstant.isDebug) {
            Log.i(tag, content);
        }
    }

    public static void d(String tag, String content) {
        if (AppConstant.isDebug) {
            Log.d(tag, content);
        }
    }

    public static void w(String tag, String content) {
        if (AppConstant.isDebug) {
            Log.w(tag, content);
        }
    }

    public static void e(String tag, String content) {
        if (AppConstant.isDebug) {
            Log.e(tag, content);
        }
    }

}
