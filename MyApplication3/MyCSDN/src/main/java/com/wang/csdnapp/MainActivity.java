package com.wang.csdnapp;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;
import com.wang.csdnapp.ui.adapter.LeftItemAdapter;
import com.wang.csdnapp.ui.adapter.TabAdapter;
import com.wang.csdnapp.util.ScreenUtil;
import com.wang.csdnapp.view.CustomRelativeLayout;
import com.wang.csdnapp.view.DragLayout;
import com.wang.csdnapp.view.ViewPagerIndicator;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private DragLayout dragLayout;
    private ListView lv_left_main;
    private TextView tv_topbar_title;
    private ImageView iv_topbar_icon;

    private ViewPagerIndicator id_indicator;
    private ViewPager id_paper;
    private CustomRelativeLayout vg_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        setStatusBar();
        initViews();
        lv_left_main.setAdapter(new LeftItemAdapter(this));
        dragLayout.setDragListener(new DragLayout.DragListener() {
            @Override
            public void onOpen() {

            }

            @Override
            public void onClose() {

            }

            @Override
            public void onDrag(float percent) {
                ViewHelper.setAlpha(iv_topbar_icon, 1 - percent);
            }
        });
        iv_topbar_icon.setOnClickListener(this);
    }

    public void setStatusBar(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            final ViewGroup linear_bar = (ViewGroup) findViewById(R.id.bar_layout);
            final int statusHeight = (int) ScreenUtil.getStatusBarHeight(this);
            linear_bar.post(new Runnable() {
                @Override
                public void run() {
                    int titleHeight = linear_bar.getHeight();
                    ViewGroup.LayoutParams params = linear_bar.getLayoutParams();
                    params.height = statusHeight + titleHeight;
                    linear_bar.setLayoutParams(params);
                }
            });
        }
    }

    private void initViews() {
        dragLayout = (DragLayout) findViewById(R.id.drag_layout);
        lv_left_main = (ListView) findViewById(R.id.lv_left_main);
        iv_topbar_icon = (ImageView) findViewById(R.id.iv_topbar_icon);
        tv_topbar_title  = (TextView)findViewById(R.id.tv_topbar_title);

        id_indicator = (ViewPagerIndicator) findViewById(R.id.id_indicator);
        id_indicator.setTabItemTitles(Arrays.asList(TabAdapter.TITLES));
        id_paper = (ViewPager) findViewById(R.id.id_pager);
        id_paper.setAdapter(new TabAdapter(getSupportFragmentManager()));
        id_indicator.setViewPager(id_paper,  0);
        id_paper.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 0){
                    dragLayout.setIsDrag(true);
                }else{
                    dragLayout.setIsDrag(false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        vg_main = (CustomRelativeLayout) findViewById(R.id.id_vg_main);
        vg_main.setViewPager(id_paper);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_topbar_icon:
                dragLayout.open();
                break;
        }

    }
}
