package com.wang.mtoolsdemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.wang.mtoolsdemo.common.view.PullRefreshListView;
import com.wang.mtoolsdemo.common.xlistview.XListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dell on 2017/11/2.
 */

public class ThirdActivity extends Activity {

//    @Bind(R.id.lv)
//    ListView lv;
//    @Bind(R.id.pl)
//    PullRefreshListView pl;
    XListView lv;

    private List<String> data;
    private MyAdapter myAdapter;
    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);
        ButterKnife.bind(this);

        lv = findViewById(R.id.lv);
        mHandler = new Handler();
        data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add("i = " + i);
        }

        myAdapter = new MyAdapter();
        lv.setAdapter(myAdapter);
        lv.setPullLoadEnable(true);
        lv.setPullRefreshEnable(true);
        lv.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        data = new ArrayList<>();
                        for (int i = 0; i < 5; i++) {
                            data.add("i = " + i);
                        }
                        myAdapter.notifyDataSetChanged();
                        lv.stopRefresh(true);
                    }
                }, 3000);
            }

            @Override
            public void onLoadMore() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 5; i++) {
                            data.add("i = " + i);
                        }
                        myAdapter.notifyDataSetChanged();
                        lv.stopLoadMore();
                    }
                }, 3000);
            }
        });

    }

    class MyAdapter extends BaseAdapter {

        public MyAdapter() {
            super();
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView tv = new TextView(ThirdActivity.this);
            tv.setHeight(100);
            tv.setGravity(Gravity.CENTER);
            tv.setText(data.get(position));
            return tv;
        }
    }
}
