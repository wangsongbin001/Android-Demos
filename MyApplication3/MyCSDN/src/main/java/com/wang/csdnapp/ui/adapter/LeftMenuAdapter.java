package com.wang.csdnapp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wang.csdnapp.R;
import com.wang.csdnapp.bean.LeftItemMenu;
import com.wang.csdnapp.util.LogUtil;
import com.wang.csdnapp.util.MenuDataUtil;

import java.util.List;

/**
 * Created by SongbinWang on 2017/6/12.
 */

public class LeftMenuAdapter extends BaseAdapter{

    private static final String TAG = "LeftMenu";
    private List<LeftItemMenu> menuList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public LeftMenuAdapter(Context context, List<LeftItemMenu> itemMenus) {
        super();
        menuList = itemMenus;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return menuList.size();
    }

    @Override
    public Object getItem(int position) {
        return menuList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder mHolder;
        if(convertView == null){
            convertView = mLayoutInflater.inflate(R.layout.item_menu, null);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        }else{
            mHolder = (ViewHolder) convertView.getTag();
        }
        final LeftItemMenu itemMenu = menuList.get(position);
        mHolder.tv_title.setText(itemMenu.getName());
        if(itemMenu.getIsSelected()){
            mHolder.tv_title.setTextColor(mContext.getResources().getColor(R.color.menu_txt_white));
            mHolder.iv_icon.setImageResource(itemMenu.getIcon_focus());
            convertView.setBackgroundColor(mContext.getResources().getColor(R.color.menu_item_select));
        }else{
            mHolder.tv_title.setTextColor(mContext.getResources().getColor(R.color.menu_txt_gray));
            mHolder.iv_icon.setImageResource(itemMenu.getIcon());
            convertView.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
        }
        return convertView;
    }

    static class ViewHolder{
        ImageView iv_icon;
        TextView tv_title;

        public ViewHolder(View root) {
            iv_icon = (ImageView) root.findViewById(R.id.iv_icon);
            tv_title = (TextView) root.findViewById(R.id.tv_title);
        }
    }
}
