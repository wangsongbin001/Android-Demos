package com.wang.mtoolsdemo.common.util;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by dell on 2017/10/18.
 * 键盘辅助类，弹出键盘，隐藏键盘
 */

public class KeyBoardUtil {

    /**
     * 打开软键盘
     */
    public static void openKeyBoard(Context context, EditText editText){
        InputMethodManager inputMethodManager = (InputMethodManager)
                context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(editText, InputMethodManager.RESULT_SHOWN);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 关闭软键盘
     */
    public static void hideKeyBoard(Context context, EditText editText){
        InputMethodManager inputMethodManager = (InputMethodManager)
                context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }
}
