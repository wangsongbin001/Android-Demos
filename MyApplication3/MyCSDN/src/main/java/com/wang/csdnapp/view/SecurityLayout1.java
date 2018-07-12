package com.wang.csdnapp.view;

import android.content.Context;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.wang.csdnapp.R;
import com.wang.csdnapp.util.LogUtil;
import com.wang.csdnapp.util.ToastUtil;

import java.io.Serializable;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by SongbinWang on 2017/5/24.
 */

public class SecurityLayout1 extends LinearLayout{

    private static final String TAG = "SecurityLayout1";
    private EditText et_phone, et_verify;
    private Button btn_next;
    private LocalVerifyCode localVerifyCode;
    private Context mContext;
    SecurityLayoutContainer mParentView;

    public SecurityLayout1(Context context) {
        this(context, null);
    }

    public SecurityLayout1(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SecurityLayout1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_verify = (EditText) findViewById(R.id.et_verifycode);
        localVerifyCode = (LocalVerifyCode) findViewById(R.id.localverifycode);
        btn_next = (Button) findViewById(R.id.btn_next);
        btn_next.setOnClickListener(onClicker);
    }

    public void setParentView(SecurityLayoutContainer view){
        mParentView = view;
    }

    private View.OnClickListener onClicker = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.btn_next:
                    LogUtil.i(TAG, "mParentView:" + mParentView );
                    //检验本地验证
                    String localVerifyCode = et_verify.getText().toString();
                    if(!checkLocalVerifyCode(localVerifyCode)){
                        ToastUtil.toast("验证码错误");
                        return;
                    }
                    String username = et_phone.getText().toString();
                    checkUserExsit(username);
            }
        }
    };

    /**
     * 核对本地验证码
     * @param verifyCode
     * @return
     */
    private boolean checkLocalVerifyCode(String verifyCode){
        if(verifyCode == null){
            return false;
        }
        return localVerifyCode.check(verifyCode);
    }

    /**
     * 检验用户名是否存在
     * @param userName
     * @return
     */
    private void checkUserExsit(String userName){
        //禁用button
        btn_next.setClickable(false);

        BmobQuery<BmobUser> query = new BmobQuery<BmobUser>();
        //查询playerName叫“比目”的数据
        query.addWhereEqualTo("username", userName);
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(1);
        //执行查询方法
        query.findObjects(new FindListener<BmobUser>() {
            @Override
            public void done(List<BmobUser> object, BmobException e) {
                btn_next.setClickable(true);
                if(e==null){
//                    ToastUtil.toast("查询成功：共"+object.size()+"条数据。");
                    //用户存在，进入下一步
                    if(object.size() > 0){
                        next(object.get(0));
                    }
                }else{
                    LogUtil.i(TAG,"bmob " + "失败："+e.getMessage()+","+e.getErrorCode());
                    ToastUtil.mobErrorDeal(e.getErrorCode());
                    //重新启用Button
                    btn_next.setClickable(true);
                }
            }
        });
    }

    private void next(BmobUser user){
        try {
            Bundle bundle = new Bundle();
            bundle.putSerializable("user", (Serializable) user);
            mParentView.next(1, bundle);
        }catch (Exception e){
            e.printStackTrace();
            LogUtil.i(TAG, "e:" + e.getMessage() );
        }
    }
}
