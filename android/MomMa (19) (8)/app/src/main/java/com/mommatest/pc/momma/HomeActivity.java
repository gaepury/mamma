package com.mommatest.pc.momma;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mommatest.pc.momma.Model.DetailMilkResult;
import com.mommatest.pc.momma.Model.HomeRank;
import com.mommatest.pc.momma.application.ApplicationController;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends Fragment {

    private ImageView othermom,manyreview;
    private LinearLayout othermomlayout1, othermomlayout2, othermomlayout3;
    private LinearLayout manyreviewlayout1, manyreviewlayout2, manyreviewlayout3;

    ImageView search_Drymilk_IV1, search_Drymilk_IV2,  search_Drymilk_IV3, r_m_img1, r_m_img2, r_m_img3;
    TextView search_Company_TV1, search_Drymilk_TV1, search_Company_TV2, search_Drymilk_TV2,  search_Company_TV3, search_Drymilk_TV3, r_m_name1, r_m_name2, r_m_name3;
    HomeRank.ResultData datas;
    DetailMilkResult.ResultData data;

    public Activity activity;


//    public HomeActivity() {
//        super();
//    }

    public HomeActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home, null);


        othermom = (ImageView) view.findViewById(R.id.othermom);
        manyreview = (ImageView) view.findViewById(R.id.manyreview);
        othermomlayout1 = (LinearLayout) view.findViewById(R.id.othermomlayout1);
        othermomlayout2 = (LinearLayout) view.findViewById(R.id.othermomlayout2);
        othermomlayout3 = (LinearLayout) view.findViewById(R.id.othermomlayout3);
        manyreviewlayout1 = (LinearLayout) view.findViewById(R.id.manyreviewlayout1);
        manyreviewlayout2 = (LinearLayout) view.findViewById(R.id.manyreviewlayout2);
        manyreviewlayout3 = (LinearLayout) view.findViewById(R.id.manyreviewlayout3);

        search_Drymilk_IV1 = (ImageView)view.findViewById(R.id.Search_Drymilk_IV);
        search_Company_TV1 = (TextView)view.findViewById(R.id.Search_Company_TV);
        search_Drymilk_IV2 = (ImageView)view.findViewById(R.id.Search_Drymilk_IV2);
        search_Company_TV2 = (TextView)view.findViewById(R.id.Search_Company_TV2);
        search_Drymilk_IV3 = (ImageView)view.findViewById(R.id.Search_Drymilk_IV3);
        search_Company_TV3 = (TextView)view.findViewById(R.id.Search_Company_TV3);


        search_Drymilk_TV1 = (TextView)view.findViewById(R.id.Search_Drymilk_TV1);
        search_Drymilk_TV2 = (TextView)view.findViewById(R.id.Search_Drymilk_TV2);
        search_Drymilk_TV3 = (TextView)view.findViewById(R.id.Search_Drymilk_TV3);


        r_m_img1 = (ImageView) view.findViewById(R.id.home_drymilk_1);
        r_m_img2 = (ImageView)view.findViewById(R.id.home_drymilk_2);
        r_m_img3 = (ImageView)view.findViewById(R.id.home_drymilk_3);
        r_m_name1 = (TextView)view.findViewById(R.id.Review_Drymilk_TV1);
        r_m_name2 = (TextView)view.findViewById(R.id.Review_Drymilk_TV2);
        r_m_name3 = (TextView)view.findViewById(R.id.Review_Drymilk_TV3);

        getDatas();

        othermom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent othermomreview = new Intent(activity, RecyclerSearch.class);
                othermomreview.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                othermomreview.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(othermomreview);
            }
        });

        manyreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent manyreviewintent = new Intent(activity, RecyclerReview.class);
                manyreviewintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                manyreviewintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                manyreviewintent.putExtra("index",0);
                startActivity(manyreviewintent);
            }
        });


        othermomlayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ol1 = new Intent(activity, DetailDrymilk.class);
                ol1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                ol1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ol1.putExtra("milk_name", search_Drymilk_TV1.getText().toString());
                startActivity(ol1);
            }
        });
        othermomlayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ol2 = new Intent(activity, DetailDrymilk.class);
                ol2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                ol2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ol2.putExtra("milk_name", search_Drymilk_TV2.getText().toString());
                startActivity(ol2);
            }
        });
        othermomlayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ol3 = new Intent(activity, DetailDrymilk.class);
                ol3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                ol3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ol3.putExtra("milk_name", search_Drymilk_TV3.getText().toString());
                startActivity(ol3);
            }
        });
        manyreviewlayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mrl1 = new Intent(activity, DetailDrymilk.class);
                mrl1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mrl1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mrl1.putExtra("milk_name", r_m_name1.getText().toString());
                startActivity(mrl1);
            }
        });
        manyreviewlayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mrl2 = new Intent(activity, DetailDrymilk.class);
                mrl2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mrl2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mrl2.putExtra("milk_name", r_m_name2.getText().toString());
                startActivity(mrl2);
            }
        });
        manyreviewlayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mrl3 = new Intent(activity, DetailDrymilk.class);
                mrl3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mrl3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mrl3.putExtra("milk_name", r_m_name3.getText().toString());
                startActivity(mrl3);
            }
        });

        return view;
    }

    public void getDatas(){

        Call<HomeRank> home_rank = ApplicationController.getInstance().getNetworkService().getHomeRank();
        home_rank.enqueue(new Callback<HomeRank>() {
            @Override
            public void onResponse(Call<HomeRank> call, Response<HomeRank> response) {
                if(response.isSuccessful()){
                    datas = response.body().result;
                    updateView();
                }
            }
            @Override
            public void onFailure(Call<HomeRank> call, Throwable t) {

            }
        });

    }
    public void updateView(){
        Glide.with(getActivity())
                .load(datas.searchrank.get(0).milk_image)
                .into(search_Drymilk_IV1);

        search_Company_TV1.setText(datas.searchrank.get(0).milk_company);
        search_Drymilk_TV1.setText(datas.searchrank.get(0).milk_name);

        Glide.with(getActivity())
                .load(datas.searchrank.get(1).milk_image)
                .into(search_Drymilk_IV2);

        search_Company_TV2.setText(datas.searchrank.get(1).milk_company);
        search_Drymilk_TV2.setText(datas.searchrank.get(1).milk_name);

        Glide.with(getActivity())
                .load(datas.searchrank.get(2).milk_image)
                .into(search_Drymilk_IV3);

        search_Company_TV3.setText(datas.searchrank.get(2).milk_company);
        search_Drymilk_TV3.setText(datas.searchrank.get(2).milk_name);



        Glide.with(getActivity())
                .load(datas.reviewRank.get(0).milk_image)
                .into(r_m_img1);

        r_m_name1.setText(datas.reviewRank.get(0).milk_name);
        Glide.with(getActivity())
                .load(datas.reviewRank.get(1).milk_image)
                .into(r_m_img2);

        r_m_name2.setText(datas.reviewRank.get(1).milk_name);

        Glide.with(getActivity())
                .load(datas.reviewRank.get(2).milk_image)
                .into(r_m_img3);

        r_m_name3.setText(datas.reviewRank.get(2).milk_name);


    }


}