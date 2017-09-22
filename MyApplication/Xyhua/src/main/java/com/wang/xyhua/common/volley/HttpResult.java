package com.wang.xyhua.common.volley;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by dell on 2017/9/22.
 */
@Setter @Getter @ToString
public class HttpResult<T> implements Serializable{
    @Expose
    private String status;
    @Expose
    private String message;
    @Expose
    private T data;
}
