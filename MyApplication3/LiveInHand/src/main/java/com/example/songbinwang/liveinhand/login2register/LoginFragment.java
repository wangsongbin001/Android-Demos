package com.example.songbinwang.liveinhand.login2register;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.songbinwang.liveinhand.R;
import com.example.songbinwang.liveinhand.login2register.entity.User;
import com.example.songbinwang.liveinhand.uitls.StringUtils;
import com.example.songbinwang.liveinhand.uitls.ToastUtils;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

/**
 * Created by songbinwang on 2016/6/16.
 */
public class LoginFragment extends BaseFragment implements View.OnClickListener{

    private Button btn_login, btn_register;
    private EditText et_name, et_password;
    private TextView tv_miss_password;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(null != getArguments()){

        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.lr_fg_login;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        btn_register = (Button) view.findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);
        btn_login = (Button) view.findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        et_name = (EditText) view.findViewById(R.id.et_name);
        et_password = (EditText) view.findViewById(R.id.et_password);
        tv_miss_password = (TextView) view.findViewById(R.id.tv_miss_password);
        tv_miss_password.setOnClickListener(this);
    }

    public static BaseFragment newInstance(Bundle bundle){
        LoginFragment loginFragment = new LoginFragment();
        loginFragment.setArguments(bundle);
        return loginFragment;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_register:
                mActivity.addFragment(RegisterFragment.newInstance(null));
                break;
            case R.id.btn_login:
                String nameOrPhone = et_name.getText().toString();
                String password = et_password.getText().toString();
                if(StringUtils.isNullOrEmpty(nameOrPhone)){
                    ToastUtils.showLong(mActivity, "用户名不能为空");
                }
                if(StringUtils.isNullOrEmpty(password)){
                    ToastUtils.showLong(mActivity, "密码不能为空");
                }
                dealLogin(nameOrPhone, password);
                break;
            case R.id.tv_miss_password:
                mActivity.addFragment(ResetPasswordFragment.newInstance(null));
                break;
        }
    }

    private void dealLogin(String nameOrPhone, String password){

        BmobUser.loginByAccount(mActivity, nameOrPhone, password, new LogInListener<User>() {

            @Override
            public void done(User user, BmobException e) {
                // TODO Auto-generated method stub
                if (user != null) {
                    ToastUtils.showLong(mActivity, "用户登陆成功！");
                    mActivity.finish();
                }else{
                    ToastUtils.showLong(mActivity, "用户登陆失败！");
                }
            }
        });
    }
}
