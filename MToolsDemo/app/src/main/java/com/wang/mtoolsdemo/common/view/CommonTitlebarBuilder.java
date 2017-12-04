package com.wang.mtoolsdemo.common.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wang.mtoolsdemo.R;

/**
 * Created by dell on 2017/12/4.
 */

public class CommonTitlebarBuilder {

    /**
     * title栏根布局
     */
    protected View titleView;
    protected TextView tvLeft;
    protected ImageView ivLeft;
    protected ImageView ivRight;
    protected TextView tvRight;
    protected TextView tvMiddle;
    protected Activity _activity;
//    protected StatusBarLayout llStatusBar;
    protected RelativeLayout rlLayout;

    /**
     * 标题栏布局，一般不建议修改
     */
    protected int titleBarId;

    /**
     * 构造函数,用于Activity
     * @param activity
     */
    public CommonTitlebarBuilder(Activity activity, int titleBarId) {
        this._activity = activity;
        this.titleBarId = titleBarId;
        titleView = _activity.findViewById(getTitleBarId());
        instanceObjects(titleView);
    }

    /**
     * 构造函数，用于Fragment
     * @param
     */
    public CommonTitlebarBuilder(View view, int titleBarId) {
        this.titleBarId = titleBarId;
        titleView = view.findViewById(getTitleBarId());
        instanceObjects(titleView);
    }

    protected int getTitleBarId(){
        return titleBarId;
    }

    /**
     * 实例化对象
     */
    protected void instanceObjects(View titleView){
//        llStatusBar = (StatusBarLayout) titleView.findViewById(R.id.title_statusBar);
        rlLayout = (RelativeLayout) titleView.findViewById(R.id.title_rlContainer);
        //暂时按照此方法修复部分机型上Titlebar背景在xml中使用默认时，设置无效的问题；
        setBackgroundColor(Color.WHITE);
        tvLeft = (TextView) titleView.findViewById(R.id.title_left_textview);
        tvMiddle = (TextView) titleView.findViewById(R.id.title_middle_textview);
        tvRight = (TextView) titleView.findViewById(R.id.title_right_textview);
        ivLeft = (ImageView) titleView.findViewById(R.id.title_left);
        ivRight = (ImageView) titleView.findViewById(R.id.title_right);
    }

    public View getTitleView() {
        return titleView;
    }

    public CommonTitlebarBuilder withBackIcon(){
        setLeftImage(R.mipmap.c_titlebar_back);

        if (_activity != null){
            setLeftIconListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    _activity.onBackPressed();
                }
            });
        } else {
            setLeftIconListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    if (context instanceof Activity) {
                        ((Activity) context).onBackPressed();
                    }
                }
            });
        }

        return this;
    }

