package com.example.songbinwang.liveinhand.uitls;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;

/**
 * Created by songbinwang on 2016/10/20.
 */

public class StatusBarCompat {

    public static final int Color_Default = Color.parseColor("#20000000");

    public static void compat(Activity activity){
        compat(activity, Color_Default);
    }

    public static void compat(Activity activity, int statusColor){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            if(statusColor != -1){
                activity.getWindow().setStatusBarColor(statusColor);
            }
        }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
            if(statusColor != -1){
                ViewGroup convertView = (ViewGroup) activity.findViewById(android.R.id.content);
                int statusHeight = getStatusBarHeight(activity);
                View statusView= new View(activity);
                statusView.setBackgroundColor(statusColor);
                ViewGroup.LayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusHeight);
                convertView.addView(statusView, lp);
            }
        }
    }

    /**
     * 获取状态栏的高度
     * @return
     */
    protected static int getStatusBarHeight(Context context){
        try
        {
            Class<?> c=Class.forName("com.android.internal.R$dimen");
            Object obj=c.newInstance();
            Field field=c.getField("status_bar_height");
            int x=Integer.parseInt(field.get(obj).toString());
            return  context.getResources().getDimensionPixelSize(x);
        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }
}
