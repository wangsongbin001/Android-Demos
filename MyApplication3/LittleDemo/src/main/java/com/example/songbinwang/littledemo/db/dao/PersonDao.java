package com.example.songbinwang.littledemo.db.dao;

import android.content.Context;

import com.example.songbinwang.littledemo.db.OrmDBHelper;
import com.example.songbinwang.littledemo.db.entry.Person;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by SongbinWang on 2017/7/31.
 */

public class PersonDao {

    private Context context;
    private OrmDBHelper mHelper;
    private Dao<Person, Integer> mDao;

    public PersonDao(Context context){
        this.context = context;
        try{
            mHelper = OrmDBHelper.getInstance(context);
            mDao = mHelper.getDao(Person.class);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //添加Person
    public void add(Person person){
        try {
            mDao.create(person);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Person person){
        try {
            mDao.delete(person);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Person person) {
        try {
            mDao.update(person);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Person> queryAll(){
        List<Person> persons = null;
        try {
            persons =  mDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return persons;
    }
}
