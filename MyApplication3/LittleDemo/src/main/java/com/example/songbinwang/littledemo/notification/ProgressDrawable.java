package com.example.songbinwang.littledemo.notification;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

/**
 * Created by songbinwang on 2017/2/13.
 */

public class ProgressDrawable extends Drawable{

    private Paint mPaint;
    private RectF mRect;
    private int progress = 90;

    public ProgressDrawable(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        mPaint.setColor(Color.YELLOW);
    }

    public void setProgress(int progress) {
        this.progress = progress * 360 / 100;
    }

    @Override
    public void draw(Canvas canvas) {
        float cx = (mRect.right + mRect.left) / 2f;
        float cy = (mRect.top + mRect.bottom) / 2f;
        float radius = Math.min(mRect.width(), mRect.height())/2 -40;
        canvas.drawArc(cx-radius, cy-radius, cx+ radius, cy+ radius, 0, progress, false, mPaint);
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);
        mRect = new RectF(left, top, right, bottom);
    }

    @Override
    public int getIntrinsicWidth() {
        if(mRect != null){
            return (int) mRect.width();
        }
        return 100;
    }

    @Override
    public int getIntrinsicHeight() {
        if(mRect != null){
            return (int) mRect.height();
        }
        return 100;
    }

    @Override
    public void setAlpha(int alpha) {
       mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
