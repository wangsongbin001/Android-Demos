package com.wangsb.app.proxy;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by xiaosongshu on 2019/3/24.
 */

public class ProxyHandler implements InvocationHandler{
    Animal animal;

    public ProxyHandler(Animal animal){
        this.animal = animal;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Log.i(Animal.TAG, "beforce " + method.getName());
        Object object = method.invoke(animal, args);
        Log.i(Animal.TAG, "behind " + method.getName());
        return object;
    }
}
