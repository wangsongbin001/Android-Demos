package com.song.myweibo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * Created by songbinwang on 2016/10/19.
 */

public class CustomRelativeLayout extends RelativeLayout{

    private DragLayout dl;

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

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(dl != null && dl.getStatus() != DragLayout.Status.CLOSE){
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(dl != null && dl.getStatus() != DragLayout.Status.CLOSE){
            if(event.getAction() == MotionEvent.ACTION_UP){
                dl.close();
            }
            return true;
        }
        return super.onTouchEvent(event);
    }
}
