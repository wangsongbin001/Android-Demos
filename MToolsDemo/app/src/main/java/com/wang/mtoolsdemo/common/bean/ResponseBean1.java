package com.wang.mtoolsdemo.common.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by dell on 2017/11/21.
 */

public class ResponseBean1 implements Serializable{

    String status;
    String message;

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "ResponseBean1{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
