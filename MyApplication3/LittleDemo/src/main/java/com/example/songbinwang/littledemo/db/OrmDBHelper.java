package com.example.songbinwang.littledemo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.songbinwang.littledemo.db.entry.Person;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by SongbinWang on 2017/7/31.
 */

public class OrmDBHelper extends OrmLiteSqliteOpenHelper{

    private static final String DB_NAME = "test.db";
    private static final int DB_VERSION = 2;
    private static OrmDBHelper instance;
    private Map<String, Dao> daos = new HashMap<String, Dao>();

    public OrmDBHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try{
            TableUtils.createTable(connectionSource, Person.class);
        }catch (Exception e){

        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
        try{
            TableUtils.dropTable(connectionSource, Person.class, true);
            onCreate(sqLiteDatabase, connectionSource);
        }catch (Exception e){

        }
    }

    /**
     * 获取单例
     * @param context
     * @return
     */
    public static synchronized OrmDBHelper getInstance(Context context){
        Context ct = context.getApplicationContext();
         if(instance == null){
             synchronized (OrmDBHelper.class){
                 if(instance == null){
                     instance = new OrmDBHelper(ct);
                 }
             }
         }
         return instance;
    }

    /**
     * 通过类名获取dao
     */
    public Dao getDao(Class clazz) throws SQLException {
        Dao dao = null;
        String name = clazz.getSimpleName();
        if(!daos.containsKey(name)){
            dao = super.getDao(clazz);
            daos.put(name, dao);
        }else{
            dao = daos.get(name);
        }
        return dao;
    }

    /**
     * 释放资源
     */
    public void close(){
        for(String key : daos.keySet()){
            Dao dao = daos.get(key);
            dao = null;
        }
        daos.clear();
    }
}
