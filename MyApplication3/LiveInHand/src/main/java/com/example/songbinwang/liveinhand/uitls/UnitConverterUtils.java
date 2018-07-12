package com.example.songbinwang.liveinhand.uitls;

import android.content.Context;

/**
 * Created by songbinwang on 2016/6/21.
 */
public class UnitConverterUtils {

    public static int sp2px(Context context, int value){
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (value * fontScale + 0.5f);
    }

    public static int px2sp(Context context, int value){
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (value/fontScale + 0.5f);
    }

    public static int dp2px(Context context, int value){
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (value * density + 0.5f);
    }

    public static int px2dp(Context context, int value){
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (value / density + 0.5f);
    }
}
