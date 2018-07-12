package com.example.songbinwang.littledemo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by xiaosongshu on 2017/7/29.
 */

public class DBHelper extends SQLiteOpenHelper{

    public static final String TABLE_USERS = "users";
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "test.db";
    private static final String SQL_ONCREATE = "" +
            "create table users(_id INTEGER PRIMARY KEY AUTOINCREMENT,username varchar(20)," +
            "age int," +
            "school varchar(50)," +
            "gender varchar(50) default 'male'," +
            "birthday date);";

    private static final String SQL_ONUPDATE= "" +
            "alter table users add other varchar(20);";


    public DBHelper(Context context){
        //CursorFactory 设为null使用默认值
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       //数据库第一次被创建时，调用onCreate方法
        db.execSQL(SQL_ONCREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion > oldVersion){
            db.execSQL(SQL_ONUPDATE);
        }
    }
}
