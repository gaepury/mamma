package com.mommatest.pc.momma;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.DrawableUtils;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by uhees on 2016-12-29.
 */

public class GradeLayoutItem {
    private Drawable drymilk1, drymilk2, drymilk3, drymilk4;
    private String company1,company2,company3,company4;
    private String name1, name2, name3,name4;


    public Drawable getDrymilk1() {
        return drymilk1;
    }

    public Drawable getDrymilk2() {
        return drymilk2;
    }

    public Drawable getDrymilk3() {
        return drymilk3;
    }

    public Drawable getDrymilk4() {
        return drymilk4;
    }

    public String getCompany1() {
        return company1;
    }

    public String getCompany2() {
        return company2;
    }

    public String getCompany3() {
        return company3;
    }

    public String getCompany4() {
        return company4;
    }

    public String getName1() {
        return name1;
    }

    public String getName2() {
        return name2;
    }

    public String getName3() {
        return name3;
    }

    public String getName4() {
        return name4;
    }

    public void setDrymilk1(Drawable img, String company, String name){
       drymilk1 = img;
       company1 = company;
       name1 = name;
   }
    public void setDrymilk2(Drawable img, String company, String name){
        drymilk2 = img;
        company2 = company;
        name2 = name;
    }
    public void setDrymilk3(Drawable img, String company, String name){
        drymilk3 = img;
        company3 = company;
        name3 = name;
    }
    public void setDrymilk4(Drawable img, String company, String name){
        drymilk4 = img;
        company4 = company;
        name4 = name;
    }



}