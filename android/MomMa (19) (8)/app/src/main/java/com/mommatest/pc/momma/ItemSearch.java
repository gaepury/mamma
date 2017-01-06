package com.mommatest.pc.momma;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ItemSearch {
    String num;
    int image;
    int imagelevel;
    String name;
    String level;
    float rate;

    public ItemSearch(String num, int image, int imagelevel, String name, String level, float rate) {
        this.num = num;
        this.image = image;
        this.imagelevel = imagelevel;
        this.name = name;
        this.level = level;
        this.rate = rate;
    }
}