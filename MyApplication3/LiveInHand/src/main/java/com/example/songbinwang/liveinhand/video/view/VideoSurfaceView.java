package com.example.songbinwang.liveinhand.video.view;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.songbinwang.liveinhand.uitls.LogUtils;
import com.example.songbinwang.liveinhand.video.entity.VideoEntity;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Vector;

/**
 * Created by songbinwang on 2016/6/29.
 */
public class VideoSurfaceView extends SurfaceView implements VideoMediaController.MediaPlayerControl {

    private Context mContext;
    private static final String TAG = "VideoSurfaceView";
    private Uri mUri;
    private Map<String, String> mHeaders = null;

    private static final int STATE_ERROR = -1;
    private static final int STATE_IDLE = 0;
    private static final int STATE_PREPARING = 1;
    private static final int STATE_PREPARED = 2;
    private static final int STATE_PLAYING = 3;
    private static final int STATE_PAUSED = 4;
    private static final int STATE_PLAYBACK_COMPLETED = 5;

    private int mCurrentState = STATE_IDLE;
    private int mTargetState = STATE_IDLE;

    //all stuff we need to palying and showing a video
    private SurfaceHolder mSurfaceHolder;
    private MediaPlayer mMediaPlayer;
    private int mAudioSession = 0;
    private int mVideoWidth;
    private int mVideoHeight;
    private int mSurfaceViewWidth;
    private int mSurfaceViewHeight;
    private VideoMediaController mMediaController;
    private MediaPlayer.OnCompletionListener mOnCompletionListener;
    private MediaPlayer.OnPreparedListener mOnPreparedListener;
    private MediaPlayer.OnErrorListener mOnErrorListener;
    private MediaPlayer.OnInfoListener mOnInfoListener;

    private int mSeekWhenPrepared;  // recording the seek position while preparing
    private boolean mCanPause = true;
    private boolean mCanSeekBack = true;
    private boolean mCanSeekForward = true;

    private Vector<Pair<InputStream, MediaFormat>> mPendingSubtitleTrack;

    private VideoEntity video;

    private int mCurrentBufferPercentage;

