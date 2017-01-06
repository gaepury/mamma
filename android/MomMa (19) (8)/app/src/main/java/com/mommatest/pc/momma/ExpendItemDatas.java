package com.mommatest.pc.momma;

import android.view.View;
import android.widget.LinearLayout;

public class ExpendItemDatas {
    private String name;
    private int ImageResource;
    private String group;
    public Boolean ClickCheck;
    public LinearLayout gradelayout;


    public int getImageResource() {
        return ImageResource;
    }

    public ExpendItemDatas(String name, String group, int ImageResource) {
        this.name = name;
        this.group = group;
        this.ImageResource = ImageResource;
        this.ClickCheck  = false;
    }
    public ExpendItemDatas(LinearLayout gradelayout, String group, int ImageResource){
        this.group = group;
        this.ImageResource = ImageResource;
        this.ClickCheck  = false;
        this.gradelayout = gradelayout;
    }


    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public ExpendItemDatas(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LinearLayout setGradelayout(LinearLayout gradelayout) { return this.gradelayout = gradelayout;}
}