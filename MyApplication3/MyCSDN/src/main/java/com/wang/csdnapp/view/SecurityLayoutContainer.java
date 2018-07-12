package com.wang.csdnapp.view;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.wang.csdnapp.R;

/**
 * Created by SongbinWang on 2017/5/31.
 */

public class SecurityLayoutContainer extends FrameLayout{

    private SecurityLayout1 securityLayout1;
    private SecurityLayout2 securityLayout2;
    private SecurityLayout3 securityLayout3;
    private Context mContext;

    public SecurityLayoutContainer(Context context) {
        this(context, null);
    }

    public SecurityLayoutContainer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SecurityLayoutContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initViews();
    }

    private void initViews(){
        if(getChildCount() > 0){
            removeAllViews();
        }
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        securityLayout1 = (SecurityLayout1) LayoutInflater
                .from(mContext).inflate(R.layout.layout_security1, null, false);
        securityLayout1.setParentView(this);
        addView(securityLayout1, lp);
    }

    public void next(int current, Bundle bundle){
       if(current == 1){
           if(getChildCount() > 0){
               removeAllViews();
           }
           if(securityLayout2 == null){
               securityLayout2 = (SecurityLayout2) LayoutInflater
                       .from(mContext).inflate(R.layout.layout_security2, null, false);
               securityLayout2.setParentView(this, bundle);
           }
           FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                   FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
           addView(securityLayout2, lp);
       }else if(current == 2){
           if(getChildCount() > 0){
               removeAllViews();
           }
           if(securityLayout3 == null){
               securityLayout3 = (SecurityLayout3) LayoutInflater
                       .from(mContext).inflate(R.layout.layout_security3, null, false);
               securityLayout3.setParentView(this, bundle);
           }
           FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                   FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
           addView(securityLayout3, lp);
       }else if(current == 3){
           if(getChildCount() > 0){
               removeAllViews();
           }
           View viewSuccess = LayoutInflater
                   .from(mContext).inflate(R.layout.layout_securitysuccess, null, false);
           FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                   FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
           addView(viewSuccess, lp);
       }
    }
}
