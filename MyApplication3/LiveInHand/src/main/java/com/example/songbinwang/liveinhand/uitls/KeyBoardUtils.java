package com.example.songbinwang.liveinhand.uitls;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by songbinwang on 2016/6/17.
 * 打开或关闭软件盘
 */
public class KeyBoardUtils {

    /**
     * 打开软键盘
     * @param editText
     * @param context
     */
    public static void openKeyBoard(EditText editText, Context context){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 关闭软键盘
     * @param editText
     * @param context
     */
    public static void closeKeyBoard(EditText editText, Context context){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }
}
