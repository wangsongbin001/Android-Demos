package com.example.songbinwang.littledemo.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songbinwang on 2016/6/1.
 */
public class AppInfoAdapter extends BaseAdapter{

    LayoutInflater mLayoutInflater;
    private Context context;
    List<String> mData;

    AppInfoAdapter(Context context, List<String> mData){
        this.context = context;
        if(mData == null){
            this.mData = new ArrayList<String>();
        }else{
            this.mData = mData;
        }
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    class ViewHolder{
        ImageView iv;
        TextView tv;
    }
}
