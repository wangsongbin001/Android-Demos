package com.wang.csdnapp.bean;

import java.io.Serializable;

/**
 * Created by songbinwang on 2017/6/19.
 * 分享内容的封装
 */

public class SharedParam implements Serializable{

    public static enum TYPE{
        type_weixin,type_wechat_circle,type_qq,type_qzone,type_weibo;
    }

    //分享的类型的，例如微信，qq
    private TYPE type;
    //文章的连接
    private String airticleUrl;
    //文章的标题
    private String title;
    //文章附带的图片
    private String imgUrl;
    //分享的文字内容
    private String txt;

    public SharedParam() {
        super();
    }

    public SharedParam(TYPE type, String airticleUrl,
                       String title, String imgUrl,
                       String txt) {
        super();
        this.type = type;
        this.airticleUrl = airticleUrl;
        this.title = title;
        this.imgUrl = imgUrl;
        this.txt = txt;
    }

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public String getTxt() {
        return txt;
    }

    public void setAirticleUrl(String airticleUrl) {
        this.airticleUrl = airticleUrl;
    }

    public String getAirticleUrl() {
        return airticleUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "SharedParam{" +
                "type=" + type +
                ", airticleUrl='" + airticleUrl + '\'' +
                ", title='" + title + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", txt='" + txt + '\'' +
                '}';
    }
}
