package com.wang.mtoolsdemo.common.xrecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2017/11/6.
 */

public abstract class CommonAdapter<T> extends RecyclerView.Adapter<ViewHolder> {

    protected Context mContext;
    protected int resId;
    protected List<T> mData;

    public interface OnItemClickListener {
        void onItemClick(View item, int position);

        void onItemLongClick(View item, int position);
    }

    OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public CommonAdapter(Context mContext, int resId, List<T> mData) {
        super();
        this.resId = resId;
        this.mContext = mContext;
        if (mData == null) {
            mData = new ArrayList<>();
        }
        this.mData = mData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = ViewHolder.get(mContext, parent, resId);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        convert(holder, mData.get(position));
        if (mOnItemClickListener != null) {
            holder.getItem().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(v, position);
                }
            });
            holder.getItem().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickListener.onItemLongClick(v, position);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    //对外的接口
    public abstract void convert(ViewHolder holder, T t);
}