//    public CommonTitlebarBuilder withHomeIcon(){
//        setRightImage(R.mipmap.icon_loan);
//        if (_activity != null){
//            setRightIconListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent();
//                    intent.putExtra("return_home", true);
//                    intent.setClass(_activity, MainFragmentActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  //注意本行的FLAG设置
//                    _activity.startActivity(intent);
//                    _activity.finish();//关掉自己
//                }
//            });
//        }
//        return this;
//    }

    /**
     * 设置文本标题
     * @param text
     * @return
     */
    public CommonTitlebarBuilder setMiddleTitleText(String text) {
        tvMiddle.setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
        tvMiddle.setText(text);
        return this;
    }

    /**
     * 设置左侧图标
     * @param resId
     * @return
     */
    public CommonTitlebarBuilder setLeftImage(int resId) {
        ivLeft.setVisibility(resId <= 0 ? View.GONE : View.VISIBLE);
        ivLeft.setBackgroundResource(resId);
        return this;
    }

    /**
     * 设置右侧图标
     * @param resId
     * @return
     */
    public CommonTitlebarBuilder setRightImage(int resId){
        ivRight.setVisibility(resId <= 0? View.GONE : View.VISIBLE);
        ivRight.setBackgroundResource(resId);
        return this;
    }

    /**
     * 设置左侧图标点击处理函数
     */
    public CommonTitlebarBuilder setLeftIconListener(View.OnClickListener listener) {
        if(ivLeft.getVisibility() == View.VISIBLE){
            ivLeft.setOnClickListener(listener);
        }

        if (tvLeft.getVisibility() == View.VISIBLE){
            tvLeft.setOnClickListener(listener);
        }
        return this;
    }

    /**
     * 设置右侧图标点击处理函数
     */
    public CommonTitlebarBuilder setRightIconListener(View.OnClickListener listener) {
        if (listener != null && ivRight.getVisibility() == View.VISIBLE) {
            ivRight.setOnClickListener(listener);
        }
        return this;
    }

    /**
     * 设置右侧文字点击处理函数
     */
    public CommonTitlebarBuilder setRightTextListener(View.OnClickListener listener) {
        if (listener != null && tvRight.getVisibility() == View.VISIBLE) {
            tvRight.setOnClickListener(listener);
        }
        return this;
    }

    /**
     * 设置右侧图标是否显示
     */
    public CommonTitlebarBuilder setRightIconVisibility(int visibility){
        ivRight.setVisibility(visibility);
        return this;
    }

    /**
     * 设置标题栏（可定制标题栏事件处理类）
     */
    public CommonTitlebarBuilder setTitlebar(int leftIcon, View.OnClickListener leftCilckListener, String titleName,
                                    int rightIcon, View.OnClickListener rightClickListener) {
        if(leftCilckListener == null){
            leftCilckListener = new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    _activity.finish();
                }
            };
        }

        return this.setLeftImage(leftIcon)
                .setLeftIconListener(leftCilckListener)
                .setMiddleTitleText(titleName)
                .setRightImage(rightIcon)
                .setRightIconListener(rightClickListener);
    }

    /**
     * 设置标题栏的外观
     * @param width
     * @param height
     * @param backgroundColor
     * @return
     */
    public CommonTitlebarBuilder setTitlebar(int width, int height, int backgroundColor){
        rlLayout.setLayoutParams(new LinearLayout.LayoutParams(width, height));
//        llStatusBar.setBackgroundResource(backgroundColor);
        rlLayout.setBackgroundResource(backgroundColor);
        return this;
    }

    /**
     * 设置标题栏
     */
    public CommonTitlebarBuilder setTitlebar(String titleName) {
        return setTitlebar(R.mipmap.c_titlebar_back, null, titleName, 0, null);
    }

    /**
     * 设置标题栏透明
     * @return
     */
    public CommonTitlebarBuilder isBackgroundTransparent(){
        return setBackgroundColor(android.R.color.transparent);
    }

    /**
     * 设置标题栏的背景
     * @param color
     * @return
     */
    public CommonTitlebarBuilder setBackgroundColor(int color){
//        llStatusBar.setBackgroundColor(color);
        rlLayout.setBackgroundColor(color);
        return this;
    }

    /**
     * 设置标题栏背景
     * @param resid
     * @return
     */
    public CommonTitlebarBuilder setBackgroundResource(int resid){
//        llStatusBar.setBackgroundResource(resid);
        rlLayout.setBackgroundResource(resid);
        return this;
    }

    /**
     * 设置左侧文字
     * @param text
     * @return
     */
    public CommonTitlebarBuilder setLeftText(String text){
        if (TextUtils.isEmpty(text))
            tvLeft.setVisibility(View.INVISIBLE);
        else {
            tvLeft.setVisibility(View.VISIBLE);
            tvLeft.setText(text);
        }
        return this;
    }
    /**
     * 设置右侧文字
     * @param text
     * @return
     */
    public CommonTitlebarBuilder setRightText(String text){
        if (TextUtils.isEmpty(text))
            tvRight.setVisibility(View.GONE);
        else {
            tvRight.setVisibility(View.VISIBLE);
            tvRight.setText(text);
        }
        return this;
    }
}
