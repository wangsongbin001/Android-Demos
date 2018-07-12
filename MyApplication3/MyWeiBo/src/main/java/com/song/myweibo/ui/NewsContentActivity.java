package com.song.myweibo.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.song.myweibo.R;
import com.song.myweibo.adapter.NewsContentAdapter;
import com.song.myweibo.util.LogUtil;
import com.song.myweibo.widget.XListView;
import com.song.spider.bean.News;
import com.song.spider.bean.NewsItem;
import com.song.spider.biz.NewsItemBiz;

import java.util.List;

/**
 * Created by songbinwang on 2016/10/31.
 */

public class NewsContentActivity extends Activity {

    private String articleUrl;
    private NewsItem newsItem;
    private NewsItemBiz biz;
    List<News> mDatas;

    private XListView xListView;
    private ProgressBar mProgressBar;
    private ImageView iv_reload;
    NewsContentAdapter mAdapter;
    private static final String tag = "NewsContent:";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_content);
        LogUtil.i(tag, "onCreate");
//        if (getIntent() == null) {
//            return;
//        }
//        fetchIntent(getIntent());
        articleUrl = "http://www.csdn.net/article/2016-10-26/2826665";
        biz = new NewsItemBiz();
        initViews();
        new LoadDataTask().execute();
    }

    private void fetchIntent(Intent intent) {
        newsItem = (NewsItem) intent.getSerializableExtra("item");
        articleUrl = newsItem.getLink();
        LogUtil.i(tag, "articleUrl:" + articleUrl);
    }

    private void initViews() {
        xListView = (XListView) findViewById(R.id.id_listview);
        mProgressBar = (ProgressBar) findViewById(R.id.id_newsContentPro);
        iv_reload = (ImageView) findViewById(R.id.reLoadImage);
        mAdapter = new NewsContentAdapter(this, null);
        xListView.setAdapter(mAdapter);
    }

    public void back(View view) {
        finish();
    }

    class LoadDataTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            try {
                mDatas = biz.getNews(articleUrl);
                return 200;
            } catch (Exception e) {

            }
            return -1;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            if (mDatas != null) {
                LogUtil.i(tag, "mData:" + mDatas.toString());
            } else {
                LogUtil.i(tag, "mData:null");
            }

            mProgressBar.setVisibility(View.GONE);
            if (result == 200 && mDatas != null) {
                iv_reload.setVisibility(View.GONE);
                mAdapter.updata(mDatas);
            } else {
                iv_reload.setVisibility(View.VISIBLE);
            }

        }
    }


}
