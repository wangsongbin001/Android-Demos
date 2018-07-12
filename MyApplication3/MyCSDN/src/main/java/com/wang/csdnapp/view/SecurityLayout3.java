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
import com.wang.csdnapp.util.ToastUtil;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by SongbinWang on 2017/5/31.
 */

public class SecurityLayout3 extends LinearLayout{

    private static final String TAG = "SecurityLayout3";
    private SecurityLayoutContainer mParentView;
    private Button btn_next;
    private EditText et_psd;
    private Context mContext;
    //上个界面的传参
    private Bundle bundle;

    public SecurityLayout3(Context context) {
        this(context, null);
    }

    public SecurityLayout3(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SecurityLayout3(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    public void setParentView(SecurityLayoutContainer view, Bundle bundle){
        mParentView = view;
        this.bundle = bundle;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        et_psd = (EditText) findViewById(R.id.et_psd);
        btn_next = (Button) findViewById(R.id.btn_next);

        btn_next.setOnClickListener(onClicker);
    }

    View.OnClickListener onClicker = new  View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_next:
                    String psd = et_psd.getText().toString();
                    if(checkPsd(psd)){
                        resetPassword(psd);
                    }else{
                        ToastUtil.toast("密码格式错误");
                    }
                    break;
            }
        }
    };

    /**
     * 验证密码的格式
     * @param psd
     * @return
     */
    private boolean checkPsd(String psd){
        if(psd != null && psd.length() >= 6 && psd.length() <= 12){
            return true;
        }
        return false;
    }
    /**
     * 重置密码
     * @param psd
     */
    private void resetPassword(String psd){
/*        String tempPsd = bundle.getString("psd");
        LogUtil.i(TAG, "tempPsd:" + tempPsd + ",psd:" + psd);
        BmobUser.updateCurrentUserPassword(tempPsd, psd, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                 if(e == null){
                     ToastUtil.toast("密码重置成功");
                 }else{
                     ToastUtil.mobErrorDeal(e.getErrorCode());
                     LogUtil.i(TAG, "error:" + e.getErrorCode());
                 }
            }
        });*/

        //用户信息更新
        BmobUser mUser = (BmobUser) bundle.getSerializable("user");
        MyUser newUser = new MyUser();
        newUser.setPassword(psd);
        newUser.update(mUser.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                 if(e == null){
//                     ToastUtil.toast("密码重置成功");
                     mParentView.next(3, null);
                 }else{
                     ToastUtil.mobErrorDeal(e.getErrorCode());
                     LogUtil.i(TAG, "error:" + e.getErrorCode());
                 }
            }
        });

        //根据验证码重置密码
/*        String smsCode = params.getString("code");
        LogUtil.i(TAG, "code:" + smsCode);
        BmobUser.resetPasswordBySMSCode(smsCode, "" + psd, new UpdateListener() {

            @Override
            public void done(BmobException ex) {
                if(ex==null){
                    LogUtil.i(TAG, "smile:"+ "密码重置成功");
                }else{
                    LogUtil.i(TAG, "smile:" + "重置失败：code ="+ex.getErrorCode()+",msg = "+ex.getLocalizedMessage());
                    ToastUtil.mobErrorDeal(ex.getErrorCode());
                }
            }
        });*/

    }
}
