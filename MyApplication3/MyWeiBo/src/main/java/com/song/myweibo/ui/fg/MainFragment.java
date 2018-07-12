package com.song.myweibo.ui.fg;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.song.myweibo.R;
import com.song.myweibo.adapter.NewsItemAdapter;
import com.song.myweibo.ui.NewsContentActivity;
import com.song.myweibo.util.AppUtil;
import com.song.myweibo.util.LogUtil;
import com.song.myweibo.widget.XListView;
import com.song.spider.bean.NewsItem;
import com.song.spider.biz.NewsItemBiz;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by songbinwang on 2016/10/24.
 */

public class MainFragment extends Fragment implements XListView.IXListViewLoadMore, XListView.IXListViewRefreshListener{

    private final static String tag = "main_fragment";
    private final static int LOADING_MORE = 1001;
    private final static int LOADING_REFRESH = 1002;

    private int newsType = 0;
    private int currentPage = 1;

    private String title;
    private XListView xListView;
    private NewsItemBiz biz;
    private LoadDataTask loadDataTask;
    private NewsItemAdapter mAdapter;
    private List<NewsItem> mDatas = new ArrayList<NewsItem>();

    public MainFragment() {
        super();
    }

    public static MainFragment getInstance(int newsType){
        MainFragment fg = new MainFragment();
        Bundle b = new Bundle();
        b.putInt("newsType", newsType);
        fg.setArguments(b);
        return fg;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null){
            LogUtil.i(tag, "saveInstanceState is null");
        }else{
            newsType = savedInstanceState.getInt("newsType", 1);
        }
        LogUtil.i(tag, "newsType:"+newsType);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.tag_item_fragment, null);
        initViews(root);
        biz = new NewsItemBiz();
        newsType = getArguments() != null ? getArguments().getInt("newsType", 1) : 1;
        LogUtil.i(tag, "newsType:"+newsType);
        //loadData();
        return root;
    }

    private void initViews(View root){
        xListView = (XListView) root.findViewById(R.id.id_xlistview);
        xListView.setPullRefreshEnable(this);
        xListView.setPullLoadEnable(this);

        mAdapter = new NewsItemAdapter(getActivity(), mDatas);
        xListView.setAdapter(mAdapter);

        xListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(getActivity(), position + "onclick", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), NewsContentActivity.class);
                intent.putExtra("item", (Serializable) mDatas.get(position - 1));
                getActivity().startActivity(intent);
            }
        });

        xListView.setRefreshTime(AppUtil.getRefreashTime(getActivity(), newsType));
        xListView.startRefresh();
    }

    @Override
    public void onRefresh() {
        LogUtil.i(tag, "onRefresh");
        loadData(LOADING_REFRESH);
    }

    @Override
    public void onLoadMore() {
        LogUtil.i(tag, "onLoadMore");
        loadData(LOADING_MORE);
    }

    private void loadData(int loadType){
        if(loadDataTask != null && loadDataTask.getStatus() == AsyncTask.Status.PENDING){
            loadDataTask.cancel(true);
        }
        loadDataTask = new LoadDataTask();
        loadDataTask.execute(loadType);
    }

    class LoadDataTask extends AsyncTask<Integer, Void, List<NewsItem>>{
        @Override
        protected List<NewsItem> doInBackground(Integer... params) {
            if(params.length <= 0){
                return null;
            }
            switch(params[0]){
                case LOADING_MORE:
                    loadMore();
                    break;
                case LOADING_REFRESH:
                    refreshData();
                    break;
            }
            return mDatas;
        }

        @Override
        protected void onPostExecute(List<NewsItem> result) {
            LogUtil.i(tag, result != null ? result.toString() : "null");
            if(result != null){
                mAdapter.update(mDatas);
            }
            xListView.stopRefresh();
            xListView.stopLoadMore();
        }
    }

    private void loadMore(){
        currentPage ++;
        List<NewsItem> items = biz.getNewsItems(newsType, currentPage);
        mDatas.addAll(items);
    }

    private void refreshData(){
        currentPage = 1;
        List<NewsItem> items  = biz.getNewsItems(newsType, currentPage);
        mDatas = items;
    }
}
