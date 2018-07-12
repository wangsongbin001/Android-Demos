package com.example.songbinwang.littledemo.loadingview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by songbinwang on 2016/8/25.
 */
public class LVGear extends View {

    private Paint mPaint, mPaintWheelBig, mPaintWheelSmall, mPaintAxle, mPaintCenterCircle;
    private float mWidth;
    private float mPadding;
    private Context mContext;

    private int mWheelBigSpace = 6;
    private int mWheelSmallSpace = 8;

    private float mWheelBigLength;
    private float mWheelSmallLength;
    private float mPaintCenterRadius;

    private float animatorValue = 0;
    private boolean isInAnim;
    private ValueAnimator valueAnimator;

    public LVGear(Context context) {
        this(context, null);
    }

    public LVGear(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LVGear(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(dp2px(2));

        mPaintWheelBig = new Paint();
        mPaintWheelBig.setAntiAlias(true);
        mPaintWheelBig.setStyle(Paint.Style.STROKE);
        mPaintWheelBig.setColor(Color.WHITE);
        mPaintWheelBig.setStrokeWidth(dp2px(1));

        mPaintWheelSmall = new Paint();
        mPaintWheelSmall.setAntiAlias(true);
        mPaintWheelSmall.setStyle(Paint.Style.STROKE);
        mPaintWheelSmall.setColor(Color.WHITE);
        mPaintWheelSmall.setStrokeWidth(dp2px(1));

        mPaintAxle = new Paint();
        mPaintAxle.setAntiAlias(true);
        mPaintAxle.setStyle(Paint.Style.FILL);
        mPaintAxle.setColor(Color.WHITE);
        mPaintAxle.setStrokeWidth(dp2px(2));

        mPaintCenterCircle = new Paint();
        mPaintCenterCircle.setAntiAlias(true);
        mPaintCenterCircle.setStyle(Paint.Style.STROKE);
        mPaintCenterCircle.setColor(Color.WHITE);
        mPaintCenterCircle.setStrokeWidth(dp2px(0.5f));
    }

    private float dp2px(float dp) {
        float density = mContext.getResources().getDisplayMetrics().density;
        return (float) (dp * density + 0.5);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getMeasuredWidth() > getMeasuredHeight()) {
            mWidth = getMeasuredHeight();
        } else {
            mWidth = getMeasuredWidth();
        }
        mPadding = dp2px(5);
        mWheelBigLength = dp2px(2.5f);
        mWheelSmallLength = dp2px(3f);
        mPaintCenterRadius = dp2px(2.5f) / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCircle(canvas);
        drawWheelBig(canvas);
        drawWheelSmall(canvas);
        drawAxleAndCenter(canvas);
    }

    private void drawCircle(Canvas canvas) {
        canvas.drawCircle(mWidth / 2, mWidth / 2, mWidth / 2 - mPadding, mPaint);
        canvas.drawCircle(mWidth / 2, mWidth / 2, mWidth / 4, mPaint);
    }

    private void drawWheelBig(Canvas canvas) {
        for (int i = 0; i < 360; i += mWheelBigSpace) {
            int angle = (int) (animatorValue * mWheelBigSpace + i);
            float x = (float) ((mWidth / 2f - mPadding) * Math.cos(angle * Math.PI / 180f));
            float y = (float) ((mWidth / 2f - mPadding) * Math.sin(angle * Math.PI / 180f));
            float x2 = (float) ((mWidth / 2f - mPadding + mWheelBigLength) * Math.cos(angle * Math.PI / 180f));
            float y2 = (float) ((mWidth / 2f - mPadding + mWheelBigLength) * Math.sin(angle * Math.PI / 180f));
            canvas.drawLine(mWidth / 2f + x, mWidth / 2f - y, mWidth / 2f + x2, mWidth / 2f - y2, mPaintWheelBig);
        }
    }

    private void drawWheelSmall(Canvas canvas) {
        for (int i = 0; i < 360; i += mWheelSmallSpace) {
            int angle = (int) ((1- animatorValue) * mWheelSmallSpace + i);
            float x = (float) ((mWidth / 4f) * Math.cos(angle * Math.PI / 180f));
            float y = (float) ((mWidth / 4f) * Math.sin(angle * Math.PI / 180f));
            float x2 = (float) ((mWidth / 4f + mWheelSmallLength) * Math.cos(angle * Math.PI / 180f));
            float y2 = (float) ((mWidth / 4f + mWheelSmallLength) * Math.sin(angle * Math.PI / 180f));
            canvas.drawLine(mWidth / 2f + x, mWidth / 2f - y, mWidth / 2f + x2, mWidth / 2f - y2, mPaintWheelSmall);
        }
    }

    private void drawAxleAndCenter(Canvas canvas) {
        for (int i = 0; i < 3; i++) {
            float x = (float) (mPaintCenterRadius * Math.cos((-90 + i * 360 / 3) * Math.PI / 180f));
            float y = (float) (mPaintCenterRadius * Math.sin((-90 + i * 360 / 3) * Math.PI / 180f));
            float x1 = (float) ((mWidth / 2f - mPadding) * Math.cos((-90 + i * 360 / 3) * Math.PI / 180f));
            float y1 = (float) ((mWidth / 2f - mPadding) * Math.sin((-90 + i * 360 / 3) * Math.PI / 180f));
            canvas.drawLine(mWidth / 2f + x, mWidth / 2f - y, mWidth / 2f + x1, mWidth / 2f - y1, mPaintAxle);
        }
        canvas.drawCircle(mWidth/2f, mWidth/2f, mPaintCenterRadius + 1, mPaintCenterCircle);
    }

    public boolean isInAnim(){
        return isInAnim;
    }

    public void stopAnim(){
        if(valueAnimator != null){
            clearAnimation();
            valueAnimator.setRepeatCount(1);
            valueAnimator.cancel();
            valueAnimator.end();
            animatorValue = 0;
        }
        isInAnim = false;
    }

    public void startAnim(){
        stopAnim();
        valueAnimator = ValueAnimator.ofFloat(0f,1f);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.setDuration(300);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animatorValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        if(!valueAnimator.isRunning()){
            valueAnimator.start();
        }
        isInAnim = true;
    }
}
