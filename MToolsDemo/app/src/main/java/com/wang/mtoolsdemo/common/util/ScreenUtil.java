package com.wang.mtoolsdemo.common.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by dell on 2017/10/18.
 */

public class ScreenUtil {

    /**
     * 获得屏幕的宽度
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
     * 获得屏幕的高度
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
     * 获得状态栏的高度，通过反射获得status_bar_height 对应的资源id
     * @param context
     * @return dp值
     */
    public static int getStatusBarHeight(Context context){
        int statusHeight= -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object obj = clazz.newInstance();
            int heightId = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(obj).toString());
            statusHeight = context.getResources().getDimensionPixelSize(heightId);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 获取屏幕的截图
     * @return
     */
    public static Bitmap snapShotWithStatusBar(Activity activity){
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        int w = getScreenWidth(activity);
        int h = getScreenHeight(activity);
        Bitmap bmp = Bitmap.createBitmap(view.getDrawingCache(), 0, 0, w, h);
        view.destroyDrawingCache();
        return bmp;
    }

    /**
     * 获取屏幕的截图，不带状态栏
     * @return
     */
    public static Bitmap snapShotWithoutStatusBar(Activity activity){
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        int w = getScreenWidth(activity);
        int h = getScreenHeight(activity);
        Bitmap bmp = Bitmap.createBitmap(view.getDrawingCache(), 0, getStatusBarHeight(activity), w, h);
        view.destroyDrawingCache();
        return bmp;
    }
}
