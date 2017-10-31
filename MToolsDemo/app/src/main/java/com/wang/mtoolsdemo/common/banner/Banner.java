package com.wang.mtoolsdemo.common.banner;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wang.mtoolsdemo.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by dell on 2017/10/27.
 * 轮播封装
 */

public class Banner extends FrameLayout implements ViewPager.OnPageChangeListener {

    private static final String tag = "banner";
    private ViewPager vp_pager;
    private BannerScroller mScroller;//自定义Scroller
    private TextView tv_title;
    private TextView num_index, num_index_inside;
    private LinearLayout ll_title, circle_index, circle_index_inside;
    private ImageView bannerDefaultEmpty;
    private Context mContext;
    private int indexType = 0;

    private int indicateSize = 0;
    private int paddingSize = 5;
    private DisplayMetrics dm;
    private List<ImageView> indicatorImages;
    private int gravity = -1;

    private List<String> urls;
    private List<String> titles;


    //缓存View
    LinkedList<View> mCacheViews;
    private BannerPagerAdaper mAdapter;

    //实际item个数
    private int mCount = 0;
    private int currentItem = 1;
    private boolean isAutoScroll = false;
    private int delay = 3 * 1000;

    //对外接口
    interface BannerPageChangeListener {
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

        public void onPageSelected(int position);

        public void onPageScrollStateChanged(int state);
    }

    private BannerPageChangeListener mBannerPageChangeListener;

    public Banner setBannerPageChangeListener(BannerPageChangeListener mBannerPageChangeListener) {
        this.mBannerPageChangeListener = mBannerPageChangeListener;
        return this;
    }

    //使用senddelay实现轮播循环
    private Handler mHandler;

    public Banner(Context context) {
        this(context, null);
    }

