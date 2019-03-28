package com.wangsb.common.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by xiaosongshu on 2019/3/24.
 */

public class MsgUtil {

    public static void showMsg(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}
