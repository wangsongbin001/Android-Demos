package com.example.songbinwang.littledemo.videoplayer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * Created by songbinwang on 2016/6/7.
 */
public class VideoPlayerController extends FrameLayout{

    private View mRoot;
    private Button btn_back;
    private ToggleButton btn_play_pause;
    private SeekBar sb_progress;
    private TextView tv_duration;
    private TextView tv_current;

    public VideoPlayerController(Context context) {
        super(context);
    }

    public VideoPlayerController(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VideoPlayerController(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public VideoPlayerController(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if(mRoot != null){
            initControllerView(mRoot);
        }
    }

    private void initControllerView(View view){

    }
}
