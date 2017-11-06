package com.wang.mtoolsdemo.common.xrecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by dell on 2017/11/6.
 */

public class ViewHolder extends RecyclerView.ViewHolder{

    private SparseArray<View> mItemViews;//避免重复findViewById带来的内耗
    private View mConvertView;
    private Context mContext;

    public ViewHolder(Context context, View itemView, ViewGroup parent) {
        super(itemView);
        mContext = context;
        mConvertView = itemView;
        mItemViews = new SparseArray<>();
    }

    public static ViewHolder get(Context context, ViewGroup parent, int resId){
        View itemView = LayoutInflater.from(context).inflate(resId, parent, false);
        ViewHolder mHolder = new ViewHolder(context, itemView, parent);
        return mHolder;
    }

    public <T extends View>  T getView(int viewId){
        View view = mItemViews.get(viewId);
        if(view == null){
            view = mConvertView.findViewById(viewId);
            mItemViews.put(viewId, view);
        }
        return (T)view;
    }

    public ViewHolder setText(int viewId, String txt){
        TextView textView = getView(viewId);
        if(textView != null){
            textView.setText(txt);
        }
        return this;
    }

    public ViewHolder setImageResource(int viewId, int resourceId){
        ImageView imageView = getView(viewId);
        if(imageView != null){
            imageView.setImageResource(resourceId);
        }
        return this;
    }

    public ViewHolder setItemOnClicker(int viewId, View.OnClickListener onClickListener){
        View view = getView(viewId);
        if(view != null){
            view.setOnClickListener(onClickListener);
        }
        return this;
    }

    public View getItem(){
        return mConvertView;
    }
}
