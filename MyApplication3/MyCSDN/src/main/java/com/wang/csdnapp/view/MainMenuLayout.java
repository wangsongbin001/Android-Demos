package com.wang.csdnapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.wang.csdnapp.R;
import com.wang.csdnapp.bean.LeftItemMenu;
import com.wang.csdnapp.ui.adapter.LeftMenuAdapter;
import com.wang.csdnapp.util.MenuDataUtil;
import com.wang.csdnapp.util.ToastUtil;

import java.util.List;

/**
 * Created by SongbinWang on 2017/6/12.
 */

public class MainMenuLayout extends LinearLayout {

    private CircleImageView iv_account;
    private TextView tv_name, tv_c_coin;
    private ListView lv_left_menu;
    private ImageView iv_night_day;
    private TextView tv_night_day;
    private LinearLayout ll_night_day;
    private Context mContext;

    //显示模式，夜间模式，日间模式
    enum ShowMode {
        day, night
    }

    public static interface OnMenuItemClickListener{
        void onMenuItemClick(int item);
    }
    OnMenuItemClickListener mItemClickListener;
    public void setOnMenuItemClickListener(OnMenuItemClickListener mItemClickListener){
        this.mItemClickListener = mItemClickListener;
    }

    ShowMode showMode = ShowMode.day;

    private List<LeftItemMenu> listMenus;
    private LeftMenuAdapter mAdapter;

    public MainMenuLayout(Context context) {
        this(context, null);
    }

    public MainMenuLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MainMenuLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        listMenus = MenuDataUtil.getLeftMenus2();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //用户信息
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_c_coin = (TextView) findViewById(R.id.tv_c_coin);
        iv_account = (CircleImageView) findViewById(R.id.circle_iv_account);
        //内容菜单
        lv_left_menu = (ListView) findViewById(R.id.lv_left_main);
        mAdapter = new LeftMenuAdapter(mContext, listMenus);
        lv_left_menu.setAdapter(mAdapter);
        updateItemSelected(0);
        //夜间模式
        iv_night_day = (ImageView) findViewById(R.id.iv_night_day);
        tv_night_day = (TextView) findViewById(R.id.tv_night_day);
        ll_night_day = (LinearLayout) findViewById(R.id.ll_night_day);
        //注册监听
        registerListeners();
    }

    /**
     * 注册监听
     */
    private void registerListeners() {
        iv_account.setOnClickListener(onClicker);
        ll_night_day.setOnClickListener(onClicker);
        lv_left_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                updateItemSelected(position);
                ToastUtil.toast(listMenus.get(position).getName());
                if(mItemClickListener != null){
                    mItemClickListener.onMenuItemClick(position);
                }
            }
        });
    }

    View.OnClickListener onClicker = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.ll_night_day:
                    if (showMode == ShowMode.day) {
                        showMode = ShowMode.night;
                        iv_night_day.setImageResource(R.drawable.day);
                        tv_night_day.setText("日间模式");
                    } else {
                        showMode = ShowMode.day;
                        iv_night_day.setImageResource(R.drawable.night);
                        tv_night_day.setText("夜间模式");
                    }
                    break;
                case R.id.circle_iv_account:
                    lv_left_menu.setSelection(lv_left_menu.getCount() - 1);
                    break;
            }
        }
    };

    /**
     * 更新item UI
     * @param position
     */
    private void updateItemSelected(int position) {
        if (listMenus == null || position >= listMenus.size()) {
            return;
        }
        for (int i = 0; i < listMenus.size(); i++) {
            listMenus.get(i).setIsSelected(false);
        }
        listMenus.get(position).setIsSelected(true);
        if(mAdapter != null){
            mAdapter.notifyDataSetChanged();
        }
    }

}
