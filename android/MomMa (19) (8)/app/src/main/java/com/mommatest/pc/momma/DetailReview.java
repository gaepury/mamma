package com.mommatest.pc.momma;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mommatest.pc.momma.Model.DetailReviewResult;
import com.mommatest.pc.momma.Model.ReviewBResult;
import com.mommatest.pc.momma.Model.ReviewGResult;
import com.mommatest.pc.momma.application.ApplicationController;
import com.mommatest.pc.momma.network.NetworkService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by uhees on 2017-01-02.
 */

public class DetailReview extends AppCompatActivity {
    String milknm, review_id, p_email, nickname;
    TextView dtmilkname,dtcompanyname, dtgood, dtbad, dttip, goodnum, badnum, one_line, dt_review_gohome;
    RatingBar dtratingBar;
    NetworkService networkService;
    DetailReviewResult.ResultData data;
    ImageView dtworry1, dtworry2, dtworry3, dtmilkimg, dt_review_searchgo;
    Button dtgoodbut, dtbadbut, dt_review_back, dt_review_mypage;
    EditText dt_review_search;
    ReviewGResult.ResultData Gdata;
    ReviewBResult.ResultData Bdata;

    SharedPreferences setting;
    SharedPreferences.Editor editor;

    boolean gclick_set;
    boolean bclick_set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_review);
        networkService = ApplicationController.getInstance().getNetworkService();

        setting = getSharedPreferences("setting", MODE_PRIVATE);
        editor = setting.edit();
        p_email = setting.getString("ID", "");
        nickname = setting.getString("p_nickname","");

        final Intent detail_review = getIntent();
        milknm = detail_review.getExtras().getString("milk_name");
        review_id = detail_review.getExtras().getString("review_id");

        dt_review_back =(Button)findViewById(R.id.dt_review_back);
        dt_review_gohome = (TextView)findViewById(R.id.dt_review_gohome);
        dt_review_mypage = (Button)findViewById(R.id.dt_review_mypage);
        dt_review_search = (EditText)findViewById(R.id.dt_review_search);
        dt_review_searchgo = (ImageView) findViewById(R.id.dt_review_searchgo);

        one_line = (TextView) findViewById(R.id.oneline);
        dtmilkimg = (ImageView) findViewById(R.id.DetailreviewDrymilk_IV);
        dtcompanyname = (TextView) findViewById(R.id.detailreviewmanu);
        dtmilkname = (TextView) findViewById(R.id.detalireview_name);
        goodnum = (TextView) findViewById(R.id.goodnum);
        badnum = (TextView) findViewById(R.id.badnum);
        dtgood = (TextView) findViewById(R.id.dtgood);
        dtbad = (TextView) findViewById(R.id.dtbad);
        dttip = (TextView) findViewById(R.id.dttip);
        dtratingBar = (RatingBar) findViewById(R.id.dtreview_RB);

        dtworry1 = (ImageView) findViewById(R.id.dtworry1);
        dtworry2 = (ImageView) findViewById(R.id.dtworry2);
        dtworry3 = (ImageView) findViewById(R.id.dtworry3);
        dtgoodbut = (Button) findViewById(R.id.goodbut);
        dtbadbut = (Button) findViewById(R.id.badbut);

        dt_review_searchgo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent searchintent = new Intent(getApplicationContext(), SearchResult.class);
                searchintent.putExtra("searchresult", dt_review_search.getText().toString());
                searchintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                searchintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(searchintent);
            }
        });

        dt_review_mypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mypage = new Intent(getApplicationContext(), MyPage.class);
                mypage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mypage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mypage);
            }
        });

        dt_review_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        dt_review_gohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gohome = new Intent(getApplicationContext(), FragmentMain.class);
                gohome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                gohome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(gohome);
            }
        });





        Call<DetailReviewResult> detailReviewResultCall = networkService.getDetailReviewResult(milknm, review_id, nickname);
        detailReviewResultCall.enqueue(new Callback<DetailReviewResult>() {
            @Override
            public void onResponse(Call<DetailReviewResult> call, Response<DetailReviewResult> response) {
                Log.i("여기", "들옴");
                if (response.isSuccessful()) {
                    Log.i("여기", "들옴2");
                    data = response.body().result;
                    Glide.with(getApplicationContext()).load(data.review.milk_image).into(dtmilkimg);
                    one_line.setText(data.review.title);
                    dtcompanyname.setText(data.review.milk_company);
                    dtmilkname.setText(milknm);
                    dtgood.setText(data.review.good);
                    dtbad.setText(data.review.bad);
                    goodnum.setText(data.review.goodnum + "");
                    badnum.setText(data.review.badnum + "");
                    dttip.setText(data.review.tip);
                    dtratingBar.setRating(data.review.review_grade);
                    if(data.review.good_check==true&&data.review.bad_check==false){
                        dtgoodbut.setBackgroundResource(R.drawable.review_good_color);
                        gclick_set = true;
                        bclick_set = false;
                        }
                    else if(data.review.bad_check==true&&data.review.good_check==false) {
                        dtbadbut.setBackgroundResource(R.drawable.review_bad_color);
                        gclick_set = false;
                        bclick_set = true;

                    }else{
                        gclick_set = false;
                        bclick_set = false;
                    }

                    if (!data.review.worry1.equals(""))
                        dtworry1.setVisibility(View.VISIBLE);
                    if (!data.review.worry2.equals(""))
                        dtworry2.setVisibility(View.VISIBLE);
                    if (!data.review.worry3.equals(""))
                        dtworry3.setVisibility(View.VISIBLE);
                    dtgoodbut.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if (gclick_set == false) {
                                if (bclick_set == false) {
                                    dtgoodbut.setBackgroundResource(R.drawable.review_good_color);
                                    gclick_set = true;
                                } else {
                                    dtgoodbut.setBackgroundResource(R.drawable.review_good_color);
                                    dtbadbut.setBackgroundResource(R.drawable.review_bad_gray);
                                    gclick_set = true;
                                    bclick_set = false;
                                }
                            } else{
                                dtgoodbut.setBackgroundResource(R.drawable.review_good_gray);
                                gclick_set = false;}
                            goodnet();

                        }
                    });


                    dtbadbut.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if (bclick_set == false) {
                                if (gclick_set == false) {
                                    dtbadbut.setBackgroundResource(R.drawable.review_bad_color);
                                    bclick_set = true;
                                } else {
                                    dtbadbut.setBackgroundResource(R.drawable.review_bad_color);
                                    dtgoodbut.setBackgroundResource(R.drawable.review_good_gray);
                                    bclick_set = true;
                                    gclick_set = false;
                                }
                            } else{
                                dtbadbut.setBackgroundResource(R.drawable.review_bad_gray);
                                bclick_set=false;}
                            badnet();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<DetailReviewResult> call, Throwable t) {
                Log.i("MyTag", "실패~");
            }
        });
    }

    public void goodnet() {
        Call<ReviewGResult> reviewgresult = networkService.getReviewGResult(p_email, review_id);
        reviewgresult.enqueue(new Callback<ReviewGResult>() {
            @Override
            public void onResponse(Call<ReviewGResult> call, Response<ReviewGResult> response) {
                Log.i("여기", "들옴");
                if (response.isSuccessful()) {
                    Log.i("여기", "들옴2");
                    Gdata = response.body().result;
                    goodnum.setText(Gdata.goodbad.goodnum + "");
                    badnum.setText(Gdata.goodbad.badnum + "");
                }
            }

            @Override
            public void onFailure(Call<ReviewGResult> call, Throwable t) {
                Log.i("MyTag", "실패~");
            }
        });
    }

    public void badnet() {
        Call<ReviewBResult> reviewbresult = networkService.getReviewBResult(p_email, review_id);
        reviewbresult.enqueue(new Callback<ReviewBResult>() {
            @Override
            public void onResponse(Call<ReviewBResult> call, Response<ReviewBResult> response) {
                Log.i("여기", "들옴");
                if (response.isSuccessful()) {
                    Log.i("여기", "들옴2");
                    Bdata = response.body().result;
                    goodnum.setText(Bdata.goodbad.goodnum + "");
                    badnum.setText(Bdata.goodbad.badnum + "");
                }

                }


            @Override
            public void onFailure(Call<ReviewBResult> call, Throwable t) {
                Log.i("MyTag", "실패~");
            }
        });
    }
}