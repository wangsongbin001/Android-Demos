package com.wang.mtoolsdemo.common.util;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;

/**
 * Created by dell on 2017/11/14.
 */

public class StringUtil {
    private StringUtil(){}

    public static Spannable getString(Context context, String data){
        if(TextUtils.isEmpty(data)){
            return null;
        }
        Spannable txt = new SpannableString( data + " Y");
//        // 背景色
//        txt.setSpan(new BackgroundColorSpan(Color.RED), 2, 5, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        // 粗体
        txt.setSpan(new StyleSpan(Typeface.BOLD), 0, data.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        // 字体大小
        txt.setSpan(new AbsoluteSizeSpan(DensityUtil.sp2px(context, 40)), 0, data.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        return txt;
    }
}
