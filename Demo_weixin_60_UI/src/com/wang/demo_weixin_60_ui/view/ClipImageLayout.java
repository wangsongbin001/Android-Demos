package com.wang.demo_weixin_60_ui.view;

import com.wang.demo_weixin_60_ui.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.RelativeLayout;

public class ClipImageLayout extends RelativeLayout{
	
	private ZoomImageView2 zoomView;
	private ClipImageBorderView borderView;
	
	private float mHorizontalPadding;//横向边距
	private final static float DEFAULT_PADDING=20;
	private Drawable drawable;//原图

	public ClipImageLayout(Context context) {
		this(context, null, 0);
	}
	
	public ClipImageLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public ClipImageLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		TypedArray typeArray=context.obtainStyledAttributes(attrs, R.styleable.ClipImageLayout);
		int count=typeArray.getIndexCount();
		for(int i=0;i<count;i++){
			int attr=typeArray.getIndex(i);
			switch(attr){
			case R.styleable.ClipImageLayout_icon:
				drawable=typeArray.getDrawable(attr);
				break;
			case R.styleable.ClipImageLayout_horizontal_padding:
				mHorizontalPadding=typeArray.getDimension(attr, 
						TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_PADDING, getResources().getDisplayMetrics()));
				break;
			}
		}
		typeArray.recycle();
		
		LayoutParams lp=new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		zoomView=new ZoomImageView2(context);
		borderView=new ClipImageBorderView(context);
		
		zoomView.setLayoutParams(lp);
		zoomView.setHorizontalPadding(mHorizontalPadding);
		zoomView.setImageDrawable(drawable);
		
		borderView.setHorizontalPadding(mHorizontalPadding);
		borderView.setLayoutParams(lp);
		
		addView(zoomView);
		addView(borderView);
	}
	
	public Bitmap clip(){
		return zoomView.clip();
	}

}
