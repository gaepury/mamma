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
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mommatest.pc.momma.Model.DetailmanufactorResult;
import com.mommatest.pc.momma.application.ApplicationController;
import com.mommatest.pc.momma.network.NetworkService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailCompany extends AppCompatActivity {

    RecyclerView recyclerview;
    LinearLayoutManager linearLayoutManager;
    NetworkService networkService;
    AdapterDetailcompany_milk Adapter;
    String companynm;
    ImageView detailcompanyimg, dtcompany_searchgo, dtcompany_back;
    TextView detailcompanynm, dtcompany_gohome;
    EditText dtcompany_search;
    RatingBar detailcompanyrating;
    Button dtcompany_mypage;
    DetailmanufactorResult.ResultData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_company);
        networkService = ApplicationController.getInstance().getNetworkService();

        Intent getcompanynm = getIntent();
        companynm = getcompanynm.getExtras().getString("companynm");

        dtcompany_gohome = (TextView)findViewById(R.id.dtcompany_home);
        dtcompany_back = (ImageView) findViewById(R.id.dtcompanyback);
        dtcompany_search = (EditText) findViewById(R.id.dtcompany_search);
        dtcompany_searchgo = (ImageView) findViewById(R.id.dtcompany_searchgo);
        dtcompany_mypage = (Button) findViewById(R.id.dtcompany_mypage);

        dtcompany_gohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gohome = new Intent(getApplicationContext(), FragmentMain.class);
                gohome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                gohome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(gohome);
            }
        });

        dtcompany_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        dtcompany_mypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mypage = new Intent(getApplicationContext(), MyPage.class);
                mypage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mypage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mypage);
            }
        });

        dtcompany_searchgo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent searchintent = new Intent(getApplicationContext(), SearchResult.class);
                searchintent.putExtra("searchresult", dtcompany_search.getText().toString());
                searchintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                searchintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(searchintent);
            }
        });


        detailcompanyimg = (ImageView)findViewById(R.id.detailcompanyimg);
        detailcompanynm = (TextView)findViewById(R.id.detailcompanynm);



        recyclerview = (RecyclerView)findViewById(R.id.recycleview_company);
        recyclerview.setHasFixedSize(true);

        linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(linearLayoutManager);

        detailmanu();


    }
    public View.OnClickListener clickEvent = new View.OnClickListener() {
        public void onClick(View v) {
            final int itemPosition = recyclerview.getChildPosition(v);
        }
    };

    public void detailmanu(){
        Call<DetailmanufactorResult> detailmanufactorResultCall = networkService.getDetailmanufactorResult(companynm);
        detailmanufactorResultCall.enqueue(new Callback<DetailmanufactorResult>() {
            @Override
            public void onResponse(Call<DetailmanufactorResult> call, Response<DetailmanufactorResult> response) {
                if(response.isSuccessful()) {
                    data = response.body().result;
                    Adapter = new AdapterDetailcompany_milk(data.drymilk, DetailCompany.this);
                    recyclerview.setAdapter(Adapter);

                    updateview();
                }
            }

            @Override
            public void onFailure(Call<DetailmanufactorResult> call, Throwable t) {
                Log.i("MyTag", "실패");
            }
        });
    }

    public void updateview() {
        Glide.with(getApplicationContext())
                .load(data.manufactor.manu_image)
                .into(detailcompanyimg);

        detailcompanynm.setText(data.manufactor.manu_name);

    }

}
