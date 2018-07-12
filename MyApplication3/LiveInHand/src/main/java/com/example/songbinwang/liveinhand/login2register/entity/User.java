package com.example.songbinwang.liveinhand.login2register.entity;

import cn.bmob.v3.BmobUser;

/**
 * Created by songbinwang on 2016/6/17.
 */
public class User extends BmobUser{

    public User(){

    }

    public User(String userName, String password, String phone){
        setUsername(userName);
        setPassword(password);
        setMobilePhoneNumber(phone);
    }

    private Integer age;

    private String address;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
