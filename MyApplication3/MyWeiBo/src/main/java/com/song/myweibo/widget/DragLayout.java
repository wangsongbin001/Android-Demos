package com.song.myweibo.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nineoldandroids.view.ViewHelper;
import com.song.myweibo.R;

/**
 * Created by songbinwang on 2016/10/19.
 */

public class DragLayout extends FrameLayout {

    private final static String TAG = "mdraglayout";

    private boolean isShowShadow = false;
    //手势处理类
    private GestureDetectorCompat gestureDetector;
    //拖拽处理帮助类
    private ViewDragHelper dragHelper;
    //拖拽监听类
    private DragListener dragListener;
    //水平拖拽距离
    private int range;
    //屏幕宽高
    private int width;
    private int height;
    //main视图在ViewGroup中左边距
    private int mainLeft;

    private Context context;
    private ImageView iv_shadow;
    //左边的视图
    private RelativeLayout vg_left;
    //右边的视图
    private CustomRelativeLayout vg_main;
    //页面状态初始close
    private Status status = Status.CLOSE;

    public DragLayout(Context context) {
        this(context, null);
    }

    public DragLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        gestureDetector = new GestureDetectorCompat(context, new YScrollDetector());
        dragHelper = ViewDragHelper.create(this, viewDragCallback);
    }

    class YScrollDetector extends GestureDetector.SimpleOnGestureListener {
        //返回false不响应
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float dx, float dy) {
            return Math.abs(dy) <= Math.abs(dx) && isDrag;
        }
    }

    /**
     * 侧滑的关键
     */
    ViewDragHelper.Callback viewDragCallback = new ViewDragHelper.Callback() {

        /**
         * @param child
         * @param pointerId
         * @return true 开始监听，拦截所有的子View
         */
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return true;
        }

        /**
         * 水平方向移动
         * @param child
         * @param left
         * @param dx
         * @return
         */
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            Log.i(TAG, "left:" + left + ", dx:" + dx);
            if (mainLeft + dx < 0) {
                return 0;
            } else if (mainLeft + dx > range) {
                return range;
            } else {
                return left;
            }
        }

        /**
         * 设置水平滑动的最远距离
         * @param child
         */
        @Override
        public int getViewHorizontalDragRange(View child) {
            return width;
        }

        /**
         * 子View被拖拽，移动时的回调
         */
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {

            if (changedView == vg_main) {
                mainLeft = left;
            } else {
                mainLeft = mainLeft + left;
            }
            if (mainLeft < 0) {
                mainLeft = 0;
            } else if (mainLeft > range) {
                mainLeft = range;
            }
            if (isShowShadow) {
                iv_shadow.layout(mainLeft, 0, mainLeft + width, height);
            }
            if (changedView == vg_left) {
                vg_left.layout(0, 0, width, height);
                vg_main.layout(mainLeft, 0, mainLeft + width, height);
            }
            dispatchDragEvent(mainLeft);
            String name = changedView == vg_left ? "vg_left" : changedView == vg_main ? "vg_main" : "";
            Log.i(TAG, "changeView is:" + name + ", left:"
                    + left + ",top:" + top + ",dx:" + dx + ",dy:" + dy + ",mainleft:"+ mainLeft);
        }

        /**
         * 拖拽子View，手势释放时的回调
         * @param releasedChild
         * @param xvel x 方向上的速度
         * @param yvel y 方向上的速度
         */
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            String name = releasedChild == vg_left ? "vg_left" : releasedChild == vg_main ? "vg_main" : "";
            Log.i(TAG, "realsedChild is:" + name + ", xvel:"
                    + xvel + ",yvel:" + yvel);
            if (xvel > 0) {
                open();
            } else if (xvel < 0) {
                close();
            } else if (releasedChild == vg_main && mainLeft > range * 0.3f) {
                open();
            } else if (releasedChild == vg_left && mainLeft > range * 0.7) {
                open();
            } else {
                close();
            }
        }
    };

    public interface DragListener {
        //侧滑打开
        public void onOpen();

        //侧滑关闭
        public void onClose();

        //侧滑正在拖动
        public void onDrag(float percent);
    }

    public void setDragListener(DragListener dragListener) {
        this.dragListener = dragListener;
    }

    /**
     * 布局加载时回调
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (isShowShadow) {
            iv_shadow = new ImageView(context);
            iv_shadow.setImageResource(R.mipmap.shadow);
            LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            addView(iv_shadow, 1, lp);
        }
        vg_left = (RelativeLayout) getChildAt(0);
        vg_main = (CustomRelativeLayout) getChildAt(isShowShadow ? 2 : 1);
        vg_main.setDragLayout(this);
        vg_left.setClickable(true);
        vg_main.setClickable(true);
    }

    public ViewGroup getVg_Left() {
        return vg_left;
    }

    public ViewGroup getVg_Main() {
        return vg_main;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = vg_left.getMeasuredWidth();
        height = vg_left.getMeasuredHeight();
        //拖拽的最大距离
        range = (int) (width * 0.6f);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        vg_left.layout(0, 0, width, height);
        vg_main.layout(mainLeft, 0, mainLeft + width, height);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return dragHelper.shouldInterceptTouchEvent(ev) && gestureDetector.onTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        try {
            dragHelper.processTouchEvent(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 处理拖拽事件
     *
     * @param mainLeft
     */
    private void dispatchDragEvent(int mainLeft) {
        float percent = mainLeft / (float)range ;
        animateView(percent);
        if (dragListener == null) {
            return;
        }
        dragListener.onDrag(percent);
        Status lastStatus = status;
        if (lastStatus != getStatus() && status == Status.CLOSE) {
            dragListener.onClose();
        } else if (lastStatus != getStatus() && status == Status.OPEN) {
            dragListener.onOpen();
        }
    }

    ;

    /**
     * 根据拖拽距离的比例，进行带有动画的放大缩小View
     *
     * @param percent
     */
    private void animateView(float percent) {
        float f1 = 1 - percent * 0.3f;
        //vg_main水平方向根据,根据比例缩放
        ViewHelper.setScaleX(vg_main, f1);
        ViewHelper.setScaleY(vg_main, f1);
        //设置水平X抽平移
        ViewHelper.setTranslationX(vg_left, -vg_left.getWidth() / 2.3f + vg_left.getWidth() / 2.3f * percent);
        //根据比例，放到缩小vg_left
        ViewHelper.setScaleX(vg_left, 0.5f + 0.5f * percent);
        ViewHelper.setScaleY(vg_left, 0.5f + 0.5f * percent);
        //根据百分比设置透明度
        ViewHelper.setAlpha(vg_left, percent);
        if (isShowShadow) {
            //阴影效果视图进行缩放
            ViewHelper.setScaleX(iv_shadow, f1 * 1.4f * (1 - percent * 0.12f));
            ViewHelper.setScaleY(iv_shadow, f1 * 1.85f * (1 - percent * 0.12f));
        }
        getBackground().setColorFilter(evaluate(percent, Color.BLACK, Color.TRANSPARENT), PorterDuff.Mode.SRC_OVER);
    }

    private Integer evaluate(float fraction, int startValue, int endValue) {
        int startInt = (Integer) startValue;
        int startA = (startInt >> 24) & 0xff;
        int startR = (startInt >> 16) & 0xff;
        int startG = (startInt >> 8) & 0xff;
        int startB = startInt & 0xff;
        int endInt = (Integer) endValue;
        int endA = (endInt >> 24) & 0xff;
        int endR = (endInt >> 16) & 0xff;
        int endG = (endInt >> 8) & 0xff;
        int endB = endInt & 0xff;
        return (int) ((startA + (int) (fraction * (endA - startA))) << 24)
                | (int) ((startR + (int) (fraction * (endR - startR))) << 16)
                | (int) ((startG + (int) (fraction * (endG - startG))) << 8)
                | (int) ((startB + (int) (fraction * (endB - startB))));
    }

    /**
     * 有加速度时，停止拖拽后，不会立即停止
     */
    @Override
    public void computeScroll() {
        if (dragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    /**
     * 页面的状态
     */
    enum Status {
        OPEN, CLOSE, DRAG
    }

    public Status getStatus() {
        return status;
    }

    public void open() {
        open(true);
    }

    private void open(boolean animate) {
        if (animate) {
            if (dragHelper.smoothSlideViewTo(vg_main, range, 0)) {
                ViewCompat.postInvalidateOnAnimation(this);
            }
        } else {
            vg_main.layout(range, 0, range + width, height);
            dispatchDragEvent(range);
        }
    }

    public void close() {
        close(true);
    }

    private void close(boolean animate) {
        if (animate) {
            if (dragHelper.smoothSlideViewTo(vg_main, 0, 0)) {
                ViewCompat.postInvalidateOnAnimation(this);
            }
        } else {
            vg_main.layout(0, 0, width, height);
            dispatchDragEvent(range);
        }
    }

    private boolean isDrag = true;

    public boolean isDrag() {
        return isDrag;
    }

    public void setIsDrag(boolean isDrag) {
        this.isDrag = isDrag;
        if (isDrag) {
            dragHelper.abort();
        }
    }
}
