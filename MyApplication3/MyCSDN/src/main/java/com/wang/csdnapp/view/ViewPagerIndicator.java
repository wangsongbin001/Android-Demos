package com.wang.csdnapp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.wang.csdnapp.R;

import java.util.List;

public class ViewPagerIndicator extends LinearLayout {

	/**
	 * 绘制三角形的画笔
	 */
	private Paint mPaint;
	/**
	 * 构建三角形的Path
	 */
	private Path mPath;
	/**
	 * 三角形的宽度
	 */
	private int mTriangleWidth;
	/**
	 * 三角形的高度
	 */
	private int mTriangleHeight;

	/**
	 * 三角高度规定为单个tag的1/6
	 */
	private static final float RADIO_TRIANGLE = 1.0F / 6;

	/**
	 * 三角形的最大宽度
	 */
	private final int DIMENSION_TRIANGLE_WIDTH = (int) (getScreenWidth() / 3 * RADIO_TRIANGLE);

	/**
	 * 初始时三角形的偏移量
	 */
	private int mInitTranslationX;

	/**
	 * 手指滑动时的偏移量
	 */
	private int mTranslationX;

	/**
	 * 默认tab的数量
	 */
	private static final int COUNT_DEFAULT_TAB = 4;

	/**
	 * tab的实际数量
	 */
	private int mTabVisiableCount = COUNT_DEFAULT_TAB;

	/**
	 * tab上的内容
	 */
	private List<String> mTabTitles;

	/**
	 * 与之捆绑的ViewPager
	 */
	private ViewPager mViewPager;

	/**
	 * 标题正常时的颜色
	 */
	private static final int COLOR_TEXT_NORMAL = 0x77ffffff;

	/**
	 * 标题选中时的颜色
	 */
	private static final int COLOR_TEXT_SELECTED = 0xffffffff;

	public ViewPagerIndicator(Context context) {
		this(context, null, 0);

	}

	public ViewPagerIndicator(Context context, AttributeSet attrs) {
		this(context, attrs, 0);

	}

	public ViewPagerIndicator(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		TypedArray typedArray = context.obtainStyledAttributes(attrs,
				 R.styleable.ViewPagerIndicator);
		mTabVisiableCount = typedArray.getInteger(R.styleable.ViewPagerIndicator_tab_count,
				COUNT_DEFAULT_TAB);
		if (mTabVisiableCount < 0) {
			mTabVisiableCount = COUNT_DEFAULT_TAB;
		}
		typedArray.recycle();
		// 初始化画笔
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setColor(COLOR_TEXT_SELECTED);
		mPaint.setStyle(Paint.Style.FILL);
		// 设置线的效果，此为焦点圆角（r为3）
		mPaint.setPathEffect(new CornerPathEffect(3));

		// mPath=new Path();

	}

	/**
	 * 绘制三角
	 */
	@Override
	protected void dispatchDraw(Canvas canvas) {
		canvas.save();
		// 平移到正确的位置
		canvas.translate(mInitTranslationX + mTranslationX, getHeight() + 1);
		canvas.drawPath(mPath, mPaint);
		canvas.restore();
		super.dispatchDraw(canvas);
	}

	/**
	 * ViewGroup的子类通常再次方法中调整布局
	 */
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		// 获得三角形的宽度
		mTriangleWidth = (int) (w * 1.0f / mTabVisiableCount * RADIO_TRIANGLE);
		// 控制三角形的宽度，不超过最大宽度
		mTriangleWidth = Math.min(DIMENSION_TRIANGLE_WIDTH, mTriangleWidth);

		// 初始化三角形
		initTriangle();

		// 初始时的偏移量
		mInitTranslationX = getWidth() / mTabVisiableCount / 2 - mTriangleWidth / 2;

