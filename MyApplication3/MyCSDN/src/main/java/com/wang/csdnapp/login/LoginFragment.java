package com.wang.csdnapp.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wang.csdnapp.MainActivity;
import com.wang.csdnapp.R;
import com.wang.csdnapp.bean.MyUser;
import com.wang.csdnapp.util.LogUtil;
import com.wang.csdnapp.util.ToastUtil;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by SongbinWang on 2017/2/28.
 */

public class LoginFragment extends BaseFragment{

    private static final String tag = "wsb_LoginFragment";

    private Button btn_login, btn_register;
    private TextView tv_psd_back;

    private EditText et_name, et_psd;

    public static BaseFragment newInstance(String tag){
        LoginFragment loginFragment = new LoginFragment();
        Bundle bundle = new Bundle();
        bundle.putString("tag", tag);
        loginFragment.setArguments(bundle);
        return loginFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fg_login;
    }

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        btn_login = (Button) view.findViewById(R.id.btn_login);
        btn_register = (Button) view.findViewById(R.id.btn_register);
        tv_psd_back = (TextView) view.findViewById(R.id.tv_psd_back);

        et_name = (EditText) view.findViewById(R.id.et_name);
        et_psd = (EditText) view.findViewById(R.id.et_psd);

        btn_login.setOnClickListener(onClicker);
        btn_register.setOnClickListener(onClicker);
        tv_psd_back.setOnClickListener(onClicker);
    }

    View.OnClickListener onClicker = new  View.OnClickListener(){
        @Override
        public void onClick(View v) {
            LogUtil.i(tag, "onClick");
            switch (v.getId()){
                case R.id.btn_login:
                    //登录操作
                    login();
                    break;
                case R.id.btn_register:
                    //跳转到注册界面
                    jumpToRegister();
                    break;
                case R.id.tv_psd_back:
                    //跳转到密码找回界面
                    jumpToVerify();
                    break;
                case R.id.btn_back:
                    if(mActivity != null){
                        mActivity.finish();
                    }
                    break;

            }
        }
    };

    private void login(){
        String name = et_name.getText().toString();
        String psd = et_psd.getText().toString();

        BmobUser bmobUser = new BmobUser();
        bmobUser.setUsername(name);
        bmobUser.setPassword(psd);
        bmobUser.login(new SaveListener<MyUser>() {

            @Override
            public void done(MyUser myUser, BmobException e) {
                if(myUser != null && e == null){
                    LogUtil.i(tag, "login success!");
                    ToastUtil.toast("登录成功");
                }else{
                    LogUtil.i(tag, "signup failure:" + e.getMessage());
                }
            }
        });
//        Intent intent = new Intent(mActivity, MainActivity.class);
//        mActivity.startActivity(intent);
    }
    //跳转注册界面
    private void jumpToRegister(){
        mActivity.addFragment(RegisterFragment.newInstance());
    }
    //跳转到密码找回界面
    private void jumpToVerify(){
        mActivity.addFragment(VerifyFragment.newInstance());
    }
}
