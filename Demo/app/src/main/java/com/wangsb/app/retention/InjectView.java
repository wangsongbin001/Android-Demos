package com.wangsb.app.retention;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by xiaosongshu on 2019/3/24.
 * 变量的注解
 */
@Target(ElementType.FIELD) //针对的对象成员变量
@Retention(RetentionPolicy.RUNTIME)  //针对的时期运行时期
public @interface InjectView {
    int value();//用来指定id，也就是findViewById的参数
}
