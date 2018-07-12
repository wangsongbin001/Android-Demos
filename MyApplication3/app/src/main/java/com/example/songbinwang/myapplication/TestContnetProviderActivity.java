package com.example.songbinwang.myapplication;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.songbinwang.myapplication.cp.TestContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaosongshu on 2017/7/30.
 */

public class TestContnetProviderActivity extends Activity {

    private Button btn_add, btn_delete;
    List<User> userList;
    private static final String tag = "TestContnetP";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testcp);

        fetchIntent();
        initViews();
        registerListeners();
    }

    private void fetchIntent() {
        userList = new ArrayList<User>();
        for(int i=0;i<1000;i++){
            userList.add(new User().setUsername("zhangsan").setAge(10 + i).setGender("male").setSchool("向阳小学"));
        }
    }

    private void initViews() {
        btn_add = (Button) findViewById(R.id.btn_add);
        btn_delete = (Button) findViewById(R.id.btn_delete);
    }

    ;

    private void registerListeners() {
        btn_add.setOnClickListener(onClicker);
        btn_delete.setOnClickListener(onClicker);
    }

    View.OnClickListener onClicker = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ContentResolver resolver = getContentResolver();
            switch (v.getId()){
                case R.id.btn_add:
                    Uri uri = TestContract.TestEntry.CONTENT_URI;
                    Log.i(tag, uri.getPath());
                    ContentValues values = new ContentValues();
                    values.put("username", "wanger");
                    values.put("age", 100);
                    resolver.insert(uri, values);
                    break;
                case R.id.btn_delete:
                    Cursor cursor = resolver.query(
                            TestContract.TestEntry.CONTENT_URI, null, null,null,null,null);
                    userList = new ArrayList<>();
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
                    Log.i(tag, "userList:" + userList.toString());
                    break;
            }

        }
    };
}
