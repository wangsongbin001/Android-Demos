package com.wang.mtoolsdemo.common.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by dell on 2017/10/18.
 */

public class NetUtil {

    /**
     * 判断网络是否连接
     *
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager ccm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (ccm != null) {
            NetworkInfo networkInfo = ccm.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()
                    && networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断WiFi是否连接
     *
     * @param context
     * @return
     */
    public static boolean isWifi(Context context) {
        ConnectivityManager ccm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (ccm != null) {
            NetworkInfo networkInfo = ccm.getActiveNetworkInfo();
            if (networkInfo != null) {
                return networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
            }
        }
        return false;
    }

    /**
     * 跳转网络设置界面
     */
    public static void openNetSetting(Activity activity, int requestCode) {
        Intent intent = null;
        //判断手机系统的版本  即API大于10 就是3.0或以上版本
        if (android.os.Build.VERSION.SDK_INT > 10) {
            intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
        } else {
            intent = new Intent();
            ComponentName component = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
            intent.setComponent(component);
            intent.setAction("android.intent.action.VIEW");
        }
        activity.startActivityForResult(intent, requestCode);
    }
}
