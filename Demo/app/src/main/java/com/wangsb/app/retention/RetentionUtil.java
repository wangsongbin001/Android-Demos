package com.wangsb.app.retention;

import android.app.Activity;
import android.util.EventLog;
import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by xiaosongshu on 2019/3/24.
 */

public class RetentionUtil {

    /**
     * 关联控件
     *
     * @param activity
     */
    public static void injectView(Activity activity) {
        //解析注解，关联控件
        if (activity == null) {
            return;
        }
        Class<? extends Activity> clazzActivity = activity.getClass();
        Field[] fields = clazzActivity.getDeclaredFields();
        for (Field field : fields) {
            //找到InjectView注解的变量
            if (field.isAnnotationPresent(InjectView.class)) {
                InjectView injectView = field.getAnnotation(InjectView.class);
                //找到Button注解上的id
                try {
                    int id = injectView.value();
                    Method findViewById = clazzActivity.getMethod("findViewById", int.class);
                    findViewById.setAccessible(true);
                    Object obj = findViewById.invoke(activity, id);
                    field.setAccessible(true);
                    field.set(activity, obj);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 绑定监听事件
     *
     * @param activity 1,获取Activity 所有的Declare方法
     *                 2,便利方法找到被OnClick注解的方法
     */
    public static void injectEvent(Activity activity) {
        if (activity == null) {
            return;
        }
        Class<? extends Activity> clazzActivity = activity.getClass();
        Method[] methods = clazzActivity.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(OnClick.class)) {
                OnClick onclick = method.getAnnotation(OnClick.class);
                //获取id集合
                int[] ids = onclick.value();
                //获取EventType
                EventType eventType = onclick.annotationType().getAnnotation(EventType.class);
                Class clazzListenerType = eventType.listenerType();
                String strListenerSetter = eventType.listenerSetter();
                String methodName = eventType.methodName();
                //通过动态代理，获取代理实例，并且讲OnClickListener中的onClick方法映射到，Activity中
                //使用OnClick注解的方法。
                RetentionProxyHandler proxyHandler = new RetentionProxyHandler(activity);
                Object onClickListener = Proxy.newProxyInstance(activity.getClassLoader(), new Class[]{clazzListenerType}, proxyHandler);
                proxyHandler.mapMethod(methodName, method);
//                OnClickProxyHandler
                try {
                    //遍历每个view，设置setOnClickListener
                    for (int id : ids) {
                        Method findViewById = clazzActivity.getMethod("findViewById", int.class);
                        View view = (View) findViewById.invoke(activity, id);
                        //设置setOnClickListener;
                        Method listenerSetterMethod = View.class.getMethod(strListenerSetter, clazzListenerType);
                        listenerSetterMethod.setAccessible(true);
                        listenerSetterMethod.invoke(view, onClickListener);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
