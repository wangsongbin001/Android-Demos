package com.wang.csdnapp.util;

import android.widget.Toast;

import com.wang.csdnapp.CSDNApp;

/**
 * Created by songbinwang on 2017/2/25.
 */

public class ToastUtil {

    public static void toast(String msg){
        Toast.makeText(CSDNApp.app, msg, Toast.LENGTH_LONG).show();
    }

    public static void mobErrorDeal(int errorCode){
        switch(errorCode){
            case 202:
                toast("用户名已存在");
                break;
            case 201:
                toast("缺失数据");
                break;
            case 209:
                toast("该手机号码已经存在");
                break;
            case 207:
                toast("验证码错误");
                break;
            case 206:
                toast("登录用户才能修改信息");
                break;
        }
    }
}
