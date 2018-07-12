package com.wang.csdnapp;

import android.app.Application;
import android.content.Context;


import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import cn.bmob.v3.Bmob;

/**
 * Created by songbinwang on 2017/2/28.
 */

public class CSDNApp extends Application{

    public static Application app;
    public static float density = 1.0f;

    /**
     * leakcanary的使用
     * 1，
     */
    private RefWatcher refWatcher;
    public static RefWatcher getRefWatcher(Context context){
        CSDNApp app = (CSDNApp) context.getApplicationContext();
        return app.refWatcher;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        density = getResources().getDisplayMetrics().density;
        initWeibo();
        initLeakCanary();
    }

    /**
     * 初始化LeakCanary
     */
    private void initLeakCanary(){
        if(LeakCanary.isInAnalyzerProcess(this)){
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        refWatcher = LeakCanary.install(this);
    }

    private void initWeibo(){
        //初始化SDK对象，在调用SDk的api之前
        WbSdk.install(this, new AuthInfo(this, Constant.WeiBo_AppKey,
                Constant.WeiBo_REDIRECT_URL, Constant.WeiBo_Score));

    }

}
