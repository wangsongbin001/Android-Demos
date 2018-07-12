package com.example.songbinwang.myapplication;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

//import com.example.songbinwang.littledemo.service.MyService;

/**
 * Created by SongbinWang on 2017/7/28.
 */

public class TestReceiverActivity extends Activity {

    private Button btn_register, btn_unregister, btn_send_normal, btn_send_order;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
//            binder = (MyService.MyBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
//    private MyService.MyBinder binder;

    MyReceiver myReceiver = new MyReceiver();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testreceiver);

        initViews();

        ContentResolver contentResolver = getContentResolver();
        contentResolver.insert(Uri.parse(""), new ContentValues());

    }

    private void initViews(){
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_unregister = (Button) findViewById(R.id.btn_unregister);
        btn_send_normal = (Button) findViewById(R.id.btn_send_normal);
        btn_send_order = (Button) findViewById(R.id.btn_send_order);
        btn_send_normal.setOnClickListener(onClicker);
        btn_send_order.setOnClickListener(onClicker);
        btn_register.setOnClickListener(onClicker);
        btn_unregister.setOnClickListener(onClicker);
    }

    View.OnClickListener onClicker = new  View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_register:
                    IntentFilter intentFilter = new IntentFilter();
                    intentFilter.addAction("com.example.myapplication.MyReceiver");
                    registerReceiver(myReceiver, intentFilter);
                    LocalBroadcastManager.getInstance(TestReceiverActivity.this)
                            .registerReceiver(myReceiver, intentFilter);
                    break;
                case R.id.btn_unregister:
                    unregisterReceiver(myReceiver);
                    break;
                case R.id.btn_send_normal:
                    Intent intent = new Intent();
                    intent.setAction("com.example.myapplication.MyReceiver");
                    sendBroadcast(intent);
                    break;
                case R.id.btn_send_order:
                    Intent intent2 = new Intent();
                    intent2.setAction("com.example.myapplication.MyReceiver");
                    sendOrderedBroadcast(intent2, null);
                    LocalBroadcastManager.getInstance(TestReceiverActivity.this)
                            .sendBroadcast(intent2);
                    break;
            }
        }
    };
}
