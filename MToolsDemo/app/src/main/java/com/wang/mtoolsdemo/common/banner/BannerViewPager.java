package com.wang.mtoolsdemo.common.banner;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Created by dell on 2017/10/30.
 */

public class BannerViewPager extends ViewPager{

    private BannerScroller mScroller;
    public void setBannerScroller(BannerScroller mScroller){
        this.mScroller = mScroller;
    }

    public BannerViewPager(Context context) {
        super(context);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setCurrentItem(int item) {
        setCurrentItem(item, true);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        if(mScroller == null){
            super.setCurrentItem(item, smoothScroll);
        }else{
            int current = getCurrentItem();
            if(Math.abs(item - current) > 1){
                mScroller.setDuration(0);
                super.setCurrentItem(item, smoothScroll);
                mScroller.setDuration(BannerScroller.DEFAULT_DURATION);
            }else{
                mScroller.setDuration(BannerScroller.DEFAULT_DURATION);
                super.setCurrentItem(item, smoothScroll);
            }
        }
    }
}
