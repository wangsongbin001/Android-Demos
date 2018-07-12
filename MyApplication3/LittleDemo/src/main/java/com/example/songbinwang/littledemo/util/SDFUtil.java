package com.example.songbinwang.littledemo.util;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by songbinwang on 2017/2/15.
 */

public class SDFUtil {

    public static String format(String format, long time){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(time));
    }

    public static String formatCurrent(String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(System.currentTimeMillis()));
    }

}
