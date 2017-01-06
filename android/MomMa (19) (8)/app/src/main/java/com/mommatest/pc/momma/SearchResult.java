package com.mommatest.pc.momma;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import com.mommatest.pc.momma.Model.SearchbarResult;
import com.mommatest.pc.momma.application.ApplicationController;
import com.mommatest.pc.momma.network.NetworkService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResult extends AppCompatActivity {

    RecyclerView recyclerview_milk,recyclerview_ingre,recyclerview_manu;
    LinearLayoutManager linearLayoutManager;
    ArrayList<ItemSearchResult> dataset;
    AdapterSearchResult Adapter_milk;
    AdapterSearchResult_ingre Adapter_ingre;
    AdapterSearchResult_manu Adapter_manu;
    Button search_main_gohome, search_main_mypage;
    NetworkService networkService;
    SearchbarResult.ResultData data;
    ImageView search_main_searchgo;
    EditText search_main_search;
    String search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        networkService = ApplicationController.getInstance().getNetworkService();
        Intent searchresult = getIntent();
        search = searchresult.getExtras().getString("searchresult");



        Log.i("keyword", search);
        search_main_gohome=(Button)findViewById(R.id.search_main_gohome);
        search_main_search=(EditText)findViewById(R.id.search_main_search);
        search_main_searchgo=(ImageView) findViewById(R.id.search_main_searchgo);
        search_main_mypage = (Button)findViewById(R.id.search_main_mypage);

        search_main_searchgo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent searchintent = new Intent(getApplicationContext(), SearchResult.class);
                searchintent.putExtra("searchresult", search_main_search.getText().toString());
                searchintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                searchintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(searchintent);
            }
        });


        search_main_mypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mypage = new Intent(getApplicationContext(), MyPage.class);
                mypage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mypage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mypage);
            }
        });

        search_main_gohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gohome = new Intent(getApplicationContext(), FragmentMain.class);
                gohome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                gohome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(gohome);
            }
        });


        recyclerview_milk = (RecyclerView)findViewById(R.id.recycleview_milksearch);
        recyclerview_milk.setHasFixedSize(true);

        linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview_milk.setLayoutManager(linearLayoutManager);

        recyclerview_ingre = (RecyclerView)findViewById(R.id.recycleview_ingredientsearch);
        recyclerview_ingre.setHasFixedSize(true);

        linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview_ingre.setLayoutManager(linearLayoutManager);

        recyclerview_manu = (RecyclerView)findViewById(R.id.recycleview_manufactorsearch);
        recyclerview_manu.setHasFixedSize(true);

        linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview_manu.setLayoutManager(linearLayoutManager);

        getSearchResult();

    }

    public View.OnClickListener clickEvent_milk = new View.OnClickListener() {
        public void onClick(View v) {
            final int itemPosition = recyclerview_milk.getChildPosition(v);
            final String temp = data.drymilk.get(itemPosition).milk_name;
            Intent detailmilk = new Intent(getApplicationContext(), DetailDrymilk.class);
            detailmilk.putExtra("milk_name", temp);
            detailmilk.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            detailmilk.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(detailmilk);


        }
    };
    public View.OnClickListener clickEvent_ingre = new View.OnClickListener() {
        public void onClick(View v) {
            final int itemPosition = recyclerview_ingre.getChildPosition(v);
            final String temp = data.ingredient.get(itemPosition).ingre_name;
            Intent detailingre = new Intent(getApplicationContext(), Ingredient_Info_Activity.class);
            detailingre.putExtra("ingre_name",temp);
            detailingre.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            detailingre.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(detailingre);


        }
    };
    public View.OnClickListener clickEvent_manu = new View.OnClickListener() {
        public void onClick(View v) {
            final int itemPosition = recyclerview_manu.getChildPosition(v);
            final String temp = data.manufactor.get(itemPosition).manu_name;
            Intent detailmanu = new Intent(getApplicationContext(), DetailCompany.class);
            detailmanu.putExtra("companynm",temp);
            detailmanu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            detailmanu.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(detailmanu);

        }
    };
    public void getSearchResult(){
        Log.i("여기","1");
        Call<SearchbarResult> searchResultCall = networkService.getSearchResult(search);
        Log.i("여기","2");
        searchResultCall.enqueue(new Callback<SearchbarResult>() {
            @Override
            public void onResponse(Call<SearchbarResult> call, Response<SearchbarResult> response) {
                Log.i("여기","3");
                if(response.isSuccessful()) {
                    Log.i("여기","4");
                    data = response.body().result;
                     Adapter_milk = new AdapterSearchResult(data.drymilk,clickEvent_milk, SearchResult.this);
                    recyclerview_milk.setAdapter(Adapter_milk);

                    Adapter_ingre = new AdapterSearchResult_ingre(data.ingredient,clickEvent_ingre, SearchResult.this);
                    recyclerview_ingre.setAdapter(Adapter_ingre);

                    Adapter_manu = new AdapterSearchResult_manu(data.manufactor, clickEvent_manu, SearchResult.this);
                    recyclerview_manu.setAdapter(Adapter_manu);

                }
            }

            @Override
            public void onFailure(Call<SearchbarResult> call, Throwable t) {
                Log.i("MyTag", "실패");
            }
        });
    }
}
