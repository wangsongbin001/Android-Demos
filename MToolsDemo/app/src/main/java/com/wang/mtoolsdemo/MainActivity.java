package com.wang.mtoolsdemo;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wang.mtoolsdemo.common.BB;
import com.wang.mtoolsdemo.common.util.AppUtil;
import com.wang.mtoolsdemo.common.util.DensityUtil;
import com.wang.mtoolsdemo.common.util.LogUtil;
import com.wang.mtoolsdemo.common.util.NetUtil;
import com.wang.mtoolsdemo.common.util.SDCardUtil;
import com.wang.mtoolsdemo.common.util.SPUtil;
import com.wang.mtoolsdemo.common.util.ScreenUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    CompositeDisposable compositeDisposable;
    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Bind(R.id.sample_text)
    TextView sampleText;

    private String[] NeedPermissions = new String[]{
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.MODIFY_AUDIO_SETTINGS
    };

    RxPermissions rxPermissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        compositeDisposable = new CompositeDisposable();

        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());
//        PermissionActivity.startPermissionActivity(this, NeedPermissions);
//        rxPermissions = new RxPermissions(this);
//        checkPermissions();

//        Log.i("wangsongbin", "MainActivity.onCreate");
//        LogUtil.i("" + "MainActivity.onCreate");
//        try{
//            LogUtil.i(BB.class, "MainActivity.onCreate");
//        }catch (Exception e){
//            Log.i("wangsongbin", "exception:" + e.getMessage());
//        }
//        LogUtil.i("TAG_MainActivity", "MainActivity.onCreate");

        SPUtil.put(getApplicationContext(), "name", "wangsongbin");
        SPUtil.put(getApplicationContext(), "age", 25);
        SPUtil.put(getApplicationContext(), "man", true);
        SPUtil.put(getApplicationContext(), "weight", 65.5);
    }

    @Override
    protected void onResume() {
        super.onResume();
        StringBuilder sb = new StringBuilder();
        sb.append(" name:" + SPUtil.get(getApplicationContext(), "name", "wangsong"));
        sb.append(" age:" + SPUtil.get(getApplicationContext(), "age", 2));
        sb.append(" man:" + SPUtil.get(getApplicationContext(), "man", false));
        sb.append(" weight:" + SPUtil.get(getApplicationContext(), "weight", 5.5));

        LogUtil.i("wangsongbin", "data-->" + sb.toString());
        LogUtil.i("wangsongbin", "NetUtil," + NetUtil.isConnected(getApplicationContext())
                + "," + NetUtil.isWifi(getApplicationContext()));
        LogUtil.i("wangsongbin", "AppUtil," + AppUtil.getAppName(getApplicationContext())
                + "," + AppUtil.getAppVersionName(getApplicationContext())
                + "," + AppUtil.getAppVersionCode(getApplicationContext()));
        LogUtil.i("wangsongbin", "densityUtil," + DensityUtil.dp2px(getApplicationContext(), 12)
                + "," + DensityUtil.px2dp(getApplicationContext(), DensityUtil.dp2px(getApplicationContext(), 12))
                + "," + DensityUtil.sp2px(getApplicationContext(), 12)
                + "," + DensityUtil.px2sp(getApplicationContext(), DensityUtil.sp2px(getApplicationContext(), 12)));
        LogUtil.i("wangsongbin", "ScreenUtil," + ScreenUtil.getScreenHeight(getApplicationContext())
                + "," + ScreenUtil.getScreenWidth(getApplicationContext())
                + "," + ScreenUtil.getStatusBarHeight(getApplicationContext()));
        LogUtil.i("wangsongbin", "SDCardUtil," + SDCardUtil.isSdcardEnable()
                + "," + SDCardUtil.getSdcardPath()
                + "," + SDCardUtil.getSdcardAvailableSize()
                + "," + SDCardUtil.getRootPath());
    }

    @OnClick({R.id.sample_text})
    public void onClick(View view){
        LogUtil.i("wangsongbin", "onClick");
        NetUtil.openNetSetting(this, 1001);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.i("wangsongbin", "requestCode-->" + requestCode);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(compositeDisposable != null){
            compositeDisposable.dispose();
        }
    }

    private void checkPermissions(){
        if(!rxPermissions.isGranted(NeedPermissions[0])
                || !rxPermissions.isGranted(NeedPermissions[1])){
            compositeDisposable.add(rxPermissions.request(NeedPermissions[0],NeedPermissions[1])
                    .subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean granted) throws Exception {
                            Log.i("wangsongbin", "granted:" + granted);
                            if(granted){
                                Log.i("wangsongbin", "");
                            }else{

                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {

                        }
                    }));
        }
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}