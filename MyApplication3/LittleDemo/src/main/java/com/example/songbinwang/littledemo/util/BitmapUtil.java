package com.example.songbinwang.littledemo.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

/**
 * Created by songbinwang on 2017/2/16.
 */

public class BitmapUtil {

    /**
     * 根据Drawaable转Bitmap
     * @param drawable
     * @return
     */
    public static Bitmap drawable2Bitmap(Drawable drawable){
        Bitmap bitmap = Bitmap.createBitmap(drawable.getBounds().width(), drawable.getBounds().height(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.draw(canvas);
        return bitmap;
    }
}
