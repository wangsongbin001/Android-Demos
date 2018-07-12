package com.example.songbinwang.littledemo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.songbinwang.littledemo.view.CycleViewPager;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by SongbinWang on 2017/7/27.
 */

public class WelcomeActivity extends Activity{

    private static final String TAG = "tag_wel";
    private CycleViewPager id_viewpager;
    private ImageView[] indicators;
    private int[] mImagesSrc = {
            R.drawable.img1,
            R.drawable.img2,
            R.drawable.img3,
            R.drawable.img4,
            R.drawable.img5
    };

    private boolean isUserTouched = false;
    private int currentPagePosition;

    private static final int DEFAULT_CONUNT = 5;
    private static final int MAX_CONUNT = 50;
    Timer mTimer;
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            if(!isUserTouched){
                handler.sendEmptyMessage(0);
            }
        }
    };
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            currentPagePosition = (currentPagePosition + 1)%MAX_CONUNT;
            if(currentPagePosition == MAX_CONUNT - 1){
                id_viewpager.setCurrentItem(DEFAULT_CONUNT - 1, false);
            }else{
                id_viewpager.setCurrentItem(currentPagePosition);
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        initViews();
        mTimer = new Timer();
        mTimer.schedule(task, 5000, 5000);
    }

    private void initViews(){
        indicators = new ImageView[]{
                (ImageView)findViewById(R.id.indicator1),
                (ImageView)findViewById(R.id.indicator2),
                (ImageView)findViewById(R.id.indicator3),
                (ImageView)findViewById(R.id.indicator4),
                (ImageView)findViewById(R.id.indicator5)
        };
        id_viewpager = (CycleViewPager) findViewById(R.id.id_cvp);
        id_viewpager.setAdapter(new CycleViewPagerAdapter(this));
        id_viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPagePosition = position;
                updateIndicators(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        id_viewpager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN
                        || event.getAction() == MotionEvent.ACTION_MOVE){
                    isUserTouched = true;
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    isUserTouched = false;
                }
                return false;
            }
        });
    }

    private void updateIndicators(int position){
        position = position % DEFAULT_CONUNT;
        for(int i=0;i<indicators.length;i++){
            if(i == position){
                indicators[i].setImageResource(R.drawable.indicator_checked);
            }else{
                indicators[i].setImageResource(R.drawable.indicator_unchecked);
            }

        }
    }

    class CycleViewPagerAdapter extends PagerAdapter{
        LayoutInflater mInflater;
        CycleViewPagerAdapter(Context context){
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return MAX_CONUNT;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View root = mInflater.inflate(R.layout.item_vp, container, false);
            ImageView imageView = (ImageView) root.findViewById(R.id.image);
            final int pos = position % DEFAULT_CONUNT;
            imageView.setImageResource(mImagesSrc[pos]);
            root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(WelcomeActivity.this, "click banner item :" + pos, Toast.LENGTH_SHORT).show();
                }
            });
            container.addView(root);
            Log.i(TAG, "postion:" + position);
            return root;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public void finishUpdate(View container) {
//            super.finishUpdate(container);
            int position = id_viewpager.getCurrentItem();
            if(position == 0){
                position = DEFAULT_CONUNT;
                id_viewpager.setCurrentItem(position, false);
            }else if(position == MAX_CONUNT -1){
                position = DEFAULT_CONUNT - 1;
                id_viewpager.setCurrentItem(position, false);
            }
            Log.d(TAG, "finish update after, position=" + position);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mTimer != null){
            mTimer.cancel();
        }
    }
}
