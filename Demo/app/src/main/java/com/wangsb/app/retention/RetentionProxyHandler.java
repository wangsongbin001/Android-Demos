package com.wangsb.app.retention;

import android.app.Activity;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Created by xiaosongshu on 2019/3/24.
 */

public class RetentionProxyHandler implements InvocationHandler{

    Activity activity;
    private HashMap<String, Method> mMethodMap = new HashMap();

    public RetentionProxyHandler(Activity activity){
        this.activity = activity;
    }

    public void mapMethod(String methodName, Method method){
        mMethodMap.put(methodName, method);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Method mapMethod = mMethodMap.get(method.getName());
        if(mapMethod != null){
            return mapMethod.invoke(activity, args);
        }
        return null;
    }
}
