package com.wang.mtoolsdemo.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dell on 2017/11/28.
 * 校验工具
 */

public class VerifyUtil {

    /**
     * 校验是否为一个正确的下载地址
     * @return
     */
    public static boolean isUrl(String pInput){
        if (pInput == null) {
            return false;
        }
        String regEx = "^((https|http|ftp|rtsp|mms)?:\\/\\/)[^\\s]+";
        Pattern p = Pattern.compile(regEx);
        Matcher matcher = p.matcher(pInput);
        return matcher.matches();
    }
}
