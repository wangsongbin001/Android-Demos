package com.song.myweibo.util;


import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.song.myweibo.R;

/**
 * Created by songbinwang on 2016/10/31.
 */

public class MImageLoader {

    static DisplayImageOptions options = null;
    static MImageLoader mImageLoader;

    static{
        options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.img_default)
                .showImageOnFail(R.drawable.img_default)
                .cacheInMemory(true)
                .build();
    }

    public static MImageLoader getInstance(){
        if(mImageLoader == null){
            mImageLoader = new MImageLoader();
        }
        return mImageLoader;
    }

    public void displayImage(String url, ImageView imageView){
        ImageLoader.getInstance().displayImage(url, imageView, options);
    }
}
