package com.wang.csdnapp.ui.account;

import android.app.Activity;
import android.content.Intent;
import android.nfc.Tag;
import android.widget.Toast;

import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbAuthListener;
import com.sina.weibo.sdk.auth.WbConnectErrorMessage;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.wang.csdnapp.Constant;
import com.wang.csdnapp.bean.MyUser;
import com.wang.csdnapp.ui.main.MainMenuActivity;
import com.wang.csdnapp.util.LogUtil;
import com.wang.csdnapp.util.PreferenceUtil;
import com.wang.csdnapp.util.ToastUtil;
import com.wang.csdnapp.util.UserUtil;

import java.text.SimpleDateFormat;

/**
 * Created by xiaosongshu on 2017/6/22.
 */

public class LoginBaseActivity extends Activity{
    private static final String TAG = "LoginActivity";
    //微博登陆start
    SsoHandler ssoHandler;
    Oauth2AccessToken mAccessToken;

    protected  void loginByWeibo(){
        if(ssoHandler == null){
            ssoHandler = new SsoHandler(this);
        }
        ssoHandler.authorize(new SelfWbAuthListener());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(ssoHandler != null){
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
        if(mTencent != null){
            mTencent.onActivityResultData(requestCode,requestCode,data, mIUiListener);
        }
    }

    class SelfWbAuthListener implements WbAuthListener{
        @Override
        public void onSuccess(final Oauth2AccessToken token) {
            LogUtil.i("baselogin", "onSuccess");
            LoginBaseActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mAccessToken = token;
                    if (mAccessToken.isSessionValid()) {
                        // 显示 Token
//                        updateTokenView(false);
                        // 保存 Token 到 SharedPreferences
                        AccessTokenKeeper.writeAccessToken(LoginBaseActivity.this, mAccessToken);
//                        Toast.makeText(WBAuthActivity.this,
//                                R.string.weibosdk_demo_toast_auth_success, Toast.LENGTH_SHORT).show();
                        String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(
                                new java.util.Date(mAccessToken.getExpiresTime()));
                        String format = "Token：%1$s \n有效期：%2$s";
                        LogUtil.i("baselogin", String.format(format, mAccessToken.getToken(),date));
                        ToastUtil.toast("登陆成功！");
                    }
                }
            });

        }

        @Override
        public void cancel() {
            LogUtil.i("baselogin", "cancel");
            ToastUtil.toast("登陆取消！");
        }

        @Override
        public void onFailure(WbConnectErrorMessage wbConnectErrorMessage) {
            LogUtil.i("baselogin", "onFailure:errorcode：" + wbConnectErrorMessage.getErrorCode()
                    + ",msg:" + wbConnectErrorMessage.getErrorMessage());
            ToastUtil.toast("登陆失败！");
        }
    }
    //微博登陆end

    //微信登录start
    //微信登录end

    //qq登录start
    private Tencent mTencent;
    protected void loginByQQ(){
        if(mTencent == null){
            mTencent = Tencent.createInstance(Constant.QQ_API_ID, getApplicationContext());
        }
        if(mTencent != null && !mTencent.isSessionValid()){
            //登录
            String scope = "all";
            mTencent.login(this, scope, mIUiListener);
        }
    }

    IUiListener mIUiListener = new IUiListener() {
        @Override
        public void onComplete(Object o) {
            LogUtil.i(TAG, "onComplete object:" + o.toString());
            ToastUtil.toast("登录成功");
        }

        @Override
        public void onError(UiError uiError) {
            ToastUtil.toast("登录失败");
        }

        @Override
        public void onCancel() {
            ToastUtil.toast("登录取消");
        }
    };
    //qq登录end

    /**
     * 登录成功,统一处理界面
     */
    protected void loginSuccess(MyUser mUser){
//        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        //保存用户名和昵称
        if(mUser.getType() == 0){//bmob平台
            UserUtil.saveUser(this, mUser);
            jumpToMainMenuActivity();
        }else if(mUser.getType() == 1){//微博用户

        }else if(mUser.getType() == 2){//qq用户

        }else if(mUser.getType() == 3){//微信用户

        }

    }

    protected void jumpToMainMenuActivity(){

    }
}
