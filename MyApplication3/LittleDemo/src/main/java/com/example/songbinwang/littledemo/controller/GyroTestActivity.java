package com.example.songbinwang.littledemo.controller;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.songbinwang.littledemo.R;

import java.io.File;
import java.io.IOException;

/**
 * Created by songbinwang on 2016/7/1.
 */
public class GyroTestActivity extends Activity{

    private TextView tv_value1,tv_value2, tv_value3;

    private SurfaceView sv_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_gyrotest);

        initView();
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //sensorManager.registerListeners(sensorEventListener, Sensor.TYPE_GYROSCOPE, SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected void onResume() {
        super.onResume();
        sv_content = (SurfaceView) findViewById(R.id.sv_content);
        sv_content.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                surface = holder.getSurface();
                initSurfaceView();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
    }

    private void initView(){
        tv_value1 = (TextView) findViewById(R.id.value1);
        tv_value2 = (TextView) findViewById(R.id.value2);
        tv_value3 = (TextView) findViewById(R.id.value3);
    }

    SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
              tv_value1.setText(""+event.values[0]);
              tv_value2.setText(""+event.values[1]);
              tv_value3.setText(""+event.values[2]);

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    MediaPlayer mediaPlayer;
    private Surface surface;
    private void initSurfaceView(){
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setSurface(surface);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            File file = new File("/sdcard/1.mp4");
            if(!file.exists()){
                Toast.makeText(this, "文件不存在", Toast.LENGTH_SHORT).show();
            }
            mediaPlayer.setDataSource(Uri.fromFile(file).toString());
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.start();
                }
            });
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            Log.i("wangsongbin" , "");
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
        }
    }
}
