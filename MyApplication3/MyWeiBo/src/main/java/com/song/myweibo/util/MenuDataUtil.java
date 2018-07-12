package com.song.myweibo.util;

import com.song.myweibo.R;
import com.song.myweibo.entity.LeftItemMenu;

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
}
