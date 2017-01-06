package com.mommatest.pc.momma;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mommatest.pc.momma.Model.ReviewRankResult;
import com.mommatest.pc.momma.application.ApplicationController;
import com.mommatest.pc.momma.network.NetworkService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerReview extends AppCompatActivity {

    private RecyclerView recyclerview;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<ItemData> dataset;
    private Ch1Adapter ch1Adapter;
    private Button manyreviewbackbtn, manyreview_mypage;
    private int index;
    private ImageView manyreview_searchgo;
    private EditText manyreview_search;
    private TextView home_TV;
    NetworkService networkService;
    ReviewRankResult.ResultData datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_review);
        networkService = ApplicationController.getInstance().getNetworkService();

        manyreview_mypage = (Button)findViewById(R.id.manyreview_mypage);
        manyreview_search = (EditText)findViewById(R.id.othermom_search);
        manyreview_searchgo = (ImageView)findViewById(R.id.manyreview_searchgo);
        manyreviewbackbtn = (Button) findViewById(R.id.manyreviewbackbtn);
        recyclerview = (RecyclerView) findViewById(R.id.recycleview_review);
        recyclerview.setHasFixedSize(true);
        getLists();
        home_TV = (TextView) findViewById(R.id.home_TV);
        home_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gohome = new Intent(getApplicationContext(), FragmentMain.class);
                gohome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                gohome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(gohome);
            }
        });
        manyreview_searchgo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent searchintent = new Intent(getApplicationContext(), SearchResult.class);
                searchintent.putExtra("searchresult", manyreview_search.getText().toString());
                searchintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                searchintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(searchintent);
            }
        });


        manyreview_mypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mypage = new Intent(getApplicationContext(), MyPage.class);
                mypage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mypage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mypage);
            }
        });

        manyreviewbackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(linearLayoutManager);
    }

    public View.OnClickListener clickEvent = new View.OnClickListener() {
        public void onClick(View v) {
            final int itemPosition = recyclerview.getChildPosition(v);
            final String temp = datas.reviewRank.get(itemPosition).milk_name;
            Intent detailmilk = new Intent(getApplicationContext(), DetailDrymilk.class);
            detailmilk.putExtra("milk_name", temp);
            detailmilk.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            detailmilk.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(detailmilk);

        }
    };

    public void getLists(){
        Call<ReviewRankResult> RankList = networkService.getReviewRankList();
        RankList.enqueue(new Callback<ReviewRankResult>() {
            @Override
            public void onResponse(Call<ReviewRankResult> call, Response<ReviewRankResult> response) {
                if(response.isSuccessful()) {
                    datas = response.body().result;
                    ch1Adapter = new Ch1Adapter(datas.reviewRank,clickEvent, RecyclerReview.this);
                    recyclerview.setAdapter(ch1Adapter);
                }
            }
            @Override
            public void onFailure(Call<ReviewRankResult> call, Throwable t) {
                Log.i("MyTag", "실패");
            }
        });
    }
}
