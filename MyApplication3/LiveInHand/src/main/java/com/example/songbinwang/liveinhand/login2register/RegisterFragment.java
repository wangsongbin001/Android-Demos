package com.example.songbinwang.liveinhand.login2register;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.songbinwang.liveinhand.R;
import com.example.songbinwang.liveinhand.login2register.entity.User;
import com.example.songbinwang.liveinhand.uitls.LogUtils;
import com.example.songbinwang.liveinhand.uitls.StringUtils;
import com.example.songbinwang.liveinhand.uitls.ToastUtils;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.SaveListener;

//import cn.bmob.sms.BmobSMS;
//import cn.bmob.sms.exception.BmobException;
//import cn.bmob.sms.listener.RequestSMSCodeListener;

/**
 * Created by songbinwang on 2016/6/16.
 */
public class RegisterFragment extends BaseFragment implements View.OnClickListener{

    private static final String TAG = "RegisterFragment";

    private EditText et_username,et_phone, et_verification_code,et_password;
    private Button btn_get_verification_code, btn_register;
    private ImageView iv_back;
    private TextView tv_actionbar_title;

    @Override
    protected int getLayoutId() {
        return R.layout.lr_fg_register;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        et_username = (EditText) view.findViewById(R.id.et_username);
        et_phone = (EditText) view.findViewById(R.id.et_phone);
        et_verification_code = (EditText) view.findViewById(R.id.et_verification_code);
        et_password = (EditText) view.findViewById(R.id.et_password);
        btn_get_verification_code = (Button) view.findViewById(R.id.btn_get_verification_code);
        btn_register = (Button) view.findViewById(R.id.btn_register);
        btn_get_verification_code.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        iv_back = (ImageView) view.findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        tv_actionbar_title = (TextView) view.findViewById(R.id.tv_action_title);
        tv_actionbar_title.setText("注册界面");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_register:
                String username = et_username.getText().toString();
                String password = et_password.getText().toString();
                String phone = et_phone.getText().toString();
                String verificationCode = et_verification_code.getText().toString();
                if(StringUtils.isNullOrEmpty(username)){
                    ToastUtils.showLong(mActivity, "用户名不可为空");
                    return;
                }
                if(password.length() < 6){
                    ToastUtils.showLong(mActivity, "密码不可少于6位");
                    return;
                }
                if(phone.length() != 11){
                    ToastUtils.showLong(mActivity, "电话号码不正确");
                    return;
                }
                if(verificationCode.length() != 6){
                    ToastUtils.showLong(mActivity, "验证码不正确");
                    return;
                }
                User user = new User(username, password, phone);
                dealRegister(user, verificationCode);
                break;
            case R.id.btn_get_verification_code:
                phone = et_phone.getText().toString();
                if(!StringUtils.isNullOrEmpty(phone) && StringUtils.isAllNumber(phone)
                        && phone.length() == 11){
                    getVerificationCode(phone);
                }else{
                    ToastUtils.showLong(mActivity, "无法获取验证码");
                }
                break;
            case R.id.iv_back:
                mActivity.removeFragment();
                break;
        }
    }

    public static BaseFragment newInstance(Bundle bundle){
        RegisterFragment registerFragment = new RegisterFragment();
        registerFragment.setArguments(bundle);
        return registerFragment;
    }

    private void getVerificationCode(String phone){
        BmobSMS.requestSMSCode(mActivity, phone, "小松鼠", new RequestSMSCodeListener() {
            @Override
            public void done(Integer integer, BmobException e) {
                if (e == null) {//验证码发送成功
                    ToastUtils.showLong(mActivity, "验证码发送成功");
                    LogUtils.i(TAG, "" + integer);
                }
            }
        });
    }

    private void dealRegister(User user, String verificationCode){
        user.signOrLogin(mActivity, verificationCode, new SaveListener() {
            @Override
            public void onSuccess() {
                ToastUtils.showLong(mActivity, "注册成功");
                mActivity.finish();
            }

            @Override
            public void onFailure(int i, String s) {
                ToastUtils.showLong(mActivity, "注册失败");
            }
        });
    }

}
