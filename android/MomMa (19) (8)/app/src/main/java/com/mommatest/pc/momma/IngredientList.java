package com.mommatest.pc.momma;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.mommatest.pc.momma.Model.Detailingredient_rankResult;
import com.mommatest.pc.momma.application.ApplicationController;
import com.mommatest.pc.momma.network.NetworkService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IngredientList extends AppCompatActivity {

    private RecyclerView recyclerview;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<ItemIngredientList> dataset;
    private AdapterIngredientList Adapter;
    private Button Detail_Back_BT, ingre_list_mypage;
    private TextView Gohome;
    EditText ingre_list_search;
    ImageView ingre_list_searchgo;
    String milk_name;
    NetworkService networkService;
    Detailingredient_rankResult.ResultData datas;
    String a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_list);
        networkService = ApplicationController.getInstance().getNetworkService();

        Intent milk_nameintent = getIntent();
        milk_name = milk_nameintent.getExtras().getString("milk_name");

        ingre_list_mypage = (Button)findViewById(R.id.ingre_list_mypage);
        ingre_list_search = (EditText)findViewById(R.id.ingre_list_search);
        ingre_list_searchgo = (ImageView)findViewById(R.id.ingre_list_searchgo);

        ingre_list_searchgo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent searchintent = new Intent(getApplicationContext(), SearchResult.class);
                searchintent.putExtra("searchresult", ingre_list_search.getText().toString());
                searchintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                searchintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(searchintent);
            }
        });


        ingre_list_mypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mypage = new Intent(getApplicationContext(), MyPage.class);
                mypage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mypage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mypage);
            }
        });

        Gohome = (TextView) findViewById(R.id.Gohome2);

        Gohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gohome = new Intent(getApplicationContext(), FragmentMain.class);
                gohome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                gohome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(gohome);
            }
        });
        Detail_Back_BT = (Button)findViewById(R.id.Detail_Back_BT);
        Detail_Back_BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backdetailreview = new Intent(getApplicationContext(), DetailDrymilk.class);
                backdetailreview.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                backdetailreview.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                backdetailreview.putExtra("milk_name", milk_name);
                startActivity(backdetailreview);
            }
        });
        recyclerview = (RecyclerView)findViewById(R.id.recycleview_ingredientlist);
        recyclerview.setHasFixedSize(true);

        getLists();

        linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(linearLayoutManager);

//        dataset = new ArrayList<ItemIngredientList>();
//
//        dataset.add(new ItemIngredientList("성분이름","0"));
//
//        Adapter = new AdapterIngredientList(dataset, clickEvent);
        recyclerview.setAdapter(Adapter);

    }
    public View.OnClickListener clickEvent = new View.OnClickListener() {
        public void onClick(View v) {
            final int itemPosition = recyclerview.getChildPosition(v);
            final String temp = dataset.get(itemPosition).name;
            Toast.makeText(getApplicationContext(),temp,Toast.LENGTH_SHORT).show();
            //AlertDialog alertDialog = delete_confirm.create();
            // alertDialog.show();
        }
    };
    public void getLists(){
        Call<Detailingredient_rankResult> dtingrerank = networkService.getDetailingredientrank(milk_name);
        dtingrerank.enqueue(new Callback<Detailingredient_rankResult>() {
            @Override
            public void onResponse(Call<Detailingredient_rankResult> call, Response<Detailingredient_rankResult> response) {
                if(response.isSuccessful()) {
                    datas = response.body().result;
                    Adapter = new AdapterIngredientList(datas.ingredient, IngredientList.this);
                    recyclerview.setAdapter(Adapter);

                }
            }

            @Override
            public void onFailure(Call<Detailingredient_rankResult> call, Throwable t) {

            }
        });
    }
}
