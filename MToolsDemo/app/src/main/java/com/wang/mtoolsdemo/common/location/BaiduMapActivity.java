package com.wang.mtoolsdemo.common.location;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.wang.mtoolsdemo.R;

/**
 * Created by dell on 2017/12/28.
 */

public class BaiduMapActivity extends Activity{

    private MapView mMapView;
    private BaiduMap mBaiduMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化，建议在Application中初始化
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_baidumap);

        mMapView = findViewById(R.id.mapview);
        //设置在地图上定位

        // 开启定位图层
//        mBaiduMap.setMyLocationEnabled(true);

       // 构造定位数据
//        MyLocationData locData = new MyLocationData.Builder()
//                .accuracy(location.getRadius())
//                // 此处设置开发者获取到的方向信息，顺时针0-360
//                .direction(100).latitude(location.getLatitude())
//                .longitude(location.getLongitude()).build();
//
//               // 设置定位数据
//                mBaiduMap.setMyLocationData(locData);
//
//               // 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
//               BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
//                    .fromResource(R.drawable.icon_geo);
//              MyLocationConfiguration config = new MyLocationConfiguration(mCurrentMode, true, mCurrentMarker);
//
//             mBaiduMap.setMyLocationConfiguration();

             // 当不需要定位图层时关闭定位图层
              mBaiduMap.setMyLocationEnabled(false);
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
