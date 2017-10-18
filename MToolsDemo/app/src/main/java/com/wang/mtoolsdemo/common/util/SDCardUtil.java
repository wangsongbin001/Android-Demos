package com.wang.mtoolsdemo.common.util;

import android.os.Environment;
import android.os.StatFs;

/**
 * Created by dell on 2017/10/18.
 * SDcard辅助类
 */

public class SDCardUtil {

    /**
     * sdcard是否可用
     * @return
     */
    public static boolean isSdcardEnable(){
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 获取sdcard的路径
     * @return
     */
    public static String getSdcardPath(){
        if(isSdcardEnable()){
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return null;
    }

    /**
     * 获取sdcard剩余空间的字节数
     * @return
     */
    public static long getSdcardAvailableSize(){
        if(isSdcardEnable()){
            StatFs statfs = new StatFs(getSdcardPath());
            long availableBlocks = statfs.getAvailableBlocks() - 4;
            long blockSize = statfs.getBlockSize();
            return availableBlocks * blockSize;
        }
        return 0;
    }

    /**
     * 获取系统存储路径
     * @return
     */
    public static String getRootPath(){
        return Environment.getRootDirectory().getAbsolutePath();
    }
}
