package com.wang.csdnapp.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wang.csdnapp.R;
import com.wang.csdnapp.util.LogUtil;
import com.wang.csdnapp.util.StringUtil;
import com.wang.csdnapp.util.ToastUtil;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by SongbinWang on 2017/2/28.
 */

public class VerifyFragment extends BaseFragment{

    private static final String tag = "wsb_VerifyFragment";

    private Button btn_confirm;
    private TextView tv_get_verify_code;
    private EditText et_phone, et_verify_code, et_new_psd;

    public static BaseFragment newInstance(){
        VerifyFragment fragment = new VerifyFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fg_verify;
    }

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        btn_confirm = (Button) view.findViewById(R.id.btn_confirm);
        tv_get_verify_code = (TextView) view.findViewById(R.id.tv_get_verify_code);
        et_phone = (EditText) view.findViewById(R.id.et_phone);
        et_verify_code = (EditText) view.findViewById(R.id.et_verify_code);
        et_new_psd = (EditText) view.findViewById(R.id.et_new_psd);

        btn_confirm.setOnClickListener(onClicker);
        tv_get_verify_code.setOnClickListener(onClicker);
    }

    View.OnClickListener onClicker = new  View.OnClickListener(){
        @Override
        public void onClick(View v) {
            LogUtil.i(tag, "onClick");
            switch (v.getId()){
                case R.id.btn_confirm:
                    verify();
                    break;
                case R.id.tv_get_verify_code:
                    getVrifyCode();
                    break;
            }
        }
    };
    //手机短信验证
    private void verify(){
        String code = et_verify_code.getText().toString().trim();
        if(TextUtils.isEmpty(code) || code.length() != 6){
            return;
        }
        String new_psd = et_new_psd.getText().toString().trim();
        BmobUser.resetPasswordBySMSCode(code, "" + new_psd, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e == null){
                    ToastUtil.toast("密码重置成功！");
                }else{
                    LogUtil.i(tag, "failure: " + e.getMessage());
                }
            }
        });
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
