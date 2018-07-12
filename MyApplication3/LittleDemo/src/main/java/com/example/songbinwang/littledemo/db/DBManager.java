package com.example.songbinwang.littledemo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaosongshu on 2017/7/29.
 */

public class DBManager {
    DBHelper dbHelper;
    SQLiteDatabase db;
    private static final String tag = "dbmanager";

    //实例化时，打开Database
    public DBManager(Context context){
        dbHelper = new DBHelper(context.getApplicationContext());
        db = dbHelper.getWritableDatabase();
    }

    //关闭数据库
    public void closeDB(){
        if(db != null){
            db.close();
            db = null;
        }
    }

    //添加多个用户
    public void addUserList(List<User> users, boolean useApi){
        long start = System.currentTimeMillis();
       try{
           db.beginTransaction();
           for(User user:users){
               if(useApi){
                   addUserByApi(user);
               }else{
                   addUser(user);
               }
           }
           db.setTransactionSuccessful();
       }catch (Exception e){
           Log.i(tag, "" + e.getMessage());
       }finally {
           db.endTransaction();
           Log.i(tag, "耗时：" + (System.currentTimeMillis() - start));
       }
    }

    //添加单个用户
    public void addUserByApi(User user){
        if(user == null){
            return;
        }
        ContentValues values = new ContentValues();
        values.put("username", user.username);
        values.put("age", user.age);
        values.put("gender", user.gender);
        values.put("school", user.school);
        db.insert(DBHelper.TABLE_USERS, null, values);
    }

    public void addUser(User user){
        String sql = "insert into users values(null,?,?,?,?,?);";
        db.execSQL(sql, new String[]{user.username, user.age  + "",user.school, user.gender, user.birthday});
    }

    //
    public void deleteUser(User user){
        db.delete(DBHelper.TABLE_USERS, "_id = ?", new String[]{user.id + ""});
    }

    public void updateUser(User user){
        ContentValues values = new ContentValues();
        values.put("username", user.username);
        values.put("age", user.age);
        values.put("gender", user.gender);
        values.put("school", user.school);
        db.update(DBHelper.TABLE_USERS, values, "_id = ?", new String[]{user.id + ""});
    }

    public List<User> querry(){
        ArrayList<User> userList = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from users;", new String[]{});
        while(cursor.moveToNext()){
            User user = new User();
            user.id = cursor.getInt(cursor.getColumnIndex("_id"));
            user.username = cursor.getString(cursor.getColumnIndex("username"));
            user.gender = cursor.getString(cursor.getColumnIndex("gender"));
            user.school = cursor.getString(cursor.getColumnIndex("school"));
            user.birthday = cursor.getString(cursor.getColumnIndex("birthday"));
            userList.add(user);
        }
        cursor.close();
        return userList;
    }
}
