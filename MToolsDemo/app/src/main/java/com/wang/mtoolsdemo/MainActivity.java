package com.wang.mtoolsdemo;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wang.mtoolsdemo.common.util.LogUtil;
import com.wang.mtoolsdemo.common.util.PermissionActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;
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
        rxPermissions = new RxPermissions(this);
        checkPermissions();

        Log.i("wangsongbin", "MainActivity.onCreate");
        LogUtil.i("" + "MainActivity.onCreate");
        LogUtil.i(getClass(), "MainActivity.onCreate");
        LogUtil.i("TAG_MainActivity", "MainActivity.onCreate");
    }

    @Override
    protected void onResume() {
        super.onResume();
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
