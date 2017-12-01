package com.wang.mtoolsdemo;

import android.app.Application;

import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.wang.mtoolsdemo.common.util.AppUtil;
import com.wang.mtoolsdemo.common.util.LogUtil;

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
            LogUtil.i("wangsongbin", "主进程初始化");
            //主进程
            UMConfigure.setLogEnabled(true);//开启log，默认关闭
//            UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "5a1fba26b27b0a5557000056");
            UMConfigure.init(this, "5a1fba26b27b0a5557000056", "Umeng",
                    UMConfigure.DEVICE_TYPE_PHONE, null);
            //设置统计场景，一般或游戏
            MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_DUM_NORMAL);
            //
            //当用户使用自有账号登录时，可以这样统计：
            MobclickAgent.onProfileSignIn("" + AppUtil.getDeviceUUID(this));
            //设置是否对日志进行加密，默认不加密
            UMConfigure.setEncryptEnabled(false);

        }
//        Log.i("wangsongbin", "channel1:" + AppUtil.getChannelV1(this));
//        Log.i("wangsongbin", "channel2:" + AppUtil.getChannelV2(this));
    }
}
