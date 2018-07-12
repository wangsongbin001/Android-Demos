package com.example.songbinwang.liveinhand.video.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.songbinwang.liveinhand.R;
import com.example.songbinwang.liveinhand.uitls.StringUtils;

import java.util.Formatter;

/**
 * Created by songbinwang on 2016/6/30.
 */
public class VideoMediaController extends FrameLayout implements View.OnClickListener{

    private MediaPlayerControl mPlayer;
    private Context mContext;
    private View mAnchor;
    private WindowManager mWindowManager;
    private Window mWindow;
    private View mRoot;
    private View mDecor;
    private WindowManager.LayoutParams mDecorLayoutParams;
    private ProgressBar mProgress;
    private TextView mEndTime, mCurrentTime;
    private boolean mShowing;
    private boolean mDragging = false;
    private static final int sDefaultTimeout = 3000;
    private static final int FADE_OUT = 1;
    private static final int SHOW_PROGRESS = 2;
    private boolean mUseFastForward;
    private boolean mFromXml;
    private boolean mListenersSet;
    private View.OnClickListener mNextListener, mPrevListener;
    StringBuilder mFormatBuilder;
    Formatter mFormatter;
    private ImageButton mPauseButton;
    private ImageButton mFfwdButton;
    private ImageButton mRewButton;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;
    private CharSequence mPlayDescription;
    private CharSequence mPauseDescription;
    private AccessibilityManager mAccessibilityManager;

    //custom:
    private ImageView  iv_top_back;
    private TextView tv_top_title;
    private TextView tv_current, tv_duration;
    private SeekBar sb_progress;
    private ImageView iv_backward, iv_forward;
    private ToggleButton btn_play_puase;

    public VideoMediaController(Context context) {
            super(context);
            mContext = context;
            mRoot = this;
    }

    public VideoMediaController(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mRoot = this;
    }

    public VideoMediaController(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mRoot = this;
    }

    public VideoMediaController(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        mRoot = this;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initControllerView(mRoot);
    }

    private void initControllerView(View root) {
        iv_top_back = (ImageView) findViewById(R.id.iv_top_back);
        iv_top_back.setOnClickListener(this);

        tv_top_title = (TextView) findViewById(R.id.tv_top_title);
        tv_current = (TextView) findViewById(R.id.tv_current);
        tv_duration = (TextView) findViewById(R.id.tv_duration);
        sb_progress = (SeekBar) findViewById(R.id.sb_progress);
        if (sb_progress != null) {
            sb_progress.setOnSeekBarChangeListener(onSeekBarChangeListener);
            sb_progress.setMax(1000);
        }

        iv_forward = (ImageView) findViewById(R.id.iv_forward);
        iv_backward = (ImageView) findViewById(R.id.iv_backward);

        btn_play_puase = (ToggleButton) findViewById(R.id.btn_play_pause);
        btn_play_puase.setOnClickListener(this);
    }

    private void initControllerView(){
        LayoutInflater  mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mInflater.inflate(R.layout.video_controller, null);
        initControllerView(view);
    }

    private void initFloatingWindowLayout() {
        mDecorLayoutParams = new WindowManager.LayoutParams();
        WindowManager.LayoutParams p = mDecorLayoutParams;
        p.gravity = Gravity.TOP | Gravity.LEFT;
        p.height = WindowManager.LayoutParams.WRAP_CONTENT;
        p.x = 0;
        p.format = PixelFormat.TRANSLUCENT;
        p.type = WindowManager.LayoutParams.TYPE_APPLICATION_PANEL;
        p.flags |= WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM
                | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_SPLIT_TOUCH;
        p.windowAnimations = 0;
    }

    public void setMediaPlayer(MediaPlayerControl player) {
        mPlayer = player;
        tv_top_title.setText(mPlayer.getTitle());
        updatePausePlay();
    }

    private void updatePausePlay() {
        if (mPauseButton == null) {
            return;
        }
        if (mPlayer.isPlaying()) {
            btn_play_puase.setChecked(false);
        } else {
            btn_play_puase.setChecked(true);
        }
    }

