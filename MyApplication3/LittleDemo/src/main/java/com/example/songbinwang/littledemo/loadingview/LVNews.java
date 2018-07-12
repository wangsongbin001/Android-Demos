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
 * Created by songbinwang on 2016/8/31.
 */
public class LVNews extends View {

    private Paint mPaint;
    private float mWidth, cornerRadius;
    private float mStrokeWidth;
    private float linesSpace, shortLineLength, longLineLength;

    private RectF mRectFbg, mRectFSquare, mRectFSquareBG, mRectFBottom, mRectFLeft;

    RectF rectFTopRight = new RectF();
    RectF rectFBottomRight = new RectF();
    RectF rectFBottomLeft = new RectF();
    RectF rectFTopLeft = new RectF();

    private float mPadding;

    private Context mContext;
    private static int ANIMATORDURATION = 2000;

    private float animatorValue;
    private ValueAnimator valueAnimator;
    private boolean isInAnim;

    public LVNews(Context context) {
        this(context, null);
    }

    public LVNews(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LVNews(Context context, AttributeSet attrs, int defStyleAttr) {
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
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getMeasuredHeight() > getMeasuredWidth()) {
            mWidth = getMeasuredWidth();
        } else {
            mWidth = getMeasuredHeight();
        }

        mStrokeWidth = dp2px(1);
        mPadding = dp2px(3);
        mRectFbg = new RectF(mPadding, mPadding, mWidth - mPadding, mWidth - mPadding);
        cornerRadius = dp2px(3.0f);

        mRectFSquare = new RectF(mPadding + cornerRadius, mPadding + cornerRadius,
                mWidth / 2f - cornerRadius , mWidth / 2f - cornerRadius);
        mRectFSquareBG = new RectF(mRectFSquare.left + mStrokeWidth, mRectFSquare.top + mStrokeWidth,
                mRectFSquare.right - mStrokeWidth, mRectFSquare.bottom - mStrokeWidth);

        rectFTopLeft = new RectF(mPadding, mPadding, mPadding + cornerRadius * 2f, mPadding + cornerRadius * 2f);

        rectFTopRight = new RectF(mWidth - mPadding - cornerRadius * 2f, mPadding,
                mWidth - mPadding, mPadding + cornerRadius * 2f);

        rectFBottomLeft = new RectF(mPadding, mWidth - mPadding - cornerRadius * 2f,
                mPadding + cornerRadius * 2f, mWidth - mPadding);

        rectFBottomRight = new RectF(mWidth - mPadding - cornerRadius * 2f, mWidth - mPadding - cornerRadius * 2f,
                mWidth - mPadding, mWidth - mPadding);

        mRectFBottom = new RectF(mRectFbg.left + cornerRadius, mWidth / 2f + cornerRadius,
                mRectFbg.right - cornerRadius, mRectFbg.bottom - cornerRadius);

        mRectFLeft = new RectF(mWidth / 2 + cornerRadius, mRectFSquare.top, mRectFbg.right - cornerRadius, mRectFSquare.bottom);

        linesSpace = (mRectFbg.bottom - mRectFbg.top) / 2;

        shortLineLength = mRectFSquare.right - mRectFSquare.left;
        longLineLength = mRectFBottom.right - mRectFBottom.left;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRoundRect(mRectFbg, cornerRadius, cornerRadius, mPaint);

        canvas.drawRect(mRectFSquare, mPaint);

        drawLines(canvas, 6);
    }

    private void drawLines(Canvas canvas, int count) {
        if (count == 3) {
           drawShortLine(canvas);
        } else if (count == 6) {
            drawShortLine(canvas);
            drawLongLine(canvas);
        }
    }

    private void drawShortLine(Canvas canvas) {
        canvas.drawLine(mRectFLeft.left, mRectFLeft.top, mRectFLeft.right, mRectFLeft.top, mPaint);
        canvas.drawLine(mRectFLeft.left, (mRectFLeft.top + mRectFLeft.bottom) / 2f, mRectFLeft.right, (mRectFLeft.top + mRectFLeft.bottom) / 2f, mPaint);
        canvas.drawLine(mRectFLeft.left, mRectFLeft.bottom, mRectFLeft.right, mRectFLeft.bottom, mPaint);
    }

    private void drawLongLine(Canvas canvas) {
        canvas.drawLine(mRectFBottom.left, mRectFBottom.top, mRectFBottom.right, mRectFBottom.top, mPaint);
        canvas.drawLine(mRectFBottom.left, (mRectFBottom.top + mRectFBottom.bottom) / 2f, mRectFBottom.right, (mRectFBottom.top + mRectFBottom.bottom) / 2f, mPaint);
        canvas.drawLine(mRectFBottom.left, mRectFBottom.bottom, mRectFBottom.right, mRectFBottom.bottom, mPaint);
    }

    private int dp2px(float dp) {
        float density = mContext.getResources().getDisplayMetrics().density;
        return (int) (density * dp + 0.5);
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

    }

    public void startAnim() {

    }

    public boolean isInAnim() {
        return isInAnim;
    }
}
