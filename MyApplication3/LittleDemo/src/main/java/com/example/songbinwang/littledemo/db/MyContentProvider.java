package com.example.songbinwang.littledemo.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by xiaosongshu on 2017/7/29.
 */

public class MyContentProvider extends ContentProvider {

    private static final String AUTHORIY = "com.example.littledemo";
    public static UriMatcher uriMatcher;
    //    private DBManager dbManager;
    private DBHelper dbHelper;
    private static final String tag = "myprovider";

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORIY, "/user", 1);
        uriMatcher.addURI(AUTHORIY, "/users", 2);
    }

    @Override
    public boolean onCreate() {
        Log.i(tag, "onCreate");
//        dbManager = new DBManager(getContext());
        dbHelper = new DBHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.i(tag, "query");
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        int code = uriMatcher.match(uri);
        Cursor cursor = null;
        if (code == 1) {

        } else if (code == 2) {

        }
        Log.i(tag, "query" + code);
        return db.query(DBHelper.TABLE_USERS, projection, selection, selectionArgs, sortOrder, null, null);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        Log.i(tag, "getType");
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Log.i(tag, "insert");
        Uri resultUri = null;
        try{
            final SQLiteDatabase db = dbHelper.getWritableDatabase();
            long id = db.insert(DBHelper.TABLE_USERS, null, values);
            resultUri = TestContract.TestEntry.buildUri(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.i(tag, "delete");
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.i(tag, "update");
        return 0;
    }
}
