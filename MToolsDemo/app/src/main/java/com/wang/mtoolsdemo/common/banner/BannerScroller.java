package com.wang.mtoolsdemo.common.banner;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * Created by dell on 2017/10/30.
 * 当duration为0的时候，可以解决setCurrentItem，数据加载不出的bug
 */

public class BannerScroller extends Scroller{
    public static final int DEFAULT_DURATION = 800;//250;
    private int mDuration = DEFAULT_DURATION;

    public BannerScroller(Context context) {
        super(context);
    }

    public BannerScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public BannerScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    public void setDuration(int mDuration) {
        this.mDuration = mDuration;
    }
}
