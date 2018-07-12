package com.wang.csdnapp.ui.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.song.spider.bean.News;
import com.song.spider.bean.News.NewsType;
import com.wang.csdnapp.R;
import com.wang.csdnapp.util.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songbinwang on 2016/11/1.
 */

public class NewsContentAdapter extends BaseAdapter{
    LayoutInflater mInflater;
    private List<News> mDatas;
    private Context mContext;

    public NewsContentAdapter(Context context, List<News> datas) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        if(datas == null){
            mDatas = new ArrayList<>();
        }else{
            this.mDatas = datas;
        }
    }

    public void updata(List<News> mDatas){
        this.mDatas.clear();
        this.mDatas.addAll(mDatas);
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
    public int getViewTypeCount() {
        return 5;
    }

    @Override
    public int getItemViewType(int position) {
        switch(mDatas.get(position).getType()){
            case NewsType.TITLE:
                return 0;
            case NewsType.SUMMARY:
                return 1;
            case NewsType.CONTENT:
                return 2;
            case NewsType.IMG:
                return 3;
            case NewsType.BOLD_TITLE:
                return 4;
        }
        return -1;
    }

    @Override
    public boolean isEnabled(int position) {
        if(mDatas.get(position).getType() == NewsType.IMG){
            return true;
        }
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        News news = mDatas.get(position);
        if(convertView == null){
            holder = new ViewHolder();
            switch(news.getType()){
                case NewsType.TITLE:
                    convertView = mInflater.inflate(R.layout.news_content_title_item, null);
                    holder.textView = (TextView) convertView.findViewById(R.id.text);
                case NewsType.SUMMARY:
                    convertView = mInflater.inflate(R.layout.news_content_summary_item, null);
                    holder.textView = (TextView) convertView.findViewById(R.id.text);
                case NewsType.CONTENT:
                    convertView = mInflater.inflate(R.layout.news_content_item, null);
                    holder.textView = (TextView) convertView.findViewById(R.id.text);
                case NewsType.IMG:
                    convertView = mInflater.inflate(R.layout.news_content_img_item, null);
                    holder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
                case NewsType.BOLD_TITLE:
                    convertView = mInflater.inflate(R.layout.news_content_bold_title_item, null);
                    holder.textView = (TextView) convertView.findViewById(R.id.text);
            }
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        switch (news.getType())
        {
            case NewsType.IMG:
                ImageLoader.getInstance(mContext).bindBitmap(news.getImgLink(), holder.imageView);
                break;
            case NewsType.TITLE:
                holder.textView.setText(news.getTitle());
                break;
            case NewsType.SUMMARY:
                holder.textView.setText(news.getSummary());
                break;
            case NewsType.CONTENT:
                holder.textView.setText("\u3000\u3000"+ Html.fromHtml(news.getContent()));
                break;
            case NewsType.BOLD_TITLE:
                holder.textView.setText("\u3000\u3000"+Html.fromHtml(news.getContent()));
            default:

                break;
        }
        return convertView;
    }

    static class ViewHolder{
        ImageView imageView;
        TextView textView;
    }
}
