package com.wang.demo_weixin_60_ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;

/**
 * 1，初始化ImageView图片的位置 
 * <a>如果图片本身的高或宽，大于屏幕的高或宽，则将超出的部分缩小至屏幕的高或宽
 * <b>如果图片的高或宽不大于屏幕的高或宽，则使其居中放置 
 * 2，利用ScaleGestureDetectorLinstener监听器，监听用户的缩放手势
 * <a>缩放比例的控制，首先拿到当前的比例，乘以监听到的比例如果超出最大、最小比例，则按最大、最小比例计算
 * <b>缩放后的位置控制，计算缩放后，是否空白出现，如果出现走相应的平移！
 * 3，添加平移功能 
 * <a>onTouchListener的Action_move
 * <b>平移位置控制 
 * 4.利用GestureDetectorListener监听双击事件 
 * <a>双击图片但数次，图片发达到当前比例的两倍，或最大比例
 * <b>双击双数次，图片缩小为初始状态比例 5,加上对对容器监听事件的冲突解决
 * 
 * @author liuxixi
 *
 */
public class ZoomImageView2 extends ImageView implements View.OnTouchListener {

	/**
	 * 最大的缩放比例
	 */
	private static final float SCALE_MAX = 4.0f;

	/**
	 * 初始化时的缩放比例
	 */
	private float initScale = 1.0f;

	/**
	 * 存放矩阵九个值的数组
	 */
	private final float[] matrixValues = new float[9];

	/**
	 * 是否为第一次，用于对initScale的初始化
	 */
	private boolean once = true;

	/**
	 * 缩放的手势检测
	 */
	private ScaleGestureDetector mScaleGestureDetector;

	/**
	 * 用于缩放的矩阵
	 */
	private final Matrix mScaleMatrix = new Matrix();

	/**
	 * 缩放的手势监听器
	 */
	private OnScaleGestureListener onScaleListener = new OnScaleGestureListener() {

		@Override
		public void onScaleEnd(ScaleGestureDetector detector) {

		}

		@Override
		public boolean onScaleBegin(ScaleGestureDetector detector) {
			return true;
		}

		@Override
		public boolean onScale(ScaleGestureDetector detector) {
			// 获得当前的缩放比例
			float scale = getScale();
			// 获得缩放比例
			float scaleFactor = detector.getScaleFactor();

			if (getDrawable() == null) {
				// 没图片则结束方法
				return true;
			}
			/**
			 * 控制缩放的范围（比例上的控制）
			 */
			if ((scale < SCALE_MAX && scaleFactor > 1.0f)
					|| (scale > initScale && scaleFactor < 1.0f)) {
				// 最大值，最小值的判断
				if (scale * scaleFactor < initScale) {
					scaleFactor = initScale / scale;
				}
				if (scale * scaleFactor > SCALE_MAX) {
					scaleFactor = SCALE_MAX / scale;
				}
				mScaleMatrix.postScale(scaleFactor, scaleFactor,
						detector.getFocusX(), detector.getFocusY());
				// 判断位置是否有空白出现
				checkBorderAndCenterWhenScale();
				ZoomImageView2.this.setImageMatrix(mScaleMatrix);
			}
			return true;
		}
	};

	/**
	 * 记录上一次在触摸过程中上一次触点的个数
	 */
	private int lastPointCount = 0;
	/**
	 * 记录在上一次触摸过程中上一次触点的坐标
	 */
	private float mLastX = 0;
	private float mLastY = 0;
	/**
	 * 平移的最小值
	 */
	private int mTouchSlop = 3;

	/**
	 * 双击手势检测
	 */
	private GestureDetector mGestureDetector;
	/**
	 * 双击的响应事件是否正在执行，即是否在自动缩放
	 */
	private boolean isAutoScale = false;
	/**
	 * 上一次自动缩放的类型
	 */
	private boolean isScaleToBiger = false;

