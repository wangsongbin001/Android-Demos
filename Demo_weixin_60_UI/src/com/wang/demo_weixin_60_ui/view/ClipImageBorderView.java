package com.wang.demo_weixin_60_ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class ClipImageBorderView extends View{
	/**
	 * 四周画半透明的黑色区域
	 * 中间画正方形的
	 * @param context
	 */
	private float mHorizontalPadding=20;
	private float mVerticalPadding;
	
	private Paint mPaint;
	
	public ClipImageBorderView(Context context) {
		this(context, null, 0);
	}
	
	public ClipImageBorderView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public ClipImageBorderView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		
		mHorizontalPadding=TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mHorizontalPadding, 
				getResources().getDisplayMetrics());
		mPaint=new Paint();
		mPaint.setAntiAlias(true);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		mVerticalPadding=getHeight()/2-(getWidth()-mHorizontalPadding)/2;
		mPaint.setStyle(Style.FILL);
		mPaint.setColor(0xaa000000);
		//先画左右，再画上下
		canvas.drawRect(0, 0, mHorizontalPadding, getHeight(), mPaint);
		canvas.drawRect(getWidth()-mHorizontalPadding, 0, getWidth(), getHeight(), mPaint);
		canvas.drawRect(mHorizontalPadding, 0, getWidth()-mHorizontalPadding, mVerticalPadding, mPaint);
		canvas.drawRect(mHorizontalPadding, getHeight()-mVerticalPadding, getWidth()-mHorizontalPadding, getHeight(), mPaint);
		//画一个画框
		mPaint.setStyle(Style.STROKE);
		mPaint.setStrokeWidth(3);
		mPaint.setColor(Color.parseColor("#000000"));
		canvas.drawRect(mHorizontalPadding, mVerticalPadding, getWidth()-mHorizontalPadding, getHeight()-mVerticalPadding, mPaint);
	}
	
	/**
	 * 设置横向边距
	 */
	public void setHorizontalPadding(float mHorizontalPadding){
		this.mHorizontalPadding=mHorizontalPadding;
		invalidate();
	}

}