		//下面三个值是相同的；不过我觉得用getScreenWidth比较合理
//		Log.i("wangsongbin", "w:" + w + " getWidth():" + getWidth() + " screenWidth:"
//				+ getScreenWidth());
	}

	/**
	 * 初始化三角形
	 */
	private void initTriangle() {
		mPath = new Path();
		mTriangleHeight = (int) (mTriangleWidth / 2 * Math.sqrt(2));
		mPath.moveTo(0, 0);
		mPath.lineTo(mTriangleWidth, 0);
		mPath.lineTo(mTriangleWidth / 2, -mTriangleHeight);
		mPath.close();
	}

	/**
	 * 设置可见的tab数
	 */
	public void setVisiableTabCount(int count) {
		this.mTabVisiableCount = count;
	}

	/**
	 * 设置标题内容（可选，可以在布局中写死）
	 */
	public void setTabItemTitles(List<String> data) {
		if (data != null && data.size() > 0) {
			// 先移除所有子控件
			this.removeAllViews();
			this.mTabTitles =  data;
			for (String title : mTabTitles) {
				// 添加view
				this.addView(generateTextView(title));
			}
			// 设置item的点击事件
			setItemClickListener();
		}
	}

	/**
	 * 为LinearLayout的所有子控件设置点击事件
	 */
	private void setItemClickListener() {
		int count = this.getChildCount();
		for (int i = 0; i < count; i++) {
			final int j = i;
			View view = getChildAt(i);
			view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					mViewPager.setCurrentItem(j);
				}
			});
		}

	}

	/**
	 * 根据标题生成我们的TextView
	 * 
	 * @param title
	 * @return
	 */
	private TextView generateTextView(String title) {
		TextView tv = new TextView(getContext());
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		lp.width = getScreenWidth() / mTabVisiableCount;
		tv.setGravity(Gravity.CENTER);
		tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		tv.setTextColor(COLOR_TEXT_NORMAL);
		tv.setText(title);
		tv.setLayoutParams(lp);
		return tv;
	}

	/**
	 * 获得屏幕的宽度
	 * 
	 * @return
	 */
	private int getScreenWidth() {
		WindowManager mWindowManager = (WindowManager) getContext().getSystemService(
				Context.WINDOW_SERVICE);
		DisplayMetrics metrics = new DisplayMetrics();
		mWindowManager.getDefaultDisplay().getMetrics(metrics);
		return metrics.widthPixels;
	}

	/**
	 * 对外公布PageChangeListener的接口
	 */
	public interface PageChangeListener {

		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

		public void onPageSelected(int position);

		public void onPageScrollStateChanged(int state);

	}

	private PageChangeListener mPageChangeListener;

	/**
	 * 设施监听
	 * 
	 * @param mPageChangeListener
	 */
	public void setOnPageChangeListener(PageChangeListener mPageChangeListener) {
		this.mPageChangeListener = mPageChangeListener;
	}

	/**
	 * 设置相关联的ViewPager
	 */

	public void setViewPager(ViewPager mViewPager, int pos) {
		this.mViewPager = mViewPager;

		this.mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				resetTextViewColor();
				LightTextView(position);

				if (mPageChangeListener != null) {
					mPageChangeListener.onPageSelected(position);
				}
			}

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				scroll(position, positionOffset);
				if (mPageChangeListener != null) {
					mPageChangeListener.onPageScrolled(position, positionOffset,
							positionOffsetPixels);
				}
			}

			@Override
			public void onPageScrollStateChanged(int state) {
				if (mPageChangeListener != null) {
					mPageChangeListener.onPageScrollStateChanged(state);
				}
			}
		});

		this.mViewPager.setCurrentItem(pos);
		// 把修改文本颜色
		LightTextView(pos);

	}

	/**
	 * 把选中标题的字体调亮一点
	 */
	private void LightTextView(int pos) {
		View view = this.getChildAt(pos);
		if (view instanceof TextView) {
			((TextView) view).setTextColor(COLOR_TEXT_SELECTED);
		}
	}

	/**
	 * 重置文本颜色
	 */
	private void resetTextViewColor() {
		int count = getChildCount();
		for (int i = 0; i < count; i++) {
			View view = getChildAt(i);
			if (view instanceof TextView) {
				((TextView) view).setTextColor(COLOR_TEXT_NORMAL);
			}
		}
	}

	/**
	 * 指示器随着ViewPager的滑动而滚动
	 */
	private void scroll(int position, float offset) {
		int tabWidth = getScreenWidth() / mTabVisiableCount;
		// 初始化三角下标的偏移
		mTranslationX = (int) (tabWidth * (position + offset));

		if (offset > 0 && position >= (mTabVisiableCount - 2)&&getChildCount()>mTabVisiableCount) {
			if (mTabVisiableCount != 1) {
				this.scrollTo((int) ((position - (mTabVisiableCount - 2)) * tabWidth + offset
						* tabWidth), 0);
			} else {
				this.scrollTo((int) ((position + offset) * tabWidth), 0);
			}
		}

		invalidate();
	}

	/**
	 * 设置布局中的一些属性，如果设置了setTabTitles()测布局中的View无效
	 */

	@Override
	protected void onFinishInflate() {
		// TODO Auto-generated method stub
		super.onFinishInflate();

		int count = getChildCount();

		if (count == 0) {
			return;
		}

		for (int i = 0; i < count; i++) {
			View view = getChildAt(i);
			LayoutParams lp = (LayoutParams) view.getLayoutParams();
			lp.weight = 0;
			lp.width = getScreenWidth() / mTabVisiableCount;
			view.setLayoutParams(lp);

		}
		// 设置点击事件
		setItemClickListener();
	}

}
