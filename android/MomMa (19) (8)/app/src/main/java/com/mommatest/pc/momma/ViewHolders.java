package com.mommatest.pc.momma;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class ViewHolders extends RecyclerView.ViewHolder {


    public ImageView ReviewLevel_IV;
    public TextView User_TV;
    public TextView Title_TV;
    public RatingBar Rating_RB;
    public ImageView iconImageView;
    public ImageView imageView;
    public ImageView img;
    public TextView num, drymilkname, level;
    public RatingBar Raiting;
    public Button mypagebtn;
    //RecyclerReview
    public TextView Num_R_TV;
    public ImageView Drymilk_R_IV;
    public ImageView Level_R_IV;
    public TextView Drymilk_R_TV;
    public TextView Level_R_TV;
    public RatingBar Rating_R_RB;

    //RecyclerSearch
    public TextView Num_S_TV;
    public ImageView Drymilk_S_IV;
    public ImageView Level_S_IV;
    public TextView Drymilk_S_TV;
    public TextView Level_S_TV;
    public RatingBar Rating_S_RB;

    //DetailReview
    public TextView Num_DR_TV;
    public TextView Title_DR_TV;
    public TextView User_DR_TV;
    public RatingBar Rating_DR_RB;

    //IngredientList
    public TextView Name_IL_TV;
    public TextView NUM_IL_TV;

    //Mypage
    public ImageView Drymilk_MP1_IV;
    public ImageView Drymilk_MP2_IV;
    public ImageView Drymilk_MP3_IV;
    public TextView Drymilk_MP1_TV;
    public TextView Drymilk_MP2_TV;
    public TextView Drymilk_MP3_TV;

    //Favorite
    public ImageView Drymilk_FV1_IV;
    public ImageView Drymilk_FV2_IV;
    public ImageView Drymilk_FV3_IV;
    public TextView Drymilk_FV1_TV;
    public TextView Drymilk_FV2_TV;
    public TextView Drymilk_FV3_TV;







    public ImageView getIconImageView() {
        return iconImageView;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public ViewHolders(View itemView) {
        super(itemView);

        ReviewLevel_IV = (ImageView) itemView.findViewById(R.id.Listnum_IV);
        User_TV = (TextView) itemView.findViewById(R.id.oneline_TV);
        Title_TV = (TextView) itemView.findViewById(R.id.user_TV);
        Rating_RB = (RatingBar) itemView.findViewById(R.id.ReviewGrade_RB);

//        //단계별 분유
//        img = (ImageView) itemView.findViewById(R.id.levelimg);
//        num = (TextView) itemView.findViewById(R.id.numT);
//        drymilkname = (TextView) itemView.findViewById(R.id.dmntv);
//        level = (TextView) itemView.findViewById(R.id.leveltv);
//        Raiting = (RatingBar) itemView.findViewById(R.id.ratingbar);

        //RecyclerReview �닽�옄, 遺꾩쑀�궗吏�, 遺꾩쑀�젅踰⑥궗吏�, 遺꾩쑀�씠由꾪뀓�뒪�듃, 遺꾩쑀�젅踰⑦뀓�뒪�듃, �젅�씠�똿諛�
        Num_R_TV = (TextView) itemView.findViewById(R.id.ReviewRecycle_Num_TV);
        Drymilk_R_IV = (ImageView) itemView.findViewById(R.id.ReviewRecycle_Drymilk_IV);
        Drymilk_R_TV = (TextView) itemView.findViewById(R.id.ReviewRecycle_Drymilk_TV);
        Level_R_TV = (TextView) itemView.findViewById(R.id.ReviewRecycle_Level_TV);
        Rating_R_RB = (RatingBar) itemView.findViewById(R.id.ReviewRecycleGrade_RB);

        //RecyclerSearch �닽�옄, 遺꾩쑀�궗吏�, 遺꾩쑀�젅踰⑥궗吏�, 遺꾩쑀�씠由꾪뀓�뒪�듃, 遺꾩쑀�젅踰⑦뀓�뒪�듃, �젅�씠�똿諛�
        Num_S_TV = (TextView) itemView.findViewById(R.id.SearchRecycle_Num_TV);
        Drymilk_S_IV = (ImageView) itemView.findViewById(R.id.SearchRecycle_Drymilk_IV);
        Level_S_IV = (ImageView) itemView.findViewById(R.id.SearchRecycle_Level_IV);
        Drymilk_S_TV = (TextView) itemView.findViewById(R.id.SearchRecycle_Drymilk_TV);
        Level_S_TV = (TextView) itemView.findViewById(R.id.SearchRecycle_Level_TV);
        Rating_S_RB = (RatingBar) itemView.findViewById(R.id.SearchRecycleGrade_RB);

        //DetailReview
//        Num_DR_TV = (TextView) itemView.findViewById(R.id.detailNum_TV);
//        Title_DR_TV = (TextView) itemView.findViewById(R.id.detailOneline_TV);
//        User_DR_TV = (TextView) itemView.findViewById(R.id.detailUser_TV);
//        Rating_DR_RB = (RatingBar) itemView.findViewById(R.id.detailGrade_RB);

        //IngredientList
        Name_IL_TV = (TextView) itemView.findViewById(R.id.IngredientName_TV);
        NUM_IL_TV = (TextView) itemView.findViewById(R.id.ingredientNum_TV);


    }
}