    SurfaceHolder.Callback mSHCallback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            LogUtils.i(TAG, "surfaceCreated");
            mSurfaceHolder = holder;
            openVideo();
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            LogUtils.i(TAG, "surfaceChanged");
            mSurfaceViewWidth = width;
            mSurfaceViewHeight = height;
            if (width == mVideoWidth && height == mVideoHeight) {
                start();
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            LogUtils.i(TAG, "surfaceDestroyed");
            mSurfaceHolder = null;
            if (mMediaController != null) {
                mMediaController.hide();
            }
            release(true);
        }
    };

    public VideoSurfaceView(Context context) {
        super(context);
        mContext = context;
        initVideoView();
    }

    public VideoSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initVideoView();
    }

    public VideoSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initVideoView();
    }

    public VideoSurfaceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        initVideoView();
    }

    /**
     * 重新定义它的大小
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getDefaultSize(mVideoWidth, widthMeasureSpec);
        int height = getDefaultSize(mVideoHeight, heightMeasureSpec);
        if (mVideoWidth > 0 && mVideoHeight > 0) {

            int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
            int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
            int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
            int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);

            if (widthSpecMode == MeasureSpec.EXACTLY && heightSpecMode == MeasureSpec.EXACTLY) {
                width = widthSpecSize;
                height = heightSpecSize;
                //视频的宽度较小
                if (mVideoWidth * height < width * mVideoHeight) {
                    width = height * mVideoWidth / mVideoHeight;
                } else if (mVideoWidth * height > width * mVideoHeight) {
                    height = width * mVideoHeight / mVideoWidth;
                }
            } else if (widthSpecMode == MeasureSpec.EXACTLY) {
                width = widthSpecSize;
                height = width * mVideoHeight / mVideoWidth;
                if (heightSpecMode == MeasureSpec.AT_MOST && height > heightSpecSize) {
                    height = heightSpecSize;
                }
            } else if (heightSpecMode == MeasureSpec.EXACTLY) {
                height = heightSpecSize;
                width = height * mVideoWidth / mVideoHeight;
                if (widthSpecMode == MeasureSpec.AT_MOST && width > widthSpecSize) {
                    width = widthSpecSize;
                }
            } else {
                width = mVideoWidth;
                height = mVideoHeight;
                if (heightSpecMode == MeasureSpec.AT_MOST && height > heightSpecSize) {
                    // too tall, decrease both width and height
                    height = heightSpecSize;
                    width = height * mVideoWidth / mVideoHeight;
                }
                if (widthSpecMode == MeasureSpec.AT_MOST && width > widthSpecSize) {
                    // too wide, decrease both width and height
                    width = widthSpecSize;
                    height = width * mVideoHeight / mVideoWidth;
                }
            }
        } else {

        }
        setMeasuredDimension(width, height);
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return VideoSurfaceView.class.getName();
    }

    public int resolveAdjustedSize(int desiredSize, int measureSpec) {
        return getDefaultSize(desiredSize, measureSpec);
    }

    private void initVideoView() {
        mVideoWidth = 0;
        mVideoHeight = 0;
        getHolder().setKeepScreenOn(true);
        getHolder().addCallback(mSHCallback);
        //getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        setFocusable(true);
        setFocusableInTouchMode(true);
        requestFocus();
        mPendingSubtitleTrack = new Vector<Pair<InputStream, MediaFormat>>();
        mCurrentState = STATE_IDLE;
        mTargetState = STATE_IDLE;
    }

    public void setVideo(VideoEntity video){
        this.video = video;
        setVideoPath(video.getResUrl());
    }

    public void setVideoPath(String path) {
        setVideoUri(Uri.parse(path));
    }

    public void setVideoUri(Uri uri) {
        setVideoUri(uri, null);
    }

    public void setVideoUri(Uri uri, Map<String, String> headers) {
        mUri = uri;
        mHeaders = headers;
        mSeekWhenPrepared = 0;
        openVideo();
        requestLayout();
        invalidate();
    }

    /**
     * 重新播放
     */
    private void openVideo() {
        if (mUri == null || mSurfaceHolder == null) {
            return;
        }
        release(true);

        AudioManager audioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        audioManager.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

        try {
            mMediaPlayer = new MediaPlayer();

            if (mAudioSession == 0) {
                mAudioSession = mMediaPlayer.getAudioSessionId();
            } else {
                mMediaPlayer.setAudioSessionId(mAudioSession);
            }
            mMediaPlayer.setOnPreparedListener(mPreparedListener);
            mMediaPlayer.setOnVideoSizeChangedListener(mVideoSizeChangedListener);
            mMediaPlayer.setOnCompletionListener(mCompletionListener);
            mMediaPlayer.setOnInfoListener(mInfoListener);
            mMediaPlayer.setOnErrorListener(mErrorListener);
            mMediaPlayer.setOnBufferingUpdateListener(mBufferingUpdateListener);

            mMediaPlayer.setDataSource(mContext, mUri);
            mMediaPlayer.setDisplay(mSurfaceHolder);
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setScreenOnWhilePlaying(true);
            mMediaPlayer.prepare();
            mMediaPlayer.start();

            mCurrentState = STATE_PREPARING;
            attachMediaController();
        } catch (IOException e) {
            Log.w(TAG, "Unable to open content: " + mUri, e);
            mCurrentState = STATE_ERROR;
            mTargetState = STATE_ERROR;
            mErrorListener.onError(mMediaPlayer, MediaPlayer.MEDIA_ERROR_UNKNOWN, 0);
            return;
        } catch (IllegalArgumentException ex) {
            Log.w(TAG, "Unable to open content: " + mUri, ex);
            mCurrentState = STATE_ERROR;
            mTargetState = STATE_ERROR;
            mErrorListener.onError(mMediaPlayer, MediaPlayer.MEDIA_ERROR_UNKNOWN, 0);
            return;
        } finally {
            mPendingSubtitleTrack.clear();
        }
        LogUtils.i(TAG, "current_state:" + mCurrentState);
    }

    public void setMediaController(VideoMediaController mMediaController) {
        if (this.mMediaController != null) {
            this.mMediaController.hide();
        }
        this.mMediaController = mMediaController;
        attachMediaController();
    }

    private void attachMediaController() {
        if (mMediaPlayer != null && mMediaController != null) {
            mMediaController.setMediaPlayer(this);
            mMediaController.setEnabled(isInPlayInBackState());
        }
    }

    private boolean isInPlayInBackState() {
        if (mMediaPlayer != null) {
            return mCurrentState != STATE_IDLE && mCurrentState != STATE_ERROR &&
                    mCurrentState != STATE_PREPARING;
        }
        return false;
    }

    /**
     * 重置播放器
     *
     * @param clearTargetState
     */
    private void release(boolean clearTargetState) {
        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
            mPendingSubtitleTrack.clear();
            mCurrentState = STATE_IDLE;
            if (clearTargetState) {
                mTargetState = STATE_IDLE;
            }
            AudioManager audioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
            audioManager.abandonAudioFocus(null);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtils.i(TAG, "onTouchEvent:"+event.getAction());
        if (isInPlayInBackState() && mMediaController != null) {
            toggleMediaControllerVisiblity();
        }
        return false;
    }

    private void toggleMediaControllerVisiblity() {
        if (mMediaController.isShowing()) {
            mMediaController.hide();
        } else {
            mMediaController.show();
        }
    }

    MediaPlayer.OnPreparedListener mPreparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            LogUtils.i(TAG, "onPrepared");
            mCurrentState = STATE_PREPARED;

            if (mOnPreparedListener != null) {
                mOnPreparedListener.onPrepared(mp);
            }
            //start();
        }
    };

    MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            LogUtils.i(TAG, "onCompletion");
        }
    };

    MediaPlayer.OnInfoListener mInfoListener = new MediaPlayer.OnInfoListener() {
        @Override
        public boolean onInfo(MediaPlayer mp, int what, int extra) {
            LogUtils.i(TAG, "onInfo");
            return false;
        }
    };

    MediaPlayer.OnErrorListener mErrorListener = new MediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            LogUtils.i(TAG, "onError");
            return false;
        }
    };

    MediaPlayer.OnVideoSizeChangedListener mVideoSizeChangedListener = new MediaPlayer.OnVideoSizeChangedListener() {
        @Override
        public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
            LogUtils.i(TAG, "onVideoSizeChanged");
            mVideoWidth = mp.getVideoWidth();
            mVideoHeight = mp.getVideoHeight();
            if (mVideoWidth != 0 && mVideoHeight != 0) {
                getHolder().setFixedSize(mVideoWidth, mVideoHeight);
                requestLayout();
            }
        }
    };

    MediaPlayer.OnBufferingUpdateListener mBufferingUpdateListener = new MediaPlayer.OnBufferingUpdateListener() {
        @Override
        public void onBufferingUpdate(MediaPlayer mp, int percent) {
            LogUtils.i(TAG, "onBufferingUpdate");
            mCurrentBufferPercentage = percent;
        }
    };

    public void stopPlayback() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
            mCurrentState = STATE_IDLE;
            mTargetState = STATE_IDLE;
            AudioManager audioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
            audioManager.abandonAudioFocus(null);
        }
    }

    @Override
    public void start() {
        if (isInPlayInBackState()) {
            mMediaPlayer.start();
            mCurrentState = STATE_PLAYING;
        }
        mTargetState = STATE_PLAYING;
    }

    @Override
    public void pause() {
        if (isInPlayInBackState()) {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.pause();
            }
            mCurrentState = STATE_PAUSED;
        }
        mCurrentState = STATE_PAUSED;
    }

    @Override
    public int getDuration() {
        if (isInPlayInBackState()){
            return mMediaPlayer.getDuration();
        }
        return -1;
    }

    @Override
    public int getCurrentPosition() {
        if (isInPlayInBackState()) {
            return mMediaPlayer.getCurrentPosition();
        }
        return -1;
    }

    @Override
    public void seekTo(int pos) {
        if(isInPlayInBackState()){
            mMediaPlayer.seekTo(pos);
            mSeekWhenPrepared = 0;
        }else{
            mSeekWhenPrepared = pos;
        }
    }

    @Override
    public boolean isPlaying() {
        return isInPlayInBackState() && mMediaPlayer.isPlaying();
    }

    @Override
    public int getBufferPercentage() {
        if (mMediaPlayer == null) {
            return mCurrentBufferPercentage;
        }
        return 0;
    }

    @Override
    public boolean canPause() {
        return mCanPause;
    }

    @Override
    public boolean canSeekBackward() {
        return mCanSeekBack;
    }

    @Override
    public boolean canSeekForward() {
        return mCanSeekForward;
    }

    @Override
    public int getAudioSessionId() {
        return mAudioSession;
    }

    @Override
    public String getTitle(){
        if (video != null) {
            return video.getVideoName();
        }
        return "";
    }
}
