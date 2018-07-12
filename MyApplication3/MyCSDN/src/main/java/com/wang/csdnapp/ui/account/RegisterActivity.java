package com.wang.csdnapp.ui.account;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wang.csdnapp.R;
import com.wang.csdnapp.bean.MyUser;
import com.wang.csdnapp.util.LogUtil;
import com.wang.csdnapp.util.StringUtil;
import com.wang.csdnapp.util.ToastUtil;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by SongbinWang on 2017/5/23.
 * signOrLogin方法（此方法V3.4.3版本提供）利用手机号，一键登录和注册接口
 */

public class RegisterActivity extends Activity{

    private static final String TAG = "RegisterActivity";
    private ImageView iv_back;
    private TextView tv_back, tv_success_register;
    private EditText et_phone, et_verify, et_psd, et_username;
    private Button btn_get_verifycode, btn_register;
    private LinearLayout ll_input_container;
    private MyUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //初始化控件
        initViews();
        //注册监听
        registerListeners();
    }

    private void initViews(){
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_back = (TextView) findViewById(R.id.tv_back);
        et_phone = (EditText) findViewById(R.id.et_phone_register);
        et_verify = (EditText) findViewById(R.id.et_verify_register);
        et_psd = (EditText) findViewById(R.id.et_psd_register);
        et_username = (EditText) findViewById(R.id.et_user_register);
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_get_verifycode = (Button) findViewById(R.id.btn_get_verifycode);
        tv_success_register = (TextView) findViewById(R.id.tv_success_register);
        ll_input_container = (LinearLayout) findViewById(R.id.ll_input_container);
    }

    private void registerListeners(){
        iv_back.setOnClickListener(onClicker);
        tv_back.setOnClickListener(onClicker);
        btn_get_verifycode.setOnClickListener(onClicker);
        btn_register.setOnClickListener(onClicker);
    }

    View.OnClickListener  onClicker = new  View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.iv_back:
                case R.id.tv_back:
                    back();
                    break;
                case R.id.btn_get_verifycode:
                    getVerifyCode();
                    break;
                case R.id.btn_register:
                    register();
                    break;
            }
        }
    };

    /**
     * 返回
     */
    private void back(){
         finish();
    }

    /**
     * 获取验证码
     */
    private void getVerifyCode(){
        //检验号码的格式
        String phone = et_phone.getText().toString();
        if(!StringUtil.isMobile(phone)){
            et_phone.setText(StringUtil.getRedStr("号码格式有误"));
        }else{
            btn_get_verifycode.setText("等待中...");
            BmobSMS.requestSMSCode(phone, "test", new QueryListener<Integer>() {
                @Override
                public void done(Integer integer, BmobException e) {
                    if(e == null){
                        btn_get_verifycode.setText("发送成功");
                    }else{
                        btn_get_verifycode.setText("重新发送");
                        ToastUtil.mobErrorDeal(e.getErrorCode());
                        LogUtil.i(TAG, "error:" + e.getErrorCode() + "," + e.getMessage());
                    }
                }
            });
        }
    }

    /**
     * 注册
     */
    private void register(){
        //检验用户信息是否齐全
        boolean canRegister = checkParams();
        LogUtil.i(TAG, "canRegister:" + canRegister);
        //canRegister = true;
        if(canRegister){
            String code = et_verify.getText().toString().trim();
            if(code.length() != 6){
                //验证码格式错误
                return;
            }
            mUser.signOrLogin(code, new SaveListener<MyUser>() {

                @Override
                public void done(MyUser myUser, BmobException e) {
                    if(e == null){
//                        ToastUtil.toast("注册成功" + myUser.toString());
                        LogUtil.i(TAG, "signup success");
                        showSuccess();
                    }else{
                        LogUtil.i(TAG, "signup failure:errorcode:" + e.getErrorCode()
                                + "msg:" + e.getMessage());
                        if(202 == e.getErrorCode()){
                            ToastUtil.toast("手机号已被注册");
                            return;
                        }
                        ToastUtil.mobErrorDeal(e.getErrorCode());
                    }
                    btn_get_verifycode.setClickable(true);
                }
            });
            btn_get_verifycode.setText("获取验证码");
            btn_get_verifycode.setClickable(false);
        }
    }

    /**
     * 注册成功，界面修改
     */
    private void showSuccess(){
        tv_success_register.setVisibility(View.VISIBLE);
        ll_input_container.setVisibility(View.GONE);
    }

    /**
     * 检验用户信息
     * @return
     */
    private boolean checkParams(){
        String phone = et_phone.getText().toString();
        String verifyCode = et_verify.getText().toString();
        String psd = et_psd.getText().toString();
        String name = et_username.getText().toString();
        //检查手机号格式
        if(!StringUtil.isMobile(phone)){
            return false;
        }
        //验证码格式
        if(verifyCode == null || verifyCode.length() != 6){
            return false;
        }
        //密码格式
        if(psd.length() < 6 || psd.length() > 12){
            ToastUtil.toast("密码长度为6-12位");
            return false;
        }

        mUser = new MyUser();
        //手机号作为唯一键
        mUser.setUsername(phone);
        mUser.setPassword(psd);
        mUser.setMobilePhoneNumber(phone);
        //昵称
        mUser.setNickname(name);
        return true;
    }
}
