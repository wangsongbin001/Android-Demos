package com.example.songbinwang.littledemo.view;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by SongbinWang on 2017/7/19.
 */

public class CustomItemLayout extends RelativeLayout{

    private int type;

    public CustomItemLayout(Context context) {
        super(context);
    }

    public CustomItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomItemLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void initViews(int bundle){
        this.type = type;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        if(mode != MeasureSpec.EXACTLY){
            if(type == 1){

            }else if(type == 2){

            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
