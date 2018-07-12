package com.example.songbinwang.littledemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

/**
 * Created by songbinwang on 2016/6/2.
 */
public class MyScrollView extends View {

    int lastX;
    int lastY;
    int initRawX = -1;
    int initRawY = -1;
    private Scroller mScroller;

    public MyScrollView(Context context) {
        this(context, null);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(Context context) {
        //初始化Scroller方法
        mScroller = new Scroller(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //android坐标
        int rawX = (int) event.getRawX();
        int rawY = (int) event.getRawY();
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastX = rawX;
                lastY = rawY;
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetX = rawX - lastX;
                int offsetY = rawY - lastY;
                layout(getLeft() + offsetX, getTop() + offsetY,getRight() + offsetX, getBottom() + offsetY);
                lastX = rawX;
                lastY = rawY;
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    //    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        //试图坐标
//        int x = (int) event.getX();
//        int y = (int) event.getY();
//        //android坐标
//        int rawX = (int) event.getRawX();
//        int rawY = (int) event.getRawY();
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                lastX = x;
//                lastY = y;
////                lastX = rawX;
////                lastY = rawY;
//                break;
//            case MotionEvent.ACTION_MOVE:
//                int offsetX = x - lastX;
//                int offsetY = y - lastY;
////                1，Layout方法
////                layout(getLeft() + offsetX, getTop() + offsetY,getRight() + offsetX, getBottom() + offsetY);
////                2,offsetLeftAndRight, offsetTopAndBottom
////                offsetLeftAndRight(offsetX);
////                offsetTopAndBottom(offsetY);
////                3,LayoutParams方法
////                ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) getLayoutParams();
////                lp.leftMargin  = getLeft() + offsetX;
////                lp.topMargin = getTop() + offsetY;
////                setLayoutParams(lp);
//                //4,scrollTo and scrollBy 方法
//                //((View)getParent()).scrollBy(-offsetX, -offsetY);
//                ((View)getParent()).scrollTo(((View)getParent()).getScrollX() -offsetX,((View)getParent()).getScrollY() -offsetY);
//                //((View)getParent()).scrollTo(-getLeft() -offsetX,-getTop() -offsetY);
//                Log.i("wangsongbin", "rawX:" + rawX + ";rawY:" + rawY);
//                Log.i("wangsongbin", "scollX:" + ((View) getParent()).getScrollX() + ";scollY:" + ((View) getParent()).getScrollY());
//                //5.Scoller辅助类
//
//
////                lastX = rawX;
////                lastY = rawY;
//                break;
//            case MotionEvent.ACTION_UP:
//                View viewGroup = (View) getParent();
//                mScroller.startScroll(viewGroup.getScrollX(), viewGroup.getScrollY(), -viewGroup.getScrollX(), -viewGroup.getScrollY(), 3000);
//                invalidate();
//                break;
//        }
//        return true;
//    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            ((View) getParent()).scrollTo(
                    mScroller.getCurrX(),
                    mScroller.getCurrY()
            );
            //通过重绘制来不停的调用computeScroll
            invalidate();
        }
    }
}
