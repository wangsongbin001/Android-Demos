package com.wang.mtoolsdemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.wang.mtoolsdemo.common.util.ChineseCharToEnUtil;
import com.wang.mtoolsdemo.common.view.swipetoload.SwipeToLoadLayout;
import com.wang.mtoolsdemo.common.xrecyclerview.SectionAdapter;
import com.wang.mtoolsdemo.common.xrecyclerview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dell on 2017/11/7.
 * https://juejin.im/entry/570f716a1ea493006b5ecfbb//各种RecyclerView的开源框架
 */

public class FourActivity extends Activity implements SwipeToLoadLayout.OnRefreshListener, SwipeToLoadLayout.OnLoadMoreListener {

    @Bind(R.id.swipe_target)
    RecyclerView recyclerview;
    @Bind(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;

    private List<String> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_4);
        ButterKnife.bind(this);

        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);

        mData.add("张三");
        mData.add("李四");
        mData.add("王五");
        mData.add("wangs");
        mData.add("aaa");
        //将所有的字符，按首字母拼音排序
        ChineseCharToEnUtil.sort(mData);
        //设置layoutManager
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        //item动画
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        //item 设置分割线
//        recyclerview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        //设置adapter
//        recyclerview.setAdapter(new CommonAdapter<String>(this, R.layout.layout_rc_item, mData){
//            @Override
//            public void convert(ViewHolder holder, String s) {
//                holder.setText(R.id.tv_item, "" + s);
//            }
//        } );
        //两种布局
//        recyclerview.setAdapter(new MultiItemCommonAdapter<String>(this, mData, new MultiItemCommonAdapter.MultiItemTypeSupport<String>() {
//            @Override
//            public int getLayoutId(int itemType) {
//                if(itemType == 0){
//                    return R.layout.layout_rc_header;
//                }
//                return R.layout.layout_rc_item;
//            }
//
//            @Override
//            public int getItemViewType(int position, String o) {
//                if(o!= null && o.contains("w")){
//                    return 0;
//                }
//                return 1;
//            }
//        }){
//            @Override
//            public void convert(ViewHolder holder, String s) {
//                if(s != null && s.contains("w")){
//                    holder.setText(R.id.tv_header, "" + s);
//                }else{
//                    holder.setText(R.id.tv_item, "" + s);
//                }
//
//            }
//        } );
        //头文件
        recyclerview.setAdapter(new SectionAdapter<String>(this, mData, R.layout.layout_rc_item,
                new SectionAdapter.SectionSupport<String>() {
                    @Override
                    public int sectionHeaderLayoutId() {
                        return R.layout.layout_rc_header;
                    }

                    @Override
                    public int sectionTitleTextViewId() {
                        return R.id.tv_header;
                    }

                    @Override
                    public String getTitle(String o) {
                        String actionName = ChineseCharToEnUtil.getFirstLetter(o);
                        Log.i("wangsongbin", "actionName" + actionName);
                        return actionName;
                    }
                }) {
            @Override
            public void convert(ViewHolder holder, String s) {
                holder.setText(R.id.tv_item, "" + s);
            }
        });

    }

    Handler handler = new Handler();
    @Override
    public void onLoadMore() {
         handler.postDelayed(new Runnable() {
             @Override
             public void run() {
                 swipeToLoadLayout.setLoadingMore(false);
             }
         }, 3 * 1000);
    }

    @Override
    public void onRefresh() {
         handler.postDelayed(new Runnable() {
             @Override
             public void run() {
                 swipeToLoadLayout.setRefreshing(false);
             }
         }, 3 * 1000);
    }
}
