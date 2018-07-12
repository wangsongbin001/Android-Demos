package com.wang.csdnapp.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by songbinwang on 2017/2/25.
 */

public class Person extends BmobObject{

    String name;
    String age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
