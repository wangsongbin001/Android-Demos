package com.wang.csdnapp.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nineoldandroids.view.ViewHelper;
import com.wang.csdnapp.R;
import com.wang.csdnapp.util.LogUtil;

/**
 * Created by SongbinWang on 2017/3/31.
 */

public class DragLayout extends FrameLayout{

    private static final String TAG = "DragLayout";
    //在滑动时，是否带阴影效果
    private boolean isShowShadow = false;
    //手势处理类
    private GestureDetectorCompat gestureDetectorCompat;

    //拖拽处理类
    private ViewDragHelper viewDragHelper;
    //屏幕的高宽
    private int width;
    private int height;
    //水平拖拽的最大距离，width * 0.6f
    private int range;
    //Main视图的left边距，是随着滑动变化的
    private int mainLeft;

    private Context context;
    //阴影
    private ImageView iv_shadow;
    //左边的视图布局
    private RelativeLayout vg_left;
    //main布局
    private CustomRelativeLayout vg_main;

    //页面状态，初始化close
    private Status status = Status.CLOSE;
    enum Status {
        OPEN, CLOSE, DRAG
    }

    private boolean isDrag = true;

    //拖拽监听类
    private DragListener dragListener;

    public DragLayout(Context context) {
        this(context, null);
    }

    public DragLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        //初始化，手势监听类
        gestureDetectorCompat = new GestureDetectorCompat(context, new YScrollDetector());
        //初始化，ViewDrawHelper
        viewDragHelper = ViewDragHelper.create(this, viewDragCallback);
    }

    class YScrollDetector extends GestureDetector.SimpleOnGestureListener {
        //返回false不响应,即在上下滑动时不响应
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float dx, float dy) {
//            return Math.abs(dy) <= Math.abs(dx) && isDrag;
            return isDrag;
        }
    }

    ViewDragHelper.Callback viewDragCallback = new ViewDragHelper.Callback() {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            //所有的子View都可以拖拽
            return true;
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            //设置水平滑动最远距离，屏幕宽
            return width;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            /**
             * 处理水平滑动，
             * @param child  滑动的子view
             * @param left  水平滑动了的距离
             * @param dx  此次水平滑动的增量
             * @return
             */
            LogUtil.i(TAG, "left:" + left + ", dx:" + dx);
            if(mainLeft + dx < 0){
                return 0;
            }else if(mainLeft + dx > range){
                return range;
            }else{
                return left;
            }
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            //实时监听子View的位置变动情况。
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            if(changedView == vg_main){
                mainLeft = left;
            }else{
                mainLeft = left + mainLeft;
            }
            if(mainLeft < 0){
                mainLeft = 0;
            }else if(mainLeft > range){
                mainLeft = range;
            }
            //根据滑动的实时监听，设置最新的布局。
            if (isShowShadow) {
                iv_shadow.layout(mainLeft, 0, mainLeft + width, height);
            }
            if (changedView == vg_left) {
                //表示,
                vg_left.layout(0, 0, width, height);
                vg_main.layout(mainLeft, 0, mainLeft + width, height);
            }
            //根据比例处理，缩放
            dispatchDragEvent(mainLeft);
            String name = changedView == vg_left ? "vg_left" : changedView == vg_main ? "vg_main" : "";
            LogUtil.i(TAG, "changeView is:" + name + ", left:"
                    + left + ",top:" + top + ",dx:" + dx + ",dy:" + dy + ",mainleft:"+ mainLeft);
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            String name = releasedChild == vg_left ? "vg_left" : releasedChild == vg_main ? "vg_main" : "";
            LogUtil.i(TAG, "realsedChild is:" + name + ", xvel:"
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

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //加载完布局，初始化子控件
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
        //因为，vg_left使用的match_parent，所以获得是屏幕的高宽
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
        //拦截事件
        return viewDragHelper.shouldInterceptTouchEvent(ev)
                && gestureDetectorCompat.onTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //拦截成功后的事件处理
        try {
            viewDragHelper.processTouchEvent(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

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

    public Status getStatus() {
        return status;
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

    public void open() {
        open(true);
        status = Status.OPEN;
    }

    private void open(boolean animate) {
        if (animate) {
            if (viewDragHelper.smoothSlideViewTo(vg_main, range, 0)) {
                ViewCompat.postInvalidateOnAnimation(this);
            }
        } else {
            vg_main.layout(range, 0, range + width, height);
            dispatchDragEvent(range);
        }
    }

    public void close() {
        close(true);
        status = Status.CLOSE;
    }

    private void close(boolean animate) {
        if (animate) {
            if (viewDragHelper.smoothSlideViewTo(vg_main, 0, 0)) {
                ViewCompat.postInvalidateOnAnimation(this);
            }
        } else {
            vg_main.layout(0, 0, width, height);
            dispatchDragEvent(range);
        }
    }

    @Override
    public void computeScroll() {
        if (viewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public boolean isDrag() {
        return isDrag;
    }

    public void setIsDrag(boolean isDrag) {
        this.isDrag = isDrag;
        if (isDrag) {
            viewDragHelper.abort();
        }
    }
}
