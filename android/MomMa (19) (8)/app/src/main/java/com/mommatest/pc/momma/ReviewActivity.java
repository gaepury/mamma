package com.mommatest.pc.momma;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mommatest.pc.momma.Model.HomeRank;
import com.mommatest.pc.momma.Model.ReviewViewResult;
import com.mommatest.pc.momma.application.ApplicationController;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class ReviewActivity extends Fragment {

    private FloatingActionButton reviewbtn;
    private Button manyreviewbut;
    private LinearLayout manyreviewlayout4, manyreviewlayout5, manyreviewlayout6;
    private ImageView r_m_img1, r_m_img2, r_m_img3;
    private TextView r_m_name1, r_m_name2, r_m_name3;

    Button month, worry1, worry2, worry3;
    TextView nickname1, nickname2, milkname, reviewviewcontent;
    ImageView milkimg;
    RatingBar reviewrating;
    LinearLayout similarreview;


    HomeRank.ResultData datas;
    ReviewViewResult.ResultData data;

    SharedPreferences setting;
    SharedPreferences.Editor editor;
    Context context;
    String nickname;

    public Activity activity;

    public ReviewActivity(Activity activity) {
        this.activity = activity;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.review, null);

        setting = activity.getSharedPreferences("setting", MODE_PRIVATE);
        editor = setting.edit();
        nickname = setting.getString("p_nickname",nickname);

        Log.i("닉넴",nickname);

        similarreview = (LinearLayout)view.findViewById(R.id.similarreview);
        month = (Button)view.findViewById(R.id.reviewviewmonth);
        worry1 = (Button)view.findViewById(R.id.reviewviewworry1);
        worry2 = (Button)view.findViewById(R.id.reviewviewworry2);
        worry3 = (Button)view.findViewById(R.id.reviewviewworry3);

        nickname1 = (TextView)view.findViewById(R.id.reviewviewnickname1);
        nickname2 = (TextView)view.findViewById(R.id.reviewviewnickname2);
        milkname = (TextView)view.findViewById(R.id.reviewviewname);
        reviewviewcontent = (TextView)view.findViewById(R.id.reviewviewcontent);
        milkimg = (ImageView)view.findViewById(R.id.reviewviewimg);
        reviewrating = (RatingBar)view.findViewById(R.id.reviewviewrating);

        r_m_img1 = (ImageView) view.findViewById(R.id.RevTab_Drymilk_IV1);
        r_m_img2 = (ImageView) view.findViewById(R.id.RevTab_Drymilk_IV2);
        r_m_img3 = (ImageView) view.findViewById(R.id.RevTab_Drymilk_IV3);
        r_m_name1 = (TextView) view.findViewById(R.id.RevTab_Drymilk_TV1);
        r_m_name2 = (TextView) view.findViewById(R.id.RevTab_Drymilk_TV2);
        r_m_name3 = (TextView) view.findViewById(R.id.RevTab_Drymilk_TV3);

        manyreviewbut = (Button) view.findViewById(R.id.manyreviewbut);
        manyreviewlayout4 = (LinearLayout) view.findViewById(R.id.manyreviewlayout4);
        manyreviewlayout5 = (LinearLayout) view.findViewById(R.id.manyreviewlayout5);
        manyreviewlayout6 = (LinearLayout) view.findViewById(R.id.manyreviewlayout6);

        getDatas();
        similarreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detailreview_intent = new Intent(getActivity(), DetailReview.class);
                detailreview_intent.putExtra("milk_name", data.similar.milk_name);
                detailreview_intent.putExtra("review_id", data.similar.review_id);
                detailreview_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                detailreview_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(detailreview_intent);
            }
        });



        manyreviewbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent manyreviewintent = new Intent(activity, RecyclerReview.class);
                manyreviewintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                manyreviewintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                manyreviewintent.putExtra("index", 1);
                startActivity(manyreviewintent);
            }
        });

        manyreviewlayout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detaildrymilk = new Intent(activity, DetailDrymilk.class);
                detaildrymilk.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                detaildrymilk.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                detaildrymilk.putExtra("milk_name",r_m_name1.getText().toString());
                startActivity(detaildrymilk);
            }
        });

        manyreviewlayout5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detaildrymilk = new Intent(activity, DetailDrymilk.class);
                detaildrymilk.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                detaildrymilk.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                detaildrymilk.putExtra("milk_name",r_m_name2.getText().toString());
                startActivity(detaildrymilk);
            }
        });

        manyreviewlayout6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detaildrymilk = new Intent(activity, DetailDrymilk.class);
                detaildrymilk.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                detaildrymilk.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                detaildrymilk.putExtra("milk_name", r_m_name3.getText().toString());
                startActivity(detaildrymilk);
            }
        });




