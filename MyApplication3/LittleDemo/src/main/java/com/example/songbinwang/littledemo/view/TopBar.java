package com.example.songbinwang.littledemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.songbinwang.littledemo.R;

/**
 * Created by songbinwang on 2016/5/4.
 */
public class TopBar extends RelativeLayout{

    private Button mRightBtn;
    private ImageView mLeftImg, mRightImg;
    private TextView mTitleTv;

    private String mTitle_Text;

    public static final String Tag = "wangTopbar";

    public TopBar(Context context){
        this(context, null);
    }

    public TopBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TopBar(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public TopBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initFromAttribute(context, attrs, defStyleAttr, defStyleRes);
        addMyChildView(context);
        registerListener();
    }

    private void initFromAttribute(Context context,AttributeSet attrs,int defStyleAttr, int defStyleRes){
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.TopBar, defStyleAttr,defStyleRes);
        mTitle_Text = mTypedArray.getString(R.styleable.TopBar_title_text);
        mTypedArray.recycle();
    };

    private void addMyChildView(Context context){
        mTitleTv = new TextView(context);
        mTitleTv.setTextColor(Color.WHITE);
        mTitleTv.setText(mTitle_Text);
        mTitleTv.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 5, context.getResources().getDisplayMetrics()));
        LayoutParams mTitleLp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        mTitleLp.addRule(RelativeLayout.CENTER_IN_PARENT);
        addView(mTitleTv, mTitleLp);

        mLeftImg = new ImageView(context);
        mLeftImg.setImageResource(R.drawable.action_bar_back_icon);
        LayoutParams mLeftImgLp = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT);
        mLeftImgLp.leftMargin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, context.getResources().getDisplayMetrics());
        mLeftImgLp.addRule(RelativeLayout.ALIGN_PARENT_LEFT | RelativeLayout.CENTER_VERTICAL);
        addView(mLeftImg, mLeftImgLp);

        mRightBtn = new Button(context);
        mRightBtn.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 5, context.getResources().getDisplayMetrics()));
        mRightBtn.setText("分享");
        mRightBtn.setTextColor(Color.WHITE);
        mRightBtn.setBackgroundColor(Color.TRANSPARENT);
        LayoutParams mRightLp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        mRightLp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        mRightLp.addRule(RelativeLayout.CENTER_VERTICAL);
        addView(mRightBtn, mRightLp);
        mRightBtn.setVisibility(View.GONE);

        mRightImg = new ImageView(context);
        mRightImg.setImageResource(R.drawable.actionbar_more_img);
        LayoutParams mRightImgLp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        mRightImgLp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        mRightImgLp.addRule(RelativeLayout.CENTER_VERTICAL);
        mRightImgLp.rightMargin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, context.getResources().getDisplayMetrics());
        addView(mRightImg, mRightImgLp);
        mRightImg.setVisibility(View.GONE);
    }

    private void registerListener(){
        mLeftImg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(mOnTopbarClickListener != null){
                    mOnTopbarClickListener.onLeftClick(v);
                }
            }
        });

        mRightBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(mOnTopbarClickListener != null){
                    mOnTopbarClickListener.onRightClick(v);
                }
            }
        });

        mRightImg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(mOnTopbarClickListener != null){
                    mOnTopbarClickListener.onRightClick(v);
                }
            }
        });
    }

    public void setRightBtnVisibility(int visibility){
        mRightBtn.setVisibility(visibility);
    }

    public void setRightImgVisibility(int visibility){
        mRightImg.setVisibility(visibility);
    }

    public Button getRightBtn(){
        return mRightBtn;
    }

    public ImageView getmRightImg(){
        return mRightImg;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
    }

    OnTopbarClickListener mOnTopbarClickListener;

    public void setOnTopbarClickListener(OnTopbarClickListener mOnTopbarClickListener){
        this.mOnTopbarClickListener = mOnTopbarClickListener;
    }

    public interface OnTopbarClickListener{

        public void onLeftClick(View view);

        public void onRightClick(View view);
    }
}
