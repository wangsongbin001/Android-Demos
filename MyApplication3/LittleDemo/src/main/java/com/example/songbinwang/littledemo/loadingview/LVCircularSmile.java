package com.example.songbinwang.littledemo.loadingview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by songbinwang on 2016/8/25.
 * 利用valueAnimator
 * 根据value的不同阶段做不同的处理
 */
public class LVCircularSmile extends View {

    private Paint mPaint;
    private float mWidth;
    private int duration = 1500;
    private float animateValue = 1;
    private boolean isInAnim;
    private Context mContext;
    private float mPadding;
    private float mEyeWidth;
    private RectF mRectF;
    private ValueAnimator valueAnimator;
    private float startAngle = 0;
    private boolean isSimle = true;

    public LVCircularSmile(Context context) {
        this(context, null);
    }

    public LVCircularSmile(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LVCircularSmile(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(dp2px(2));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getMeasuredWidth() > getMeasuredHeight()) {
            mWidth = getMeasuredHeight();
        } else {
            mWidth = getMeasuredWidth();
        }
        mPadding = dp2px(10);
        mEyeWidth = dp2px(3);
        mRectF = new RectF(mPadding, mPadding, mWidth - mPadding, mWidth - mPadding);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(mRectF, startAngle, 180, false, mPaint);
        if (isSimle) {
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(mPadding + mEyeWidth + mEyeWidth / 2, mWidth / 3, mEyeWidth, mPaint);
            canvas.drawCircle(mWidth - mPadding - mEyeWidth - mEyeWidth / 2, mWidth / 3, mEyeWidth, mPaint);
        }
    }

    public void startAnim() {
        stopAnim();
        valueAnimator = ValueAnimator.ofFloat(0f, 1f);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.setDuration(duration);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animateValue = (float) animation.getAnimatedValue();
                if (animateValue < 0.5f) {
                    startAngle = 720 * animateValue;
                    isSimle = false;
                } else {
                    startAngle = 720;
                    isSimle = true;
                }
                invalidate();
            }
        });
        if(!valueAnimator.isRunning()){
            valueAnimator.start();
        }
        isInAnim = true;
    }

    public void stopAnim() {
        if (valueAnimator != null) {
            clearAnimation();
            valueAnimator.setRepeatCount(1);
            valueAnimator.cancel();
            valueAnimator.end();
        }
        isInAnim = false;
    }

    private float dp2px(float dp) {
        float density = mContext.getResources().getDisplayMetrics().density;
        return (float) (dp * density + 0.5);
    }

    public boolean isInAnim(){
        return isInAnim;
    }
}
