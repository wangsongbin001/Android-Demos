package com.wang.mtoolsdemo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.wang.mtoolsdemo.common.util.AppUtil;
import com.wang.mtoolsdemo.common.util.LogUtil;
import com.wang.mtoolsdemo.work.um.UmUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dell on 2017/11/30.
 * 测试友盟业务。
 * 1.1，注册账号
 * 1.2，申请权限
 * 1.3，代码混淆
 * 1.4，App调用UMConfigure.init
 * 完成了友盟基本初始化：
 * 2 友盟统计 U-App集成
 * 2.1，场景设计
 * 2.2，Session统计
 * 2.3，账号统计
 * 2.4.1，计数事件：统计事件发生次数，统计件事为某些属性的发生次数
 * 2.4.2, 计算事件：统计数值型变量的值得分布，
 * 2.5  错误信息收集，默认自动收集，还可以上传自己捕获到的异常。
 * 3,友盟社会化分享
 * 参考ShareActivity
 * 4,友盟推送
 *
 */

public class SevenActivity extends AppCompatActivity {

    @Bind(R.id.btn_27)
    Button btn27;
    @Bind(R.id.btn_error)
    Button btnError;
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_7);
        ButterKnife.bind(this);
        mContext = this;

        //设置统计场景，一般或游戏
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_DUM_NORMAL);
        MobclickAgent.setSecret(this, App.UM_App_Key);
        //当用户使用自有账号登录时，可以这样统计：
        MobclickAgent.onProfileSignIn("" + AppUtil.getDeviceUUID(this));
        LogUtil.i("wangsongbin", "UUID:" + AppUtil.getDeviceUUID(this));
        LogUtil.i("wangsongbin", "deviceInfo:" + UmUtil.getDeviceInfo(this));

        requestPermission();
    }

    @OnClick({R.id.btn_27, R.id.btn_error, R.id.btn_share, R.id.btn_share2})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btn_27:
                intent.setClass(this, SixActivity.class);
                //计数事件
                MobclickAgent.onEvent(mContext, "event_install");
                //安装信用花
                Map<String, String> map = new HashMap<>();
                map.put("apk", "XinYongHua");
                MobclickAgent.onEvent(mContext, "event_install", map);
                startActivity(intent);
                //计算事件
                Map<String, String> map2 = new HashMap<>();
                map2.put("person", "student");
                MobclickAgent.onEventValue(mContext, "user_age", map2, (int) (10 + 10 * Math.random()));
                break;
            case R.id.btn_error:
                try{
                    throw new IOException("自定义IO异常");
                }catch (Exception e){
                    LogUtil.i("wangsongbin", "e:" + e.getMessage());
                    MobclickAgent.reportError(mContext, e);
                }
                break;
            case R.id.btn_share:
                new ShareAction(this)
                        .withText("hello")
                        .setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.QQ,SHARE_MEDIA.WEIXIN)
                        .setCallback(shareListener)
                        .open();
                break;
            case R.id.btn_share2:
                new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.QQ)//传入平台
                        .withText("hello")//分享内容
                        .setCallback(shareListener)//回调监听器
                        .share();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(mContext);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(mContext);
    }

    public void requestPermission(){
        if(Build.VERSION.SDK_INT>=23){
            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.CALL_PHONE,Manifest.permission.READ_LOGS,
                    Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.SET_DEBUG_APP,Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.GET_ACCOUNTS,Manifest.permission.WRITE_APN_SETTINGS};
            ActivityCompat.requestPermissions(this,mPermissionList,123);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MobclickAgent.onProfileSignOff();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
            Log.i("wangsongbin" , "onStart:" + platform);
        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(SevenActivity.this,"成功了",Toast.LENGTH_LONG).show();
            Log.i("wangsongbin" , "onResult:" + platform);
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(SevenActivity.this,"失败"+t.getMessage(),Toast.LENGTH_LONG).show();
            Log.i("wangsongbin" , "onError:" + platform + ",t" + t.getMessage());
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(SevenActivity.this,"取消了",Toast.LENGTH_LONG).show();
            Log.i("wangsongbin" , "onError:" + platform);
        }
    };
}
