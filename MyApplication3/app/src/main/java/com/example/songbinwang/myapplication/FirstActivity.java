package com.example.songbinwang.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.songbinwang.myapplication.util.HttpUtil;

import java.io.IOException;
import java.net.URL;

/**
 * Created by songbinwang on 2016/10/18.
 */

public class FirstActivity extends Activity{

    private static final String TAG = "wangfirst";
    private Button btn_id;
    private TextView tv_id;
    private EditText et_id;
    Handler handler = new Handler();
    Process process1, process2;
    private Button btn_tosecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_first);

//        EthernetManager a;
        try {
//            process1 = Runtime.getRuntime().exec("su");
            Log.i(TAG, "exec 1");
//            process2 = Runtime.getRuntime().exec("logcat -v time *:V>/mnt/sdcard/test.log");
            Log.i(TAG, "exec 2");
        } catch (Exception e) {
            e.printStackTrace();
        }
        initViews();
        btn_tosecond = (Button) findViewById(R.id.btn_tosecond);
        btn_tosecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstActivity.this, TestSecondActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initViews(){
        btn_id = (Button) findViewById(R.id.btn_id);
        tv_id = (TextView) findViewById(R.id.tv_id);
        et_id = (EditText) findViewById(R.id.et_id);
        et_id.setText("25102117");

        btn_id.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                   new Thread(){
                       @Override
                       public void run() {
                           final String result = HttpUtil.getIntroByVid(""+et_id.getText().toString());
                           handler.post(new Runnable() {
                               @Override
                               public void run() {
                                   tv_id.setText("result n/" + result);
                               }
                           });
                       }
                   }.start();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
//        process1.destroy();
//        process2.destroy();
    }
}
