package com.example.songbinwang.littledemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by songbinwang on 2016/9/9.
 */
public class CustomCurve extends View {

    private float mWidth, mHeight, mPadding, mWidthSpec, mCanvasHeight;
    private Paint mPaintLine, mPaintPoint, mPaintCurve;

    private Path mPath;

    private Context mContext;

    private int[] values;
    private int minValue, maxValue, totalValue;

    public CustomCurve(Context context) {
        this(context, null);
    }

    public CustomCurve(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomCurve(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initPaint();
    }

    private void initPaint() {
        mPaintLine = new Paint();
        mPaintLine.setStyle(Paint.Style.STROKE);
        mPaintLine.setAntiAlias(true);
        mPaintLine.setStrokeWidth(dp2px(1));
        mPaintLine.setColor(Color.GRAY);

        mPaintCurve = new Paint();
        mPaintCurve.setStyle(Paint.Style.STROKE);
        mPaintCurve.setAntiAlias(true);
        mPaintCurve.setStrokeWidth(dp2px(1));
        mPaintCurve.setColor(Color.YELLOW);

        mPaintPoint = new Paint();
        mPaintPoint.setStyle(Paint.Style.FILL);
        mPaintPoint.setAntiAlias(true);
        mPaintPoint.setColor(Color.YELLOW);
        mPaintPoint.setStrokeWidth(dp2px(2));

        values = new int[]{1000, 1500, 2300, 1500, 2000};
        setValues(values);
    }

    ;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mWidthSpec = (mWidth - mPadding * 2) / values.length;
        mCanvasHeight = mHeight - mPadding * 2;
        drawLine(canvas);
        drawPoint(canvas);
    }

    private int dp2px(float dp) {
        float density = mContext.getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5);
    }

    public void setValues(int[] values) {
        if (values == null) {
            return;
        }
        this.values = values;
        for (int i = 0; i < values.length; i++) {
            minValue = minValue < values[i] ? minValue : values[i];
            maxValue = maxValue > values[i] ? maxValue : values[i];
        }
        totalValue = minValue + maxValue;
    }

    private void drawLine(Canvas canvas) {
        for (int i = 0; i < values.length; i++) {
            canvas.drawLine(mPadding + i * mWidthSpec, mHeight - mPadding, mPadding + i * mWidthSpec, mPadding, mPaintLine);
        }
        canvas.drawLine(mPadding, mHeight - mPadding, mWidth - mPadding, mHeight - mPadding, mPaintLine);
    }

    private void drawPoint(Canvas canvas){
        for(int i=0;i < values.length; i++){
            canvas.drawPoint(mPadding + i * mWidthSpec, mHeight - mPadding - mCanvasHeight * values[i] / totalValue, mPaintPoint);
        }
    }

    private void drawCurve(Canvas canvas){
        mPath = new Path();
        for(int i = 0;i<values.length -1; i++){
        }
    }
}
