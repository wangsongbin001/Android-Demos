package com.song.myweibo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.song.myweibo.ui.fg.MainFragment;
import com.song.myweibo.util.LogUtil;

/**
 * Created by songbinwang on 2016/10/24.
 */

public class TabAdapter extends FragmentPagerAdapter {

    private final static String tag = "tab_adapter";

    public static final String[] TITLES = new String[] { "业界", "移动开发", "研发研发", "程序员", "云计算" };

    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        position = position % TITLES.length + 1;
        LogUtil.i(tag, "position:"+position);
        return MainFragment.getInstance(position);
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position%TITLES.length];
    }
}
