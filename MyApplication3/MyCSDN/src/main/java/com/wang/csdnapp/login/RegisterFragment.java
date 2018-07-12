package com.wang.csdnapp.login;

import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wang.csdnapp.R;
import com.wang.csdnapp.bean.MyUser;
import com.wang.csdnapp.util.LogUtil;
import com.wang.csdnapp.util.StringUtil;
import com.wang.csdnapp.util.ToastUtil;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by SongbinWang on 2017/2/28.
 */

public class RegisterFragment extends BaseFragment {

    private static final String tag = "wsb_registerfragment";

    private Button btn_register;
    private TextView tv_get_verify_code;
    private EditText et_name, et_psd, et_confirm_psd, et_phone, et_verify;

    private String verify_code;
    private MyUser mUser;

    public static BaseFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
        return fragment;
    }

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        btn_register = (Button) view.findViewById(R.id.btn_register);
        tv_get_verify_code = (TextView) view.findViewById(R.id.tv_get_verify_code);
        et_name = (EditText) view.findViewById(R.id.et_name);
        et_psd = (EditText) view.findViewById(R.id.et_psd);
        et_confirm_psd = (EditText) view.findViewById(R.id.et_confirm_psd);
        et_phone = (EditText) view.findViewById(R.id.et_phone);
        et_verify = (EditText) view.findViewById(R.id.et_verify);

        btn_register.setOnClickListener(onClicker);
        tv_get_verify_code.setOnClickListener(onClicker);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fg_register;
    }

    View.OnClickListener onClicker = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LogUtil.i(tag, "onClick");
            switch (v.getId()) {
                case R.id.btn_register:
                    //进行注册
                    register();
                    break;
                case R.id.tv_get_verify_code:
                    //获取验证码
                    getVrifyCode();
                    break;
            }
        }
    };

    //验证
    private void register() {
        boolean canRegister = checkParams();
        LogUtil.i(tag, "canRegister:" + canRegister);
        //canRegister = true;
        String code = et_verify.getText().toString().trim();
        if(canRegister){
            mUser.signOrLogin(code, new SaveListener<MyUser>() {

                @Override
                public void done(MyUser myUser, BmobException e) {
                    if(e == null){
                        ToastUtil.toast("注册成功" + myUser.toString());
                        LogUtil.i(tag, "signup success");
                    }else{
                        LogUtil.i(tag, "signup failure:" + e.getMessage());
                    }
                }
            });
        }
        //失败分析原因
    }

    private boolean checkParams() {
        //用户名6-12个字符
        String name = et_name.getText().toString();
        if (name.length() < 6 || name.length() > 12) {
            et_name.setHint(StringUtil.getRedStr("用户名为6-12个字符"));
            return false;
        }
        //用户密码为6-8个字符
        String psd = et_psd.getText().toString();
        if(psd.length() < 6 || psd.length() > 8){
            et_name.setHint(StringUtil.getRedStr("用户密码为6-8个字符"));
            return false;
        }
        //用户密码确认
        String confirm_psd = et_confirm_psd.getText().toString();
        if (!psd.equals(confirm_psd)){
            et_confirm_psd.setText(StringUtil.getRedStr("两次密码输入不一致"));
            return false;
        }
        //手机号
        String phone = et_phone.getText().toString();
        boolean b = StringUtil.isMobile(phone);
        if(!b){
            et_phone.setText(StringUtil.getRedStr("号码格式有误"));
            return false;
        }
        //验证码
        String txt_verify_code = et_verify.getText().toString();
        if(TextUtils.isEmpty(txt_verify_code) || txt_verify_code.length() != 6){
            et_verify.setText(StringUtil.getRedStr("验证码格式有误"));
            return false;
        }
        verify_code = txt_verify_code;
        mUser = new MyUser();
        //手机号作为唯一键
        mUser.setUsername(phone);
        mUser.setPassword(psd);
        mUser.setMobilePhoneNumber(phone);
        //昵称
        mUser.setNickname(name);
        return true;
    }

    //获取验证码
    private void getVrifyCode() {
        //检验号码的格式
        String phone = et_phone.getText().toString();
        if(!StringUtil.isMobile(phone)){
            et_phone.setText(StringUtil.getRedStr("号码格式有误"));
        }else{
            tv_get_verify_code.setText("等待中...");
            BmobSMS.requestSMSCode(phone, "test", new QueryListener<Integer>() {
                @Override
                public void done(Integer integer, BmobException e) {
                    if(e == null){
                        tv_get_verify_code.setText("发送成功");
                    }else{
                        tv_get_verify_code.setText("重新发送");
                    }
                }
            });

        }
    }
}
