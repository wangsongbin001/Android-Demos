package com.wang.xyhua.work;

import android.app.Application;
import android.text.TextUtils;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.meituan.android.walle.WalleChannelReader;
import com.wang.xyhua.common.global.GlobalConfig;
import com.wang.xyhua.common.utils.LogUtil;

import java.nio.MappedByteBuffer;

/**
 * Created by dell on 2017/9/19.
 */

public class MApp extends Application{

    private static MApp mInstance;
    public static MApp getInstance(){
        return mInstance;
    }

    private static GlobalConfig mConfig;
    public static GlobalConfig getConfig(){
        return mConfig;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        //Fresco 初始化
        Fresco.initialize(getApplicationContext());
        //初始化配置信息
        initConfig();
    }

    private void initConfig(){
        mConfig = new GlobalConfig();
        //获取渠道号
        String channel = WalleChannelReader.getChannel(getApplicationContext());
        LogUtil.i("channelId:" + channel);
        if(!TextUtils.isEmpty(channel)){
            mConfig.setChannelId(channel);
        }

    }



}
