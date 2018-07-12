package com.example.songbinwang.myapplication;

import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.hotcast.vr.initialize.Hotcast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String PPTV_ANDROID_ID = "pptv_android";
    public static final String PPTV_VERSION = "v1.0.5";
    private Button btn_start_local,btn_start_online;

    public static final String Tag = "wangVr";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        Hotcast.create(this, PPTV_ANDROID_ID, PPTV_VERSION);
        Log.i(Tag, "Hotcast.create(this, PPTV_ANDROID_ID, PPTV_VERSION);");
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Hotcast.startHotcast(MainActivity.this);
                Log.i(Tag, " Hotcast.startHotcast(MainActivity.this);;");
            }
        });

        initView();
        registerListener();
    }

    private void initView(){
        btn_start_local = (Button)findViewById(R.id.btn_start_local);
        btn_start_online = (Button)findViewById(R.id.btn_start_online);
    }

    private void registerListener(){
        btn_start_local.setOnClickListener(this);
        btn_start_online.setOnClickListener(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_start_local:
                Hotcast.startHotcast(MainActivity.this, 1, Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+"3d/aa.mp4");
                break;
            case R.id.btn_start_online:
                Hotcast.startHotcast(MainActivity.this, 1, "http://218.59.213.36/3274959754bc38d31e130ceb495f7a53.mp4?w=1&key=e20bc33b2605d2e6ff4939a7b474d861&k=cefe64679f32cf88ca976c0f07d5d195-6a8e-1462340968%26bppcataid%3D1&type=phone.android.download&vvid=dc3bdbc3-30b5-4254-bae7-2407cb18daf5&sv=1.0.0&platform=android3&ft=2&accessType=wifi");
                break;
        }
        Log.i(Tag, "onClick");
    }
}
