package com.wangsb.common.annotation;

import android.text.TextUtils;

public class MRouter {
    public static Class AnnotationWrapClazz;
    public static Object object;


    public static void init(){
        try {
            AnnotationWrapClazz = Class.forName("com.wangsb.annotation.create.AnnotationRouteWrap");
            object = AnnotationWrapClazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static IRouter build(String path){
        return new RealRouter().build(path);
    }

}
