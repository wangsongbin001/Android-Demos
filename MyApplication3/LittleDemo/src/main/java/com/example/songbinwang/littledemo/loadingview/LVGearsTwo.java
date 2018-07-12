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
 * Created by songbinwang on 2016/8/28.
 */
public class LVGearsTwo extends View {

    private Paint mPaintLine;

    private float mWidth;

    private float mRadiusBig, mRadiusSmall;

    private float mPadding;

    private Context mContext;

    private float mGearLength;
    private float bigWheelCenterX, bigWheelCenterY, smallWheelCenterX, smallWheelCenterY;

    private float animatorValue;
    private ValueAnimator valueAnimator;
    private boolean isInAnim;
    private int mWheelBigSpace = 6, mWheelSmallSpace = 10;

    public LVGearsTwo(Context context) {
        this(context, null);
    }

    public LVGearsTwo(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LVGearsTwo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initPaint();
    }

    private void initPaint() {
        mPaintLine = new Paint();
        mPaintLine.setAntiAlias(true);
        mPaintLine.setStyle(Paint.Style.STROKE);
        mPaintLine.setColor(Color.WHITE);
        mPaintLine.setStrokeWidth(dp2px(1f));


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getMeasuredHeight() > getMeasuredWidth()) {
            mWidth = getMeasuredWidth();
        } else {
            mWidth = getMeasuredHeight();
        }
        mPadding = dp2px(5);
        mGearLength = dp2px(5) /2;

        float paddingWidth = mWidth - 2 * mPadding;
        bigWheelCenterX = mPadding + paddingWidth * 1 / 2f;
        bigWheelCenterY = mPadding + paddingWidth * 1 / 2f;
        smallWheelCenterX = mWidth - mPadding - paddingWidth / 6;
        smallWheelCenterY = mWidth - mPadding - paddingWidth / 6;

        float lengthBetweenTwoPoint = (float) Math.sqrt((smallWheelCenterX - bigWheelCenterX) * (smallWheelCenterX - bigWheelCenterX)
                + (smallWheelCenterY - bigWheelCenterY) * (smallWheelCenterY - bigWheelCenterY));

        mRadiusBig = (lengthBetweenTwoPoint - mGearLength *  2) * 2 / 3f;
        mRadiusSmall = (lengthBetweenTwoPoint - mGearLength * 2) / 3f;
    }

    private int dp2px(float dp) {
        float density = mContext.getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBigWheel(canvas);
        drawSmallWheel(canvas);
        drawAxle(canvas);
        drawSmallGears(canvas);
        drawBigGears(canvas);
    }

    private void drawBigWheel(Canvas canvas) {
        mPaintLine.setStrokeWidth(dp2px(1));
        canvas.drawCircle(bigWheelCenterX, bigWheelCenterY, mRadiusBig, mPaintLine);
        mPaintLine.setStrokeWidth(dp2px(1.5f));
        canvas.drawCircle(bigWheelCenterX, bigWheelCenterY, mRadiusBig / 2, mPaintLine);
    }

    private void drawSmallWheel(Canvas canvas) {
        mPaintLine.setStrokeWidth(dp2px(1));
        canvas.drawCircle(smallWheelCenterX, smallWheelCenterY, mRadiusSmall, mPaintLine);
        mPaintLine.setStrokeWidth(dp2px(1.5f));
        canvas.drawCircle(smallWheelCenterX, smallWheelCenterY, mRadiusSmall / 2, mPaintLine);
    }

    private void drawAxle(Canvas canvas) {
        mPaintLine.setStrokeWidth(dp2px(2));
        for (int i = 0; i < 3; i++) {
            int angle = -90 + 360 * i / 3;
            float xbig = (float) (mRadiusBig * Math.cos(angle * Math.PI / 180));
            float ybig = (float) (mRadiusBig * Math.sin(angle * Math.PI / 180));
            float xSmall = (float) (mRadiusSmall * Math.cos(angle * Math.PI / 180));
            float ySmall = (float) (mRadiusSmall * Math.sin(angle * Math.PI / 180));

            canvas.drawLine(bigWheelCenterX + xbig, bigWheelCenterY - ybig, bigWheelCenterX, bigWheelCenterY, mPaintLine);
            canvas.drawLine(smallWheelCenterX + xSmall, smallWheelCenterY - ySmall, smallWheelCenterX, smallWheelCenterY, mPaintLine);
        }
    }

    private void drawSmallGears(Canvas canvas) {
        mPaintLine.setStrokeWidth(dp2px(1));
        for (int i = 0; i < 360; i += mWheelSmallSpace) {
             float angle = i + mWheelSmallSpace * animatorValue;
             float x1 = (float) (mRadiusSmall * Math.cos(angle * Math.PI / 180));
             float y1 = (float) (mRadiusSmall * Math.sin(angle * Math.PI / 180));
             float x2 = (float) ((mRadiusSmall + mGearLength) * Math.cos(angle * Math.PI / 180));
             float y2 = (float) ((mRadiusSmall + mGearLength) * Math.sin(angle * Math.PI / 180));
             canvas.drawLine(smallWheelCenterX - x1, smallWheelCenterY - y1, smallWheelCenterX - x2, smallWheelCenterY - y2,mPaintLine);
        }

    }

    private void drawBigGears(Canvas canvas) {
        mPaintLine.setStrokeWidth(dp2px(1.5f));
        for (int i = 0; i < 360; i += mWheelBigSpace) {
            float angle = i - mWheelBigSpace * animatorValue;
            float x1 = (float) (mRadiusBig * Math.cos(angle * Math.PI / 180));
            float y1 = (float) (mRadiusBig * Math.sin(angle * Math.PI / 180));
            float x2 = (float) ((mRadiusBig + mGearLength) * Math.cos(angle * Math.PI / 180));
            float y2 = (float) ((mRadiusBig + mGearLength) * Math.sin(angle * Math.PI / 180));
            canvas.drawLine(bigWheelCenterX - x1, bigWheelCenterY - y1, bigWheelCenterX - x2, bigWheelCenterY - y2,mPaintLine);
        }
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
        valueAnimator  = ValueAnimator.ofFloat(0f, 1f);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.setInterpolator(new LinearInterpolator());
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

    public boolean isInAnim(){
        return isInAnim;
    }
}
