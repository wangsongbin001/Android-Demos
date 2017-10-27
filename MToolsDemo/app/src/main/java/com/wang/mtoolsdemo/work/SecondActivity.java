package com.wang.mtoolsdemo.work;

import android.app.Activity;
import android.os.Bundle;

import com.wang.mtoolsdemo.App;
import com.wang.mtoolsdemo.R;
import com.wang.mtoolsdemo.common.banner.Banner;

/**
 * Created by dell on 2017/10/27.
 */

public class SecondActivity extends Activity {

    Banner banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        banner = (Banner) findViewById(R.id.banner);
        banner.setData(App.getInstance().mUrls, App.getInstance().mTitles, 0);
    }
}
