package com.wang.mtoolsdemo.common.util;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wang.mtoolsdemo.R;

/**
 * Created by dell on 2017/10/23.
 */

public class DialogUtil {

    private DialogUtil() {
    }

    public static AlertDialog showDialog(Context context, String title, String msg,
                                         String positiveTxt, DialogInterface.OnClickListener positiveOnClicker
            , String negativeTxt, DialogInterface.OnClickListener negativeOnClicker) {
        return showDialog(context, title, msg, positiveTxt, positiveOnClicker, negativeTxt, negativeOnClicker, true);
    }

    public static AlertDialog showDialog(Context context, String title, String msg,
                                         String positiveTxt, DialogInterface.OnClickListener positiveOnClicker
            , String negativeTxt, DialogInterface.OnClickListener negativeOnClicker
            , boolean cancelOutSide) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton(positiveTxt, positiveOnClicker)
                .setNegativeButton(negativeTxt, negativeOnClicker)
                .setCancelable(true);
        AlertDialog mInstance = builder.create();
        mInstance.setCanceledOnTouchOutside(cancelOutSide);
        mInstance.show();
        return mInstance;
    }

    public static AlertDialog showCustomDialog(Context context, String title, String msg,
                                               String positiveTxt, DialogInterface.OnClickListener positiveClicker,
                                               String negativeTxt, DialogInterface.OnClickListener negativeClicker) {
        return showDialog(context, title, msg, positiveTxt, positiveClicker, negativeTxt, negativeClicker, true);
    }

    public static AlertDialog showCustomDialog(Context context, String title, String msg,
                                               String positiveTxt, final DialogInterface.OnClickListener positiveClicker,
                                               String negativeTxt, final DialogInterface.OnClickListener negativeClicker
            , boolean cancelOutside) {
        final AlertDialog mInstance = new AlertDialog.Builder(context, R.style.dialog_custom).create();
        mInstance.show();
        Window mWindow = mInstance.getWindow();
        mWindow.setContentView(R.layout.layout_customdialog);

        TextView tv_title = mWindow.findViewById(R.id.tv_title);
        FrameLayout fl_body = mWindow.findViewById(R.id.fl_body);
        TextView tv_msg = mWindow.findViewById(R.id.tv_txt);
        FrameLayout fl_bottom_btns = mWindow.findViewById(R.id.fl_bottom_btns);
        Button btn_cancel = mWindow.findViewById(R.id.btn_custom_dialog_cancle);
        Button btn_sure = mWindow.findViewById(R.id.btn_custom_dialog_sure);
        ImageView iv_cancel = mWindow.findViewById(R.id.iv_cancle);

        if (!TextUtils.isEmpty(title)) {
            tv_title.setText(title);
        } else {
            tv_title.setVisibility(View.GONE);
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) fl_body.getLayoutParams();
            lp.topMargin = 0;
            fl_body.setLayoutParams(lp);
        }

        if (!TextUtils.isEmpty(msg)) {
            tv_msg.setText(msg);
        } else {
            tv_msg.setVisibility(View.GONE);
        }

        if (TextUtils.isEmpty(negativeTxt) && !TextUtils.isEmpty(positiveTxt)) {
            btn_cancel.setVisibility(View.GONE);
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) btn_sure.getLayoutParams();
            params.width = DensityUtil.dp2px(context, 171);
            params.gravity = Gravity.CENTER_HORIZONTAL;
            btn_sure.setLayoutParams(params);
        } else if (TextUtils.isEmpty(negativeTxt) && TextUtils.isEmpty(positiveTxt)) {
            fl_bottom_btns.setVisibility(View.GONE);
        }
        btn_cancel.setText(negativeTxt);
        btn_sure.setText(positiveTxt);
        View.OnClickListener onCancel = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (negativeClicker != null) {
                    negativeClicker.onClick(mInstance, DialogInterface.BUTTON_NEGATIVE);
                } else {
                    mInstance.dismiss();
                }
            }
        };

        View.OnClickListener onSure = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (positiveClicker != null) {
                    positiveClicker.onClick(mInstance, DialogInterface.BUTTON_POSITIVE);
                } else {
                    mInstance.dismiss();
                }
            }
        };
        btn_cancel.setOnClickListener(onCancel);
        btn_sure.setOnClickListener(onSure);

        WindowManager.LayoutParams params = mWindow.getAttributes();
        params.width = context.getResources().getDisplayMetrics().widthPixels
                - DensityUtil.dp2px(context, 30 * 2);
        mWindow.setAttributes(params);
        mInstance.setCanceledOnTouchOutside(cancelOutside);
        mInstance.setCancelable(true);
        return mInstance;
    }

    public static AlertDialog showCreditDialog(Context context,
                                               String positiveTxt,
                                               final DialogInterface.OnClickListener positiveClicker) {
        final AlertDialog mInstance = new AlertDialog.Builder(context, R.style.dialog_custom).create();
        mInstance.show();
        Window mWindow = mInstance.getWindow();
        mWindow.setContentView(R.layout.layout_credit_dialog);

        TextView tv_quota = mWindow.findViewById(R.id.tv_quota);
        Button btn_use = mWindow.findViewById(R.id.btn_use);
        ImageView iv_cancel = mWindow.findViewById(R.id.iv_cancel);

        if(!TextUtils.isEmpty(positiveTxt)){
            btn_use.setText(positiveTxt);
        }else {
            btn_use.setText("立即使用");
        }
        View.OnClickListener onSure = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (positiveClicker != null) {
                    positiveClicker.onClick(mInstance, DialogInterface.BUTTON_POSITIVE);
                } else {
                    mInstance.dismiss();
                }
            }
        };
        btn_use.setOnClickListener(onSure);
        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInstance.dismiss();
            }
        });
//        WindowManager.LayoutParams params = mWindow.getAttributes();
//        mWindow.setAttributes(params);
        mInstance.setCanceledOnTouchOutside(true);
        mInstance.setCancelable(true);
        return mInstance;
    }

    public static android.support.v7.app.AlertDialog showCouponUsedDialog(Context context,
                                                                          String finalQouta) {
        final android.support.v7.app.AlertDialog mInstance
                = new android.support.v7.app.AlertDialog.Builder(context, R.style.dialog_custom).create();
        mInstance.show();
        Window mWindow = mInstance.getWindow();
        mWindow.setContentView(R.layout.layout_coupon_used_dialog);
        TextView tv_title = (TextView) mWindow.findViewById(R.id.tv_title);
        TextView tv_add_quota = (TextView) mWindow.findViewById(R.id.tv_add_quota);
        TextView tv_final_quota = (TextView) mWindow.findViewById(R.id.tv_final_quota);
        ImageView iv_cancel = mWindow.findViewById(R.id.iv_cancel);
        //代初始化
        tv_add_quota.setText(StringUtil.getString(context, "+" + 100));
        tv_final_quota.setText("当前可用额度" + finalQouta);
        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInstance.dismiss();
            }
        });

        WindowManager.LayoutParams params = mWindow.getAttributes();
        params.width = context.getResources().getDisplayMetrics().widthPixels
                - DensityUtil.dp2px(context, 30 * 2);
        mWindow.setAttributes(params);
        mInstance.setCanceledOnTouchOutside(true);
        mInstance.setCancelable(true);
        return mInstance;
    }
}
