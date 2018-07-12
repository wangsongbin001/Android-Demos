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
public class LVCircularZoom extends View {

    private Paint mPaint;
    private int circularCount = 3;

    private float mWidth;

    private int zoomIndex;
    private float animatorValue = 1;
    private ValueAnimator valueAnimator;
    private boolean isInAnim;

    private float space;

    private float circularRadius = 8;

    public LVCircularZoom(Context context) {
        this(context, null);
    }

    public LVCircularZoom(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LVCircularZoom(Context context, AttributeSet attrs, int defStyleAttr) {
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
        if (getMeasuredWidth() > getMeasuredHeight()) {
            mWidth = getMeasuredHeight();
        } else {
            mWidth = getMeasuredWidth();
        }

        space = mWidth / circularCount;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < circularCount; i++) {
            if (zoomIndex % circularCount == i) {
//                canvas.drawCircle(i * space + space / 2, mWidth / 2f, circularRadius + circularRadius * animatorValue, mPaint);
                canvas.drawCircle(i * space + space / 2, mWidth / 2f, circularRadius * animatorValue, mPaint);
                continue;
            }
            canvas.drawCircle(i * space + space / 2, mWidth / 2f, circularRadius, mPaint);
        }
    }

    public void stopAnim() {
        if (valueAnimator != null) {
            clearAnimation();
            valueAnimator.setRepeatCount(1);
            valueAnimator.cancel();
            valueAnimator.end();
            animatorValue = 1;
            postInvalidate();
        }
        isInAnim = false;
    }

    public void startAnim() {
        stopAnim();
        valueAnimator = ValueAnimator.ofFloat(0f, 1f);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animatorValue = (float) animation.getAnimatedValue();
//                if (animatorValue < 0.25) {
//                    animatorValue = -animatorValue;
//                } else if (animatorValue >= 0.25 && animatorValue < 0.5) {
//                    animatorValue = animatorValue - 0.5f;
//                } else if (animatorValue >= 0.5 && animatorValue < 0.75) {
//                    animatorValue = animatorValue - 0.5f;
//                }else if (animatorValue >= 0.75 && animatorValue < 1) {
//                    animatorValue = 1 - animatorValue;
//                }
                invalidate();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
                zoomIndex++;
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
