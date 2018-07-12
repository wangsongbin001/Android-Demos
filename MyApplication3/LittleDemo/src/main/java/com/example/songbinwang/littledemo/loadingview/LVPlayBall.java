package com.example.songbinwang.littledemo.loadingview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by songbinwang on 2016/8/30.
 */
public class LVPlayBall extends View {

    private Paint mPaint, mPaintBall;

    private float mWidth;
    private float mHeight;
    private Context mContext;
    private float mPadding, mCircleRadius, mStrokeWidth, mBallRadius;
    private Path mPath;

    private float ballY, quadY;

    private ValueAnimator valueAnimator;
    private boolean isInAnim;

    public LVPlayBall(Context context) {
        this(context, null);
    }

    public LVPlayBall(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LVPlayBall(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(dp2px(1));

        mPaintBall = new Paint();
        mPaintBall.setAntiAlias(true);
        mPaintBall.setStyle(Paint.Style.FILL);
        mPaintBall.setColor(Color.WHITE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

        mPadding = dp2px(2);
        mCircleRadius = dp2px(3);

        mStrokeWidth = dp2px(1f);
        mBallRadius = dp2px(4);
        ballY = mHeight / 2f;
        quadY = mHeight / 2f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawTwoCircle(canvas);
        drawPath(canvas);
        drawBall(canvas);
    }

    private void drawTwoCircle(Canvas canvas) {
        mPaint.setStrokeWidth(mStrokeWidth);
        canvas.drawCircle(mPadding + mCircleRadius + mStrokeWidth, mHeight / 2f, mCircleRadius, mPaint);
        canvas.drawCircle(mWidth - mPadding - mCircleRadius - mStrokeWidth, mHeight / 2f, mCircleRadius, mPaint);
    }

    private void drawPath(Canvas canvas) {
        mPaint.setStrokeWidth(2);
        mPath = new Path();
        mPath.moveTo(mPadding + 2f * mCircleRadius + mStrokeWidth, mHeight / 2);
        mPath.quadTo(mWidth / 2, quadY, mWidth - mStrokeWidth - 2 * mCircleRadius - mPadding, mHeight / 2f);
        canvas.drawPath(mPath, mPaint);
    }

    private void drawBall(Canvas canvas) {
        if (ballY - mBallRadius < mBallRadius) {
            canvas.drawCircle(mWidth / 2f, mBallRadius, mBallRadius, mPaintBall);
        } else {
            canvas.drawCircle(mWidth / 2f, ballY - mBallRadius, mBallRadius, mPaintBall);
        }

    }

    private int dp2px(float dp) {
        float density = mContext.getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5);
    }

    public void stopAnim() {
        if (valueAnimator != null) {
            clearAnimation();
            valueAnimator.setRepeatCount(1);
            valueAnimator.cancel();
            valueAnimator.end();
            quadY = mHeight / 2f;
            ballY = mHeight / 2f;
            postInvalidate();
        }
        isInAnim = false;
    }

    public void startAnim() {
        stopAnim();
        valueAnimator = ValueAnimator.ofFloat(0f, 1f);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(1500);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                if (value > 0.75) {
                    quadY = mHeight / 2f - mHeight / 3f * (1f - value);
                } else {
                    quadY = mHeight / 2f + mHeight / 3f * (1f - value);
                }

                if (value > 0.35f) {
                    ballY = mHeight / 2f - mHeight / 2f * value;
                } else {
                    ballY = mHeight / 2f + mHeight / 5f * value;
                }

                invalidate();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
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
