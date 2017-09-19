package com.wang.xyhua.work.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wang.xyhua.R;
import com.wang.xyhua.common.base.BaseActivity;
import com.wang.xyhua.common.view.StatusBarLayout;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by dell on 2017/9/19.
 */

public class LoginActivity extends BaseActivity {

    @Bind(R.id.title_statusBar)
    StatusBarLayout titleStatusBar;
    @Bind(R.id.title_left)
    ImageView titleLeft;
    @Bind(R.id.title_left_textview)
    TextView titleLeftTextview;
    @Bind(R.id.title_middle_textview)
    TextView titleMiddleTextview;
    @Bind(R.id.title_right)
    ImageView titleRight;
    @Bind(R.id.title_right_textview)
    TextView titleRightTextview;
    @Bind(R.id.title_rlContainer)
    RelativeLayout titleRlContainer;
    @Bind(R.id.title_bar)
    LinearLayout titleBar;
    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.et_verifycode)
    EditText etVerifycode;
    @Bind(R.id.btn_get_verifycode)
    Button btnGetVerifycode;
    @Bind(R.id.iv_login_wechat)
    ImageView ivLoginWechat;
    @Bind(R.id.iv_login_qq)
    ImageView ivLoginQq;
    @Bind(R.id.iv_login_sina)
    ImageView ivLoginSina;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @OnClick({R.id.iv_login_qq, R.id.iv_login_wechat, R.id.iv_login_sina})
    private void onClick(View v){
        switch(v.getId()){
            case R.id.iv_login_qq:
                break;
            case R.id.iv_login_sina:
                break;
            case R.id.iv_login_wechat:
                break;
        }
    }
}
