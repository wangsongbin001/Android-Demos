package com.wang.csdnapp.util;

import android.content.Context;
import android.util.Base64;

import com.wang.csdnapp.bean.MyUser;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.ExecutionException;

/**
 * Created by xiaosongshu on 2017/6/27.
 */

public class UserUtil {

    private static final String TAG = "UserUtil";
    /**
     * 保存用户信息
     * @param context
     * @param myUser
     */
    public static synchronized void saveUser(Context context, MyUser myUser){
        String data = obj2str(myUser);
        LogUtil.i(TAG, "存之前：" + data);
        PreferenceUtil.write(context, "user", data);
    }

    private static String obj2str(Object object){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try{
            oos = new ObjectOutputStream(bos);
            oos.writeObject(object);
            return new String(Base64.encode(bos.toByteArray(), Base64.DEFAULT));
        }catch (Exception e){
            LogUtil.i(TAG, "obj2str:" + e.getMessage());
        }finally {
            try{ if(oos != null) oos.close(); }catch (Exception e){}
        }
        return null;
    }

    /**
     * 获取用户信息
     * @param context
     * @return
     */
    public static synchronized MyUser getUser(Context context){
        String data = PreferenceUtil.readString(context, "user");
        LogUtil.i(TAG, "取出的数据：" + data);
        Object o = str2obj(data);
        if(o != null){
            return (MyUser)o;
        }
        return null;
    }

    private static Object str2obj(String data){
        byte[] decodeByte = Base64.decode(data.getBytes(), Base64.DEFAULT);
        ByteArrayInputStream bis = new ByteArrayInputStream(decodeByte);
        ObjectInputStream ois = null;
        try{
            ois = new ObjectInputStream(bis);
            return ois.readObject();
        }catch (Exception e){
            LogUtil.i(TAG, "str2obj:" + e.getMessage());
        }finally {
            try{ if(ois != null) ois.close();}catch (Exception e){}
        }
        return null;
    }

    /**
     * 清除用户信息
     */
    public static void clearAccount(){

    }

}
