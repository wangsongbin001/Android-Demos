package com.wang.mtoolsdemo;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wang.mtoolsdemo.common.util.AppUtil;
import com.wang.mtoolsdemo.common.util.DialogUtil;
import com.wang.mtoolsdemo.common.util.DownloadManagerUtil;
import com.wang.mtoolsdemo.common.util.LogUtil;
import com.wang.mtoolsdemo.common.util.SPUtil;
import com.wang.mtoolsdemo.work.SecondActivity;

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
    @Bind(R.id.btn_matrinal)
    Button btnMatrinal;
    @Bind(R.id.btn_custom)
    Button btnCustom;
    @Bind(R.id.btn_md)
    Button btnMd;

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

        getPreferences(Context.MODE_PRIVATE);
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

        Integer integer;
        try {
//            AppUtil.getDeviceId(this);
//            Log.i("wangsongbin", AppUtil.getDeviceUUID(this));
        } catch (Exception e) {
//            Log.i("wangsongbin", "msg:" + e.getMessage());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        StringBuilder sb = new StringBuilder();
//        sb.append(" name:" + SPUtil.get(getApplicationContext(), "name", "wangsong"));
//        sb.append(" age:" + SPUtil.get(getApplicationContext(), "age", 2));
//        sb.append(" man:" + SPUtil.get(getApplicationContext(), "man", false));
//        sb.append(" weight:" + SPUtil.get(getApplicationContext(), "weight", 5.5));
//
//        LogUtil.i("wangsongbin", "data-->" + sb.toString());
//        LogUtil.i("wangsongbin", "NetUtil," + NetUtil.isConnected(getApplicationContext())
//                + "," + NetUtil.isWifi(getApplicationContext()));
//        LogUtil.i("wangsongbin", "AppUtil," + AppUtil.getAppName(getApplicationContext())
//                + "," + AppUtil.getAppVersionName(getApplicationContext())
//                + "," + AppUtil.getAppVersionCode(getApplicationContext()));
//        LogUtil.i("wangsongbin", "densityUtil," + DensityUtil.dp2px(getApplicationContext(), 12)
//                + "," + DensityUtil.px2dp(getApplicationContext(), DensityUtil.dp2px(getApplicationContext(), 12))
//                + "," + DensityUtil.sp2px(getApplicationContext(), 12)
//                + "," + DensityUtil.px2sp(getApplicationContext(), DensityUtil.sp2px(getApplicationContext(), 12)));
//        LogUtil.i("wangsongbin", "ScreenUtil," + ScreenUtil.getScreenHeight(getApplicationContext())
//                + "," + ScreenUtil.getScreenWidth(getApplicationContext())
//                + "," + ScreenUtil.getStatusBarHeight(getApplicationContext()));
//        LogUtil.i("wangsongbin", "SDCardUtil," + SDCardUtil.isSdcardEnable()
//                + "," + SDCardUtil.getSdcardPath()
//                + "," + SDCardUtil.getSdcardAvailableSize()
//                + "," + SDCardUtil.getRootPath());
    }

    String[] sourceUrl = new String[]{
            "", "", "", "", "", "", "", "", "", ""
    };
    AlertDialog mInstance;

    @OnClick({R.id.sample_text, R.id.btn_custom, R.id.btn_matrinal, R.id.btn_md})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_custom:
                if (mInstance == null) {
                    mInstance = DialogUtil.showCustomDialog(this, "测试title", "test message", "sure", null, "cancel", null);
                } else {
                    mInstance.dismiss();
                    mInstance = null;
                }

                break;
            case R.id.btn_matrinal:
                if (mInstance == null) {
                    mInstance = DialogUtil.showDialog(this, "测试title", "test message", "sure", null, "cancel", null);
                } else {
                    mInstance.dismiss();
                    mInstance = null;
                }
                break;
            case R.id.btn_md:
//                Log.i("wangsongbin", "enable" + DownloadManagerUtil.isEnable(this));
//                //open
//                DownloadManagerUtil.enableDownloadManager(this);
                if (mInstance == null) {
                    mInstance = DialogUtil.showCreditDialog(this, "立即使用", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                            startActivity(intent);
                            dialog.dismiss();
                        }
                    });
                } else {
                    mInstance.dismiss();
                    mInstance = null;
                }
                break;
        }
//        LogUtil.i("wangsongbin", "onClick");
//        NetUtil.openNetSetting(this, 1001);
//        Intent intent = new Intent(this, ImagesShowActivitiy.class);
//        intent.putExtra(ImagesShowActivitiy.SOURCE_KEY, BB.data);
//        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.i("wangsongbin", "requestCode-->" + requestCode);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    private void checkPermissions() {
        if (!rxPermissions.isGranted(NeedPermissions[0])
                || !rxPermissions.isGranted(NeedPermissions[1])) {
            compositeDisposable.add(rxPermissions.request(NeedPermissions[0], NeedPermissions[1])
                    .subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean granted) throws Exception {
                            Log.i("wangsongbin", "granted:" + granted);
                            if (granted) {
                                Log.i("wangsongbin", "");
                            } else {

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
