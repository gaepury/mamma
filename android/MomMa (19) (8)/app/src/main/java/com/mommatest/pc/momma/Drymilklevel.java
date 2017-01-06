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

import com.mommatest.pc.momma.Model.TabIngredientRankResult;
import com.mommatest.pc.momma.application.ApplicationController;
import com.mommatest.pc.momma.network.NetworkService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Drymilklevel extends AppCompatActivity {

    private RecyclerView recyclerview;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<ItemDataLevel> dataset;
    private Adapterlevel Adapter;
    private Button btnS, btn1, btn2, btn3, btn4,ingre_inrank_mypage,ingre_inrank_back;
    private int level = 1;
    String ingre_name;
    ImageView ingre_inrank_searchgo;
    EditText ingre_inrank_search;
    TabIngredientRankResult.ResultData data;
    NetworkService networkService;
    TextView ingre_nm,ingre_inrank_gohome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drymilk_level);

        Intent getrank = getIntent();
        ingre_name = getrank.getExtras().getString("rank");

        ingre_inrank_back = (Button)findViewById(R.id.ingre_inrank_back);
        ingre_inrank_search = (EditText)findViewById(R.id.ingre_inrank_search);
        ingre_inrank_mypage = (Button)findViewById(R.id.ingre_inrank_mypage);
        ingre_inrank_searchgo = (ImageView)findViewById(R.id.ingre_inrank_searchgo);
        ingre_inrank_gohome = (TextView)findViewById(R.id.ingre_inrank_gohome);

        ingre_inrank_searchgo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent searchintent = new Intent(getApplicationContext(), SearchResult.class);
                searchintent.putExtra("searchresult", ingre_inrank_search.getText().toString());
                searchintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                searchintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(searchintent);
            }
        });

        ingre_inrank_mypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mypage = new Intent(getApplicationContext(), MyPage.class);
                mypage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mypage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mypage);
            }
        });

        ingre_inrank_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ingre_inrank_gohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gohome = new Intent(getApplicationContext(), FragmentMain.class);
                gohome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                gohome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(gohome);
            }
        });

        ingre_nm = (TextView) findViewById(R.id.ingre_nm);
        ingre_nm.setText(ingre_name+" 포함순위");

        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btnS = (Button) findViewById(R.id.btnS);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                level = 1;
                btn1.setBackgroundResource(R.drawable.grade_1_click);
                btn2.setBackgroundResource(R.drawable.grade_2);
                btn3.setBackgroundResource(R.drawable.grade_3);
                btn4.setBackgroundResource(R.drawable.grade_4);
                btnS.setBackgroundResource(R.drawable.grade_s);
                recyclerview = (RecyclerView) findViewById(R.id.rcvlevel);
                recyclerview.setHasFixedSize(true);

                linearLayoutManager = new LinearLayoutManager(Drymilklevel.this);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerview.setLayoutManager(linearLayoutManager);

                setStage(level);


            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                level = 2;
                btn1.setBackgroundResource(R.drawable.grade_1);
                btn2.setBackgroundResource(R.drawable.grade_2_click);
                btn3.setBackgroundResource(R.drawable.grade_3);
                btn4.setBackgroundResource(R.drawable.grade_4);
                btnS.setBackgroundResource(R.drawable.grade_s);
                recyclerview = (RecyclerView) findViewById(R.id.rcvlevel);
                recyclerview.setHasFixedSize(true);

                linearLayoutManager = new LinearLayoutManager(Drymilklevel.this);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerview.setLayoutManager(linearLayoutManager);

                setStage(level);


            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                level = 3;
                btn1.setBackgroundResource(R.drawable.grade_1);
                btn2.setBackgroundResource(R.drawable.grade_2);
                btn3.setBackgroundResource(R.drawable.grade_3_click);
                btn4.setBackgroundResource(R.drawable.grade_4);
                btnS.setBackgroundResource(R.drawable.grade_s);
                recyclerview = (RecyclerView) findViewById(R.id.rcvlevel);
                recyclerview.setHasFixedSize(true);

                linearLayoutManager = new LinearLayoutManager(Drymilklevel.this);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerview.setLayoutManager(linearLayoutManager);

                setStage(level);


            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                level = 4;
                btn1.setBackgroundResource(R.drawable.grade_1);
                btn2.setBackgroundResource(R.drawable.grade_2);
                btn3.setBackgroundResource(R.drawable.grade_3);
                btn4.setBackgroundResource(R.drawable.grade_4_click);
                btnS.setBackgroundResource(R.drawable.grade_s);
                recyclerview = (RecyclerView) findViewById(R.id.rcvlevel);
                recyclerview.setHasFixedSize(true);

                linearLayoutManager = new LinearLayoutManager(Drymilklevel.this);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerview.setLayoutManager(linearLayoutManager);

                setStage(level);


            }
        });
        btnS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                level = 0;
                btn1.setBackgroundResource(R.drawable.grade_1);
                btn2.setBackgroundResource(R.drawable.grade_2);
                btn3.setBackgroundResource(R.drawable.grade_3);
                btn4.setBackgroundResource(R.drawable.grade_4);
                btnS.setBackgroundResource(R.drawable.grade_s_click);
                recyclerview = (RecyclerView) findViewById(R.id.rcvlevel);
                recyclerview.setHasFixedSize(true);

                linearLayoutManager = new LinearLayoutManager(Drymilklevel.this);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerview.setLayoutManager(linearLayoutManager);

                setStage(level);


            }
        });
        if (level == 1) {
            recyclerview = (RecyclerView) findViewById(R.id.rcvlevel);
            recyclerview.setHasFixedSize(true);

            linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerview.setLayoutManager(linearLayoutManager);

            setStage(level);


        }
    }
    public View.OnClickListener clickEvent = new View.OnClickListener() {
        public void onClick(View v) {
            final int itemPosition = recyclerview.getChildPosition(v);
            final String temp = data.ingre_ranking.get(itemPosition).milk_name;
            Intent detailmilk = new Intent(getApplicationContext(), DetailDrymilk.class);
            detailmilk.putExtra("milk_name", temp);
            detailmilk.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            detailmilk.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(detailmilk);


        }
    };

    public void setStage(int level){
        Call<TabIngredientRankResult> RankList = ApplicationController.getInstance().getNetworkService().getTabingredientrankresult(ingre_name,level);
        RankList.enqueue(new Callback<TabIngredientRankResult>() {
            @Override
            public void onResponse(Call<TabIngredientRankResult> call, Response<TabIngredientRankResult> response) {
                if (response.isSuccessful()) {
                    data = response.body().result;
                    Adapter = new Adapterlevel(data.ingre_ranking,clickEvent, Drymilklevel.this);
                    //ch1Adapter = new Ch1Adapter(datas.reviewranklist, RecyclerReview.this);
                    recyclerview.setAdapter(Adapter);
                    Log.i("MyTag", "성공~");
                }
            }

            @Override
            public void onFailure(Call<TabIngredientRankResult> call, Throwable t) {
                Log.i("MyTag", "실패~");
            }
        });
    }
}