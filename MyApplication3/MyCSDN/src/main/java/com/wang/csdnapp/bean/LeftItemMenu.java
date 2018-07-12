package com.wang.csdnapp.bean;


/**
 * Created by songbinwang on 2016/10/20.
 */

public class LeftItemMenu {

    private int icon;
    private int icon_focus;
    private String name;
    private boolean isSelected = false;

    public LeftItemMenu(int icon, String name) {
        this.icon = icon;
        this.name = name;
    }

    public LeftItemMenu(int icon, int icon_focus, String name) {
        this.icon = icon;
        this.name = name;
        this.icon_focus = icon_focus;
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

    public int getIcon_focus() {
        return icon_focus;
    }

    public void setIcon_focus(int icon_focus) {
        this.icon_focus = icon_focus;
    }

    public void setIsSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean getIsSelected(){
        return isSelected;
    }

    @Override
    public String toString() {
        return "name:"+name + ",icon" + icon + ",foucus:" + icon_focus;
    }
}
