package com.wang.mtoolsdemo.common.util;

import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import java.util.UUID;

/**
 * Created by dell on 2017/10/18.
 */

public class AppUtil {

    /**
     * 获取应用的名称
     *
     * @param context
     * @return
     */
    public static String getAppName(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取版本名称
     *
     * @param context
     * @return
     */
    public static String getAppVersionName(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取版本号
     *
     * @param context
     * @return
     */
    public static int getAppVersionCode(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 获取当前进程的名称
     *
     * @param context
     * @return
     */
    public static String getCurrentProcessName(Context context) {
        int pid = android.os.Process.myPid();
        String processName = null;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : am.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                processName = appProcess.processName;
                break;
            }
        }
        return processName;
    }

    /**
     * @param context
     * @return 获取设备的唯一ID或者应用的唯一ID
     */
    private static final String FILE_NAME = "DEVICE_ID.xml";
    private static final String DEVICE_KEY = "device_id";
    private static UUID uuid = null;
    public static String getDeviceUUID(Context context) throws Exception {
        if (context == null) {
            throw new Exception("conext is null");
        }
        if (uuid == null) {
            synchronized (FILE_NAME) {
                if (uuid == null) {
                    SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
                    String id = sp.getString(DEVICE_KEY, null);
                    if (id != null) {
                        uuid = UUID.fromString(id);
                    } else {
                        //先找Android ID
                        String androidId = Settings.Secure.getString(
                                context.getContentResolver(), Settings.Secure.ANDROID_ID);
                        Log.i("wangsongbin", "androidID:" + androidId);
                        if (!TextUtils.isEmpty(androidId) && !"9774d56d682e549c".equals(androidId)) {
                            uuid = UUID.nameUUIDFromBytes(androidId.getBytes("utf-8"));
                        } else {
                            //尝试获取deviceId
                            try {
                                TelephonyManager tm = (TelephonyManager)
                                        context.getSystemService(Context.TELEPHONY_SERVICE);
                                String deviceid = tm.getDeviceId();
                                Log.i("wangsongbin", "deviceId:" + deviceid);
                                if(!TextUtils.isEmpty(deviceid) && !"000000000000000".equals(deviceid)){
                                    uuid = UUID.nameUUIDFromBytes(deviceid.getBytes("utf-8"));
                                }
                            } catch (Exception e) {
                                 Log.i("wangsongbin", "exception:" + e.getMessage());
                            }finally {
                                if(uuid == null){
                                    uuid = UUID.randomUUID();
                                }
                            }
                        }
                    }
                }
            }
        }
        return uuid.toString();
    }

    /**
     * 获取设备唯一标识
     *
     * @return
     */
    public static String getDeviceId(Context context) {
        String deviceId;
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        deviceId = tm.getDeviceId();
        Log.i("wangsongbin", "deviceId:" + tm.getDeviceId());
        /**
         * 1，非手机设备，没有通话硬件支持。就没有系统中就没有TelePhonyService
         * 2，需要通讯相关的权限，
         * 3，Android手机的碎片化，会返回垃圾数据，例如zero
         */

        String wifiMac, blueToothMac;
        WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        wifiMac = wm.getConnectionInfo().getMacAddress();
        Log.i("wangsongbin", "wifi:" + wifiMac);
//        blueToothMac = BluetoothAdapter.getDefaultAdapter().getAddress();
//        Log.i("wangsongbin", "wifi:" + wifiMac + ", bluetooth:" + blueToothMac);
        /**
         * 1,硬件限制，有的没有WiFi和蓝牙
         * 2，权限限制
         * 3，系统限制，Android6.0之后，将不能获取WiFi和蓝牙的有效地址（02:0:00:00:00:0）
         */

        String serial;//硬件设备要 Android 2.3以后
        serial = android.os.Build.SERIAL;
        Log.i("wangsongbin", "serial:" + serial);
        /**
         * 1,有可能不存在序列号的情况。
         */

        String simSerial;//sim卡
        simSerial = tm.getSimSerialNumber();
        Log.i("wangsongbin", "simserial:" + simSerial);
        /**
         * 1，设备限制，是否有sim卡，而且对于cdma设备返回为空。
         * 2，权限限制
         */

        String androidId;//设备第一次启动，64byte字节，16进制存储
//        androidId = Settings.Secure.ANDROID_ID;
        androidId = Settings.Secure.getString(
                context.getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.i("wangsongbin", "androidId:" + androidId);
        /**
         * 1，重装系统会重置
         * 2，Android碎片化，导致返回值相同9774d56d682e549c, 或者为null
         * 3，Android 4.2后，设备启动多用户功能后，每个用户的Android Id可能不同
         */

        String uuid = Installation.id(context);
        Log.i("wangsongbin", "UUID:" + uuid);
        /**
         * 每次安装应用时，生成唯一ID
         * bf4768a9-5293-45f7-b1f3-26e63fac58e6
         */
        return null;
    }

}
