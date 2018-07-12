package com.wang.csdnapp.ui.fg;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wang.csdnapp.R;
import com.wang.csdnapp.ui.adapter.TabAdapter;
import com.wang.csdnapp.util.ToastUtil;
import com.wang.csdnapp.view.ViewPagerIndicator;

import java.util.Arrays;

/**
 * Created by SongbinWang on 2017/6/13.
 */

public class HeadLinesFragment extends MBaseFragment{

    private ViewPagerIndicator id_indicator;
    private ViewPager id_pager;
    private ImageView iv_drawer, iv_add, iv_search;

    public static HeadLinesFragment newInstance(Bundle bundle){
        HeadLinesFragment fg = new HeadLinesFragment();
        fg.setArguments(bundle);
        return fg;
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null){

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fg_headlines, null);
        initViews(root);
        registerListeners();
        return root;
    }

    /**
     * 初始化控件
     * @param root
     */
    private void initViews(View root){
        iv_drawer = (ImageView) root.findViewById(R.id.iv_drawer);
        iv_add = (ImageView) root.findViewById(R.id.iv_add);
        iv_search = (ImageView) root.findViewById(R.id.iv_search);

        id_indicator = (ViewPagerIndicator) root.findViewById(R.id.id_indicator);
        id_indicator.setTabItemTitles(Arrays.asList(TabAdapter.TITLES));
        id_pager = (ViewPager) root.findViewById(R.id.id_pager);
        id_pager.setAdapter(new TabAdapter(getFragmentManager()));
        id_pager.setOffscreenPageLimit(4);
        id_indicator.setViewPager(id_pager,  0);
        id_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void registerListeners(){
        iv_drawer.setOnClickListener(onClicker);
        iv_search.setOnClickListener(onClicker);
        iv_add.setOnClickListener(onClicker);
    }

    View.OnClickListener onClicker = new  View.OnClickListener(){
        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.iv_drawer:
                    if(onFragmentItemClickListener != null){
                        onFragmentItemClickListener.onFragmentItemClick("drawer");
                    }
                    break;
                case R.id.iv_add:
                    ToastUtil.toast("add");
                    if(onFragmentItemClickListener != null){
                        onFragmentItemClickListener.onFragmentItemClick("add");
                    }
                    break;
                case R.id.iv_search:
                    ToastUtil.toast("search");
                    if(onFragmentItemClickListener != null){
                        onFragmentItemClickListener.onFragmentItemClick("search");
                    }
                    break;
            }
        }
    };
}
