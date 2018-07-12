package com.example.songbinwang.littledemo.util;

import java.util.Calendar;

/**
 * Created by songbinwang on 2017/2/16.
 */

public class StringUtil {

    private static String[] weeks = new String[]{
            "周日", "周一","周二"," 周三","周四","周五","周六"
    };

    public static String getAppWidgetTxt(){
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int day_of_week = calendar.get(Calendar.DAY_OF_WEEK);
        int am = calendar.get(Calendar.AM_PM);

        StringBuffer sb = new StringBuffer();
        sb.append(month+ "/" + day + " ")
                .append(weeks[day_of_week - 1] + " ")
                .append(am == 0 ? "上午 " : "下午 ")
                .append(SDFUtil.format("hh:mm", calendar.getTimeInMillis()));
        return sb.toString();
    }
}
