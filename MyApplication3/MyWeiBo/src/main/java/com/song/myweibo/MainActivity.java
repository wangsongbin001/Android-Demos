package com.song.myweibo;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;
import com.song.myweibo.adapter.LeftItemAdapter;
import com.song.myweibo.adapter.TabAdapter;
import com.song.myweibo.widget.DragLayout;
import com.song.myweibo.widget.ViewPagerIndicator;

import java.util.Arrays;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    private DragLayout dragLayout;
    private ListView lv_left_main;
    private TextView tv_topbar_title;
    private ImageView iv_topbar_icon;

    private ViewPagerIndicator id_indicator;
    private ViewPager id_paper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setStatusBar();
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
