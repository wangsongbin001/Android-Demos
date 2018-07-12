package com.wang.csdnapp.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.wang.csdnapp.CSDNApp;

import java.lang.reflect.Field;

/**
 * Created by songbinwang on 2016/10/24.
 */

public class ScreenUtil {
    private static final String TAG = "ScreenUtil";
    /**
     * 获取状态栏的高度
     *
     * @return
     */
    public static float getStatusBarHeight(Context context) {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return context.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取屏幕高度
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获取屏幕宽度
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * sp 转换成px
     * @param mContext
     * @param sp
     * @return
     */
    public static float sp2px(Context mContext, float sp){
        float scaleFont = mContext.getResources().getDisplayMetrics().scaledDensity;
        LogUtil.i(TAG, "scaleFont:"+ scaleFont + ",sp:"+ sp);
        return sp * scaleFont;
    }

    /**
     * sp 转换成px
     * @param mContext
     * @param dp
     * @return
     */
    public static float dp2px(Context mContext, float dp){
        float density = mContext.getResources().getDisplayMetrics().density;
        LogUtil.i(TAG, "density:"+ density + ",dp:"+ dp);
        return dp * density;
    }

    public static float dp2px(float dp){
        LogUtil.i(TAG, "density:"+ CSDNApp.density + ",dp:"+ dp);
        return dp * CSDNApp.density;
    }
}
