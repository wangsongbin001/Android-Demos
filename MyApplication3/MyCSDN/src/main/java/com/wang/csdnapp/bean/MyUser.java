package com.wang.csdnapp.bean;

import cn.bmob.v3.BmobUser;

/**
 * Created by songbinwang on 2017/2/28.
 */

public class MyUser extends BmobUser{
    /**
     * type
     * 0, bmob用户
     * 1，微博用户
     * 2，qq用户
     * 3，微信用户
     */
    //用户类型
    private int type;
    //添加昵称，使用手机号，作为username
    private String nickname;

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "MyUser{" +
                "type=" + type +
                ", nickname='" + nickname + '\'' +
                ", eamil='" + getEmail() + '\'' +
                ", username'" + getUsername() + '\'' +
                '}';
    }
}
