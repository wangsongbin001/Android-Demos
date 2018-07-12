package com.song.myweibo.ui;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import com.song.myweibo.BaseActivity;
import com.song.myweibo.MainActivity;
import com.song.myweibo.R;

/**
 * Created by songbinwang on 2016/10/19.
 */

public class WelcomeActivity extends BaseActivity {

    private VideoView video_welcome;
    private Button btn_myweibo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        initViews();

        video_welcome.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.kr36));
        video_welcome.start();
        video_welcome.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                video_welcome.start();
            }
        });

        btn_myweibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity(MainActivity.class);
                WelcomeActivity.this.finish();
            }
        });
    }

    private void initViews(){
        video_welcome = (VideoView)findViewById(R.id.video_wecome);
        btn_myweibo = (Button) findViewById(R.id.btn_myweibo);
    }

}
