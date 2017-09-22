package com.wang.xyhua.common.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by dell on 2017/9/22.
 */

public class ToastUtil {

    public static void toast(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}
