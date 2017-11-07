package com.wang.mtoolsdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.wang.mtoolsdemo.common.util.ChineseCharToEnUtil;
import com.wang.mtoolsdemo.common.xrecyclerview.SectionAdapter;
import com.wang.mtoolsdemo.common.xrecyclerview.ViewHolder;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dell on 2017/11/7.
 */

public class FourActivity extends Activity {

    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;

    private List<String> mData = new ArrayList<>();
    private static char[] chartable = {'啊', '芭', '擦', '搭', '蛾', '发', '噶', '哈',
            '哈', '击', '喀', '垃', '妈', '拿', '哦', '啪', '期', '然', '撒', '塌', '塌',
            '塌', '挖', '昔', '压', '匝',};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_4);
        ButterKnife.bind(this);

        mData.add("张三");
        mData.add("李四");
        mData.add("王五");
        mData.add("wangs");
        mData.add("aaa");
        //将所有的字符，按首字母拼音排序
        ChineseCharToEnUtil.sort(mData);
//        Comparator<Object> com = Collator.getInstance(Locale.CHINA);
//        Collections.sort(mData);
//        Log.i("wangsongbin", mData.toString());

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
}
