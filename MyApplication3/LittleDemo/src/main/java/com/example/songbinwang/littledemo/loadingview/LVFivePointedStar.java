package com.example.songbinwang.littledemo.loadingview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songbinwang on 2016/8/22.
 * 1，使用ValueAnimator
 * 2，对不同的阶段，做不同的处理。
 * 3，调用invalidate，达到动画的效果
 */
public class LVFivePointedStar extends View {

    private Paint mPaintLine, mPaintCircle;
    private float mWidth;
    private float mPadding;
    private List<Point> listPoint = new ArrayList<Point>();
    private Context mContext;
    private int hornCount = 5;

    private float animatorValue = 0.75f;
    private ValueAnimator valueAnimator;

    private RectF rectF;
    private boolean isDrawPath = false;
    private boolean isInAnim = false;

    public LVFivePointedStar(Context context) {
        this(context, null);
    }

    public LVFivePointedStar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LVFivePointedStar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initPaint();
    }

    private void initPaint() {
        mPaintLine = new Paint();
        mPaintLine.setAntiAlias(true);
        mPaintLine.setStyle(Paint.Style.FILL);
        mPaintLine.setColor(Color.WHITE);
        mPaintLine.setStrokeWidth(dp2px(1));

        mPaintCircle = new Paint();
        mPaintCircle.setAntiAlias(true);
        mPaintCircle.setStyle(Paint.Style.STROKE);
        mPaintCircle.setColor(Color.WHITE);
        mPaintCircle.setStrokeWidth(dp2px(1));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getMeasuredWidth() > getMeasuredHeight()) {
            mWidth = getMeasuredHeight();
        } else {
            mWidth = getMeasuredWidth();
        }
        mPadding = dp2px(1);
        rectF = new RectF(mPadding, mPadding, mWidth - mPadding, mWidth - mPadding);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        listPoint.clear();
        for (int i = 0; i < hornCount; i++) {
            Point p = getPoint(mWidth / 2 - mPadding, i);
            listPoint.add(p);
        }
        Point cp = null;
        float currentTime = animatorValue * 10 - (int) (animatorValue * 10);
        if(currentTime * 10 == 1 || currentTime * 10 == 5 || currentTime * 10 ==9){
            Log.i("wangsongbin", "animatorvalue:" + animatorValue);
        }
        if (animatorValue >= 0 && animatorValue <= 0.1) {
            cp = getCurrentPoint(currentTime, 1, listPoint.get(0), listPoint.get(2));
            if (isDrawPath) {
                canvas.drawLine(mWidth / 2 + listPoint.get(0).x, mWidth / 2 - listPoint.get(0).y, mWidth / 2 + cp.x, mWidth / 2 - cp.y, mPaintLine);
            } else {
                canvas.drawCircle(mWidth / 2 + cp.x, mWidth / 2 - cp.y, mPadding, mPaintLine);
            }
        } else if (animatorValue > 0.1 && animatorValue <= 0.2) {
            cp = getCurrentPoint(currentTime, 1, listPoint.get(2), listPoint.get(4));
            if (isDrawPath) {
                drawEdge(canvas, 1);
                canvas.drawLine(mWidth / 2 + listPoint.get(2).x, mWidth / 2 - listPoint.get(2).y, mWidth / 2 + cp.x, mWidth / 2 - cp.y, mPaintLine);
            } else {
                canvas.drawCircle(mWidth / 2 + cp.x, mWidth / 2 - cp.y, mPadding, mPaintLine);
            }
        } else if (animatorValue > 0.2 && animatorValue <= 0.3) {
            cp = getCurrentPoint(currentTime, 1, listPoint.get(4), listPoint.get(1));
            if (isDrawPath) {
                drawEdge(canvas, 2);
                canvas.drawLine(mWidth / 2 + listPoint.get(4).x, mWidth / 2 - listPoint.get(4).y, mWidth / 2 + cp.x, mWidth / 2 - cp.y, mPaintLine);
            } else {
                canvas.drawCircle(mWidth / 2 + cp.x, mWidth / 2 - cp.y, mPadding, mPaintLine);
            }
        } else if (animatorValue > 0.3 && animatorValue <= 0.4) {
            cp = getCurrentPoint(currentTime, 1, listPoint.get(1), listPoint.get(3));
            if (isDrawPath) {
                drawEdge(canvas, 3);
                canvas.drawLine(mWidth / 2 + listPoint.get(1).x, mWidth / 2 - listPoint.get(1).y, mWidth / 2 + cp.x, mWidth / 2 - cp.y, mPaintLine);
            } else {
                canvas.drawCircle(mWidth / 2 + cp.x, mWidth / 2 - cp.y, mPadding, mPaintLine);
            }
        } else if (animatorValue > 0.4 && animatorValue <= 0.5) {
            cp = getCurrentPoint(currentTime, 1, listPoint.get(3), listPoint.get(0));
            if (isDrawPath) {
                drawEdge(canvas, 4);
                canvas.drawLine(mWidth / 2 + listPoint.get(3).x, mWidth / 2 - listPoint.get(3).y, mWidth / 2 + cp.x, mWidth / 2 - cp.y, mPaintLine);
            } else {
                canvas.drawCircle(mWidth / 2 + cp.x, mWidth / 2 - cp.y, mPadding, mPaintLine);
            }
        } else if (animatorValue > 0.5 && animatorValue <= 0.75) {
            drawEdge(canvas, 5);
            canvas.drawArc(rectF, -180 + (90 - 360 / hornCount), 360 * (animatorValue - 0.5f) / 0.25f, false, mPaintCircle);
        } else {
            mPaintCircle.setStrokeWidth(dp2px(1.5f));
            mPaintLine.setShadowLayer(1, 1, 1, Color.WHITE);
            drawEdge(canvas, 5);
            canvas.drawArc(rectF, -180 + (90 - 360 / hornCount), 360, false, mPaintCircle);
        }
        mPaintCircle.setStrokeWidth(dp2px(1));
        mPaintLine.setShadowLayer(0, 1, 1, Color.WHITE);
    }

    private void drawEdge(Canvas canvas, int edgeCount) {

        switch (edgeCount) {
            case 1:
                drawFirstEdge(canvas);
                break;
            case 2:
                drawFirstEdge(canvas);
                drawSecondEdge(canvas);
                break;
            case 3:
                drawFirstEdge(canvas);
                drawSecondEdge(canvas);
                drawThirdEdge(canvas);
                break;
            case 4:
                drawFirstEdge(canvas);
                drawSecondEdge(canvas);
                drawThirdEdge(canvas);
                drawFourthEdge(canvas);
                break;
            case 5:
                drawFirstEdge(canvas);
                drawSecondEdge(canvas);
                drawThirdEdge(canvas);
                drawFourthEdge(canvas);
                drawFifthEdge(canvas);
                break;
        }

    }

    private void drawFirstEdge(Canvas canvas) {
        canvas.drawLine(mWidth / 2 + listPoint.get(0).x, mWidth / 2 - listPoint.get(0).y,
                mWidth / 2 + listPoint.get(2).x, mWidth / 2 - listPoint.get(2).y, mPaintLine);
    }

    private void drawSecondEdge(Canvas canvas) {
        canvas.drawLine(mWidth / 2 + listPoint.get(2).x, mWidth / 2 - listPoint.get(2).y,
                mWidth / 2 + listPoint.get(4).x, mWidth / 2 - listPoint.get(4).y, mPaintLine);
    }

    private void drawThirdEdge(Canvas canvas) {
        canvas.drawLine(mWidth / 2 + listPoint.get(4).x, mWidth / 2 - listPoint.get(4).y,
                mWidth / 2 + listPoint.get(1).x, mWidth / 2 - listPoint.get(1).y, mPaintLine);
    }

    private void drawFourthEdge(Canvas canvas) {
        canvas.drawLine(mWidth / 2 + listPoint.get(1).x, mWidth / 2 - listPoint.get(1).y,
                mWidth / 2 + listPoint.get(3).x, mWidth / 2 - listPoint.get(3).y, mPaintLine);
    }

    private void drawFifthEdge(Canvas canvas) {
        canvas.drawLine(mWidth / 2 + listPoint.get(3).x, mWidth / 2 - listPoint.get(3).y,
                mWidth / 2 + listPoint.get(0).x, mWidth / 2 - listPoint.get(0).y, mPaintLine);
    }

    private Point getCurrentPoint(float currentTime, float allTime, Point startPoint, Point endPoint) {
        Point cp = new Point();
        cp.x = startPoint.x + (endPoint.x - startPoint.x) * currentTime / allTime;
        cp.y = startPoint.y + (endPoint.y - startPoint.y) * currentTime / allTime;
        return cp;
    }

    public int dp2px(float dp) {
        float density = mContext.getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5);
    }

    public Point getPoint(float radius, int index) {
        float x = (float) (radius * Math.cos((90 + 360 / hornCount - index * 360 / hornCount) * Math.PI / 180));
        float y = (float) (radius * Math.sin((90 + 360 / hornCount - index * 360 / hornCount) * Math.PI / 180));
        Point p = new Point(x, y);
        return p;
    }

    public void stopAnim() {
        if (valueAnimator != null) {
            clearAnimation();
            valueAnimator.setRepeatCount(1);
            valueAnimator.cancel();
            valueAnimator.end();
            animatorValue = 0.75f;
            postInvalidate();
        }
        isInAnim  = false;
    }

    public void startAnim() {
        stopAnim();
        valueAnimator = ValueAnimator.ofFloat(0f, 1.0f);
        valueAnimator.setDuration(3500);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animatorValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

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

    public boolean isInAnim(){
        return isInAnim;
    }

    public void setIsDrawPath(boolean isDrawPath){
        this.isDrawPath = isDrawPath;
    }

    class Point {
        private float x;
        private float y;

        Point() {
        }

        Point(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }
}
