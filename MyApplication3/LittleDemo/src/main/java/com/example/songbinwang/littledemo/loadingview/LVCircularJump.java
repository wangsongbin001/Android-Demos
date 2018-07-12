package com.example.songbinwang.littledemo.loadingview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by songbinwang on 2016/8/30.
 */
public class LVCircularJump extends View {

    private Paint mPaint;

    private float mWidth;

    private int circularCount = 4;
    private float space;
    private float circularRadius = 6;

    private float animatorValue;
    private ValueAnimator valueAnimator;
    private boolean isInAnim = false;

    private int jumpIndex = 0;

    public LVCircularJump(Context context) {
        this(context, null);
    }

    public LVCircularJump(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LVCircularJump(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.WHITE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getMeasuredHeight() > getMeasuredWidth()) {
            mWidth = getMeasuredWidth();
        } else {
            mWidth = getMeasuredHeight();
        }
        space = mWidth / circularCount;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < circularCount; i++) {
            if (jumpIndex % circularCount == i) {
                canvas.drawCircle(i * space + space / 2f, mWidth / 2f - mWidth / 2f * animatorValue, circularRadius, mPaint);
                continue;
            }
            canvas.drawCircle(i * space + space / 2f, mWidth / 2f, circularRadius, mPaint);
        }
    }

    public void stopAnim() {
        if (valueAnimator != null) {
            clearAnimation();
            valueAnimator.setRepeatCount(1);
            valueAnimator.cancel();
            valueAnimator.end();
            animatorValue = 0;
            postInvalidate();
        }
        isInAnim = false;
    }

    public void startAnim() {
        stopAnim();
        valueAnimator = ValueAnimator.ofFloat(0f, 1f);
        valueAnimator.setDuration(500);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animatorValue = (float) animation.getAnimatedValue();
                if(animatorValue >  0.5){
                    animatorValue = 1 - animatorValue;
                }
                invalidate();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
                jumpIndex ++;
            }
        });
        if (!valueAnimator.isRunning()) {
            valueAnimator.start();
        }
        isInAnim = true;
    }

    public boolean isInAnim() {
        return isInAnim;
    }
}
