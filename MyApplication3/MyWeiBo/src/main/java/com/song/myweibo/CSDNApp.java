package com.song.myweibo;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by songbinwang on 2016/10/25.
 */

public class CSDNApp extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        //ImageLoader 初始化
        ImageLoaderConfiguration config = ImageLoaderConfiguration.createDefault(this);
        ImageLoader.getInstance().init(config);
    }
}
