package com.wang.csdnapp.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.wang.csdnapp.R;
import com.wang.csdnapp.bean.SharedParam;
import com.wang.csdnapp.ui.SharedActivity;
import com.wang.csdnapp.util.ScreenUtil;
import com.wang.csdnapp.util.ToastUtil;
import com.wang.csdnapp.wxapi.WXEntryActivity;

import java.io.Serializable;

/**
 * Created by SongbinWang on 2017/6/19.
 * 1，自定义布局
 * 2，有一个进入的动画
 * 3，监听事件
 * 4，退出模式
 */

public class ShareDialog extends Dialog{

    private LinearLayout ll_share_weixin, ll_share_wechat_circle, ll_share_qq,
            ll_share_qzone, ll_share_weibo, ll_share_copy, ll_share_close;
    private RelativeLayout rl_share_container;
    private Context mContext;
    private int screenHeight;
    private int ANIMATIONTIME = 500;
    private Handler handler;
    private SharedParam sharedParam;

    public ShareDialog(Context context) {
        super(context, R.style.dim_back_dialog);
        mContext = context;
        View mRoot = LayoutInflater.from(context).inflate(R.layout.dialog_share, null);
        setContentView(mRoot);
        initViews(mRoot);
        registerListener();
    }

    public ShareDialog(Context context, SharedParam sharedParam) {
        this(context);
        this.sharedParam = sharedParam;
    }

    @Override
    public void show() {
        //设置,触碰消失
        setCanceledOnTouchOutside(true);
        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.getAttributes().width = ScreenUtil.getScreenWidth(mContext);
        window.getAttributes().height = WindowManager.LayoutParams.WRAP_CONTENT;
        super.show();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus){
            enterAnimation();
        }
    }

    /**
     * 进入动画
     */
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
        final ObjectAnimator weChatFriendAnimator = ObjectAnimator.ofFloat(ll_share_wechat_circle, "translationY",
                screenHeight, 0);
        weChatFriendAnimator.setDuration(ANIMATIONTIME);
        weChatFriendAnimator.setInterpolator(new MyInterpolator());
        weChatFriendAnimator.addListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationStart(Animator animation)
            {
                super.onAnimationStart(animation);
                ll_share_wechat_circle.setVisibility(View.VISIBLE);
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
        final ObjectAnimator weiBoAnimator = ObjectAnimator.ofFloat(ll_share_weibo, "translationY", screenHeight, 0);
        weiBoAnimator.setDuration(ANIMATIONTIME);
        weiBoAnimator.setInterpolator(new MyInterpolator());
        weiBoAnimator.addListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationStart(Animator animation)
            {
                super.onAnimationStart(animation);
                ll_share_weibo.setVisibility(View.VISIBLE);
            }
        });
        final ObjectAnimator qZoneAnimator = ObjectAnimator.ofFloat(ll_share_qzone, "translationY", screenHeight, 0);
        qZoneAnimator.setDuration(ANIMATIONTIME);
        qZoneAnimator.setInterpolator(new MyInterpolator());
        qZoneAnimator.addListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationStart(Animator animation)
            {
                super.onAnimationStart(animation);
                ll_share_qzone.setVisibility(View.VISIBLE);
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
        final ObjectAnimator closeAnimation = ObjectAnimator.ofFloat(ll_share_close, "translationY", screenHeight, 0);
        closeAnimation.setDuration(ANIMATIONTIME);
        closeAnimation.setInterpolator(new MyInterpolator());
        closeAnimation.addListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationStart(Animator animation)
            {
                super.onAnimationStart(animation);
                ll_share_close.setVisibility(View.VISIBLE);
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
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                closeAnimation.start();
            }
        }, 100);
    }

    /**
     * 祖册监听
     */
    private void registerListener(){
        ll_share_weixin.setOnClickListener(onClicker);
        ll_share_wechat_circle.setOnClickListener(onClicker);
        ll_share_qq.setOnClickListener(onClicker);
        ll_share_qzone.setOnClickListener(onClicker);
        ll_share_weibo.setOnClickListener(onClicker);
        ll_share_copy.setOnClickListener(onClicker);
        ll_share_close.setOnClickListener(onClicker);

        rl_share_container.setOnClickListener(onClicker);
    }

    /**
     * 初始化组件
     * @param mRoot
     */
    private void initViews(View mRoot){
        screenHeight = ScreenUtil.getScreenHeight(mContext);
        handler = new Handler();

        ll_share_weixin = (LinearLayout) mRoot.findViewById(R.id.ll_share_weixin);
        ll_share_wechat_circle = (LinearLayout) mRoot.findViewById(R.id.ll_share_wechat_circle);
        ll_share_qq = (LinearLayout) mRoot.findViewById(R.id.ll_share_qq);
        ll_share_qzone = (LinearLayout) mRoot.findViewById(R.id.ll_share_qzone);
        ll_share_weibo = (LinearLayout) mRoot.findViewById(R.id.ll_share_weibo);
        ll_share_copy = (LinearLayout) mRoot.findViewById(R.id.ll_share_copy);
        ll_share_close = (LinearLayout) mRoot.findViewById(R.id.ll_share_close);

        rl_share_container = (RelativeLayout) mRoot.findViewById(R.id.rl_share_container);
    }

    View.OnClickListener onClicker = new  View.OnClickListener(){
        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.ll_share_weixin:
                    sharedParam.setType(SharedParam.TYPE.type_weixin);
                    break;
                case R.id.ll_share_wechat_circle:
                    sharedParam.setType(SharedParam.TYPE.type_wechat_circle);
                    break;
                case R.id.ll_share_qq:
                    sharedParam.setType(SharedParam.TYPE.type_qq);
                    break;
                case R.id.ll_share_qzone:
                    sharedParam.setType(SharedParam.TYPE.type_qzone);
                    break;
                case R.id.ll_share_weibo:
                    sharedParam.setType(SharedParam.TYPE.type_weibo);
                    break;
                case R.id.ll_share_copy:
                    copy();
                    return;
                case R.id.ll_share_close:
                case R.id.rl_share_container:
                    dismiss();
                    return;
            }
            Intent intent = new Intent(mContext, WXEntryActivity.class);
            intent.putExtra("param", (Serializable) sharedParam);
            mContext.startActivity(intent);
            dismiss();
        }
    };

    /**
     * 复制链接
     */
    private void copy(){
        ClipboardManager cm = (ClipboardManager)
                mContext.getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setText(sharedParam.getAirticleUrl());
        ToastUtil.toast("复制成功");
        dismiss();
    }

    @Override
    public void dismiss() {
        ll_share_weixin.setVisibility(View.INVISIBLE);
        ll_share_wechat_circle.setVisibility(View.INVISIBLE);
        ll_share_qq.setVisibility(View.INVISIBLE);
        ll_share_qzone.setVisibility(View.INVISIBLE);
        ll_share_weibo.setVisibility(View.INVISIBLE);
        ll_share_copy.setVisibility(View.INVISIBLE);
        ll_share_close.setVisibility(View.INVISIBLE);
        super.dismiss();
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
