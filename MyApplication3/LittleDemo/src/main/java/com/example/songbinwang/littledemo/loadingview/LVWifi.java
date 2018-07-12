package com.example.songbinwang.littledemo.loadingview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by songbinwang on 2016/8/28.
 */
public class LVWifi extends View {

    private Paint mPaint, mPaintArc;

    private float mWidth;

    private Context mContext;

    private float mPadding;

    private float animatorValue = 1;
    private ValueAnimator valueAnimator;
    private boolean isInAnim;

    private Point centerPoint;

    private int startAngle = -90 - 45;
    float signalRadiu;

    public LVWifi(Context context) {
        this(context, null);
    }

    public LVWifi(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LVWifi(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.WHITE);

        mPaintArc = new Paint();
        mPaintArc.setAntiAlias(true);
        mPaintArc.setStyle(Paint.Style.STROKE);
        mPaintArc.setColor(Color.WHITE);
        mPaintArc.setStrokeWidth(dp2px(2));
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

        centerPoint = new Point();
        centerPoint.x = mWidth / 2;
        centerPoint.y = mWidth * 3.0f / 4;

        signalRadiu = mWidth / 8f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (animatorValue >= 0 && animatorValue < 0.25) {
            drawWifi(canvas, 1);
        } else if (animatorValue >= 0.25 && animatorValue < 0.5) {
            drawWifi(canvas, 2);
        } else if (animatorValue >= 0.5 && animatorValue < 0.75) {
            drawWifi(canvas, 3);
        } else if (animatorValue >= 0.75 && animatorValue <= 1) {
            drawWifi(canvas, 4);
        }
    }

    private void drawWifi(Canvas canvas, int index) {
        switch (index) {
            case 1:
                drawArcOne(canvas);
                break;
            case 2:
                drawArcOne(canvas);
                drawArcSecond(canvas);
                break;
            case 3:
                drawArcOne(canvas);
                drawArcSecond(canvas);
                drawArcThird(canvas);
                break;
            case 4:
                drawArcOne(canvas);
                drawArcSecond(canvas);
                drawArcThird(canvas);
                drawArcFourTh(canvas);
                break;
        }
    }

    private void drawArcOne(Canvas canvas) {
        canvas.drawArc(getRectF(centerPoint, signalRadiu), startAngle, 90, true, mPaint);
    }

    private void drawArcSecond(Canvas canvas) {
        canvas.drawArc(getRectF(centerPoint, 2 * signalRadiu), startAngle, 90, false, mPaintArc);
    }

    private void drawArcThird(Canvas canvas) {
        canvas.drawArc(getRectF(centerPoint, 3 * signalRadiu), startAngle, 90, false, mPaintArc);
    }

    private void drawArcFourTh(Canvas canvas) {
        canvas.drawArc(getRectF(centerPoint, 4 * signalRadiu), startAngle, 90, false, mPaintArc);
    }

    private float dp2px(float dp) {
        float density = mContext.getResources().getDisplayMetrics().density;
        return (float) (density * dp + 0.5);
    }

    private RectF getRectF(Point p, float radius) {
        return new RectF(p.x - radius, p.y - radius, p.x + radius, p.y + radius);
    }

    public void stopAnim() {
        if (valueAnimator != null) {
            clearAnimation();
            valueAnimator.setRepeatCount(1);
            valueAnimator.setRepeatMode(ValueAnimator.RESTART);
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
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.setDuration(1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animatorValue = (float) animation.getAnimatedValue();
                invalidate();
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

    class Point {
        float x;
        float y;
    }

}
