package com.example.songbinwang.littledemo.util;

import android.content.Context;

/**
 * Created by songbinwang on 2017/2/15.
 */

public class PixelUtil {

    public static int dp2px(int dp, Context context){
        float desity = context.getResources().getDisplayMetrics().density;
        return (int) (dp * desity + 0.5f);
    }

    public static int sp2px(int sp, Context context){
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * fontScale + 0.5f);
    }

}
