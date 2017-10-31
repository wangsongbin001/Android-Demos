package com.wang.mtoolsdemo.work;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.wang.mtoolsdemo.App;
import com.wang.mtoolsdemo.R;
import com.wang.mtoolsdemo.common.banner.AccordionTransformer;
import com.wang.mtoolsdemo.common.banner.Banner;
import com.wang.mtoolsdemo.common.banner.CubeOutTransformer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2017/10/27.
 */

public class SecondActivity extends Activity {

    Banner banner, banner1, banner2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        banner = (Banner) findViewById(R.id.banner);
        List<String> urls = new ArrayList<>();
        urls.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic1xjab4j30ci08cjrv.jpg");
        banner.setImages(App.getInstance().mUrls)
              .setBannerTitles(App.getInstance().mTitles)
              .setIndicatorGravity(Banner.BannerConfig.RIGHT)
              .setIndicateType(Banner.BannerConfig.INDEX_YPTE_TITLE_NUM)
              .setBannerAnimation(AccordionTransformer.class)
              .setAutoScroll(true)
              .start();

        banner1 = (Banner) findViewById(R.id.banner1);
        banner1.setImages(null)
                .setBannerTitles(App.getInstance().mTitles)
                .setIndicatorGravity(Banner.BannerConfig.RIGHT)
                .setIndicateType(Banner.BannerConfig.INDEX_YPTE_CIRCLE)
                .setAutoScroll(true)
                .start();

        banner2 = (Banner) findViewById(R.id.banner2);
        banner2.setImages(App.getInstance().mUrls)
                .setBannerTitles(App.getInstance().mTitles)
                .setIndicatorGravity(Banner.BannerConfig.RIGHT)
                .setIndicateType(Banner.BannerConfig.INDEX_YPTE_NUM)
                .setAutoScroll(true)
                .setDelayTime(1 * 1000)
                .setBannerAnimation(CubeOutTransformer.class)
                .start();

    }
}
