package com.wang.mtoolsdemo.common.util;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;

/**
 * Created by dell on 2017/11/7.
 * 只支持GB2312字符集中的汉字
 */

public class ChineseCharToEnUtil {

    private ChineseCharToEnUtil() {
    }

    private static int[] li_SecPosValue = new int[]{
            1601, 1637, 1833, 2078, 2274, 2302, 2433, 2594, 2787, 3106, 3212,
            3472, 3635, 3722, 3730, 3858, 4027, 4086, 4390, 4558, 4684, 4925,
            5249, 5590
    };
    private final static String[] lc_FirstLetter = {
            "a", "b", "c", "d", "e", "f", "g", "h", "j", "k", "l", "m", "n", "o",
            "p", "q", "r", "s", "t", "w", "x", "y", "z"};


    public static String getAllFirstLetter(String str) {
        if (str == null || str.length() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            sb.append(getFirstLetter(str.substring(i, i + 1)));
        }
        return sb.toString();
    }

    public static String getFirstLetter(String chinese) {
        if (chinese == null && chinese.length() == 0) {
            return "";
        }
        chinese = conversionStr(chinese, "GB2312", "ISO8859-1");
        if (chinese.length() > 1) // 判断是不是汉字
        {
            int li_SectorCode = (int) chinese.charAt(0); // 汉字区码
            int li_PositionCode = (int) chinese.charAt(1); // 汉字位码
            li_SectorCode = li_SectorCode - 160;
            li_PositionCode = li_PositionCode - 160;
            int li_SecPosCode = li_SectorCode * 100 + li_PositionCode; // 汉字区位码
            if (li_SecPosCode > 1600 && li_SecPosCode < 5590) {
                for (int i = 0; i < 23; i++) {
                    if (li_SecPosCode >= li_SecPosValue[i]
                            && li_SecPosCode < li_SecPosValue[i + 1]) {
                        chinese = lc_FirstLetter[i];
                        break;
                    }
                }
            } else
            // 非汉字字符,如图形符号或ASCII码
            {
                chinese = conversionStr(chinese, "ISO8859-1", "GB2312");
                chinese = chinese.substring(0, 1);
            }
        }
        return chinese;
    }

    /**
     * 字符串编码转换
     *
     * @param str           要转换编码的字符串
     * @param charsetName   原来的编码
     * @param toCharsetName 转换后的编码
     * @return 经过编码转换后的字符串
     */
    private static String conversionStr(String str, String charsetName,
                                        String toCharsetName) {
        try {
            str = new String(str.getBytes(charsetName), toCharsetName);
        } catch (UnsupportedEncodingException ex) {
            System.out.println("字符串编码转换异常：" + ex.getMessage());
        }
        return str;
    }

    //中英文排序（中文则是以拼音首字母为标识）
    public static void sort(List<String> mData){
        if(mData == null || mData.size() == 0){
            return;
        }
        int size = mData.size();
        for(int i=0;i<size;i++){
            //前面加上“首字母&”
            mData.set(i, getFirstLetter(mData.get(i)) + "&" + mData.get(i));
        }
        Log.i("wangsongbin", ""  + mData.toString());
        Collections.sort(mData);
        for(int i=0;i<size;i++){
            String temp = mData.get(i);
            mData.set(i, temp.substring(temp.lastIndexOf("&") + 1));
        }
        Log.i("wangsongbin", ""  + mData.toString());
    }
}
