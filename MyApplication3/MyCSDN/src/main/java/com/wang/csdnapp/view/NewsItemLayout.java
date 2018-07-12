package com.wang.csdnapp.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.song.spider.bean.NewsItem;
import com.wang.csdnapp.R;
import com.wang.csdnapp.util.ImageLoader;
import com.wang.csdnapp.util.LogUtil;

/**
 * Created by SongbinWang on 2017/4/1.
 */

public class NewsItemLayout extends LinearLayout{

    private static final String tag = "NewsItemLayout";
    private TextView tv_title, tv_content, tv_date;
    private ImageView iv_cover;
    private NewsItem item;
    private Context mContext;

    public NewsItemLayout(Context context) {
        this(context, null);
    }

    public NewsItemLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NewsItemLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        tv_title = (TextView) findViewById(R.id.id_title);
        tv_content = (TextView) findViewById(R.id.id_content);
        tv_date = (TextView) findViewById(R.id.id_date);
        iv_cover = (ImageView) findViewById(R.id.id_newsImg);
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        LogUtil.i(tag, "onMeasure" + " item" + item);
//        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
//
//        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
//        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//
//        if(heightMode != MeasureSpec.EXACTLY && item != null){
//
//        }
//        setMeasuredDimension(widthSize, heightSize);
//    }

    public void createViews(NewsItem item){
        LogUtil.i(tag, "createViews");
        if(item == null){
            return;
        }
        this.item = item;
        if(TextUtils.isEmpty(item.getImgUrl())){
            iv_cover.setVisibility(View.GONE);
        }else{
            ImageLoader.getInstance(mContext).bindBitmap(item.getImgUrl(), iv_cover);
        }
        tv_title.setText(item.getTitle());
        tv_content.setText(item.getContent());
        tv_date.setText(item.getDate());
//        invalidate();
    }
}
