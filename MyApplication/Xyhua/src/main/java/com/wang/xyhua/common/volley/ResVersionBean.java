package com.wang.xyhua.common.volley;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Setter
@Getter
@ToString
@Accessors(chain = true)
public class ResVersionBean implements Serializable {
    @Expose
    private PackageBean android;
    @Expose
    private PackageBean ios;

    @Setter
    @Getter
    @ToString
    @Accessors(chain = true)
    public class PackageBean implements Serializable
    {
        @Expose
        private String downloadUrl;
        @Expose
        private String versionName;
        @Expose
        private int versionCode;
        @Expose
        private int enumCode;
        @Expose
        private String description;
        @Expose
        private float appSize;
        @Expose
        private int isOpen;
    }

}
