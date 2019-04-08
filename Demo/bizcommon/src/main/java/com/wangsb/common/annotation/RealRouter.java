package com.wangsb.common.annotation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RealRouter implements IRouter{

    private RouterRequest request;

    @Override
    public IRouter build(String path) {
        this.request = new RouterRequest();
        request.path = path;
        return this;
    }

    @Override
    public IRouter with(Bundle bundle) {
        this.request.bundle = bundle;
        return this;
    }

    @Override
    public void go(Activity context) {
        if(context == null){
            return;
        }
        if(MRouter.AnnotationWrapClazz == null || MRouter.object == null ){
            return;
        }
        //通过反射获得classname
        try {
            Method getAtivityName = MRouter.AnnotationWrapClazz.getMethod("getActivityName", String.class);
            String className = (String) getAtivityName.invoke(MRouter.object, request.path);
            if(!TextUtils.isEmpty(className)){
                Class target = Class.forName(className);
                Intent intent = new Intent(context, target);
                context.startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
