package com.example.songbinwang.littledemo.controller;

import android.app.Activity;
import android.content.Context;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.songbinwang.littledemo.R;

import java.io.File;

/**
 * Created by songbinwang on 2016/6/7.
 */
public class VideoPlayerActivity extends Activity{

    private VideoView mVideoView;
    private MediaController mController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_videoplayer);

        mVideoView = (VideoView) findViewById(R.id.videoview);
        mController = new MediaController(this);

        File file = new File(Environment.getExternalStorageDirectory(), "123.mp4");
        Log.i("wangsongbin", "path:"+file.getAbsolutePath()+";exists:"+file.exists());

        if(file.exists()){
            mVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    Log.i("wangsongbin","what:"+what+";extra:"+extra);
                    return false;
                }
            });
            mVideoView.setVideoPath(file.getAbsolutePath());
            mVideoView.setMediaController(mController);
            mController.setMediaPlayer(mVideoView);
            mVideoView.requestFocus();
        }

        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

    }



    private void initView(){
        mVideoView = (VideoView) findViewById(R.id.videoview);
    }
}
