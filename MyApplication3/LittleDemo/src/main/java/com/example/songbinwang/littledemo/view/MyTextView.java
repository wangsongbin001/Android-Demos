package com.example.songbinwang.littledemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

/**
 *  on 2016/5/4.
 */
public class MyTextView extends TextView{

    public static final String Tag = "wangMyText";
    private Paint mPaint1,mPaint2;
    private LinearGradient mLinearGradient;
    private Matrix mLinearMatrix;
    private int mMeasuredWidth;
    private int mMeasuredHeight;

    private Paint mPaint;
    private int mTranslate;

    public MyTextView(Context context){
        this(context, null);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mPaint1 = new Paint();
        mPaint1.setColor(Color.LTGRAY);
        mPaint1.setStyle(Paint.Style.FILL);

        mPaint2 = new Paint();
        mPaint2.setColor(Color.YELLOW);
        mPaint2.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.i(Tag,"onFinishInflate");
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w,h,oldw,oldh);
        Log.i(Tag, "onSizeChanged");
        if(mLinearGradient == null){
            mMeasuredWidth = getMeasuredWidth();
            mPaint = getPaint();
            if(mMeasuredWidth > 0){
                mLinearGradient = new LinearGradient(
                        0,0,mMeasuredWidth, 0,new int[]{Color.RED,Color.WHITE,Color.RED},null, Shader.TileMode.CLAMP
                );
                mPaint.setShader(mLinearGradient);
                mLinearMatrix = new Matrix();
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i(Tag, "onMeasure");
        mMeasuredWidth = getMeasuredWidth();
        mMeasuredHeight = getMeasuredHeight();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.i(Tag, "onLayout");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.i(Tag, "onDraw");
        canvas.drawRect(0, 0, mMeasuredWidth, mMeasuredHeight, mPaint1);
        canvas.drawRect(getPaddingLeft(), getPaddingTop(), getMeasuredWidth() - getPaddingRight(), getMeasuredHeight() - getPaddingBottom(), mPaint2);
        super.onDraw(canvas);
        if(mLinearGradient != null) {
            mTranslate += mMeasuredWidth / 5;
            if (mTranslate > 2 * mMeasuredWidth) {
                mTranslate = -mMeasuredWidth;
            }
            mLinearMatrix.setTranslate(mTranslate, 0);
            mLinearGradient.setLocalMatrix(mLinearMatrix);
            postInvalidateDelayed(100);
        }
    }
}
