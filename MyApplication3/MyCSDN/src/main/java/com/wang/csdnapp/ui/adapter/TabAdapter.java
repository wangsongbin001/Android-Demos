package com.wang.csdnapp.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.wang.csdnapp.ui.fg.MainFragment;
import com.wang.csdnapp.util.LogUtil;


/**
 * Created by songbinwang on 2016/10/24.
 */

public class TabAdapter extends FragmentPagerAdapter {

    private final static String tag = "tab_adapter";

    public static final String[] TITLES = new String[]{"业界", "移动开发", "软件研发", "云计算"};
    public static final Integer[] NewsType = new Integer[]{1, 2, 3, 5};

    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        int newsType = NewsType[position];
        LogUtil.i(tag, "position:" + position + ",newType:" + newsType);
        return MainFragment.getInstance(newsType);
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position % TITLES.length];
    }
}
