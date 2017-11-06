package com.wang.mtoolsdemo.common.xrecyclerview;

import android.content.Context;

import java.util.List;

/**
 * Created by dell on 2017/11/6.
 */

public abstract class SectionAdapter<T> extends MultiItemCommonAdapter<T>{

    public interface SectionSupport<T>
    {
        public int sectionHeaderLayoutId();

        public int sectionTitleTextViewId();

        public String getTitle(T t);
    }

    private SectionSupport mSectionSupport;
    private MultiItemTypeSupport mMultiItemTypeSupport = new MultiItemTypeSupport() {
        @Override
        public int getLayoutId(int itemType) {
            return 0;
        }

        @Override
        public int getItemViewType(int position, Object o) {
            return 0;
        }
    };

    public SectionAdapter(Context mContext, List mData, SectionSupport mSectionSupport) {
        super(mContext, mData, null);
        this.mSectionSupport = mSectionSupport;
    }
}
