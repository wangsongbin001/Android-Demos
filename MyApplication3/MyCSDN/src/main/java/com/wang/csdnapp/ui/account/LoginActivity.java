package com.wang.csdnapp.ui.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.wang.csdnapp.Constant;
import com.wang.csdnapp.MainActivity;
import com.wang.csdnapp.R;
import com.wang.csdnapp.bean.MyUser;
import com.wang.csdnapp.ui.main.MainMenuActivity;
import com.wang.csdnapp.util.LogUtil;
import com.wang.csdnapp.util.PreferenceUtil;
import com.wang.csdnapp.util.StringUtil;
import com.wang.csdnapp.util.ToastUtil;

import java.io.Serializable;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by SongbinWang on 2017/5/22.
 * ************************************
 * 微博登陆功能
 * 1，初始化微博sdb
 * 2，初始化SsoHandler,
 * 3,调用授权方法，mSsoHandler.authorize(new SelfWbAuthListener());
 *   此方法有客户端时调用客户端，没有调用网页
 * 4，重写onActivityResult方法
 * ************************************
 *
 */

public class LoginActivity extends LoginBaseActivity{
    private static final String TAG = "LoginActivity";
    
    private EditText et_user_login, et_psd_login;
    private TextView tv_create_account, tv_forget_psd;
    private ImageView iv_qq_login, iv_weixin_login, iv_weibo_login;
    private Button btn_login;
    private MyUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //初始化BMOB
        Bmob.initialize(this, Constant.BMOB_APP_ID);
        //初始化控件
        initViews();
        //注册监听
        registerListener();
    }

    private void initViews(){
        et_user_login = (EditText) findViewById(R.id.et_user_login);
        et_psd_login = (EditText) findViewById(R.id.et_psd_login);
        tv_create_account = (TextView) findViewById(R.id.tv_create_account);
        tv_forget_psd = (TextView) findViewById(R.id.tv_forget_psd);
        iv_qq_login = (ImageView) findViewById(R.id.iv_qq_login);
        iv_weixin_login = (ImageView) findViewById(R.id.iv_weixin_login);
        iv_weibo_login = (ImageView) findViewById(R.id.iv_weibo_login);
        btn_login = (Button) findViewById(R.id.btn_login);
    }

    private void registerListener(){
        btn_login.setOnClickListener(onClicker);
        tv_create_account.setOnClickListener(onClicker);
        tv_forget_psd.setOnClickListener(onClicker);
        iv_qq_login.setOnClickListener(onClicker);
        iv_weixin_login.setOnClickListener(onClicker);
        iv_weibo_login.setOnClickListener(onClicker);
    }

    View.OnClickListener onClicker = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.btn_login:
                    login();
                    break;
                case R.id.tv_create_account:
                    Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                    startActivity(intent);
                    break;
                case R.id.tv_forget_psd:
                    Intent intent1 = new Intent(LoginActivity.this, SecurityActivity.class);
                    startActivity(intent1);
                    break;
                case R.id.iv_qq_login:
                    loginByQQ();
                    break;
                case R.id.iv_weixin_login:
                    break;
                case R.id.iv_weibo_login:
                    loginByWeibo();
                    break;
            }
        }
    };

    /**
     * 登录
     */
    private void login(){
        String name = et_user_login.getText().toString();
        String psd = et_psd_login.getText().toString();

        if(!StringUtil.isMobile(name)){
            ToastUtil.toast("用户名错误");
            return;
        }else if(psd.length() < 6 || psd.length() > 12){
            ToastUtil.toast("密码错误");
            return;
        }

        BmobUser bmobUser = new BmobUser();
        bmobUser.setUsername(name);
        bmobUser.setPassword(psd);
        bmobUser.login(new SaveListener<MyUser>() {

            @Override
            public void done(MyUser myUser, BmobException e) {
                if(myUser != null && e == null){
                    LogUtil.i(TAG, "login success!");
//                    ToastUtil.toast("登录成功");
                    mUser = myUser;
                    loginSuccess(myUser);
                }else{
                    ToastUtil.mobErrorDeal(e.getErrorCode());
                    LogUtil.i(TAG, "signup failure:" + e.getErrorCode() + " msg:" + e.getMessage());
                }
            }
        });
    }

    @Override
    protected void jumpToMainMenuActivity() {
        super.jumpToMainMenuActivity();
        //跳转到主界面
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
        //结束自身界面
        finish();
    }
}
