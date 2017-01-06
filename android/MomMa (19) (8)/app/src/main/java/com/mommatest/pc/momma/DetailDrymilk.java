package com.mommatest.pc.momma;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.mommatest.pc.momma.Model.Bookmark;
import com.mommatest.pc.momma.Model.BookmarkResult;
import com.mommatest.pc.momma.Model.DetailMilkResult;
import com.mommatest.pc.momma.application.ApplicationController;
import com.mommatest.pc.momma.network.NetworkService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailDrymilk extends AppCompatActivity {

    private RecyclerView recyclerview;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<ItemDataReview> dataset;
    private AdapterReview Adapter;
    private TextView home_TV;
    private EditText detailmilksearch;
    private Button PageMore_BT1, PageMore_BT2, ingredent_grade, detailmilkback, bookmark, detailmilkmypage, detailmilk_create_review, priceInfo;
    RatingBar detailratingbar;
    ImageView milk_IV, detailmilksearchgo;
    TextView milktext_TV, milkcompany_TV, ingerdent1, ingerdent2;
    String milk_name,p_nickname;
    NetworkService networkService;
    DetailMilkResult.ResultData data;
    SharedPreferences setting;
    SharedPreferences.Editor editor;
    BookmarkResult result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_drymilk);
        networkService = ApplicationController.getInstance().getNetworkService();
        Intent milknmintent = getIntent();
        milk_name = milknmintent.getExtras().getString("milk_name");
        setting = getSharedPreferences("setting", MODE_PRIVATE);
        editor = setting.edit();
        p_nickname = setting.getString("p_nickname", "");

        priceInfo = (Button)findViewById(R.id.priceInfo);

        priceInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent priceIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.shopping.naver.com/search/all.nhn?query="+data.milk.milk_name+"&frm=NVSHATC"));
                startActivity(priceIntent);
            }
        });


        Log.i("분유 이름", milk_name);
        detailmilk_create_review=(Button)findViewById(R.id.detailmilk_create_review);
        detailmilksearch=(EditText)findViewById(R.id.detailmilksearch);
        detailmilkmypage=(Button)findViewById(R.id.detailmilkmypage);
        detailmilksearchgo=(ImageView)findViewById(R.id.detailmilksearchgo);
        milk_IV = (ImageView) findViewById(R.id.milk_IV);
        milktext_TV = (TextView) findViewById(R.id.miliktext_TV);
        milkcompany_TV=(TextView) findViewById(R.id.milkcompany_TV);
        detailmilkback = (Button) findViewById(R.id.detailmilkback);
        PageMore_BT1=(Button)findViewById(R.id.PageMore_BT1);
        PageMore_BT2=(Button)findViewById(R.id.PageMore_BT2);
        ingredent_grade=(Button)findViewById(R.id.ingredent_grade);
        ingerdent1 = (TextView) findViewById(R.id.ingerdent1);
        ingerdent2 = (TextView) findViewById(R.id.ingerdent2);
        home_TV = (TextView)findViewById(R.id.home_TV);
        recyclerview = (RecyclerView)findViewById(R.id.recycleview_review1);
        recyclerview.setHasFixedSize(true);
        detailratingbar = (RatingBar)findViewById(R.id.detailmilkrating);
        bookmark = (Button)findViewById(R.id.bookmark);



        detailmilk_create_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reviewintent = new Intent(getApplicationContext(), create_review_activity.class);
                reviewintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                reviewintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(reviewintent);
            }
        });
        detailmilksearchgo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent searchintent = new Intent(getApplicationContext(), SearchResult.class);
                searchintent.putExtra("searchresult", detailmilksearch.getText().toString());
                searchintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                searchintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(searchintent);
            }
        });
        detailmilkmypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mypage = new Intent(getApplicationContext(), MyPage.class);
                mypage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mypage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mypage);
            }
        });



        getDetailmilk();

        home_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gohome = new Intent(getApplicationContext(), FragmentMain.class);
                gohome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                gohome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(gohome);
            }
        });

        detailmilkback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        PageMore_BT1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pmbintent1 = new Intent(getApplicationContext(), Ingredient_Info_Activity.class);
                pmbintent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                pmbintent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                pmbintent1.putExtra("ingre_name", ingerdent1.getText().toString());
                startActivity(pmbintent1);
            }
        });
        PageMore_BT2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pmbintent2 = new Intent(getApplicationContext(), Ingredient_Info_Activity.class);
                pmbintent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                pmbintent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                pmbintent2.putExtra("ingre_name", ingerdent2.getText().toString());
                startActivity(pmbintent2);
            }
        });
        ingredent_grade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ingredentgradeintent = new Intent(getApplicationContext(), IngredientList.class);
                ingredentgradeintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                ingredentgradeintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ingredentgradeintent.putExtra("milk_name", milk_name);
                startActivity(ingredentgradeintent);

            }
        });
        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bookmark bookmark = new Bookmark();
                bookmark.p_nickname = p_nickname.toString();
                bookmark.milk_name = milk_name;

                Call<BookmarkResult> bookmarkResultCall = networkService.getBookmarkResult(bookmark);
                bookmarkResultCall.enqueue(new Callback<BookmarkResult>() {
                    @Override
                    public void onResponse(Call<BookmarkResult> call, Response<BookmarkResult> response) {
                        if (response.isSuccessful()) {
                            result = response.body();
                            if (result.isResult()) {
                                Log.i("MyTag4", "완전성공");
                                Toast.makeText(DetailDrymilk.this, "즐겨찾기에 추가되었습니다.", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.i("MyTag4", "즐겨찾기 실패");
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<BookmarkResult> call, Throwable t) {
                        Log.i("MyTag4", "실패~");
                    }
                });

            }
        });



        linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(linearLayoutManager);

    }
    public View.OnClickListener clickEvent = new View.OnClickListener() {
        public void onClick(View v) {
            final int itemPosition = recyclerview.getChildPosition(v);
            final String temp = data.review.get(itemPosition).review_id;
            Intent detailreview_intent = new Intent(getApplicationContext(), DetailReview.class);
            detailreview_intent.putExtra("milk_name", milk_name);
            detailreview_intent.putExtra("review_id", temp);
            detailreview_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            detailreview_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(detailreview_intent);
        }
    };

    public void getDetailmilk() {
        Call<DetailMilkResult> detailMilkResultCall = networkService.getDetailmilk(milk_name);
        Log.i("milk_name", milk_name);

        detailMilkResultCall.enqueue(new Callback<DetailMilkResult>() {
            @Override
            public void onResponse(Call<DetailMilkResult> call, Response<DetailMilkResult> response) {
                if (response.isSuccessful()) {
                    Log.i("MyTag", "성공~");
                    data = response.body().result;
                    Adapter = new AdapterReview(data.review, clickEvent, DetailDrymilk.this);
                    recyclerview.setAdapter(Adapter);
                    updateView1();
                    updateView2();
                }
            }

            @Override
            public void onFailure(Call<DetailMilkResult> call, Throwable t) {
                Log.i("MyTag", "실패~");
            }
        });
    }
    public void updateView1(){
        Log.i("들어옴","여기로1");
        Glide.with(getApplicationContext())
                .load(data.milk.milk_image)
                .into(milk_IV);

        milktext_TV.setText(data.milk.milk_name);
        milkcompany_TV.setText(data.milk.milk_company);

        detailratingbar.setRating(data.milk.milk_grade);


    }
    public void updateView2(){
        Log.i("들어옴","여기로2");
        ingerdent1.setText(data.ingredient.get(0));
        ingerdent2.setText(data.ingredient.get(1));


    }

}