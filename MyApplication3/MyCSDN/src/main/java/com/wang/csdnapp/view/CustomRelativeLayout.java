package com.wang.csdnapp.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.wang.csdnapp.util.LogUtil;

/**
 * Created by songbinwang on 2016/10/19.
 */

public class CustomRelativeLayout extends RelativeLayout{

    private DragLayout dl;
    private ViewPager viewPager;
    private float lastX;

    public CustomRelativeLayout(Context context){
        this(context, null);
    }

    public CustomRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setDragLayout(DragLayout dl){
        this.dl = dl;
    }

    public void setViewPager(ViewPager vp){
        this.viewPager = vp;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;

        switch(ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                intercept = false;
                break;
            case MotionEvent.ACTION_MOVE:
                if(dl != null && dl.getStatus() != DragLayout.Status.CLOSE){
                    intercept = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercept =false;
                break;
        }
        LogUtil.i("DragLayout", "intercept:" + intercept);
        return intercept;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        switch(ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastX = ev.getRawX();
                LogUtil.i("DragLayout", "lastX:" + lastX);
                break;
            case MotionEvent.ACTION_MOVE:
                LogUtil.i("DragLayout", "currentX:" + ev.getRawX()
                        + " currentItem:" + viewPager.getCurrentItem());
                if(viewPager != null && viewPager.getCurrentItem() == 0
                        && ev.getRawX() - lastX >= 0){
                    return false;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        if(dl != null && dl.getStatus() != DragLayout.Status.CLOSE){
//            if(event.getAction() == MotionEvent.ACTION_UP){
//                dl.close();
//            }
//            //return true;
//        }
        return super.onTouchEvent(event);
    }
}
