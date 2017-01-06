package com.mommatest.pc.momma;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

public class ViewHolder {

    public ImageView iconImageView;
    public ImageView imageView;
    public LinearLayout gradelayout;
    public ImageView img;
    public TextView num, drymilkname, level;
    public Float Raiting;
    public Button mypagebtn;



    public ImageView getIconImageView() {
        return iconImageView;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public LinearLayout getGradelayout() {
        return gradelayout;
    }


}

