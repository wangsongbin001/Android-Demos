package com.example.songbinwang.littledemo.controller;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;

import com.example.songbinwang.littledemo.R;

/**
 * Created by songbinwang on 2016/5/27.
 */
public class PackageManagerActivity extends Activity {

    private final static String TAG = "PackageManagerActivity";

    private Button btn_system_app, btn_sdcard_app;
    private ListView lv_app_info;
//    private ListView lv_app_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pm);
        handleIntent();
        initViews();
        registerListeners();
        Log.i(TAG, "");

    }

    private void handleIntent() {
        //Toast.makeText(PackageManagerActivity.this, "", Toast.LENGTH_LONG).show();
    }

    private void initViews() {
        btn_system_app = (Button) findViewById(R.id.btn_system_app);
        btn_sdcard_app = (Button) findViewById(R.id.btn_sdcard_app);
//        lv_app_info = (ListView)findViewById(R.id.lv_app_info);
        lv_app_info = (ListView) findViewById(R.id.lv_app_info);
    }

    private void registerListeners() {

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

}
