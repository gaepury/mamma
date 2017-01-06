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


import com.mommatest.pc.momma.Model.SearchRankResult;
import com.mommatest.pc.momma.application.ApplicationController;
import com.mommatest.pc.momma.network.NetworkService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerSearch extends AppCompatActivity {

    private RecyclerView recyclerview;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<ItemData_s> dataset;
    private AdapterSearch adapterSearch;
    private Button othermombackbtn;
    private EditText othermom_search;
    private ImageView othermom_searchgo;
    private TextView home_TV;
    private Button othermom_mypage;
    NetworkService networkService;

    SearchRankResult.ResultData datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_search);
        networkService = ApplicationController.getInstance().getNetworkService();

        othermom_mypage = (Button) findViewById(R.id.othermom_mypage);
        othermombackbtn = (Button) findViewById(R.id.othermombackbtn);
        othermom_search = (EditText) findViewById(R.id.othermom_search);
        othermom_searchgo = (ImageView) findViewById(R.id.othermom_searchgo);


        recyclerview = (RecyclerView) findViewById(R.id.recycleview_search);
        recyclerview.setHasFixedSize(true);

        home_TV = (TextView) findViewById(R.id.home_TV);

        getLists();
        home_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        othermombackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent othermombackintent = new Intent(getApplicationContext(), FragmentMain.class);
                othermombackintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                othermombackintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(othermombackintent);
            }
        });

        othermom_searchgo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent searchintent = new Intent(getApplicationContext(), SearchResult.class);
                searchintent.putExtra("searchresult", othermom_search.getText().toString());
                searchintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                searchintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(searchintent);
            }
        });

        othermom_mypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mypage = new Intent(getApplicationContext(), MyPage.class);
                mypage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mypage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mypage);
            }
        });

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(linearLayoutManager);


    }

    public View.OnClickListener clickEvent = new View.OnClickListener() {
        public void onClick(View v) {
            final int itemPosition = recyclerview.getChildPosition(v);
            final String temp = datas.searchrank.get(itemPosition).milk_name;
            Intent detailmilk = new Intent(getApplicationContext(), DetailDrymilk.class);
            detailmilk.putExtra("milk_name", temp);
            detailmilk.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            detailmilk.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(detailmilk);

        }
    };

    public void getLists() {
        Log.i("MyTag", "ddd");
        Call<SearchRankResult> RankList = networkService.getSearchRankList();
        RankList.enqueue(new Callback<SearchRankResult>() {
            @Override
            public void onResponse(Call<SearchRankResult> call, Response<SearchRankResult> response) {
                if (response.isSuccessful()) {
                    Log.i("MyTag", "성공");
                    datas = response.body().result;
                    adapterSearch = new AdapterSearch(datas.searchrank,clickEvent, RecyclerSearch.this);
                    //ch1Adapter = new Ch1Adapter(datas.reviewranklist, RecyclerReview.this);
                    recyclerview.setAdapter(adapterSearch);
                }
            }

            @Override
            public void onFailure(Call<SearchRankResult> call, Throwable t) {
                Log.i("MyTag", "실패");
            }
        });
    }
}