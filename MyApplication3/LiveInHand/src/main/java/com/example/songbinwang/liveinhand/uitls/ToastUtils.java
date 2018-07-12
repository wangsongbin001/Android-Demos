package com.example.songbinwang.liveinhand.uitls;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by songbinwang on 2016/6/17.
 */
public class ToastUtils {

    public static void showShort(Context context, String msg){
        if (AppConstant.isDebug){
            Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
        }
    }

    public static void showLong(Context context, String msg){
        if (AppConstant.isDebug){
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        }
    }

    public static void show(Context context, String msg, int duration){
        if (AppConstant.isDebug){
            Toast.makeText(context, msg, duration).show();
        }
    }
}
