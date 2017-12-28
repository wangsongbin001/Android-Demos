package com.wang.mtoolsdemo.common.location;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.wang.mtoolsdemo.common.util.LogUtil;
import com.wang.mtoolsdemo.common.util.ToastUtil;

/**
 * Created by dell on 2017/12/28.
 * 定位功能开发，定位分为三种，gps定位，地图定位，和基站定位。
 * 1，GPS定位，优点：精准，缺点：耗电，耗内存，耗时。GPS走的是卫星通讯通道，没网也能用。
 * 2，地图定位。
 */

public class LocationUtil {

    public static void locationWidthGPS(Context context) {
        if (context == null) {
            return;
        }
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        //检查权限
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            ToastUtil.showLong(context, "GPS已开启");
            //开始定位
            String msg = "";

            Criteria criteria = new Criteria();
            // 获得最好的定位效果
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            criteria.setAltitudeRequired(false);
            criteria.setBearingRequired(false);
            criteria.setCostAllowed(false);
            // 使用省电模式
            criteria.setPowerRequirement(Criteria.POWER_LOW);

            // 获得当前的位置提供者
            String provider = locationManager.getBestProvider(criteria, true);
            // 获得当前的位置
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                ToastUtil.showLong(context, "没有权限");
                return;
            }
            Location location = locationManager.getLastKnownLocation(provider);
            if(location == null){
                LogUtil.i("wangsongbin", "location is null");
                return;
            }
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            LogUtil.i("wangsongbin", "latitude:" + latitude + ",longitude:" + longitude);
        }else{
            ToastUtil.showLong(context, "GPS未开启，请开启");
        }
    }


    private static LocationClient mLocationClient;
    public static LocationClient locationWidthBD(Context context, BDAbstractLocationListener mLocationListener){
        if(mLocationClient == null){
            mLocationClient = new LocationClient(context);
            //设置Option
            initOption();
            //设置回调
            mLocationClient.registerLocationListener(mLocationListener);
            //开始定位
            mLocationClient.start();
        }
        return mLocationClient;
    }

    private static void initOption(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        option.setScanSpan(1000);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(false);//可选，默认false,设置是否使用gps
        option.setLocationNotify(false);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(false);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    public static void stopLocationWidthBD(){
        if(mLocationClient != null){
            mLocationClient.stop();
        }
    }

    public static void restartLocationWidthBD(){
        if(mLocationClient != null){
            mLocationClient.restart();
        }
    }


}
