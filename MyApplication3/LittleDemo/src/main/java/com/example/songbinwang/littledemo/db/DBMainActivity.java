package com.example.songbinwang.littledemo.db;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.songbinwang.littledemo.R;
import com.example.songbinwang.littledemo.db.dao.PersonDao;
import com.example.songbinwang.littledemo.db.entry.Person;
import com.example.songbinwang.littledemo.util.LogUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaosongshu on 2017/7/29.
 */

public class DBMainActivity extends Activity {

    private Button btn_add, btn_delete,btn_update,btn_query;
    private Button btn_add2;
//    private DBManager dbManager;
    private List<User> userList;
    private static final String tag = "DBMainActivity";
//    private OrmDBHelper ormDBHelper;
    PersonDao personDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbmain);

        fetchIntent();
        initData();
        initViews();
        registerListeners();
//        dbManager = new DBManager(this);
//        ormDBHelper = OrmDBHelper.getInstance(this);
        personDao = new PersonDao(getApplicationContext());
    }

    private void fetchIntent(){


    }

    private void initData(){
        userList = new ArrayList<User>();
        for(int i=0;i<10;i++){
            userList.add(new User().setUsername("zhangsan").setAge(10 + i).setGender("male").setSchool("向阳小学"));
        }
    }

    private void initViews(){
        btn_add = (Button) findViewById(R.id.btn_add);
        btn_delete = (Button) findViewById(R.id.btn_delete);
        btn_update = (Button) findViewById(R.id.btn_update);
        btn_query = (Button) findViewById(R.id.btn_query);
        btn_add2 = (Button) findViewById(R.id.btn_add2);
    };

    private void registerListeners(){
        btn_add.setOnClickListener(onClicker);
        btn_delete.setOnClickListener(onClicker);
        btn_update.setOnClickListener(onClicker);
        btn_query.setOnClickListener(onClicker);
        btn_add2.setOnClickListener(onClicker);
    }

    View.OnClickListener onClicker = new View.OnClickListener(){
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.btn_add:
//                    dbManager.addUserList(userList, true);
                    long start =  System.currentTimeMillis();
                    Log.i(tag, "add ");
//                    for(int i =0;i<userList.size();i++){
//
//                        try {
//                            ormDBHelper.getDao(Person.class).create(person);
//                        } catch (SQLException e) {
//                            e.printStackTrace();
//                        }
//                    }
                    Person person = new Person("张三", "2B青年");
                    personDao.add(person);
                    Log.i(tag, "made:" + (System.currentTimeMillis() - start));
                    break;
                case R.id.btn_delete:
//                    dbManager.deleteUser(new User().setId(1));
//                    try {
//
//                        person.set_id(1);
//                        OrmDBHelper.getInstance(getApplicationContext()).getDao(Person.class).delete(person);
//                    } catch (SQLException e) {
//                        e.printStackTrace();
//                    }
                    Person p1 = new Person("李四", "小瘪三");
                    p1.set_id(2);
                    personDao.delete(p1);
                    break;
                case R.id.btn_update:
//                    dbManager.updateUser(new User().setId(4).
//                            setUsername("lisi").setAge(10).setGender("male").setSchool("向阳小学"));
//                    try {
//                        Person person = new Person("李四", "小瘪三");
//                        person.set_id(2);
//                        OrmDBHelper.getInstance(getApplicationContext()).getDao(Person.class).update(person);
//                    } catch (SQLException e) {
//                        e.printStackTrace();
//                    }
                    Person p2 = new Person("李四", "小瘪三");
                    p2.set_id(2);
                    personDao.update(p2);
                    break;
                case R.id.btn_query:
//                    LogUtil.i(tag, "" + dbManager.querry().toString());
//                    try {
//                        List<Person> personList = OrmDBHelper.getInstance(getApplicationContext())
//                                .getDao(Person.class).queryForAll();
//                        Log.i(tag, "" + personList.toString());
//                    } catch (SQLException e) {
//                        e.printStackTrace();
//                    }
                    List<Person> personList = personDao.queryAll();
                    Log.i(tag, "" + personList.toString());
                    break;
                case R.id.btn_add2:
                    lookout();
                    break;
            }
        }
    };

    private void lookout(){
        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(TestContract.TestEntry.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()){
            User user = new User();
            user.id = cursor.getInt(cursor.getColumnIndex("_id"));
            user.username = cursor.getString(cursor.getColumnIndex("username"));
            user.gender = cursor.getString(cursor.getColumnIndex("gender"));
            user.school = cursor.getString(cursor.getColumnIndex("school"));
            user.birthday = cursor.getString(cursor.getColumnIndex("birthday"));
            LogUtil.i(tag, "user:" + user.toString());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        dbManager.closeDB();
    }
}
