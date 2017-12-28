package com.wang.mtoolsdemo.common.location;

import android.Manifest;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.wang.mtoolsdemo.R;
import com.wang.mtoolsdemo.common.util.LogUtil;
import com.wang.mtoolsdemo.common.util.PermissionActivity;
import com.wang.mtoolsdemo.common.util.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dell on 2017/12/28.
 */

public class LocationActivity extends PermissionActivity implements PermissionActivity.AllPermissionGrantedCallBack{

    //需要的权限
    private String[] needPermissions = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    MapView mMapView;
    BaiduMap mBaiduMap;

    BDAbstractLocationListener mLocationListener = new MyLocationListener();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_location);
        ButterKnife.bind(this);
        checkNeedPermissions(needPermissions, this, null);

        mMapView = (MapView) findViewById(R.id.mapview);
    }

    @OnClick({R.id.btn_logcation_bd})
    public void onViewClicked(View view) {
        switch(view.getId()){
            case R.id.btn_logcation_bd:
                LocationUtil.locationWidthBD(getApplicationContext(), mLocationListener);
                break;
        }
    }

    @Override
    public void allPermissionGranted() {
        if(Build.VERSION.SDK_INT >= 24){
            LocationUtil.restartLocationWidthBD();
        }
    }

    class MyLocationListener extends BDAbstractLocationListener{
        @Override
        public void onReceiveLocation(BDLocation location) {

            String city = location.getCity();
            String addrStr = "";
            double latitude = 0, longitude = 0;

            if (!TextUtils.isEmpty(city)) {
                addrStr = location.getAddrStr();
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                ToastUtil.showLong(getApplicationContext(), "定位成功");

                //在地图上定位
                mBaiduMap = mMapView.getMap();
                // 开启定位图层
                mBaiduMap.setMyLocationEnabled(true);
                // 构造定位数据
                MyLocationData locData = new MyLocationData.Builder()
                        .accuracy(location.getRadius())
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                        .direction(100)
                        .latitude(location.getLatitude())
                        .longitude(location.getLongitude())
                        .build();

                // 设置定位数据
                mBaiduMap.setMyLocationData(locData);

                // 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
//                BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
//                        .fromResource(R.mipmap.img_hb_close);
//                MyLocationConfiguration config = new MyLocationConfiguration(
//                        MyLocationConfiguration.LocationMode.NORMAL, //模式 NORMAL（普通态）, FOLLOWING（跟随态）, COMPASS（罗盘态）
//                        true,//是否设置方向
//                        mCurrentMarker,//定位图片
//                        0xAAFFFF88,//自定精度圈的填充
//                        0xAA00FF00);//自定义精度圈的边框
//                mBaiduMap.setMyLocationConfiguration(config);

                // 当不需要定位图层时关闭定位图层
//                mBaiduMap.setMyLocationEnabled(false);
            } else {
                ToastUtil.showLong(getApplicationContext(), "定位失败");
            }
            LogUtil.i("wangsongbin", "addrStr:" + addrStr + ",latitude:" + latitude
                    + ",longitude:" + longitude + ",city:" + city);
            LocationUtil.stopLocationWidthBD();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }
}
