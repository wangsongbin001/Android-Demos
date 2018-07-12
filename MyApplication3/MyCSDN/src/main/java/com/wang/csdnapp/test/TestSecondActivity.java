package com.wang.csdnapp.test;

import android.nfc.Tag;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.wang.csdnapp.CSDNApp;
import com.wang.csdnapp.R;
import com.wang.csdnapp.util.LogUtil;

/**
 * Created by SongbinWang on 2017/7/12.
 */

public class TestSecondActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testsecond);
//        TextView textView = (TextView) findViewById(R.id.tv_appname);
//        TestLeakSingleton.getInstance(this).setTvAppName(textView);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                LogUtil.i("TestSE", "thread:" + Thread.currentThread() + "start");
                try{
                    Thread.sleep(80000l);
                }catch (Exception e){

                }
                LogUtil.i("TestSE", "thread:" + Thread.currentThread() + " end");
            }
        };
        new Thread(runnable).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        CSDNApp.getRefWatcher(getApplicationContext()).watch(this);
    }
}
