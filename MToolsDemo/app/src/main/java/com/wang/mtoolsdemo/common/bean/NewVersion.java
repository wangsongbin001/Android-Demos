package com.wang.mtoolsdemo.common.bean;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by dell on 2017/11/22.
 */

public class NewVersion implements Serializable{
    @Expose
    PlatformVersion android;
    @Expose
    PlatformVersion ios;

    public PlatformVersion getAndroid() {
        return android;
    }

    public PlatformVersion getIos() {
        return ios;
    }

    public void setAndroid(PlatformVersion android) {
        this.android = android;
    }

    public void setIos(PlatformVersion ios) {
        this.ios = ios;
    }

    @Override
    public String toString() {
        return "NewVersion{" +
                "android=" + android +
                ", ios=" + ios +
                '}';
    }
}
