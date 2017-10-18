package com.wang.mtoolsdemo.common.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by dell on 2017/10/18.
 * 简单数据存储方式
 * SharedPreferences ContextImp中有个Map<String(packagename), SharedPreferences>
 * 保证每个进程app，中的SharedPreference是单例的
 * commit是同步写入文件，apply是写入内存，启线程一部写入文件。
 */

public class SPUtil {
    private static final String FILE_NAME = "share_data";

    public static void put(Context context, String key, Object obj) {
        if (context == null || key == null || obj == null) {
            return;
        }
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if (obj instanceof Integer) {
            editor.putInt(key, (Integer) obj);
        } else if (obj instanceof Boolean) {
            editor.putBoolean(key, (Boolean) obj);
        } else if (obj instanceof Float) {
            editor.putFloat(key, (Float) obj);
        } else if(obj instanceof Double){
            double b = (Double)obj;
            editor.putFloat(key, new Float(b));
        }else if (obj instanceof Long) {
            editor.putLong(key, (Long) obj);
        } else if (obj instanceof String) {
            editor.putString(key, (String) obj);
        } else {
            editor.putString(key, obj.toString());
        }
        SharedPreferenceCompat.apply(editor);
    }

    public static Object get(Context context, String key, Object defaultObj) {
        if (context == null || key == null || defaultObj == null) {
            return null;
        }
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        if (defaultObj instanceof String) {
            return sp.getString(key, (String) defaultObj);
        } else if (defaultObj instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObj);
        } else if (defaultObj instanceof Long) {
            return sp.getLong(key, (Long) defaultObj);
        } else if (defaultObj instanceof Float) {
            return sp.getFloat(key, (Float) defaultObj);
        } else if(defaultObj instanceof Double){
            Double b = (Double) defaultObj;
            return sp.getFloat(key, new Float(b));
        } else if (defaultObj instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObj);
        }
        return null;
    }

    public static void remove(Context context, String key) {
        if (context == null || key == null) {
            return;
        }
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferenceCompat.apply(editor);
    }

    public static boolean contain(Context context, String key) {
        if (context == null) {
            return false;
        }
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    public static void clear(Context context) {
        if (context == null) {
            return;
        }
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferenceCompat.apply(editor);
    }

    /**
     * commit早于apply
     * 用这种方法实现兼容
     */
    private static class SharedPreferenceCompat {

        private static Method method = getMethod();

        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method getMethod() {
            try {
                return SharedPreferences.Editor.class.getMethod("apply");
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            return null;
        }

        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (method != null) {
                    method.invoke(editor);
                    return;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            editor.commit();
        }
    }

}
