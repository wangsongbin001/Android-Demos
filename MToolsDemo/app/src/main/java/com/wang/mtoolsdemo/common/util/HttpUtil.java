package com.wang.mtoolsdemo.common.util;

import java.util.Date;

/**
 * Created by dell on 2017/11/21.
 */

public class HttpUtil {
    private static String sessionId;//回话识别码
    private static Date serverDate;//服务器时间

    public static String getSessionId() {
        return sessionId;
    }

    public static void setSessionId(String sessionId) {
        HttpUtil.sessionId = sessionId;
    }

    public static Date getServerDate() {
        return serverDate;
    }

    public static void setServerDate(Date serverDate) {
        HttpUtil.serverDate = serverDate;
    }
}
