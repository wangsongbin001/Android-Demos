package com.wang.mtoolsdemo.common.bean;

import com.google.gson.annotations.Expose;

/**
 * Created by dell on 2017/11/21.
 */

public class ResultBean<T> extends ResponseBean {
    @Expose
    T data;
}
