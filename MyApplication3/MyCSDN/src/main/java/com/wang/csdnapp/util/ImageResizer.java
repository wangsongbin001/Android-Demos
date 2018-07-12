package com.wang.csdnapp.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.FileDescriptor;
import java.io.FileInputStream;

/**
 * Created by SongbinWang on 2017/3/3.
 * 图片的搞笑加载功能。
 */

public class ImageResizer {
    public static final String tag = "ImageResizer";

    public ImageResizer() {
    }

    public Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        // 第一次加载只获取图片的信息
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        // 计算inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // 正式加载Bitmap
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    /**
     * 不能直接对文件流做如下操作，因为两次的decodeStream会影响文件的位置属性
     * 导致第二次decodeStream的返回为null,所以中转一下
     * FileDescriptor fileDescriptor = FileInputStream.getFD();
     */
    public Bitmap decodeSampledBitmapFromFileDescriptor(FileDescriptor descriptor, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        //从文件信息中提取高宽信息
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(descriptor, null, options);
        //计算inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        //正式加载Bitmap
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFileDescriptor(descriptor, null, options);
    }


    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        if (reqWidth == 0 || reqHeight == 0) {
            return 1;
        }
        final int width = options.outWidth;
        final int height = options.outHeight;
        Log.i(tag, "outOption: height:" + height + ",widht:" + width);
        int inSampleSize = 1;
        if (width > reqWidth && height > reqHeight) {
            int halfWith = options.outWidth / 2;
            int halfHeight = options.outHeight / 2;

            while (halfWith / inSampleSize >= reqWidth && halfHeight / inSampleSize >= reqHeight) {
                inSampleSize *= 2;
            }
        }
        Log.i(tag, "sampleSize:" + inSampleSize);
        return inSampleSize;
    }


}
