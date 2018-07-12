package com.example.songbinwang.liveinhand.im.entity;

/**
 * Created by songbinwang on 2016/9/6.
 */
public class ShareParam {

    public int type;

    private String url;

    private String comment;

    private int shareType;

    public ShareParam(int type, String url, String comment){
        this.type = type;
        this.url = url;
        this.comment = comment;
    }

    public int getShareType()
    {
        return shareType;
    }
}
