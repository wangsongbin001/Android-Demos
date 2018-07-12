package com.example.songbinwang.littledemo.glide;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.GlideModule;

/**
 * Created by SongbinWang on 2017/7/19.
 */

public class MyGlideModule implements GlideModule{

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        //设置默认Bitmap的格式
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
        //设置内存缓存的策略
        builder.setMemoryCache(new LruResourceCache((int) (Runtime.getRuntime().maxMemory()/8)));
        //设置磁盘缓存的策略
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context,"glide_cache", 1024 * 1024 * 50));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
