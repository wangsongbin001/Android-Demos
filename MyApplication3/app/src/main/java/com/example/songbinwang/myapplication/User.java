package com.example.songbinwang.myapplication;

/**
 * Created by xiaosongshu on 2017/7/29.
 */

public class User {
    //id
    public int id = -1;
    //名字
    public String username;
    //年龄
    public int age;
    //生日
    public String birthday;
    //学校
    public String school;
    //性别
    public String gender;

    public User setId(int id) {
        this.id = id;
        return this;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public User setAge(int age) {
        this.age = age;
        return this;
    }

    public User setBirthday(String birthday) {
        this.birthday = birthday;
        return this;
    }

    public User setSchool(String school) {
        this.school = school;
        return this;
    }

    public User setGender(String gender) {
        this.gender = gender;
        return this;
    }
}
