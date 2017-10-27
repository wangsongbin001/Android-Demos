package com.wang.mtoolsdemo.common.banner;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wang.mtoolsdemo.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by dell on 2017/10/27.
 * 轮播封装
 */

public class Banner extends FrameLayout{

    //使用senddelay实现轮播循环
    private Handler mHandler;
    private ViewPager vp_pager;
    private TextView tv_title, tv_index;
    private Context mContext;

    private List<String> urls;
    private List<String> titles;

    //缓存View
    LinkedList<View> mCacheViews;
    private MyAdaper mAdapter;

    //实际item个数
    private int mCount = 0;

    public Banner(Context context) {
        this(context, null);
    }

    public Banner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Banner(Context context, AttributeSet attrs,int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        mCacheViews = new LinkedList<>();
        urls = new ArrayList<>();
        titles = new ArrayList<>();
        initViews();
        registerListeners();
    }

    private void initViews(){
        LayoutInflater.from(mContext).inflate(R.layout.layout_banner, this, true);
        vp_pager = findViewById(R.id.id_pager);
        tv_title = findViewById(R.id.id_title);
        tv_index = findViewById(R.id.id_index);
        mAdapter = new MyAdaper();
        vp_pager.setAdapter(mAdapter);
    }

    private void registerListeners(){
        vp_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position <= 0){
                    vp_pager.setCurrentItem(mCount - 3, false);
                    position = mCount - 3;
                }else if(position >= mCount - 1){
                    vp_pager.setCurrentItem(1, false);
                    position = 0;
                }else{
                    position = position - 1;
                }
                //此时的position为逻辑上urls中的位置
                StringBuilder index = new StringBuilder((position + 1) + "").append("/").append(urls.size());
                tv_index.setText(index.toString());
                String title = (position < titles.size() && position >=0) ? titles.get(position) : "";
                tv_title.setText(title);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void setData(List<String> urls, List<String> titles, int index){
        this.urls.clear();
        this.urls.addAll(urls);
        mCount = urls.size() + 2;
        mAdapter.notifyDataSetChanged();
        this.titles.clear();
        this.titles.addAll(titles);
        vp_pager.setCurrentItem(1);
    }

    class MyAdaper extends PagerAdapter{

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView iv = new ImageView(mContext);
            String url = "";
            if(position <= 0){
                url = urls.get(urls.size() - 1);
            }else if(position >= mCount - 1){
                url = urls.get(0);
            }else{
                url = urls.get(position - 1);
            }
            Glide.with(mContext)
                    .load(url)
                    .placeholder(R.mipmap.no_banner)
                    .centerCrop()
                    .into(iv);
            container.addView(iv);
            return iv;
//            return super.instantiateItem(container, position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
            ImageView view = (ImageView) object;
            container.removeView(view);
        }

        @Override
        public int getCount() {
            return mCount;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
