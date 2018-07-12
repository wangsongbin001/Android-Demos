package com.example.songbinwang.liveinhand.video;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.songbinwang.liveinhand.R;
import com.example.songbinwang.liveinhand.uitls.ScreenUtils;
import com.example.songbinwang.liveinhand.video.entity.VideoEntity;

import java.io.File;

/**
 * Created by songbinwang on 2016/6/27.
 */
public class VideoPlayerActivity extends AppCompatActivity{

    private VideoView videoView;
    private VideoEntity video;
    private MediaController mController;
    private Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        setContentView(R.layout.video_player);
        mActivity = this;
        if(getIntent() != null){
            handleIntent(getIntent());
        }
        initVideoView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        ScreenUtils.hideSystemUI(mActivity);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(videoView != null){
            videoView.start();
        }
    }

    private void handleIntent(Intent intent){
        video = (VideoEntity) intent.getSerializableExtra("video");
    }

    private void initVideoView(){
        videoView = (VideoView) findViewById(R.id.videoview);
        mController = new MediaController(this);
        File file = new File(video.getResUrl());
        if(file.exists()){
            videoView.setVideoPath(video.getResUrl());
            videoView.setMediaController(mController);
            mController.setMediaPlayer(videoView);
            videoView.requestFocus();
        }

    }
}
