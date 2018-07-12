package com.wang.csdnapp.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by xiaosongshu on 2017/6/22.
 */

public class Util {

    private static final String TAG = "Util";

    /**
     * 判断这张图片的缓存是不是存在
     * @param url
     * @return
     */
    public static File getCacheFile(Context context,String url){
        String key = StringUtil.hashKeyFromUrl(url);
        return new File(getDiskCacheDir(context, url), key);
    }

    /**
     * 获取磁盘缓存的路径
     *
     * @param context
     * @param uniqueName
     * @return
     */
    public static File getDiskCacheDir(Context context, String uniqueName) {
        //获取磁盘缓存路径
        boolean externalStorageAvailable =
                Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                        && !Environment.isExternalStorageRemovable();
        String cachePath = "";
        if (externalStorageAvailable) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    public static Bitmap getBitmapFromCache(Context context, String url){
        if(TextUtils.isEmpty(url)){
            LogUtil.i(TAG, "url is empty");
            return null;
        }
        String key = StringUtil.hashKeyFromUrl(url);
        File file = getCacheFile(context, key);
        if(!file.exists()){
            LogUtil.i(TAG, "cache unexsit");
            return null;
        }
        LogUtil.i(TAG, "cache exsit");
        return BitmapFactory.decodeFile(file.getAbsolutePath());
    }

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
