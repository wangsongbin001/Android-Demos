package com.wang.csdnapp.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by songbinwang on 2016/10/31.
 */

public class DBHelper extends SQLiteOpenHelper{

    private static final String DB_NAME = "csdn_app";
    private static final int VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /**
         * id,title,link,date,imgLink,content,newstype
         */
        String sql = "create table tb_newsItem( _id integer primary key autoincrement , "
                + " title text , link text , date text , imgLink text , content text , newstype integer  );";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
