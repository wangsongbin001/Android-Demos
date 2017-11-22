package com.wang.mtoolsdemo.common.bean;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

import retrofit2.http.GET;

/**
 * Created by dell on 2017/11/22.
 */

public class PlatformVersion implements Serializable{
//"downloadUrl": "",
//        "versionName": "1.1.0",
//        "versionCode": 110,
//        "enumCode": 1,
//        "description": "1.若干优化\n2.添加抢红包功能",
//        "appSize": 5.3,
//        "isOpen": 0
    @Expose
    String downloadUrl;
    @Expose
    String versionName;
    @Expose
    int versionCode;
    @Expose
    int enumCode;
    @Expose
    String description;
    @Expose
    float appSize;
    @Expose
    int isOpen;

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public String getVersionName() {
        return versionName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public int getEnumCode() {
        return enumCode;
    }

    public String getDescription() {
        return description;
    }

    public float getAppSize() {
        return appSize;
    }

    public int getIsOpen() {
        return isOpen;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public void setEnumCode(int enumCode) {
        this.enumCode = enumCode;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAppSize(float appSize) {
        this.appSize = appSize;
    }

    public void setIsOpen(int isOpen) {
        this.isOpen = isOpen;
    }

    @Override
    public String toString() {
        return "PlatformVersion{" +
                "downloadUrl='" + downloadUrl + '\'' +
                ", versionName='" + versionName + '\'' +
                ", versionCode=" + versionCode +
                ", enumCode=" + enumCode +
                ", description='" + description + '\'' +
                ", appSize=" + appSize +
                ", isOpen=" + isOpen +
                '}';
    }
}
