package com.example.songbinwang.liveinhand.video;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.example.songbinwang.liveinhand.R;
import com.example.songbinwang.liveinhand.uitls.ScreenUtils;
import com.example.songbinwang.liveinhand.video.entity.VideoEntity;
import com.example.songbinwang.liveinhand.video.view.VideoMediaController;
import com.example.songbinwang.liveinhand.video.view.VideoSurfaceView;

/**
 * Created by songbinwang on 2016/6/29.
 */
public class VideoPlayerNewActivity extends AppCompatActivity{

    private VideoSurfaceView mVideoSurfaceView;
    private VideoMediaController mMediaController;
    private VideoEntity video;
    private Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        setContentView(R.layout.video_playernew);
        mActivity = this;
        if(getIntent() != null){
            handleIntent(getIntent());
        }
        initVideoView();
    }

    private void handleIntent(Intent intent){
        video = (VideoEntity) intent.getSerializableExtra("video");
    }

    private void initVideoView(){
        mVideoSurfaceView = (VideoSurfaceView) findViewById(R.id.surfaceview);
        mVideoSurfaceView.setVideo(video);
        mMediaController = (VideoMediaController) findViewById(R.id.controller);
        //关联组件
        mVideoSurfaceView.setMediaController(mMediaController);
        //VideoView v;
        //mMediaController.setMediaPlayer(mVideoSurfaceView);

        // MediaController mediaController = new MediaController(this);
        //mMediaController.setMediaPlayer(mVideoSurfaceView);

//        surfaceView = (SurfaceView) findViewById(R.id.surfaceview);
//        mediaPlayer = new MediaPlayer();
//        surfaceView.getHolder().setKeepScreenOn(true);
//        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
//            @Override
//            public void surfaceCreated(SurfaceHolder holder) {
//                  play();
//            }
//
//            @Override
//            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//
//            }
//
//            @Override
//            public void surfaceDestroyed(SurfaceHolder holder) {
//                if (mediaPlayer != null) {
//                    mediaPlayer.release();
//                }
//            }
//        });

    }

//    private void play(){
//        try {
//            mediaPlayer.reset();
//            mediaPlayer.setDataSource(mActivity, Uri.parse(video.getResUrl()));
//            mediaPlayer.setDisplay(surfaceView.getHolder());
//            mediaPlayer.prepare();
//            mediaPlayer.start();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    protected void onStart() {
        super.onStart();
        ScreenUtils.hideSystemUI(mActivity);
    }
}
