package com.wang.csdnapp.util;


import com.wang.csdnapp.R;
import com.wang.csdnapp.bean.LeftItemMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songbinwang on 2016/10/20.
 */

public class MenuDataUtil {

    public static List<LeftItemMenu> getLeftMenus(){
        List<LeftItemMenu> menus = new ArrayList<LeftItemMenu>();
        menus.add(new LeftItemMenu(R.drawable.icon_zhanghaoxinxi,"账号信息"));
        menus.add(new LeftItemMenu(R.drawable.icon_wodeguanzhu,"我的关注"));
        menus.add(new LeftItemMenu(R.drawable.icon_shoucang,"我的收藏"));
        menus.add(new LeftItemMenu(R.drawable.icon_yijianfankui,"意见反馈"));
        menus.add(new LeftItemMenu(R.drawable.icon_shezhi,"设置"));
        return  menus;
    }

    public static List<LeftItemMenu> getLeftMenus2(){
        List<LeftItemMenu> menus = new ArrayList<>();
        menus.add(new LeftItemMenu(R.drawable.read, R.drawable.read_white, "头条"));
        menus.add(new LeftItemMenu(R.drawable.blog, R.drawable.blog_white, "博客"));
        menus.add(new LeftItemMenu(R.drawable.ask, R.drawable.ask_white, "问答"));
        menus.add(new LeftItemMenu(R.drawable.bbs, R.drawable.bbs_white, "论坛"));
        menus.add(new LeftItemMenu(R.drawable.my, R.drawable.my_white, "我的"));
        return menus;
    }
}
