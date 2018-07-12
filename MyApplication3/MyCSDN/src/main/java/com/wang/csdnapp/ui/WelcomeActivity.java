package com.wang.csdnapp.ui;

import android.app.Activity;
import android.content.Intent;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.wang.csdnapp.MainActivity;
import com.wang.csdnapp.R;
import com.wang.csdnapp.bean.MyUser;
import com.wang.csdnapp.ui.account.LoginActivity;
import com.wang.csdnapp.ui.main.MainMenuActivity;
import com.wang.csdnapp.util.LogUtil;
import com.wang.csdnapp.util.UserUtil;

import java.util.concurrent.CountDownLatch;

/**
 * Created by SongbinWang on 2017/5/22.
 */

public class WelcomeActivity extends Activity {

    private ImageView iv_splash;
    private MyUser mUser;
    CountDownLatch latch;
    private static final String TAG = "WelcomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        if(!isTaskRoot()){
            finish();
        }

        iv_splash = (ImageView) findViewById(R.id.iv_splash);

        //开启渐变动画
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.anim_splash);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                //动画结束
                new Thread(){
                    @Override
                    public void run() {
                        try {
                            LogUtil.i(TAG, "thread time:" + System.currentTimeMillis());
                            latch.await();
                            handler.sendEmptyMessage(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        LogUtil.i(TAG, "onCreate time:" + System.currentTimeMillis());
        iv_splash.startAnimation(anim);
        latch = new CountDownLatch(1);
        new GetUserTask().execute();
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            onAnimEnd(null);
        }
    };

    /**
     * 动画结束后，判断是否登录
     *
     * @param animation
     */
    private void onAnimEnd(Animation animation) {
        LogUtil.i(TAG, "onAnimEnd time:" + System.currentTimeMillis());
        if (isLogined()) {
            //已登录跳转主界面
            Intent intent = new Intent(WelcomeActivity.this, MainMenuActivity.class);
            startActivity(intent);
//            overridePendingTransition(R.anim.translate_right_in, R.anim.translate_left_out);
        } else {
            //未登录跳转登录界面
            Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        finish();
    }

    /**
     * 用户登录判断
     *
     * @return
     */
    private boolean isLogined() {
        if(mUser == null){
            return false;
        }else{
            return true;
        }
    }

    class GetUserTask extends AsyncTask<Void, Void, MyUser>{
        @Override
        protected MyUser doInBackground(Void... params) {
            return UserUtil.getUser(WelcomeActivity.this);
        }

        @Override
        protected void onPostExecute(MyUser myUser) {
            super.onPostExecute(myUser);
            mUser = myUser;
            LogUtil.i(TAG, "user:" + (myUser == null ? "null":myUser.toString()));
            LogUtil.i(TAG, "onPost time:" + System.currentTimeMillis());
            latch.countDown();
        }
    }
}
