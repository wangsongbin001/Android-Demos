package com.wang.csdnapp.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.song.spider.bean.NewsItem;
import com.wang.csdnapp.R;
import com.wang.csdnapp.util.ImageLoader;
import com.wang.csdnapp.view.NewsItemLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songbinwang on 2016/10/25.
 */

public class NewsItemAdapter extends BaseAdapter{

    List<NewsItem> mDatas;
    private Context mContext;
    private LayoutInflater mInflater;

    public NewsItemAdapter(Context context, List<NewsItem> mDatas){
        if(mDatas == null){
            this.mDatas = new ArrayList<NewsItem>();
        }else{
            this.mDatas = mDatas;
        }
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    public void update(List<NewsItem> items){
        mDatas.clear();
        mDatas.addAll(items);
        notifyDataSetChanged();
    }

    public void addAll(List<NewsItem> items){
        mDatas.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int i) {
        return mDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.news_item, null);
            viewHolder = new ViewHolder(convertView);
        }else{
            viewHolder = ViewHolder.getFromView(convertView);
        }
        NewsItem item = mDatas.get(position);
//        NewsItemLayout newsItemLayout = (NewsItemLayout) convertView;
//        newsItemLayout.createViews(item);
        viewHolder.tv_title.setText(item.getTitle());
        viewHolder.tv_content.setText(item.getContent());
        viewHolder.tv_date.setText(item.getDate());
        if(!TextUtils.isEmpty(item.getImgUrl())){
            viewHolder.iv_cover.setImageResource(R.drawable.img_default);
            ImageLoader.getInstance(mContext).bindBitmap(item.getImgUrl(), viewHolder.iv_cover);
        }else{
            viewHolder.iv_cover.setVisibility(View.GONE);
        }

        return convertView;
    }

    static class ViewHolder{
        TextView tv_title, tv_content, tv_date;
        ImageView iv_cover;

        ViewHolder(View convertView){
            tv_title = (TextView) convertView.findViewById(R.id.id_title);
            tv_content = (TextView) convertView.findViewById(R.id.id_content);
            tv_date = (TextView) convertView.findViewById(R.id.id_date);
            iv_cover = (ImageView) convertView.findViewById(R.id.id_newsImg);
            convertView.setTag(this);
        }

        static ViewHolder getFromView(View convertView){
            if(convertView != null && convertView.getTag() instanceof ViewHolder){
                return (ViewHolder) convertView.getTag();
            }
            return new ViewHolder(convertView);
        }

    }
}
