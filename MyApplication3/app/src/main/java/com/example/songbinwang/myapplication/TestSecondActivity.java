package com.example.songbinwang.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


/**
 * Created by SongbinWang on 2017/7/12.
 */

public class TestSecondActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testsecond);
        TextView textView = (TextView) findViewById(R.id.tv_appname);
        TestLeakSingleton.getInstance(this).setTvAppName(textView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        MyApp.getRefWatcher(getApplicationContext()).watch(this);
    }
}