	/**
	 * 作为微信头像剪切界面的横向边距，和纵向边距
	 */
	private float mHorizontalPadding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 
			20, getResources().getDisplayMetrics());
	private float mVerticalPadding;

	private SimpleOnGestureListener onDoubleListener = new SimpleOnGestureListener() {

		public boolean onDoubleTap(MotionEvent event) {
			Log.i("wangsongbin", "" + isAutoScale);
			if (isAutoScale) {
				return true;
			}
			// 获得触点坐标
			float x = event.getX();
			float y = event.getY();
			// 获得当前的缩放比例
			float scale = getScale();
			if (isScaleToBiger) {
				ZoomImageView2.this.postDelayed(new AutoScaleRunnable(
						initScale, x, y), 16);
				isScaleToBiger = false;
			} else {
				if (scale * 2 >= SCALE_MAX) {
					ZoomImageView2.this.postDelayed(new AutoScaleRunnable(
							SCALE_MAX, x, y), 16);
				} else {
					ZoomImageView2.this.postDelayed(new AutoScaleRunnable(
							scale * 2, x, y), 16);
				}
				isScaleToBiger = true;
			}
			return true;
		};

	};

	public ZoomImageView2(Context context) {
		this(context, null, 0);
	}

	public ZoomImageView2(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ZoomImageView2(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// 用矩阵来描绘
		super.setScaleType(ScaleType.MATRIX);
		// 缩放检测器
		mScaleGestureDetector = new ScaleGestureDetector(context,
				onScaleListener);
		// 双击检测器
		mGestureDetector = new GestureDetector(context, onDoubleListener);
		// 注册触摸监听器
		this.setOnTouchListener(this);
	}

	/**
	 * 根据传入的值，判断是放大还是缩小 并且会一直发达缩小到目标值
	 *
	 */
	public class AutoScaleRunnable implements Runnable {
		final static float BIGER = 1.07f;
		final static float SMALLER = 0.93f;

		private float mTargetScale = 0;// 目标放缩比例
		private float tempScale;

		/**
		 * 缩放中心
		 * 
		 * @param scale
		 * @param x
		 * @param y
		 */
		private float x;
		private float y;

		public AutoScaleRunnable(float scale, float x, float y) {
			mTargetScale = scale;
			this.x = x;
			this.y = y;
			if (getScale() < mTargetScale) {
				tempScale = BIGER;
			} else {
				tempScale = SMALLER;
			}
		}

		@Override
		public void run() {
			// 进行缩放
			mScaleMatrix.postScale(tempScale, tempScale, x, y);
			checkBorderAndCenterWhenScale();
			ZoomImageView2.this.setImageMatrix(mScaleMatrix);

			final float currentScale = getScale();
			if ((currentScale < mTargetScale && tempScale > 1.0f)
					|| (currentScale > mTargetScale && tempScale < 1.0f)) {
				ZoomImageView2.this.postDelayed(this, 16);
			} else {
				final float deltaScale = mTargetScale / currentScale;
				mScaleMatrix.postScale(deltaScale, deltaScale, x, y);
				checkBorderAndCenterWhenScale();
				ZoomImageView2.this.setImageMatrix(mScaleMatrix);
				isAutoScale = false;// 自动缩放结束
			}

		}

	}

	/**
	 * 控制缩放范围，（位置上的控制）
	 */
	private void checkBorderAndCenterWhenScale() {
		// 根据当前ImageView的matrix获得图片的范围的矩阵
		RectF rect = getMatrixRectF();
		// 需要平移的x,y轴的偏移量
		float tansX = 0;
		float tansY = 0;

		int width = getWidth();
		int height = getHeight();
		// 当高宽大于屏幕的高宽时，控制不能有空白处的出现
		if (rect.width() >= width-2*mHorizontalPadding) {
			if (rect.left > mHorizontalPadding) {
				tansX = mHorizontalPadding - rect.left;
			}
			if (rect.right < width - mHorizontalPadding) {
				tansX = width - mHorizontalPadding - rect.right;
			}
		}

		if (rect.height() >= height-2*mVerticalPadding) {
			if (rect.top > mVerticalPadding) {
				tansY = mVerticalPadding - rect.top;
			}
			if (rect.bottom < height - mVerticalPadding) {
				tansY = height - mVerticalPadding - rect.bottom;
			}
		}

		// 如果高宽小于屏幕高宽时，则使其垂直活横向居中
		// if (rect.width() < width) {
		// tansX = width * 1.0f / 2 + rect.width() / 2 - rect.right;
		// // 或者tanX=width/2-rect.width/2-rect.left;
		// }
		// if (rect.height() < height) {
		// tansY = height * 1.0f / 2 + rect.height() / 2 - rect.bottom;
		// }

		mScaleMatrix.postTranslate(tansX, tansY);
	}

	/**
	 * 根据当前ImageView的matrix获得图片的范围的矩阵
	 */
	private RectF getMatrixRectF() {
		Matrix matrix = mScaleMatrix;
		RectF rect = new RectF();
		Drawable d = getDrawable();
		if (d != null) {
			rect.set(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
			matrix.mapRect(rect);
		}
		return rect;
	}

	/**
	 * 获得当前的缩放比例
	 */
	private float getScale() {
		mScaleMatrix.getValues(matrixValues);
		return matrixValues[Matrix.MSCALE_X];
	}

	/**
	 * 布局摆放的监听
	 * 
	 * @param context
	 */
	ViewTreeObserver.OnGlobalLayoutListener globalListener = new OnGlobalLayoutListener() {

		@Override
		public void onGlobalLayout() {
			if (once) {
				// 拿到图片
				Drawable draw = getDrawable();
				// 如果没拿到，只能遗憾的结束方法
				if (draw == null) {
					return;
				}
				// 获得控件的高和宽
				int width = getWidth();
				int height = getHeight();
				// 根据横向边距设置纵向边距
				mVerticalPadding = height / 2
						- (width / 2 - mHorizontalPadding / 2);

				// 如果拿到了图片我们要计算它的高和宽，检验是否超出屏幕，如果有则进行缩小，如果不够，测放大
				int dw = draw.getIntrinsicWidth();
				int dh = draw.getIntrinsicHeight();
				float scale = 1.0f;
				// 如果屏幕的高或宽大于屏幕的高或宽，则缩小至屏幕的高或宽
				if (dw > width && dh <= height) {
					scale = width * 1.0f / dw;
				}
				if (dw <= width && dh > height) {
					scale = height * 1.0f / dh;
				}
				if (dw > width && dh > height) {
					scale = Math.min(width * 1.0f / dw, height * 1.0f / dh);
				}
				// 如果图片的高或宽中间框的高或宽，则放大到恰好能覆盖中间框的大小
				float borderWidth = getWidth() - mHorizontalPadding * 2;
				if (dw < (borderWidth) || dh < (borderWidth)) {
					scale = Math.max(borderWidth * 1.0f / dw, borderWidth
							* 1.0f / dh);
				}
				initScale = scale;

				// 先平移到中心位置，在进行缩放
				mScaleMatrix.postTranslate((width - dw) / 2, (height - dh) / 2);

				mScaleMatrix.postScale(scale, scale, width / 2, height / 2);
				ZoomImageView2.this.setImageMatrix(mScaleMatrix);
				once = false;
			}
		}
	};

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		this.getViewTreeObserver().addOnGlobalLayoutListener(globalListener);
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		this.getViewTreeObserver().removeGlobalOnLayoutListener(globalListener);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (mGestureDetector.onTouchEvent(event)) {
			return true;// 如果相应了双击事件则不响应其他事件
		}
		// 回调手势缩放监听
		mScaleGestureDetector.onTouchEvent(event);
		// 用于记录用户的起始触点位置，多点触摸时去平均值
		float x = 0;
		float y = 0;
		int pointCount = event.getPointerCount();
		for (int i = 0; i < pointCount; i++) {
			x += event.getX(i);
			y += event.getY(i);
		}
		x = x / pointCount;
		y = y / pointCount;

		// 当触摸点数发生改变时，起始位置要重新确定
		if (pointCount != lastPointCount) {
			mLastX = x;
			mLastY = y;
		}
		lastPointCount = pointCount;
		// 获得当前的图片范围
		RectF rect = getMatrixRectF();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			break;
		case MotionEvent.ACTION_MOVE:
			// 获得移动的距离
			float dx = x - mLastX;
			float dy = y - mLastY;

			if (isCanDrag(dx, dy)) {

				if (getDrawable() != null) {

					int width = getWidth();
					int height = getHeight();
					// 如果当前图片的高或宽小于控件的高或宽，则不进行平移
					if (rect.width() <= width-mHorizontalPadding*2) {
						dx = 0;
					}
					if (rect.height() <= height-mVerticalPadding*2) {
						dy = 0;
					}
					// 设置平移量
					mScaleMatrix.postTranslate(dx, dy);
					// 检查平移后是否有空白出现
					checkBorderAndCenterWhenScale();
					ZoomImageView2.this.setImageMatrix(mScaleMatrix);
				}

				mLastX = x;
				mLastY = y;
			}
			break;
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
			lastPointCount = 0;
			break;
		}

		return true;
	}

	/**
	 * 设置横向边距
	 */
	public void setHorizontalPadding(float mHorizontalPaddding) {
		this.mHorizontalPadding = mHorizontalPaddding;
		requestLayout();
	}

	/**
	 * 是否可以满足平移的最小值 dx: 横向偏移距离 dy: 纵向偏移距离
	 */
	private boolean isCanDrag(float dx, float dy) {
		return Math.sqrt(dx * dx + dy * dy) > mTouchSlop;
	}
	
	/**
	 * 用于截取中间中的图片
	 */
	public Bitmap clip(){
		//先把整张图描绘下来，再截取我们需要的部分
		Bitmap bitmap=Bitmap.createBitmap(getWidth(),
				getHeight(), Config.ARGB_8888);
		Canvas canvas=new Canvas(bitmap);
		draw(canvas);
		return Bitmap.createBitmap(bitmap, (int)mHorizontalPadding, (int)mVerticalPadding, (int)(getWidth()-2*mHorizontalPadding), (int)(getHeight()-2*mVerticalPadding));
	}

}
