package com.wang.mtoolsdemo.common.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.ListView;

import com.wang.mtoolsdemo.R;

/**
 * Created by dell on 2017/11/2.
 * 1,有很大的问题。
 * 2,有提升，但依然，不能投入使用
 */

public class PullRefreshListView extends SwipeRefreshLayout {
    private static final String tag = "PullRefreshListView";

    //包装ListView，并为其设置滑动监听
    private ListView mListView;
    private boolean mIsLastItem = true;

    //最小滑动距离
    private float mTouchSlop = 80;

    //是否正在加载中
    private boolean isLoading = false;
    private boolean isRefreshing = false;

    private View mFooterView;
    private float bottomPadding = 80;

    /**
     * 对外接口
     */
    public interface OnPullRefreshListener {
        void onRefresh();

        void loadMore();
    }

    OnPullRefreshListener mOnPullRefreshListener;

    public void setmOnPullRefreshListener(OnPullRefreshListener mOnPullRefreshListener) {
        this.mOnPullRefreshListener = mOnPullRefreshListener;
    }

    public PullRefreshListView(Context context) {
        this(context, null);
    }

    public PullRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    //初始化下拉刷新的模块
    private void init(Context context) {
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mOnPullRefreshListener != null) {
                    isRefreshing = true;
                    mOnPullRefreshListener.onRefresh();
                }
            }
        });
        mFooterView = LayoutInflater.from(context).inflate(R.layout.listview_footer, null, false);
//        bottomPadding = mFooterView.getMeasuredHeight();
//        Log.i(tag, "bottomPadding:" + bottomPadding);
//        if (0 == bottomPadding) {
//            bottomPadding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0,
//                    context.getResources().getDisplayMetrics());
//        }
//        Log.i(tag, "bottomPadding:" + bottomPadding);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (mListView == null) {
            if (getChildCount() > 0 && getChildAt(0) instanceof ListView) {
                mListView = (ListView) getChildAt(0);
                setListViewScrollListener();
                mListView.addFooterView(mFooterView);
                int b = (int) bottomPadding;
                mListView.setPadding(0, 0, 0, -b);
            }
        }
    }

    /**
     * 设置滑动监听
     */
    private void setListViewScrollListener() {
        if (mListView == null) {
            return;
        }
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (visibleItemCount + firstVisibleItem >= totalItemCount) {
                    mIsLastItem = true;
                } else {
                    mIsLastItem = false;
                }
            }
        });
    }

    private float mDownY = -1, mUpY = -1, mMoveY = -1;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(!mIsLastItem){
            return super.dispatchTouchEvent(ev);
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownY = ev.getY();
                mUpY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if(mDownY == -1){
                    mMoveY = mDownY = ev.getY();
                    break;
                }
                mMoveY = ev.getY();
                float tempY = mDownY - mMoveY;
                tempY = tempY < 0 ? 0 : tempY;
                tempY = tempY > bottomPadding ? bottomPadding : tempY;
                int b = (int) (bottomPadding - tempY * 0.8) ;
                mListView.setPadding(0, 0, 0, -b);
                break;
            case MotionEvent.ACTION_UP:
                mUpY = ev.getY();
                if (canLoadMore()) {
                    loadMore();
                }
                mDownY = mUpY = mMoveY = -1;
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    private Boolean canLoadMore() {
        //1，是上拉的状态
        boolean condition1 = false;
        if (mDownY - mUpY > bottomPadding) {
            condition1 = true;
        }
        //2, 已经到最后一个Item
        boolean condition2 = mIsLastItem;

        //3是否正在加载中
        boolean condition3 = !isLoading && !isRefreshing;

        if (condition1) {
            Log.i(tag, "");
        }
        return condition1 && condition2 && condition3;
    }

    public void setLoading(boolean isLoading) {
        this.isLoading = isLoading;
        if (isLoading) {
            //显示加载的View
//            mListView.addFooterView(mFooterView);
            mListView.setPadding(0, 0, 0, 0);
        } else {
            //隐藏加载的View
//            mListView.addFooterView(mFooterView);
            mListView.setPadding(0, 0, 0, (int) -bottomPadding);
        }
    }

    @Override
    public void setRefreshing(boolean refreshing) {
        super.setRefreshing(refreshing);
        isRefreshing = true;
    }

    private void loadMore() {
        setLoading(true);
        if (mOnPullRefreshListener != null) {
            mOnPullRefreshListener.loadMore();
        }
    }

//    public void setAdapter(ListAdapter adapter){
//        Log.i(tag, "setAdapter" + mListView);
//        if(mListView != null){
//            mListView.setAdapter(adapter);
//
//            Log.i(tag, "setAdapter");
//        }
//    }

}