    public Banner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Banner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        mHandler = new Handler();
        dm = context.getResources().getDisplayMetrics();
        indicateSize = dm.widthPixels / 80;
        initViews();
    }

    private void initViews() {
        mCacheViews = new LinkedList<>();
        urls = new ArrayList<>();
        titles = new ArrayList<>();
        indicatorImages = new ArrayList<>();

        LayoutInflater.from(mContext).inflate(R.layout.layout_banner, this, true);
        vp_pager = findViewById(R.id.id_pager);
        circle_index = findViewById(R.id.circle_index);
        num_index = findViewById(R.id.num_index);
        ll_title = findViewById(R.id.ll_title);
        tv_title = findViewById(R.id.id_title);
        circle_index_inside = findViewById(R.id.circle_index_inside);
        num_index_inside = findViewById(R.id.num_index_inside);
        bannerDefaultEmpty = findViewById(R.id.banner_default_image);

//        mAdapter = new BannerPagerAdaper();
//        vp_pager.setAdapter(mAdapter);
        //设置监听
//        vp_pager.addOnPageChangeListener(this);
        initPagerScroller();
    }

    private void initPagerScroller() {
        try {
            Field mField = ViewPager.class.getDeclaredField("mScroller");
            mField.setAccessible(true);
            mScroller = new BannerScroller(vp_pager.getContext());
            mScroller.setDuration(BannerScroller.DEFAULT_DURATION);
            mField.set(vp_pager, mScroller);
        } catch (Exception e) {
            Log.e(tag, e.getMessage());
        }
    }

    //自动播放
    public Banner setAutoScroll(boolean isAutoScroll) {
        this.isAutoScroll = isAutoScroll;
        return this;
    }

    //设置延时时间
    public Banner setDelayTime(int delay){
        this.delay = delay;
        return this;
    }

    //设置indicator的位置
    public Banner setIndicatorGravity(int type) {
        switch (type) {
            case BannerConfig.LEFT:
                this.gravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;
                break;
            case BannerConfig.CENTER:
                this.gravity = Gravity.CENTER;
                break;
            case BannerConfig.RIGHT:
                this.gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
                break;
        }
        return this;
    }

    //设置ViewPager的切换动画
    public Banner setBannerAnimation(Class<? extends ViewPager.PageTransformer> transformer) {
        try {
            setPageTransformer(true, transformer.newInstance());
        } catch (Exception e) {
            Log.e(tag, "Please set the PageTransformer class");
        }
        return this;
    }
    public Banner setPageTransformer(boolean reverseDrawingOrder, ViewPager.PageTransformer transformer) {
        vp_pager.setPageTransformer(reverseDrawingOrder, transformer);
        return this;
    }

    //设置缓存页面数
    public Banner setOffscreenPageLimit(int limit) {
        if (vp_pager != null) {
            vp_pager.setOffscreenPageLimit(limit);
        }
        return this;
    }

    //设置标题数据
    public Banner setBannerTitles(List<String> titles) {
        this.titles = titles;
        return this;
    }

    //设置图片资源
    public Banner setImages(List<String> imageUrls) {
        if(imageUrls == null){
            return this;
        }
        this.urls = imageUrls;
        mCount = imageUrls.size() + 2;
        return this;
    }

    public void update(List<String> imageUrls, List<String> titles) {
        if(imageUrls == null || titles == null){
            return;
        }
        this.titles.clear();
        this.titles.addAll(titles);
        update(imageUrls);
    }

    private void update(List<String> imageUrls){
        this.urls.clear();
        this.indicatorImages.clear();
        this.urls.addAll(imageUrls);
        this.mCount = urls.size() + 2;
        start();
    }

    /**
     * 设置下标类型
     * @param type
     * @return
     */
    public Banner setIndicateType(int type) {
        indexType = type;
        return this;
    }

    public void start(){
        setBannerStyleUI();
        setData();
    };

    private void setBannerStyleUI(){
        switch (indexType) {
            case BannerConfig.INDEX_YPTE_CIRCLE:
                createIndicator();
                ll_title.setVisibility(View.GONE);
                circle_index.setVisibility(View.VISIBLE);
                break;
            case BannerConfig.INDEX_YPTE_NUM:
                ll_title.setVisibility(View.GONE);
                num_index.setVisibility(View.VISIBLE);
                break;
            case BannerConfig.INDEX_YPTE_TITLE_CIRCLR:
                createIndicator();
                ll_title.setVisibility(View.VISIBLE);
                tv_title.setVisibility(View.VISIBLE);
                circle_index_inside.setVisibility(View.VISIBLE);
                break;
            case BannerConfig.INDEX_YPTE_TITLE_NUM:
                ll_title.setVisibility(View.VISIBLE);
                tv_title.setVisibility(View.VISIBLE);
                num_index_inside.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void setData(){
        if(urls == null || urls.size() == 0){
            bannerDefaultEmpty.setVisibility(View.VISIBLE);
            bannerDefaultEmpty.setImageResource(R.mipmap.no_banner);
            return;
        }
        bannerDefaultEmpty.setVisibility(View.GONE);
        currentItem = 1;
        if(mAdapter  == null){
            mAdapter = new BannerPagerAdaper();
            vp_pager.addOnPageChangeListener(this);
        }
        vp_pager.setAdapter(mAdapter);
        vp_pager.setFocusable(true);
        vp_pager.setCurrentItem(1);

        if (gravity != -1)
            circle_index.setGravity(gravity);
        if(isAutoScroll){
            startAutoPlay();
        }
    }

    /**
     * 废弃使用
     */
    private Banner setData(List<String> urls, List<String> titles, int index) {
        this.urls.clear();
        this.urls.addAll(urls);
        mCount = urls.size() + 2;
        mAdapter.notifyDataSetChanged();
        this.titles.clear();
        this.titles.addAll(titles);
        vp_pager.setCurrentItem(1);
        return this;
    }

    private void createIndicator() {
        circle_index.removeAllViews();
        indicatorImages.clear();
        circle_index_inside.removeAllViews();
        int length = urls == null ? 0 : urls.size();
        if (indexType == BannerConfig.INDEX_YPTE_TITLE_NUM
                || indexType == BannerConfig.INDEX_YPTE_TITLE_NUM) {
            return;
        }
        for (int i = 0; i < length; i++) {
            ImageView imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(indicateSize, indicateSize);
            lp.rightMargin = paddingSize;
            lp.leftMargin = paddingSize;
            if (i == 0) {
                imageView.setImageResource(R.drawable.white_radius);
            } else {
                imageView.setImageResource(R.drawable.gray_radius);
            }
            indicatorImages.add(imageView);
            if (indexType == BannerConfig.INDEX_YPTE_CIRCLE) {
                circle_index.addView(imageView, lp);
            } else if (indexType == BannerConfig.INDEX_YPTE_TITLE_CIRCLR) {
                circle_index_inside.addView(imageView, lp);
            }
        }
    }

    /**
     * ViewPager的Adapter
     */
    class BannerPagerAdaper extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView iv = new ImageView(mContext);
            String url = "";
            if (position <= 0) {
                url = urls.get(urls.size() - 1);
            } else if (position >= mCount - 1) {
                url = urls.get(0);
            } else {
                url = urls.get(position - 1);
            }
            Glide.with(mContext)
                    .load(url)
                    .placeholder(R.mipmap.no_banner)
                    .centerCrop()
                    .into(iv);
            container.addView(iv);
            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
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

    /**
     * OnPageChangeListener,
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {
        currentItem = position;
        if (position <= 0) {
            position = mCount - 3;
        } else if (position >= mCount - 1) {
            position = 0;
        } else {
            position = position - 1;
        }
        //此时的position为逻辑上urls中的位置
        StringBuilder index = new StringBuilder((position + 1) + "").append("/").append(urls.size());
        String title = (position < titles.size() && position >= 0) ? titles.get(position) : "";
        switch (indexType) {
            case BannerConfig.INDEX_YPTE_CIRCLE:
                int length = urls == null ? 0 : urls.size();
                if(length != indicatorImages.size()){
                    return;
                }
                for (int i = 0; i < length; i++) {
                     if(i == position){
                         indicatorImages.get(i).setImageResource(R.drawable.white_radius);
                     }else{
                         indicatorImages.get(i).setImageResource(R.drawable.gray_radius);
                     }
                }
                break;
            case BannerConfig.INDEX_YPTE_NUM:
                num_index.setText(index.toString());
                break;
            case BannerConfig.INDEX_YPTE_TITLE_CIRCLR:
                int num = urls == null ? 0 : urls.size();
                if(num != indicatorImages.size()){
                    return;
                }
                for (int i = 0; i < num; i++) {
                    if(i == position){
                        indicatorImages.get(i).setImageResource(R.drawable.white_radius);
                    }else{
                        indicatorImages.get(i).setImageResource(R.drawable.gray_radius);
                    }
                }
                tv_title.setText(title);
                break;
            case BannerConfig.INDEX_YPTE_TITLE_NUM:
                num_index_inside.setText(index.toString());
                tv_title.setText(title);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        switch (state) {
            case 0://no operation
                if (currentItem <= 0) {
                    vp_pager.setCurrentItem(mCount - 3, false);
                } else if (currentItem >= mCount - 1) {
                    vp_pager.setCurrentItem(1, false);
                }
                break;
            case 1://start sliding
                if (currentItem <= 0) {
                    vp_pager.setCurrentItem(mCount - 3, false);
                } else if (currentItem >= mCount - 1) {
                    vp_pager.setCurrentItem(1, false);
                }
                break;
            case 2://end sliding
                break;
        }
    }

    public void startAutoPlay() {
        mHandler.removeCallbacks(task);
        mHandler.postDelayed(task, delay);
    }

    private void stopAutoPlay() {
        mHandler.removeCallbacks(task);
    }

    Runnable task = new Runnable() {
        @Override
        public void run() {
            int length = urls == null ? 0 : urls.size();
            if (length > 1 && isAutoScroll) {
                currentItem = currentItem % (length + 1) + 1;
                if (currentItem == 1) {
                    vp_pager.setCurrentItem(currentItem, false);
                    mHandler.post(task);
                } else {
                    vp_pager.setCurrentItem(currentItem, true);
                    mHandler.postDelayed(task, delay);
                }
            }
        }
    };

    /**
     * 解决手指滑动与自动播放的冲突
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        Log.i(tag, ev.getAction() + "--" + isAutoPlay);
        if (isAutoScroll) {
            int action = ev.getAction();
            if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL
                    || action == MotionEvent.ACTION_OUTSIDE) {
                startAutoPlay();
            } else if (action == MotionEvent.ACTION_DOWN) {
                stopAutoPlay();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * Banner一些常量
     */
    public static interface BannerConfig {
        int INDEX_YPTE_CIRCLE = 0;
        int INDEX_YPTE_NUM = 1;
        int INDEX_YPTE_TITLE_CIRCLR = 2;
        int INDEX_YPTE_TITLE_NUM = 3;

        int LEFT = 1001;
        int RIGHT = 1002;
        int CENTER = 1003;
    }

}