    public void setAnchorView(View view) {
        if (mAnchor != null) {
            mAnchor.removeOnLayoutChangeListener(mLayoutChangeListener);
        }
        mAnchor = view;
        if (mAnchor != null) {
            mAnchor.addOnLayoutChangeListener(mLayoutChangeListener);
        }

        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        removeAllViews();
        View v = makeControllerView();
        addView(v, lp);
        if(mAnchor instanceof FrameLayout){
            FrameLayout layout = (FrameLayout)mAnchor;
            layout.addView(this, lp);
        }
    }

    private View makeControllerView(){
        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //mRoot = mInflater.inflate(com.android.internal.R.layout.media_controller, null);
        initControllerView(mRoot);
        return mRoot;
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
    }

    public void hide(){
        if(mShowing && getVisibility() ==  View.VISIBLE){
            mHandler.removeMessages(SHOW_PROGRESS);
            setVisibility(View.GONE);
            mShowing = false;
        }
    }

    public boolean isShowing(){
        return mShowing;
    }

    public void show(){
        show(sDefaultTimeout);
    }

    public void show(int timeout){
        if (!mShowing && getVisibility() != View.VISIBLE) {
            setProgress();
            setVisibility(View.VISIBLE);
            mShowing = true;
        }
        updatePausePlay();
        mHandler.removeMessages(SHOW_PROGRESS);
        mHandler.sendEmptyMessage(SHOW_PROGRESS);
        if(timeout != 0){
            mHandler.removeMessages(FADE_OUT);
            Message msg = mHandler.obtainMessage(FADE_OUT);
            mHandler.sendMessageDelayed(msg, timeout);
        }else{
           mHandler.removeMessages(FADE_OUT);
        }
    }

    private int setProgress(){
        if(mPlayer == null && mDragging){
            return 0;
        }
        int current = mPlayer.getCurrentPosition();
        int duration = mPlayer.getDuration();

        if (tv_current != null) {
            tv_current.setText(StringUtils.getFormatDuration(current));
        }
        if (tv_duration != null) {
            tv_duration.setText(StringUtils.getFormatDuration(duration));
        }

        if (sb_progress != null) {
            if(duration > 0){
                int pos = (int) (1000 * current*1.0f / duration * 1.0f);
                sb_progress.setProgress(pos);
            }

            int percent = mPlayer.getBufferPercentage();
            sb_progress.setSecondaryProgress(10* percent);
        }

        return mPlayer.getCurrentPosition();
    }

    private final OnLayoutChangeListener mLayoutChangeListener = new OnLayoutChangeListener() {
        @Override
        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {

        }
    };

    private final SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            if (!fromUser || mPlayer == null) {
                return;
            }

            long duration = mPlayer.getDuration();
            long newPos = progress * duration / 1000l;

            mPlayer.seekTo((int) newPos);
            if (tv_current != null) {
                tv_current.setText(StringUtils.getFormatDuration(newPos));
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            show(360000);
            mDragging = true;

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            mDragging = false;
            setProgress();
            updatePausePlay();
            show(sDefaultTimeout);
        }
    };

    private Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int pos;
            switch(msg.what){
                case FADE_OUT:
                    hide();
                    break;
                case SHOW_PROGRESS:
                    pos = setProgress();
                    if(!mDragging && mShowing && mPlayer.isPlaying()){
                        msg = obtainMessage(SHOW_PROGRESS);
                        sendMessageDelayed(msg, 1000);
                    }
                    break;

            }
        }
    };

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.iv_top_back:
                if(mContext instanceof Activity){
                ((Activity)mContext).finish();
            }
                break;
            case R.id.btn_play_pause:
                togglePlayPause();
                break;
        }
    }

    private void togglePlayPause(){
        if (mPlayer != null) {
            if(mPlayer.isPlaying()){
                mPlayer.pause();
                show(0);
            }else{
                mPlayer.start();
                show(sDefaultTimeout);
            }
            updatePausePlay();
        }
    }

    public interface MediaPlayerControl {
        void start();

        void pause();

        int getDuration();

        int getCurrentPosition();

        void seekTo(int pos);

        boolean isPlaying();

        int getBufferPercentage();

        boolean canPause();

        boolean canSeekBackward();

        boolean canSeekForward();

        /**
         * Get the audio session id for the player used by this VideoView. This can be used to
         * apply audio effects to the audio track of a video.
         *
         * @return The audio session, or 0 if there was an error.
         */
        int getAudioSessionId();

        String getTitle();
    }
}
