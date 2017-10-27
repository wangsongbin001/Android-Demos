package com.wang.mtoolsdemo.common.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wang.mtoolsdemo.R;

import java.util.LinkedList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dell on 2017/10/19.
 * 缩放图片展示界面
 */

public class ImagesShowActivitiy extends Activity {
    public static final String SOURCE_KEY = "source_key";

    @Bind(R.id.id_pager)
    ViewPager idPager;
    @Bind(R.id.tv_index)
    TextView tvIndex;

    private String[] sourceUrls;
    private LinkedList<PinchImageView> mCacheView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageshow);
        ButterKnife.bind(this);

        mCacheView = new LinkedList<>();
        fetchIntent();
        init();
    }

    /**
     * 获取资源
     */
    private void fetchIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            sourceUrls = intent.getStringArrayExtra(SOURCE_KEY);
        }
        if (sourceUrls == null || sourceUrls.length == 0) {
            finish();
        }
    }

    //初始化ViewPager
    private void init() {
        tvIndex.setText("1/" + sourceUrls.length);
        idPager.setAdapter(new MAdapter());
        idPager.setOffscreenPageLimit(3);
        idPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                tvIndex.setText((position + 1) + "/" + sourceUrls.length);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    class MAdapter extends PagerAdapter {
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            PinchImageView pv = null;
            if (mCacheView.size() > 0) {
                pv = mCacheView.remove();
                pv.reset();
            } else {
                pv = new PinchImageView(ImagesShowActivitiy.this);
            }
            Glide.with(ImagesShowActivitiy.this)
                    .load(sourceUrls[position])
                    .centerCrop()
                    .placeholder(R.mipmap.sh)
                    .into(pv);
            container.addView(pv);
            return pv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            PinchImageView pv = (PinchImageView) object;
            mCacheView.add(pv);
            container.removeView(pv);
        }

        @Override
        public int getCount() {
            return sourceUrls.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
