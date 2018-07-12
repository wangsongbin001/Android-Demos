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
public class LVCircularCD extends View {

    private Paint mPaint;

    private RectF mRectF1, mRectF2;

    private int mWidth;

    private int mPadding;

    private Animation mProgressAnimation;

    private boolean isInAnim;

    public LVCircularCD(Context context) {
        this(context, null);
    }

    public LVCircularCD(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LVCircularCD(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
        initAnimation();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.WHITE);
    }

    private void initAnimation() {
        mProgressAnimation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mProgressAnimation.setRepeatCount(-1);
        mProgressAnimation.setInterpolator(new LinearInterpolator());
    }

    public void startAnim() {
        stopAnim();
        mProgressAnimation.setDuration(1500);
        startAnimation(mProgressAnimation);
        isInAnim = true;
    }

    public void stopAnim() {
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
        mPaint.setStrokeWidth(2);
        canvas.drawCircle(mWidth / 2, mWidth / 2, mWidth / 2 - mPadding, mPaint);

        mPaint.setStrokeWidth(3);
        canvas.drawCircle(mWidth / 2, mWidth / 2, mPadding, mPaint);

        mRectF1 = new RectF(mWidth * 1.0f / 8, mWidth * 1.0f / 8, mWidth * 7.0f / 8, mWidth * 7.0f / 8);
        canvas.drawArc(mRectF1, 0, 80, false, mPaint);
        canvas.drawArc(mRectF1, 180, 80, false, mPaint);

        mRectF1 = new RectF(mWidth * 2.0f / 8, mWidth * 2.0f / 8, mWidth * 6.0f / 8, mWidth * 6.0f / 8);
        canvas.drawArc(mRectF1, 0, 80, true, mPaint);
        canvas.drawArc(mRectF1, 180, 80, true, mPaint);
    }
}
