package com.wang.csdnapp.view;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.wang.csdnapp.R;
import com.wang.csdnapp.bean.MyUser;
import com.wang.csdnapp.util.LogUtil;
import com.wang.csdnapp.util.StringUtil;
import com.wang.csdnapp.util.ToastUtil;

import java.io.Serializable;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by SongbinWang on 2017/5/31.
 * 由于验证码，验证一次之后就失效了。
 * 方案一（行不通）
 * 1,先调用验证码重置密码，resetPasswordBySMSCode
 * 2，再根据旧密码，更新新密码，updateCurrentUserPassword("旧密码", "新密码"
 *
 * 方案二（）
 * 1，先利用手机+ 短信验证码登录
 * 2，利用登录用户更新用户信息的方式，修改密码
 */

public class SecurityLayout2 extends LinearLayout{
    private static final String TAG = "SecurityLayout2";
    private static final String tempPsd = "000000";
    private Button btn_next, btn_get_verifycode;
    private EditText et_phone, et_verify_code;
    private Context mContext;
    private SecurityLayoutContainer mParentView;
    //上个界面的传参
    private Bundle bundle;
    public SecurityLayout2(Context context) {
        this(context, null);
    }

    public SecurityLayout2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SecurityLayout2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_verify_code = (EditText) findViewById(R.id.et_verify_code);
        btn_get_verifycode = (Button) findViewById(R.id.btn_get_verifycode);
        btn_next = (Button) findViewById(R.id.btn_next);

        btn_next.setOnClickListener(onClicker);
        btn_get_verifycode.setOnClickListener(onClicker);
        mParentView = (SecurityLayoutContainer) getParent();
    }

    private View.OnClickListener onClicker = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_next:
                    String verifyCode = et_verify_code.getText().toString();
                    String phone = et_phone.getText().toString();
                    //验证
//                    verify(phone, verifyCode);
//                    resetPsdByCode(verifyCode);
                    loginBySms(phone, verifyCode);
                    break;
                case R.id.btn_get_verifycode:
                    getVerifyCode();
                    break;
            }
        }
    };

    public void setParentView(SecurityLayoutContainer view, Bundle bundle){
        mParentView = view;
        this.bundle = bundle;
    }

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
                    LogUtil.i(TAG, "done,integer:" + integer);
                    if(e == null){
                        btn_get_verifycode.setText("发送成功");
                    }else{
                        btn_get_verifycode.setText("重新发送");
                        LogUtil.i(TAG, "errorCode:" + e.getErrorCode());
                    }
                }
            });
        }
    }

    /**
     * 下一步
     */
    private void next(){
        try {
            //传递验证码
            bundle.putString("code", et_verify_code.getText().toString());
            bundle.putString("psd", "" + tempPsd);
            mParentView.next(2, bundle);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 验证验证码
     * @param smsCode
     */
    private void verify(String phone, String smsCode){
        BmobSMS.verifySmsCode(phone, smsCode, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e == null){
                    //验证成功
                    next();
                }else{
                    LogUtil.i(TAG, "" + e.getErrorCode());
                    ToastUtil.mobErrorDeal(e.getErrorCode());
                    btn_get_verifycode.setText("获取验证码");
                }
            }
        });
    }

    /**
     * 验证修改密码
     * @param smsCode
     */
    private void resetPsdByCode(String smsCode){
        BmobUser.resetPasswordBySMSCode(smsCode,tempPsd, new UpdateListener() {

            @Override
            public void done(BmobException ex) {
                if(ex==null){
                    next();
                    LogUtil.i(TAG, "smile,"+"密码重置成功");
                }else{
                    LogUtil.i(TAG, "smile," + "重置失败：code ="+ex.getErrorCode()+",msg = "+ex.getLocalizedMessage());
                    ToastUtil.mobErrorDeal(ex.getErrorCode());
                }
            }
        });
    }

    /**
     * 验证登录
     */
    private void loginBySms(String phone, String smsCode){
        BmobUser.loginBySMSCode(phone, smsCode, new LogInListener<MyUser>() {

            @Override
            public void done(MyUser user, BmobException e) {
                if(user!=null){
                    LogUtil.i(TAG, "smile," + "用户登陆成功");
                    bundle.putSerializable("user", (Serializable) user);
                    next();
                }else{
                    ToastUtil.mobErrorDeal(e.getErrorCode());
                    LogUtil.i(TAG, "smile:" + "用户登陆成功");
                }
            }
        });
    }
}
