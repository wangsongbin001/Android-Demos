package com.example.songbinwang.liveinhand.uitls;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by songbinwang on 2016/6/28.
 */
public class VideoImageLoaderHelper {

    private static LruCache<String,Bitmap> mLruCache = null;

    public static synchronized LruCache<String, Bitmap> createCache(){
        if (mLruCache == null) {
            long maxMemory = Runtime.getRuntime().maxMemory();
            int cacheSize = (int) (maxMemory/8);
            mLruCache = new LruCache<String, Bitmap>(cacheSize){
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return value.getByteCount();
                }
            };
        }
        return mLruCache;
    }

    public static synchronized void addBitmapToLruCache(String key, Bitmap value){
        if(mLruCache.get(key) == null && key != null && value != null){
            mLruCache.put(key, value);
        }
    }

    public static synchronized Bitmap getBitmapFromLruCache(String key){
        if(mLruCache != null){
            return mLruCache.get(key);
        }
        return null;
    }

    public static synchronized void removeBitmapFromLruCache(String key){
        if (mLruCache != null && key != null){
            Bitmap bitmap = mLruCache.remove(key);
            if (bitmap != null) {
                bitmap.recycle();
            }
        }
    }

    public static synchronized void clearCache(){
        if (mLruCache != null) {
            mLruCache.evictAll();
            mLruCache = null;
        }
    }
}
