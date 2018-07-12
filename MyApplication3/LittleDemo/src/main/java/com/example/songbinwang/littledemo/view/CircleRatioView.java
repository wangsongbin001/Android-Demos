package com.example.songbinwang.littledemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.example.songbinwang.littledemo.R;

/**
 * Created by songbinwang on 2016/5/16.
 */
public class CircleRatioView extends View {

    private String title = "title";
    private float ratio = 0.5f;
    private Paint mPaint;
    private Rect mTextBound;
    private int mArcWidth;
    private int mCirclePadding;
    private int distanceBetweenCircleAndArc;

    public CircleRatioView(Context context){
        this(context, null);
    }

    public CircleRatioView(Context context, @Nullable AttributeSet attrs){
        this(context, attrs, 0);
    }

    public CircleRatioView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public CircleRatioView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes){
        super(context, attrs, defStyleAttr, defStyleRes);

        TypedArray mTypeArray = context.obtainStyledAttributes(attrs, R.styleable.CircleRatioView,defStyleAttr,defStyleRes);
        int length = mTypeArray.length();
        for(int i=0;i<length;i++){
            int attr = mTypeArray.getIndex(i);
            switch(attr){
                case R.styleable.CircleRatioView_ratio:
                    ratio = mTypeArray.getFloat(R.styleable.CircleRatioView_ratio,0.5f);
                    break;
                case R.styleable.CircleRatioView_circle_ratio_title:
                    title = mTypeArray.getString(R.styleable.CircleRatioView_circle_ratio_title);
                    break;
            }
        }
        mTypeArray.recycle();

        mArcWidth = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20.0f, context.getResources().getDisplayMetrics());
        mCirclePadding = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20.0f, context.getResources().getDisplayMetrics());
        distanceBetweenCircleAndArc = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20.0f, context.getResources().getDisplayMetrics());
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLUE);
        mPaint.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, context.getResources().getDisplayMetrics()));

        mTextBound = new Rect();
        mPaint.getTextBounds(title, 0, title.length(), mTextBound);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    private int measureWidth(int widthMeasureSpec){
        int width = 0;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        if(widthMode == MeasureSpec.EXACTLY){
            width = widthSize;
        }else if(widthMode == MeasureSpec.AT_MOST){
            width = mTextBound.width() + (mArcWidth + distanceBetweenCircleAndArc + mCirclePadding)* 2;
        }
        return width;
    };

    private int measureHeight(int heightMeasureSpec){
        int height = 0;
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if(heightMode == MeasureSpec.EXACTLY){
            height = heightSize;
        }else if(heightMode == MeasureSpec.AT_MOST){
            height = mTextBound.height() + (mArcWidth + distanceBetweenCircleAndArc + mCirclePadding) * 2;
        }
        return height;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        mPaint.reset();
        mPaint.setColor(getResources().getColor(android.R.color.holo_green_light));
        mPaint.setStyle(Paint.Style.FILL);
        int radius = width/2 - mArcWidth - distanceBetweenCircleAndArc;
        canvas.drawCircle(width / 2, height / 2, radius, mPaint);

        RectF rectArc = new RectF(0.1f*width ,0.1f*height, 0.9f*width,0.9f*height);
        mPaint.reset();
        mPaint.setColor(getResources().getColor(android.R.color.holo_green_light));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mArcWidth);
        canvas.drawArc(rectArc, -90, (int) 360 * ratio, false, mPaint);


        mPaint.reset();
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, getResources().getDisplayMetrics()));
        int distanceX = width/2 - mTextBound.width()/2;
        int distanceY = height/2 - mTextBound.height()/2 + mTextBound.height()/2;
        canvas.save();
        canvas.translate(distanceX,distanceY);
        canvas.drawText(title, 0, title.length(),0,0,mPaint);
        canvas.restore();
    }

    public void setRatio(float ratio, String title){
        this.ratio = ratio;
        this.title = title;
        mPaint.reset();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLUE);
        mPaint.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, getResources().getDisplayMetrics()));
        mTextBound = new Rect();
        mPaint.getTextBounds(title, 0, title.length(), mTextBound);
        invalidate();
    }
}
