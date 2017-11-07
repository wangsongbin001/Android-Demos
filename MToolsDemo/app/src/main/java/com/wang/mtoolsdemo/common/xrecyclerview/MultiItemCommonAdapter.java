package com.wang.mtoolsdemo.common.xrecyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by dell on 2017/11/6.
 * 参考http://blog.csdn.net/lmj623565791/article/details/51118836/
 */

public abstract class MultiItemCommonAdapter<T> extends CommonAdapter<T> {

    public interface MultiItemTypeSupport<T>
    {
        int getLayoutId(int itemType);

        int getItemViewType(int position, T t);
    }

    protected MultiItemTypeSupport mMultiItemTypeSupport;

    public MultiItemCommonAdapter(Context mContext, List mData,
                                  MultiItemTypeSupport multiItemTypeSupport) {
        super(mContext, -1, mData);
        this.mMultiItemTypeSupport = multiItemTypeSupport;
    }

    //正对不同的布局
    @Override
    public int getItemViewType(int position) {
        return mMultiItemTypeSupport.getItemViewType(position, mData.get(position));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = mMultiItemTypeSupport.getLayoutId(viewType);
        View itemView = LayoutInflater.from(mContext).inflate(layoutId, parent, false);
        ViewHolder viewHolder = new ViewHolder(mContext, itemView, parent);
        return viewHolder;
    }
}
