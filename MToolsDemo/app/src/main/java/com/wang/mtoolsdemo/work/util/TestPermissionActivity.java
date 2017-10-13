package com.wang.mtoolsdemo.work.util;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;

import com.wang.mtoolsdemo.R;
import com.wang.mtoolsdemo.common.util.PermissionActivity;


/**
 * Created by dell on 2017/10/13.
 */

public class TestPermissionActivity extends PermissionActivity {
    //需要的权限
    private String[] needPermissions = new String[]{
            Manifest.permission.MODIFY_AUDIO_SETTINGS,
            Manifest.permission.RECORD_AUDIO
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testpermission);

        checkNeedPermissions(needPermissions, new AllPermissionGrantedCallBack() {
            @Override
            public void allPermissionGranted() {
                //被授权的回调
                Log.i(tag, "allPermissionGranted");
            }
        }, null);
//                new PermissionsDeniedCallback() {
//            @Override
//            public void somePermissionsDenied(List<String> deniedPermissions) {
//                Log.i(tag, "somePermissionsDenied" + deniedPermissions);
//                startAppSetting();
//            }
//        });
    }
}
