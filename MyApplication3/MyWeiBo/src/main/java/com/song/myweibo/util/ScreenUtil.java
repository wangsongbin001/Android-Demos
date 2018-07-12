package com.song.myweibo.util;

import android.content.Context;

import java.lang.reflect.Field;

/**
 * Created by songbinwang on 2016/10/24.
 */

public class ScreenUtil {
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
}
