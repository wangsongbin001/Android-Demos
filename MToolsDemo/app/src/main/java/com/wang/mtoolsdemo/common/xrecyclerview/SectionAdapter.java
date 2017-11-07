package com.wang.mtoolsdemo.common.xrecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * Created by dell on 2017/11/6.
 */

public abstract class SectionAdapter<T> extends MultiItemCommonAdapter<T> {

    private static final int TYPE_SECTION = 0;
    private LinkedHashMap<String, Integer> mSections;
    private SectionSupport mSectionSupport;

    public interface SectionSupport<T> {
        //header布局
        public int sectionHeaderLayoutId();
        //header textID
        public int sectionTitleTextViewId();
        //对应首字母
        public String getTitle(T t);
    }

    private MultiItemTypeSupport mHeaderMultiItemTypeSupport = new MultiItemTypeSupport() {
        @Override
        public int getLayoutId(int itemType) {
            if (itemType == TYPE_SECTION) {
                return mSectionSupport.sectionHeaderLayoutId();
            }
            return resId;
        }

        @Override
        public int getItemViewType(int position, Object o) {
            if (mSections.values().contains(position)) {
                return TYPE_SECTION;
            }
            return 1;
        }
    };

    public SectionAdapter(Context mContext, List mData, int resId, SectionSupport mSectionSupport) {
        super(mContext, mData, null);
        this.mSectionSupport = mSectionSupport;
        mSections = new LinkedHashMap<>();
        this.resId = resId;
        mMultiItemTypeSupport = mHeaderMultiItemTypeSupport;
        findSections();
        registerAdapterDataObserver(mObserver);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        unregisterAdapterDataObserver(mObserver);
    }

    @Override
    public int getItemViewType(int position) {
        //应为data.size 有可能小于position
        return mMultiItemTypeSupport.getItemViewType(position, null);
    }

    @Override
    public int getItemCount() {
        return mData.size() + mSections.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        position = getIndexForPosition(position);
        if (holder.getItemViewType() == TYPE_SECTION) {
            holder.setText(mSectionSupport.sectionTitleTextViewId(),
                    mSectionSupport.getTitle(mData.get(position)));
            return;
        }
        super.onBindViewHolder(holder, position);
    }

    /**
     * 返回mData的下标index，对应的数据的首字母，是position需要的。
     * @param position
     * @return
     */
    public int getIndexForPosition(int position) {
        int nSections = 0;
        Set<Map.Entry<String, Integer>> set = mSections.entrySet();
        for (Map.Entry<String, Integer> entry : set) {
            if(entry.getValue() < position){
                nSections ++;
            }
        }
        return position - nSections;
    }

    final RecyclerView.AdapterDataObserver mObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            findSections();
        }
    };

    private void findSections() {

        int n = mData.size();
        int nSections = 0;
        mSections.clear();
        for (int i = 0; i < n; i++) {
            String actionName = mSectionSupport.getTitle(mData.get(i));
            if (!mSections.keySet().contains(actionName)) {
                mSections.put(actionName, i + nSections);
                nSections++;
            }
        }
    }


}
