package com.song.myweibo.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.song.myweibo.util.LogUtil;
import com.song.spider.bean.NewsItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songbinwang on 2016/10/31.
 */

public class NewsItemDao {

    private static final String tag = "NewsItemDao";

    private DBHelper dbHelper;

    public NewsItemDao(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void add(NewsItem newsItem) {
        String sql = "insert into tb_newsItem (title,link,date,imgLink,content,newstype) values(?,?,?,?,?,?) ;";
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL(sql,
                new Object[]{newsItem.getTitle(), newsItem.getLink(), newsItem.getDate(), newsItem.getImgUrl(),
                        newsItem.getContent(), newsItem.getNewsType()});
        db.close();
    }

    public void add(List<NewsItem> items) {
        String sql = "insert into tb_newsItem (title,link,date,imgLink,content,newstype) values(?,?,?,?,?,?) ;";
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            db.beginTransaction();
            for (NewsItem newsItem : items) {
                db.execSQL(sql,
                        new Object[]{newsItem.getTitle(), newsItem.getLink(), newsItem.getDate(), newsItem.getImgUrl(),
                                newsItem.getContent(), newsItem.getNewsType()});
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {

        } finally {
            db.close();
        }
    }

    public void deleteAll(int newsType) {
        String sql = "delete from tb_newsItem where newstype = ?";
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL(sql, new Object[]{newsType});
        db.close();
    }

    public List<NewsItem> list(int newsType, int currentPage) {
        List<NewsItem> newsItems = new ArrayList<NewsItem>();
        try
        {
            int offset = 10 * (currentPage - 1);
            String sql = "select title,link,date,imgLink,content,newstype from tb_newsItem where newstype = ? limit ?,? ";
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor c = db.rawQuery(sql, new String[] { newsType + "", offset + "", "" + (offset + 10) });

            NewsItem newsItem = null;

            while (c.moveToNext())
            {
                newsItem = new NewsItem();

                String title = c.getString(0);
                String link = c.getString(1);
                String date = c.getString(2);
                String imgLink = c.getString(3);
                String content = c.getString(4);
                Integer newstype = c.getInt(5);

                newsItem.setTitle(title);
                newsItem.setLink(link);
                newsItem.setImgUrl(imgLink);
                newsItem.setDate(date);
                newsItem.setNewsType(newstype);
                newsItem.setContent(content);

                newsItems.add(newsItem);

            }
            c.close();
            db.close();
            LogUtil.i(tag, newsItems.size() + "  newsItems.size()");
        } catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return newsItems;
    }
}
