package com.example.songbinwang.liveinhand.uitls;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by songbinwang on 2016/6/17.
 */
public class StringUtils {

    /**
     * 全数字判断
     * @param txt
     * @return
     */
    public static boolean isAllNumber(String txt){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher matcher = pattern.matcher(txt);
        return matcher.matches();
    }

    /**
     * 非空判断
     * @param txt
     * @return
     */
    public static boolean isNullOrEmpty(String txt){
        if(txt == null || "".equals(txt)){
            return true;
        }
        return false;
    }

    /**
     * 是否可以强转成Long
     * @param txt
     * @return
     */
    public static boolean canParseLong(String txt){
        if(!isAllNumber(txt)){
            return false;
        }
        if(txt.startsWith("0")){
            return false;
        }
        try{
            Long.parseLong(txt);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 是否可以强转成Integer
     * @param txt
     * @return
     */
    public static boolean canParseInt(String txt){
        if(!isAllNumber(txt)){
            return false;
        }
        if(txt.startsWith("0")){
            return false;
        }
        try{
            Integer.parseInt(txt);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public static String getFormatDuration(long msec){
        long time = msec / 1000;
        int second = (int) (time % 60);
        int minute = (int) (time / 60 % 60);
        int hour = (int) (time / 60 / 60 % 60);
        StringBuffer temp = new StringBuffer();
        temp.append(hour);
        temp.append(":");
        if (minute < 10) {
            temp.append("0" + minute + ":");
        } else {
            temp.append(minute + ":");
        }
        if (second < 10) {
            temp.append("0" + second);
        } else {
            temp.append(second);
        }
        return temp.toString();
    }


}
