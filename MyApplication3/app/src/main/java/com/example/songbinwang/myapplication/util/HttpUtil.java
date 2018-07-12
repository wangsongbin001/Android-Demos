package com.example.songbinwang.myapplication.util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by songbinwang on 2016/10/18.
 */

public class HttpUtil {

    public static final String BaseUrl = "http://vr.api.pptv.com/app/description?vid=";

    public static String getIntroByVid(String vid) {
        String strUrl = BaseUrl + vid;
        HttpURLConnection conn = null;
        InputStream is = null;
        try {
            URL url = new URL(strUrl);
            //打开服务器
            conn = (HttpURLConnection) url.openConnection();
            //连接数据库
            conn.connect();
            /**读服务器数据**/
            is = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            StringBuffer sb = new StringBuffer();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (Exception e) {

        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {
                }
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return null;
    }
}
