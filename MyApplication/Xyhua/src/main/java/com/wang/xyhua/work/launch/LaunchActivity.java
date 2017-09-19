package com.wang.xyhua.work.launch;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wang.xyhua.R;
import com.wang.xyhua.common.utils.LogUtil;
import com.wang.xyhua.work.login.LoginActivity;

import butterknife.Bind;

/**
 * Created by dell on 2017/9/19.
 */

public class LaunchActivity extends BaseLaunchActivity {

    @Bind(R.id.img_ad)
    SimpleDraweeView imgAd;
    @Bind(R.id.txt_time)
    TextView textTime;
    MCountDownTimer mCountDownTimer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isTaskRoot()) {
            finish();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_launch;
    }

    @Override
    protected void onResume() {
        super.onResume();
        initDatas();
    }

    private void initDatas() {
        textTime.setVisibility(View.VISIBLE);
        mCountDownTimer = new MCountDownTimer(textTime, 4000);
        mCountDownTimer.start();
    }

    /**
     * 计时器
     */
    class MCountDownTimer extends CountDownTimer {
        private TextView textView;

        MCountDownTimer(TextView tv, long millisInFutue) {
            super(millisInFutue, 1000);
            this.textView = tv;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            int mills = (int) (millisUntilFinished / 1000);
            LogUtil.i("" + millisUntilFinished + " " + String.format("跳过%d秒", mills));
            textView.setText("" + String.format("广告剩余%d秒", mills));
        }

        @Override
        public void onFinish() {
            LogUtil.i(" 广告结束 ");
            finishLaunch();
        }

    }

    public void finishLaunch() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        //销毁自身
        finish();
    }
}
