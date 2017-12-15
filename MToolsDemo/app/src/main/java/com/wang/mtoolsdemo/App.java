package com.wang.mtoolsdemo;

import android.app.Application;
import android.util.Log;

import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.socialize.PlatformConfig;
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

    public static final String UM_App_Key = "5a1fba26b27b0a5557000056";
    public static final String UM_Message_SecretKey = "42ce47108b6d37b163e3b218db974f3d";

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
            UMConfigure.init(this, UM_App_Key, "Umeng",
                    UMConfigure.DEVICE_TYPE_PHONE, UM_Message_SecretKey);
            //设置是否对日志进行加密，默认不加密
            UMConfigure.setEncryptEnabled(false);
            initSharedAppKey();
        }
//        Log.i("wangsongbin", "channel1:" + AppUtil.getChannelV1(this));
//        Log.i("wangsongbin", "channel2:" + AppUtil.getChannelV2(this));
        //初始化推送
        final PushAgent mPushAgent = PushAgent.getInstance(this);
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String s) {
                Log.i("wangsongbin", "deviceToken:" + s + ","
                        + mPushAgent.getRegistrationId());
            }

            @Override
            public void onFailure(String s, String s1) {
                Log.i("wangsongbin", "s:" + s + "s1" + s1);
            }
        });
        mPushAgent.addAlias("15210095469", "xyh", new UTrack.ICallBack() {
            @Override
            public void onMessage(boolean b, String s) {
                Log.i("wangsongbin", "b:" + b + ",s:" + s);
            }
        });
        Log.i("wangsongbin", "deviceToken:" + mPushAgent.getRegistrationId());
    }

    private void initSharedAppKey(){
        PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad", "http://sns.whalecloud.com");
    }
}
