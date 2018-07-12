package com.example.songbinwang.littledemo.view;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by songbinwang on 2016/6/2.
 */
public class MyDrawerLayout extends FrameLayout {

    View mMenuView, mMainView;
    ViewDragHelper mViewDragHelper;
    private int mMenuWidth;

    //第四步
    ViewDragHelper.Callback mCallback = new ViewDragHelper.Callback() {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            //开始滑动检测的条件,返回true开始检测
            return child == mMainView;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            //通常只要return left就可以实现滑动。
            //return left;
            //这里我们复杂一点，控制其滑动的范围为(leftPadding, leftPadding+mMenuView.Width)
            int newLeft = Math.min(Math.max(getPaddingLeft(), left), getPaddingLeft() + mMenuWidth);
            return newLeft;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return 0;
        }

        /**
         * 用户手指离开时回调。
         * @param releasedChild
         * @param xvel
         * @param yvel
         */
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            if (mMainView.getLeft() > mMenuWidth / 2) {
                //滑到一半时展开菜单
                mViewDragHelper.smoothSlideViewTo(mMainView, mMenuWidth, 0);
                ViewCompat.postInvalidateOnAnimation(MyDrawerLayout.this);
            } else {
                //未滑到一半时，关闭菜单
                mViewDragHelper.smoothSlideViewTo(mMainView, 0, 0);
                ViewCompat.postInvalidateOnAnimation(MyDrawerLayout.this);
            }
        }

        /**
         * 用户触摸到MainView时回调
         * @param capturedChild
         * @param activePointerId
         */
        @Override
        public void onViewCaptured(View capturedChild, int activePointerId) {
            super.onViewCaptured(capturedChild, activePointerId);
        }

        /**
         * 滑动状态改变时调用
         * @param state
         */
        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);
        }

        /**
         * 在位置改变时回调，通常用于滑动时进行缩放效果
         * @param changedView
         * @param left
         * @param top
         * @param dx
         * @param dy
         */
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
        }
    };


    public MyDrawerLayout(Context context) {
        super(context);
        initView();
    }

    public MyDrawerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MyDrawerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public MyDrawerLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    //第一步
    private void initView() {
        mViewDragHelper = ViewDragHelper.create(this, mCallback);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mMenuView = getChildAt(0);
        mMainView = getChildAt(1);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mMenuWidth = mMenuView.getMeasuredWidth();
    }

    //第二步
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //将触摸事件传递给ViewDragHelper，此操作必不可少
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    //第三步
    @Override
    public void computeScroll() {
        //super.computeScroll();
        if (mViewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }
}
