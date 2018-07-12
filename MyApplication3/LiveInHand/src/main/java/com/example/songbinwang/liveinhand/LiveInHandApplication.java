package com.example.songbinwang.liveinhand;

import android.app.Application;

import cn.bmob.v3.Bmob;

/**
 * Created by songbinwang on 2016/6/16.
 */
public class LiveInHandApplication extends Application{

    private static final String BMOB_APPID = "cd3e1b4307ce96d4fcdfdba52c0fd4b9";

    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this, BMOB_APPID);
    }
}
