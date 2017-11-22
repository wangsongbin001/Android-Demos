package com.wang.mtoolsdemo.common.excep;

import java.io.IOException;

/**
 * Created by dell on 2017/11/22.
 */

public class ApiException extends IOException{
    private String code;
    private String msg;

    public ApiException(String code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public String getCode() {
        return code;
    }
}
