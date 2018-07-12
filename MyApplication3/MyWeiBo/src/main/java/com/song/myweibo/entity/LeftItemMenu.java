package com.song.myweibo.entity;


/**
 * Created by songbinwang on 2016/10/20.
 */

public class LeftItemMenu {

    private int icon;

    private String name;

    public LeftItemMenu(int icon, String name) {
        this.icon = icon;
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "name:"+name + ",icon" + icon;
    }
}
