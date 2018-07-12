package com.example.songbinwang.liveinhand.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;

import com.example.songbinwang.liveinhand.R;
import com.example.songbinwang.liveinhand.im.entity.ShareParam;
import com.example.songbinwang.liveinhand.uitls.ScreenUtils;

/**
 * Created by songbinwang on 2016/8/1.
 */
public class ShareDialog extends Dialog implements View.OnClickListener{

    private int ANIMATIONTIME = 500;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private LinearLayout ll_share_weixin, ll_share_pengyouquan, ll_share_qq, ll_share_xinlang, ll_share_qqkongjian, ll_share_copy;

    private int screenHeight;
    private Handler handler;

    private ShareParam shareParam;

    public ShareDialog(Context context) {
        super(context, R.style.dim_back_dialog);
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        View viewContent = mLayoutInflater.inflate(R.layout.share_dialog_view, null);
        setContentView(viewContent);
        initView(viewContent);
    }

    public ShareDialog(Context context, ShareParam shareParam){
        this(context);
        this.shareParam = shareParam;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus){
            enterAnimation();
        }
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        handler = null;
    }

    private void initView(View mRoot){
        screenHeight = ScreenUtils.getScreenHeight(mContext);
        handler = new Handler();

        ll_share_weixin = (LinearLayout) mRoot.findViewById(R.id.ll_share_weixin);
        ll_share_pengyouquan = (LinearLayout) mRoot.findViewById(R.id.ll_share_pengyouquan);
        ll_share_qq = (LinearLayout) mRoot.findViewById(R.id.ll_share_qq);
        ll_share_xinlang = (LinearLayout) mRoot.findViewById(R.id.ll_share_xinlang);
        ll_share_qqkongjian = (LinearLayout) mRoot.findViewById(R.id.ll_share_qqkongjian);
        ll_share_copy = (LinearLayout) mRoot.findViewById(R.id.ll_share_copy);

        findViewById(R.id.iv_share_weixin).setOnClickListener(this);
        findViewById(R.id.iv_share_pengyouquan).setOnClickListener(this);
        findViewById(R.id.iv_share_qq).setOnClickListener(this);
        findViewById(R.id.iv_share_xinlang).setOnClickListener(this);
        findViewById(R.id.iv_share_qqkongjian).setOnClickListener(this);
        findViewById(R.id.iv_share_copy).setOnClickListener(this);
        findViewById(R.id.iv_share_close).setOnClickListener(this);

    }

    @Override
    public void show() {
        setCanceledOnTouchOutside(true);
        Window w = getWindow();
        w.setGravity(Gravity.BOTTOM);
        w.getAttributes().width  = ScreenUtils.getScreenWidth(mContext);
        w.getAttributes().height = WindowManager.LayoutParams.WRAP_CONTENT;
        super.show();
    }

    private void enterAnimation(){

        final ObjectAnimator weChatAnimator = ObjectAnimator.ofFloat(ll_share_weixin, "translationY", screenHeight, 0);
        weChatAnimator.setDuration(ANIMATIONTIME);
        weChatAnimator.setInterpolator(new MyInterpolator());
        weChatAnimator.addListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationStart(Animator animation)
            {
                super.onAnimationStart(animation);
                ll_share_weixin.setVisibility(View.VISIBLE);
            }
        });
        final ObjectAnimator weChatFriendAnimator = ObjectAnimator.ofFloat(ll_share_pengyouquan, "translationY",
                screenHeight, 0);
        weChatFriendAnimator.setDuration(ANIMATIONTIME);
        weChatFriendAnimator.setInterpolator(new MyInterpolator());
        weChatFriendAnimator.addListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationStart(Animator animation)
            {
                super.onAnimationStart(animation);
                ll_share_pengyouquan.setVisibility(View.VISIBLE);
            }
        });
        final ObjectAnimator QQAnimator = ObjectAnimator.ofFloat(ll_share_qq, "translationY", screenHeight, 0);
        QQAnimator.setDuration(ANIMATIONTIME);
        QQAnimator.setInterpolator(new MyInterpolator());
        QQAnimator.addListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationStart(Animator animation)
            {
                super.onAnimationStart(animation);
                ll_share_qq.setVisibility(View.VISIBLE);
            }
        });
        final ObjectAnimator weiBoAnimator = ObjectAnimator.ofFloat(ll_share_xinlang, "translationY", screenHeight, 0);
        weiBoAnimator.setDuration(ANIMATIONTIME);
        weiBoAnimator.setInterpolator(new MyInterpolator());
        weiBoAnimator.addListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationStart(Animator animation)
            {
                super.onAnimationStart(animation);
                ll_share_xinlang.setVisibility(View.VISIBLE);
            }
        });
        final ObjectAnimator qZoneAnimator = ObjectAnimator.ofFloat(ll_share_qqkongjian, "translationY", screenHeight, 0);
        qZoneAnimator.setDuration(ANIMATIONTIME);
        qZoneAnimator.setInterpolator(new MyInterpolator());
        qZoneAnimator.addListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationStart(Animator animation)
            {
                super.onAnimationStart(animation);
                ll_share_qqkongjian.setVisibility(View.VISIBLE);
            }
        });
        final ObjectAnimator copyAnimation = ObjectAnimator.ofFloat(ll_share_copy, "translationY", screenHeight, 0);
        copyAnimation.setDuration(ANIMATIONTIME);
        copyAnimation.setInterpolator(new MyInterpolator());
        copyAnimation.addListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationStart(Animator animation)
            {
                super.onAnimationStart(animation);
                ll_share_copy.setVisibility(View.VISIBLE);
            }
        });
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                weChatAnimator.start();
            }
        }, 100);
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                weChatFriendAnimator.start();
            }
        }, 150);
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                QQAnimator.start();
            }
        }, 180);
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                weiBoAnimator.start();
            }
        }, 100);
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                qZoneAnimator.start();
            }
        }, 150);
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                copyAnimation.start();
            }
        }, 180);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_share_weixin:

                break;
            case R.id.iv_share_pengyouquan:
                break;
            case R.id.iv_share_qq:
                break;
            case R.id.iv_share_xinlang:
                break;
            case R.id.iv_share_qqkongjian:
                break;
            case R.id.iv_share_copy:
                break;
            case R.id.iv_share_close:
                dismiss();
                break;
        }

    }

    class MyInterpolator extends OvershootInterpolator
    {
        public float getInterpolation(float t)
        {
            t -= 1.0f;
            return t * t * (1.8f * t + 0.8f) + 1.0f;
        }
    }
}
