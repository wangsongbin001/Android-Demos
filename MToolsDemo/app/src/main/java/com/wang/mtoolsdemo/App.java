package com.wang.mtoolsdemo;

import android.app.Application;

import com.wang.mtoolsdemo.common.util.AppUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dell on 2017/10/27.
 */

public class App extends Application{
    public List<String> mUrls;
    public List<String> mTitles;

    private static App app;
    public static App getInstance(){
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        String[] urls = getResources().getStringArray(R.array.url);
        String[] titles = getResources().getStringArray(R.array.title);
        List list1 = Arrays.asList(urls);
        List list2 = Arrays.asList(titles);

        mUrls = new ArrayList<>(list1);
        mTitles = new ArrayList<>(list2);

//        LogUtil.i("wangsongbin", mUrls.toString());
//        LogUtil.i("wangsongbin", mUrls.toString());
        if(getPackageName().equals(AppUtil.getCurrentProcessName(this))){
            //主进程
        }
//        Log.i("wangsongbin", "channel1:" + AppUtil.getChannelV1(this));
//        Log.i("wangsongbin", "channel2:" + AppUtil.getChannelV2(this));
    }
}
