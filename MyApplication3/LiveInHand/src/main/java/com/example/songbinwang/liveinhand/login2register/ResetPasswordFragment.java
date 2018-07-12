package com.example.songbinwang.liveinhand.login2register;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.songbinwang.liveinhand.R;

/**
 * Created by songbinwang on 2016/6/20.
 */
public class ResetPasswordFragment extends BaseFragment implements View.OnClickListener{

    private Button btn_confirm, btn_get_verification_code;
    private EditText et_phone, et_verification_code, et_newpassword, et_confirmpassword;
    private TextView tv_actionbar_title;
    private ImageView iv_back;

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        btn_confirm = (Button) view.findViewById(R.id.btn_confirm);
        btn_get_verification_code = (Button) view.findViewById(R.id.btn_get_verification_code);
        et_phone = (EditText) view.findViewById(R.id.et_phone);
        et_verification_code = (EditText) view.findViewById(R.id.et_verification_code);
        et_newpassword = (EditText) view.findViewById(R.id.et_newpassword);
        et_confirmpassword = (EditText) view.findViewById(R.id.et_confirmpassword);
        tv_actionbar_title = (TextView) view.findViewById(R.id.tv_action_title);
        tv_actionbar_title.setText("密码重置");
        iv_back = (ImageView) view.findViewById(R.id.iv_back);
        btn_confirm.setOnClickListener(this);
        btn_get_verification_code.setOnClickListener(this);
        iv_back.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.lr_fg_resetpassword;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_get_verification_code:
                break;
            case R.id.btn_confirm:
                break;
            case R.id.iv_back:
                mActivity.removeFragment();
                break;
        }
    }

    private void getVerificationCode(String phone){

    }

    public static ResetPasswordFragment newInstance(Bundle bundle){
        ResetPasswordFragment resetPasswordFragment = new ResetPasswordFragment();
        resetPasswordFragment.setArguments(bundle);
        return resetPasswordFragment;
    }
}
