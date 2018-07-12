package com.example.songbinwang.littledemo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.songbinwang.littledemo.service.MyService;

/**
 * Created by SongbinWang on 2017/7/28.
 */

public class TestServiceActivity extends Activity {

    private Button btn_start, btn_stop, btn_bind, btn_unbind;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = (MyService.MyBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    private MyService.MyBinder binder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testservice);

        initViews();
    }

    private void initViews(){
        btn_start = (Button) findViewById(R.id.btn_start);
        btn_stop = (Button) findViewById(R.id.btn_stop);
        btn_bind = (Button) findViewById(R.id.btn_bind);
        btn_unbind = (Button) findViewById(R.id.btn_unbind);
        btn_bind.setOnClickListener(onClicker);
        btn_unbind.setOnClickListener(onClicker);
        btn_start.setOnClickListener(onClicker);
        btn_stop.setOnClickListener(onClicker);
    }

    View.OnClickListener onClicker = new  View.OnClickListener(){
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setAction("com.example.songbinwang.littledemo.service.MyService");

            PackageManager pm = getPackageManager();
            ResolveInfo info = pm.resolveService(intent, PackageManager.MATCH_DEFAULT_ONLY);
            if(info == null){
                Log.i("MyService", "info is null");
                return;
            }
            String packageName = info.serviceInfo.packageName;
            String className = info.serviceInfo.name;
            ComponentName componentName = new ComponentName(packageName, className);
            intent.setComponent(componentName);
            switch (v.getId()){
                case R.id.btn_start:
                    startService(intent);
                    break;
                case R.id.btn_stop:
                    stopService(intent);
                    break;
                case R.id.btn_bind:
                    bindService(intent, serviceConnection, BIND_AUTO_CREATE);
                    if(binder != null){
                        binder.startDownload();
                    }
                    break;
                case R.id.btn_unbind:
                    unbindService(serviceConnection);
                    break;
            }
        }
    };
}
