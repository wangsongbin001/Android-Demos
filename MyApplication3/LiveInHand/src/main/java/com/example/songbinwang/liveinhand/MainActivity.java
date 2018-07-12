package com.example.songbinwang.liveinhand;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.songbinwang.liveinhand.im.IMMainActivity;
import com.example.songbinwang.liveinhand.news.NewsMainActivity;
import com.example.songbinwang.liveinhand.setting.SettingActivity;
import com.example.songbinwang.liveinhand.uitls.UnitConverterUtils;
import com.example.songbinwang.liveinhand.uitls.VideoImageLoaderHelper;
import com.example.songbinwang.liveinhand.video.VideoListActivity;
import com.example.songbinwang.liveinhand.view.StretchLayout;
import com.example.songbinwang.liveinhand.view.TopBar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView iv_back;
    private TextView tv_actionbar_title;
    private DrawerLayout mDrawerLayout;
    private Context mContext;
    private LinearLayout ll_setting;
    private StretchLayout sl_video, sl_news, sl_im, sl_payment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        initView();
        registerListner();
    }

    private void initView(){
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        tv_actionbar_title  = (TextView) findViewById(R.id.tv_action_title);
        tv_actionbar_title.setText("掌上生活");
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setImageResource(R.drawable.user_logo);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) iv_back.getLayoutParams();
        int padding = UnitConverterUtils.dp2px(mContext,10);
        iv_back.setPadding(padding,padding,padding,padding);

        ll_setting = (LinearLayout) findViewById(R.id.ll_setting);

        sl_video = (StretchLayout) findViewById(R.id.sl_video);
        sl_news = (StretchLayout) findViewById(R.id.sl_news);

        sl_im = (StretchLayout) findViewById(R.id.sl_im);

        sl_payment = (StretchLayout) findViewById(R.id.sl_payment);
    }

    private void registerListner(){
        iv_back.setOnClickListener(this);
        ll_setting.setOnClickListener(this);
        sl_video.setAnimatorListener(new StretchLayout.BaseAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                startActivity(new Intent(mContext, VideoListActivity.class));
            }
        });

        sl_im.setAnimatorListener(new StretchLayout.BaseAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                startActivity(new Intent(mContext, IMMainActivity.class));
            }
        });

        sl_news.setAnimatorListener(new StretchLayout.BaseAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                startActivity(new Intent(mContext, NewsMainActivity.class));
            }
        });

        sl_payment.setAnimatorListener(new StretchLayout.BaseAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                startActivity(new Intent(mContext, TopBarActivity.class));
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.iv_back:
                mDrawerLayout.openDrawer(Gravity.LEFT);
                break;
            case R.id.ll_setting:
                startActivity(new Intent(mContext, SettingActivity.class));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        VideoImageLoaderHelper.clearCache();
    }
}
