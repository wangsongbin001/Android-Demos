package com.example.songbinwang.littledemo.loadingview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

/**
 * Created by songbinwang on 2016/8/19.
 * 1，画出图案，
 * 2，使用RotateAnimation完成旋转
 */
public class LVCircularRing extends View{

    private Paint mPaint;
    private int mWidth;
    private int mPadding;
    private Animation mProgressAnimation;
    private boolean isInAnim;
    private RectF mRectF;

    public LVCircularRing(Context context) {
        this(context, null);
    }

    public LVCircularRing(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LVCircularRing(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
        initAnima();
    }

    private void initPaint(){
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.WHITE);
    }

    private void initAnima(){
        mProgressAnimation =  new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mProgressAnimation.setRepeatCount(-1);
        mProgressAnimation.setInterpolator(new LinearInterpolator());
    }

    public void startAnim(){
        stopAnim();
        mProgressAnimation.setDuration(1500);
        startAnimation(mProgressAnimation);
        isInAnim = true;
    };

    public void stopAnim(){
        clearAnimation();
        isInAnim = false;
    }

    public boolean isInAnim(){
        return isInAnim;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mPadding = 5;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStrokeWidth(4);
        mPaint.setColor(Color.GRAY);
        canvas.drawCircle(mWidth / 2, mWidth / 2, mWidth / 2 - mPadding, mPaint);

        mPaint.setColor(Color.WHITE);
        mRectF = new RectF(mPadding, mPadding, mWidth - mPadding, mWidth - mPadding);
        canvas.drawArc(mRectF,0, 80, false, mPaint);
    }
}
