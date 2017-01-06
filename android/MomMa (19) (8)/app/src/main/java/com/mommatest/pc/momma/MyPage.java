package com.mommatest.pc.momma;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mommatest.pc.momma.Model.DetailMilkResult;
import com.mommatest.pc.momma.Model.MyPageResult;
import com.mommatest.pc.momma.application.ApplicationController;
import com.mommatest.pc.momma.network.NetworkService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyPage extends AppCompatActivity {

    TextView editbut;
    ImageView mpback;

    RecyclerView recyclerview1, recyclerview2;
    LinearLayoutManager linearLayoutManager1;
    LinearLayoutManager linearLayoutManager2;
    ArrayList<Itemmyreview> dataset1;
    ArrayList<ItemFavorite> dataset2;
    AdapterMyreview adapter1;
    AdapterFavorite adapter2;
    TextView p_name, p_nickname, b_name, b_birth;
    ImageView worry1, worry2, worry3, noworry, mypage_logout;
    NetworkService networkService;
    MyPageResult.ResultData data;
    SharedPreferences setting;
    SharedPreferences.Editor editor;
    String nickname, sex, b_worry1, b_worry2, b_worry3;
    DetailMilkResult.ResultData datas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);
        networkService = ApplicationController.getInstance().getNetworkService();

        setting = getSharedPreferences("setting", MODE_PRIVATE);
        editor = setting.edit();
        nickname = setting.getString("p_nickname", "");
        Log.i("MyTag2", nickname);

        mypage_logout = (ImageView) findViewById(R.id.mypage_logout);
        p_name = (TextView) findViewById(R.id.p_name);
        p_nickname = (TextView) findViewById(R.id.p_nickname);
        b_name = (TextView) findViewById(R.id.b_name);
        b_birth = (TextView) findViewById(R.id.b_birth);
        worry1 = (ImageView) findViewById(R.id.mpworry1);
        worry2 = (ImageView) findViewById(R.id.mpworry2);
        worry3 = (ImageView) findViewById(R.id.mpworry3);
        noworry = (ImageView) findViewById(R.id.mpnoworry);
        editbut = (TextView) findViewById(R.id.edit_BT);
        mpback = (ImageView) findViewById(R.id.mpback);

        getnetwork();


        mypage_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.clear();
                editor.commit();
                Intent logout = new Intent(getApplicationContext(), Login.class);
                logout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                logout.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(logout);

            }
        });
        editbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editintent = new Intent(getApplicationContext(), MyPageEdit.class);
                editintent.putExtra("p_name", p_name.getText().toString());
                editintent.putExtra("b_birth", b_birth.getText().toString());
                editintent.putExtra("p_nickname", p_nickname.getText().toString());
                editintent.putExtra("nickname", nickname);
                editintent.putExtra("b_name", b_name.getText().toString());
                editintent.putExtra("b_sex", sex);
                editintent.putExtra("b_worry1", b_worry1);
                editintent.putExtra("b_worry2", b_worry2);
                editintent.putExtra("b_worry3", b_worry3);
                Log.i("MyTag3", "넘겨주는 성별 : " + sex);
                Log.i("MyTag3", "넘겨주는 worry1 : " + b_worry1);
                Log.i("MyTag3", "넘겨주는 worry2 : " + b_worry2);
                Log.i("MyTag3", "넘겨주는 worry3 : " + b_worry3);

                editintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                editintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(editintent);
            }
        });

        mpback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        recyclerview1 = (RecyclerView) findViewById(R.id.recycleview_Myreview);
        recyclerview1.setHasFixedSize(true);

        recyclerview2 = (RecyclerView) findViewById(R.id.recycleview_Favorite);
        recyclerview2.setHasFixedSize(true);


        linearLayoutManager1 = new LinearLayoutManager(this);
        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        linearLayoutManager2 = new LinearLayoutManager(this);
        linearLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerview1.setLayoutManager(linearLayoutManager1);
        recyclerview2.setLayoutManager(linearLayoutManager2);
    }

    public View.OnClickListener clickEvent1 = new View.OnClickListener() {
        public void onClick(View v) {
            final int itemPosition = recyclerview1.getChildPosition(v);
            final String temp = data.myreview.get(itemPosition).review_id;
            final String temp2 = data.myreview.get(itemPosition).milk_name;
            Intent detailreview_intent = new Intent(getApplicationContext(), DetailReview.class);
            detailreview_intent.putExtra("milk_name", temp2);
            detailreview_intent.putExtra("review_id", temp);
            detailreview_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            detailreview_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(detailreview_intent);
        }
    };
    public View.OnClickListener clickEvent2 = new View.OnClickListener() {
        public void onClick(View v) {
            final int itemPosition = recyclerview2.getChildPosition(v);
            final String temp = data.bookmark.get(itemPosition).milk_name;
            Intent detailmilk = new Intent(getApplicationContext(), DetailDrymilk.class);
            detailmilk.putExtra("milk_name", temp);
            detailmilk.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            detailmilk.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(detailmilk);
        }
    };

    public void getnetwork() {

        Call<MyPageResult> RankList = networkService.getMyPageResult(nickname);
        RankList.enqueue(new Callback<MyPageResult>() {
            @Override
            public void onResponse(Call<MyPageResult> call, Response<MyPageResult> response) {
                if (response.isSuccessful()) {
                    data = response.body().result;
                    sex = data.baby.sex.toString();
                    if(data.baby.worry1.equals("")) {
                        b_worry1 = "";
                    }
                    else {
                        b_worry1 = "알레르기";
                    }
                    if(data.baby.worry2.equals("")) {
                        b_worry2 = "";
                    }
                    else {
                        b_worry2 = "아토피";
                    }
                    if(data.baby.worry3.equals("")) {
                        b_worry3 = "";
                    }
                    else {
                        b_worry3 = "배변";
                    }

                    adapter1 = new AdapterMyreview(data.myreview,clickEvent1, MyPage.this);
                    adapter2 = new AdapterFavorite(data.bookmark,clickEvent2, MyPage.this);
                    recyclerview1.setAdapter(adapter1);
                    recyclerview2.setAdapter(adapter2);

                    updateview();
                }
            }

            @Override
            public void onFailure(Call<MyPageResult> call, Throwable t) {
                Log.i("MyTag", "실패");
            }
        });
    }

    private void updateview() {
        p_name.setText(data.parent.p_name);
        p_nickname.setText(data.parent.p_nickname);
        b_name.setText(data.baby.b_name);
        b_birth.setText(data.baby.b_month + "개월");

        if (b_worry1.equals("알레르기"))
            worry1.setVisibility(View.VISIBLE);
        if (b_worry2.equals("아토피"))
            worry2.setVisibility(View.VISIBLE);
        if (b_worry3.equals("배변"))
            worry3.setVisibility(View.VISIBLE);
        if (b_worry1.equals("") && b_worry2.equals("") && b_worry3.equals(""))
            noworry.setVisibility(View.VISIBLE);


    }
}