package com.example.songbinwang.littledemo.videoplayer;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.songbinwang.littledemo.R;

/**
 * Created by songbinwang on 2016/6/7.
 */
public class MediaPlayerActivity extends Activity{

    private SurfaceView mSurfaceView;
    private SurfaceHolder mHolder;
    private int Height = 320;
    private int Width = 320;
    private int x_offset = 5;
    private int y_offset = Height/2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mediaplayer);

        mSurfaceView = (SurfaceView) findViewById(R.id.sv_content);
        mHolder = mSurfaceView.getHolder();
        mHolder.addCallback(mCallback);
    }

    SurfaceHolder.Callback mCallback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {

        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
              drawBack();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }
    };


    private void drawBack(){
        Canvas canvas = mHolder.lockCanvas();
        //绘制白色的背景
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        //绘制坐标线。
        canvas.drawLine(x_offset,y_offset,Width, y_offset, paint);
        canvas.drawLine(x_offset,0, x_offset, Height, paint);
        mHolder.unlockCanvasAndPost(canvas);
        mHolder.lockCanvas(new Rect(0,0,0,0));
        mHolder.unlockCanvasAndPost(canvas);
    }


}
