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
import com.wang.csdnapp.util.MenuDataUtil;

import java.util.List;

/**
 * Created by songbinwang on 2016/10/20.
 */

public class LeftItemAdapter extends BaseAdapter{

    private Context context;
    LayoutInflater mInflater;
    private List<LeftItemMenu> leftItemMenus;

    public LeftItemAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        leftItemMenus = MenuDataUtil.getLeftMenus();
    }

    @Override
    public int getCount() {
        return leftItemMenus == null? 0:leftItemMenus.size();
    }

    @Override
    public Object getItem(int i) {
        return leftItemMenus.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.item_left_menu_layout, null);
            viewHolder = new ViewHolder(convertView);
        }else{
            viewHolder = ViewHolder.getFromView(convertView);
        }
        LeftItemMenu menu = leftItemMenus.get(position);
        viewHolder.item_left_view_img.setImageResource(menu.getIcon());
        viewHolder.item_left_view_title.setText(menu.getName());
        return convertView;
    }

    static class ViewHolder{
        ImageView item_left_view_img;
        TextView item_left_view_title;

        ViewHolder(View view){
            item_left_view_img = (ImageView) view.findViewById(R.id.item_left_view_img);
            item_left_view_title = (TextView) view.findViewById(R.id.item_left_view_title);
        }

        static ViewHolder getFromView(View view){
            if(view.getTag() instanceof  ViewHolder){
                return (ViewHolder) view.getTag();
            }
            return new ViewHolder(view);
        }
    }

}
