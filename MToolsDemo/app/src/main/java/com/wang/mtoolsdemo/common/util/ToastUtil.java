package com.wang.mtoolsdemo.common.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by dell on 2017/10/18.
 * Toast的统一管理
 */

public class ToastUtil {

    private ToastUtil(){};
    private static final boolean isShow = true;

    public static void showShort(Context context, String msg){
        if(isShow)
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(Context context, String msg){
        if(isShow)
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

}