//        reviewbtn = (FloatingActionButton)view.findViewById(R.id.reviewbtn);
//
//        reviewbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent reviewintent = new Intent(activity, create_review_activity.class);
//                reviewintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                reviewintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(reviewintent);
//            }
//        });

        return view;
    }
    public void getDatas(){


        Call<ReviewViewResult> reviewViewResultCall = ApplicationController.getInstance().getNetworkService().getReviewViewResult("333");
        Log.i("닉넴",nickname);
        Log.i("ㅇㅇ","성공1");
        reviewViewResultCall.enqueue(new Callback<ReviewViewResult>() {
            @Override
            public void onResponse(Call<ReviewViewResult> call, Response<ReviewViewResult> response) {
                Log.i("ㅇㅇ","성공2");
                if(response.isSuccessful()){
                    Log.i("ㅇㅇ","성공3");
                    data = response.body().result;
                    updateViewSimiar();
                    //  updateView();
                }
            }
            @Override
            public void onFailure(Call<ReviewViewResult> call, Throwable t) {
                Log.i("ㅇㅇ","실패~");

            }
        });

    }

    public void updateViewSimiar() {
        Log.i("ㅇㅇ","성공3");
        Glide.with(getActivity())
                .load(data.similar.milk_image)
                .into(milkimg);

        nickname1.setText(nickname);
        nickname2.setText(data.similar.nickname);
        milkname.setText(data.similar.milk_name);
        reviewviewcontent.setText(data.similar.title);
        month.setText(data.similar.age+" 개월");
        reviewrating.setRating(data.similar.review_grade);

        if(data.similar.worry.get(0).equals("알레르기"))
            worry1.setVisibility(View.VISIBLE);
        if(data.similar.worry.get(1).equals("아토피"))
            worry1.setVisibility(View.VISIBLE);
        if(data.similar.worry.get(2).equals("배변"))
            worry1.setVisibility(View.VISIBLE);

        Glide.with(getActivity())
                .load(data.reviewrank.get(0).milk_image)
                .into(r_m_img1);

        r_m_name1.setText(data.reviewrank.get(0).milk_name);

        Glide.with(getActivity())
                .load(data.reviewrank.get(1).milk_image)
                .into(r_m_img2);

        r_m_name2.setText(data.reviewrank.get(1).milk_name);

        Glide.with(getActivity())
                .load(data.reviewrank.get(2).milk_image)
                .into(r_m_img3);

        r_m_name3.setText(data.reviewrank.get(2).milk_name);

    }
//
//    public void updateView() {
//        Glide.with(getActivity())
//                .load(data.reviewrank.get(0).milk_image)
//                .into(r_m_img1);
//
//        r_m_name1.setText(data.reviewrank.get(0).milk_name);
//        Glide.with(getActivity())
//                .load(data.reviewrank.get(1).milk_image)
//                .into(r_m_img2);
//
//        r_m_name2.setText(data.reviewrank.get(1).milk_name);
//
//        Glide.with(getActivity())
//                .load(data.reviewrank.get(2).milk_image)
//                .into(r_m_img3);
//
//        r_m_name3.setText(data.reviewrank.get(2).milk_name);
//    }
}