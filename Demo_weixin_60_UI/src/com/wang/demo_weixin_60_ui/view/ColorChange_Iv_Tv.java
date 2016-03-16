package com.wang.demo_weixin_60_ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.wang.demo_weixin_60_ui.R;

public class ColorChange_Iv_Tv extends View {

	private Bitmap iconBitmap;// 小图标对象
	private String text;// 文本对象
	private float text_size;// 文本大小
	private int color;// 选中后的颜色

	private float iconAlpha = 0f;// 透明度，越小越透明

	private Paint mPaint;//画笔对象

	private Canvas iconCanvas;//绘制icon的画布对象

	private Rect textBound;// 文本的范围
	private Rect iconBound;// 图标的范围

	public ColorChange_Iv_Tv(Context context) {
		this(context, null, 0);
	}

	public ColorChange_Iv_Tv(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ColorChange_Iv_Tv(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		TypedArray typeArray = context.obtainStyledAttributes(attrs,
				R.styleable.ColorChange_Iv_Tv);
		int n = typeArray.getIndexCount();
		for (int i = 0; i < n; i++) {
			int attr = typeArray.getIndex(i);
			switch (attr) {
			case R.styleable.ColorChange_Iv_Tv_color:
				color = typeArray.getColor(R.styleable.ColorChange_Iv_Tv_color,
						Color.BLUE);
				break;
			case R.styleable.ColorChange_Iv_Tv_icon:
				BitmapDrawable iconDraw = (BitmapDrawable) typeArray
						.getDrawable(R.styleable.ColorChange_Iv_Tv_icon);
				iconBitmap=iconDraw.getBitmap();
				break;
			case R.styleable.ColorChange_Iv_Tv_text:
				text = typeArray.getString(R.styleable.ColorChange_Iv_Tv_text);
				break;
			case R.styleable.ColorChange_Iv_Tv_text_size:
				text_size = typeArray.getDimension(
						R.styleable.ColorChange_Iv_Tv_text_size, TypedValue
								.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10,
										getResources().getDisplayMetrics()));
				break;
			}
		}

		typeArray.recycle();

		mPaint=new Paint();
		mPaint.setTextSize(text_size);
		mPaint.setColor(Color.BLACK);
		mPaint.setAlpha(255);
		textBound=new Rect();
		mPaint.getTextBounds(text, 0, text.length(), textBound);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int width=getMeasuredWidth();
		int height=getMeasuredHeight();
		int iconWith=Math.min(width-getPaddingLeft()-getPaddingRight(), 
				height-textBound.height()-getPaddingTop()-getPaddingBottom());
		int left=width/2-iconWith/2;
		int top=height/2-(iconWith+textBound.height())/2;
		
		iconBound=new Rect(left, top, left+iconWith, top+iconWith);
		
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		int alpha=(int) Math.ceil(255*iconAlpha);
		canvas.drawBitmap(iconBitmap, null, iconBound, null);
		drawTextBlack(canvas, alpha);
		drawText(canvas,alpha);
		drawIcon(canvas,alpha);
	}
	
	//绘制底层的文本
	private void drawTextBlack(Canvas canvas,int alpha){
		mPaint.reset();
		mPaint.setTextSize(text_size);
		mPaint.setColor(0xff333333);
		mPaint.setAlpha(255-alpha);
		canvas.drawText(text, iconBound.left+iconBound.width()/2-textBound.width()/2,
				iconBound.bottom+textBound.height(), mPaint);
	}
	
	//绘制文本
	private void drawText(Canvas canvas,int alpha){
		mPaint.reset();
		mPaint.setTextSize(text_size);
		mPaint.setColor(color);
		mPaint.setAlpha(alpha);//要先设置颜色在设置，透明度才能出效果
		//Log.i("wangsongbin", alpha+"");
		canvas.drawText(text, iconBound.left+iconBound.width()/2-textBound.width()/2,
				iconBound.bottom+textBound.height(), mPaint);
	}
	
	//绘制icon
	private void drawIcon(Canvas canvas,int alpha){
		Bitmap bitmap=Bitmap.createBitmap(iconBound.width(), 
				iconBound.height(), Config.ARGB_8888);
		Canvas iconCanvas=new Canvas(bitmap);
		
		mPaint.reset();
		mPaint.setColor(color);
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		mPaint.setAlpha(alpha);
		iconCanvas.drawRect(0,0,iconBound.width(),iconBound.height(), mPaint);
	
		mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
		mPaint.setAlpha(255);
		iconCanvas.drawBitmap(iconBitmap, 
				null, new RectF(0, 0, iconBound.width(), iconBound.height()), mPaint);
		
		canvas.drawBitmap(bitmap, null,iconBound, null);
		
	}
	
	//设置透明度
	public void setIconAlpha(float iconAlpha){
		this.iconAlpha=iconAlpha;
		if(Looper.getMainLooper()==Looper.myLooper()){
			invalidate();
		}else{
			postInvalidate();
		}
	}
	
	private final String instance_state="instance_state";
	private final String state_alpha="state_alpha";
	@Override
	protected Parcelable onSaveInstanceState() {
		Bundle bundle=new Bundle();
		bundle.putParcelable(instance_state, super.onSaveInstanceState());
		bundle.putFloat(state_alpha, iconAlpha);
		return bundle;
	}
	
	@Override
	protected void onRestoreInstanceState(Parcelable state) {
		if(state instanceof Bundle){
			Bundle bundle=(Bundle) state;
			iconAlpha=bundle.getFloat(state_alpha);
			super.onRestoreInstanceState(bundle.getParcelable(instance_state));
		}else{
			super.onRestoreInstanceState(state);
		}
	}
	
}
