package com.example.songbinwang.littledemo.loadingview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
public class LVCircular extends View {

    private Paint mPaint;
    private int mWidth;
    private int centerCircleRadius;
    private int littleCircleRadius;
    private int startAngle = 0;
    private Animation mProgressAnimation;
    private boolean isInAnim;

    public LVCircular(Context context) {
        this(context, null);
    }

    public LVCircular(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LVCircular(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.WHITE);

        mProgressAnimation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mProgressAnimation.setInterpolator(new LinearInterpolator());
        mProgressAnimation.setRepeatCount(-1);
        mProgressAnimation.setDuration(3500);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        centerCircleRadius = (int) ((mWidth / 2) * 4.0f / 6);
        littleCircleRadius = (int) ((mWidth / 2) * 1.0f / 20);
    }

    public void startAnim() {
        stopAnim();
        startAnimation(mProgressAnimation);
        isInAnim = true;
    }

    public void stopAnim() {
        clearAnimation();
        isInAnim = false;
    }

    public boolean isInAnim() {
        return isInAnim;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < 9; i++) {
            float x1 = mWidth / 2 + (float) ((mWidth / 2f - littleCircleRadius) * Math.sin(2 * Math.PI / 9 * i));
            float y1 = mWidth / 2 + (float) ((mWidth / 2f - littleCircleRadius) * Math.cos(2 * Math.PI / 9 * i));
            canvas.drawCircle(x1, y1, littleCircleRadius, mPaint);
        }
        canvas.drawCircle(mWidth / 2, mWidth / 2, centerCircleRadius, mPaint);
    }
}
