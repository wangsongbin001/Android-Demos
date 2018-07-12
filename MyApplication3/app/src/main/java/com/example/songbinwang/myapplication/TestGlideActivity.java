package com.example.songbinwang.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

/**
 * Created by xiaosongshu on 2017/7/19.
 */

public class TestGlideActivity extends Activity{

    private ImageView iv_content;
    private static final String Image_Url = "http://img1.imgtn.bdimg.com/it/u=1861947898,2677579279&fm=26&gp=0.jpg";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testglide);

        iv_content = (ImageView) findViewById(R.id.iv_content);

        Glide.with(this)
                .load("http://img1.imgtn.bdimg.com/it/u=1861947898,2677579279&fm=26&gp=0.jpg")
                .into(iv_content);

        Picasso.with(this.getApplicationContext())
                .load("http://img1.imgtn.bdimg.com/it/u=1861947898,2677579279&fm=26&gp=0.jpg")
                .into(iv_content);

        String b;


    }
}
