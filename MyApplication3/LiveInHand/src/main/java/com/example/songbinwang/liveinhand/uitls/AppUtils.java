package com.example.songbinwang.liveinhand.uitls;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by songbinwang on 2016/6/17.
 */
public class AppUtils {

    public AppUtils(){
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static String getAppName(Context context){
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
            int labelRes = info.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static String getVersionName(Context context){
        try{
            PackageManager pm = context.getPackageManager();
            PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
