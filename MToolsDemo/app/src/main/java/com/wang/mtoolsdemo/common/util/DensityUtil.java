package com.wang.mtoolsdemo.common.util;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by dell on 2017/10/18.
 * 单位转换
 */

public class DensityUtil {

    public static int dp2px(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }

    public static int sp2px(Context context, int sp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                context.getResources().getDisplayMetrics());
    }

    public static int px2dp(Context context, int px){
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (px/density);
    }

    public static int px2sp(Context context, int px){
        float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (px/scale);
    }
}
