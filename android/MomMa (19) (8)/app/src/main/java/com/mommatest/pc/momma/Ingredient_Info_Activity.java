package com.mommatest.pc.momma;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.mommatest.pc.momma.Model.IngredientInfoResult;
import com.mommatest.pc.momma.application.ApplicationController;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Ingredient_Info_Activity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ExpandableListView epListView_info;
    private ExpandableListAdapter adapter;
    private Button ingre_info_back, ingre_info_mypage;
    private TextView ingre_info_gohome, ingre_info_name;
    String name, usesinfo, sideinfo;
    IngredientInfoResult.ResultData data;
    EditText ingre_info_search;
    TextView overview;
    private ImageView ingredientrank1_IV, ingredientrank2_IV, ingredientrank3_IV, ingredientrank4_IV, ingre_info_searchgo;
    private TextView  ingredientrank1_TV1, ingredientrank1_TV2, ingredientrank2_TV1, ingredientrank2_TV2, ingredientrank3_TV1, ingredientrank3_TV2, ingredientrank4_TV1, ingredientrank4_TV2;
    Button manyIngredient_BT;
    LinearLayout manyIngredientlayout, ingredientrank1;
    boolean expend = true;

    private static final String TAG_TAB = "TAB";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.mommatest.pc.momma.R.layout.ingredient_info);

        ingre_info_back = (Button)findViewById(com.mommatest.pc.momma.R.id.ingre_info_back);
        ingre_info_gohome = (TextView)findViewById(com.mommatest.pc.momma.R.id.ingre_info_gohome);
        ingre_info_mypage = (Button)findViewById(com.mommatest.pc.momma.R.id.ingre_info_mypage);
        ingre_info_search =(EditText)findViewById(com.mommatest.pc.momma.R.id.ingre_info_search);
        ingre_info_searchgo = (ImageView)findViewById(com.mommatest.pc.momma.R.id.ingre_info_searchgo);

        ingre_info_searchgo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent searchintent = new Intent(getApplicationContext(), SearchResult.class);
                searchintent.putExtra("searchresult", ingre_info_search.getText().toString());
                searchintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                searchintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(searchintent);
            }
        });

        ingre_info_mypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mypage = new Intent(getApplicationContext(), MyPage.class);
                mypage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mypage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mypage);
            }
        });

        ingre_info_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ingre_info_gohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gohome = new Intent(getApplicationContext(), FragmentMain.class);
                gohome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                gohome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(gohome);
            }
        });

        manyIngredient_BT = (Button) findViewById(com.mommatest.pc.momma.R.id.IngredientLIst_BT);
        manyIngredientlayout = (LinearLayout) findViewById(com.mommatest.pc.momma.R.id.Ingredientmilks);
        ingredientrank1 = (LinearLayout) findViewById(com.mommatest.pc.momma.R.id.Ingredientmilks);

        ingredientrank1_IV = (ImageView) findViewById(com.mommatest.pc.momma.R.id.ingretabrank1_IV);
        ingredientrank2_IV = (ImageView) findViewById(com.mommatest.pc.momma.R.id.ingretabrank2_IV);
        ingredientrank3_IV = (ImageView) findViewById(com.mommatest.pc.momma.R.id.ingretabrank3_IV);
        ingredientrank4_IV = (ImageView) findViewById(com.mommatest.pc.momma.R.id.ingretabrank4_IV);

        ingredientrank1_TV1 = (TextView) findViewById(com.mommatest.pc.momma.R.id.ingretabrank1_TV1);
        ingredientrank2_TV1 = (TextView) findViewById(com.mommatest.pc.momma.R.id.ingretabrank2_TV1);
        ingredientrank3_TV1 = (TextView) findViewById(com.mommatest.pc.momma.R.id.ingretabrank3_TV1);
        ingredientrank4_TV1 = (TextView) findViewById(com.mommatest.pc.momma.R.id.ingretabrank4_TV1);

        ingredientrank1_TV2 = (TextView) findViewById(com.mommatest.pc.momma.R.id.ingretabrank1_TV2);
        ingredientrank2_TV2 = (TextView) findViewById(com.mommatest.pc.momma.R.id.ingretabrank2_TV2);
        ingredientrank3_TV2 = (TextView) findViewById(com.mommatest.pc.momma.R.id.ingretabrank3_TV2);
        ingredientrank4_TV2 = (TextView) findViewById(com.mommatest.pc.momma.R.id.ingretabrank4_TV2);

        manyIngredient_BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (expend == true) {
                    manyIngredientlayout.setVisibility(View.VISIBLE);
                    manyIngredient_BT.setBackgroundResource(com.mommatest.pc.momma.R.drawable.ingredient_minus);
                    expend = false;
                } else {
                    manyIngredientlayout.setVisibility(View.INVISIBLE);
                    manyIngredient_BT.setBackgroundResource(com.mommatest.pc.momma.R.drawable.ingredient_add);
                    expend = true;
                }
            }
        });

        Intent ingre_name = getIntent();
        name = ingre_name.getExtras().getString("ingre_name");
        ingre_info_name = (TextView) findViewById(com.mommatest.pc.momma.R.id.ingre_info_name);
        ingre_info_name.setText(name);
        getingre_info();


        overview = (TextView) findViewById(com.mommatest.pc.momma.R.id.overview_info);

    }

    public void getingre_info() {
        Log.i("들옴", "여기");
        Call<IngredientInfoResult> ingre_result = ApplicationController.getInstance().getNetworkService().getIngredientInfoResult(name);
        Log.i("들옴", "여기2");
        ingre_result.enqueue(new Callback<IngredientInfoResult>() {
            @Override
            public void onResponse(Call<IngredientInfoResult> call, Response<IngredientInfoResult> response) {
                if (response.isSuccessful()) {
                    data = response.body().result;
                    usesinfo = data.ingredient_info.uses.toString();
                    sideinfo = data.ingredient_info.side.toString();
                    overview.setText(data.ingredient_info.overview.toString());
                    Log.i("들옴", "여기3");

                    ArrayList<String> arr1 = new ArrayList<String>();
                    arr1.add("Uses");
                    arr1.add("Side effects");


                    final ArrayList<ArrayList<ExpendItemDatas>> arr2 = new ArrayList<ArrayList<ExpendItemDatas>>();
                    final ArrayList<ExpendItemDatas> arrchild = new ArrayList<ExpendItemDatas>();
                    ArrayList<ExpendItemDatas> arrchild2 = new ArrayList<ExpendItemDatas>();
                    ArrayList<ExpendItemDatas> arrchild3 = new ArrayList<ExpendItemDatas>();

                    ExpendItemDatas uses = new ExpendItemDatas(usesinfo, "효능", com.mommatest.pc.momma.R.drawable.ingredient_uses);
                    ExpendItemDatas sideeffects = new ExpendItemDatas(sideinfo, "부작용", com.mommatest.pc.momma.R.drawable.ingredient_sideeffect);

                    arrchild.add(uses);
                    arrchild2.add(sideeffects);

                    arr2.add(arrchild);
                    arr2.add(arrchild2);

                    epListView_info = (ExpandableListView) findViewById(com.mommatest.pc.momma.R.id.expandablelist_info);
                    adapter = new ExpandableListAdapter(getApplicationContext(), arr1, arr2);

                    epListView_info.setAdapter(adapter);
                    int count = adapter.getGroupCount();
                    for (int i = 0; i < count; i++) {
                        epListView_info.collapseGroup(i);
                    }

                    ingredientrank1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent ingre_rank = new Intent(getApplicationContext(), Drymilklevel.class);
                            ingre_rank.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            ingre_rank.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            ingre_rank.putExtra("rank", name);
                            startActivity(ingre_rank);
                        }
                    });
                    rank();
                }
            }

            @Override
            public void onFailure(Call<IngredientInfoResult> call, Throwable t) {
                Log.i("들옴", "여기4");

            }
        });


    }

    private void rank() {
        Glide.with(getApplicationContext())
                .load(data.rank.get(0).milk_image)
                .into(ingredientrank1_IV);

        Glide.with(getApplicationContext())
                .load(data.rank.get(1).milk_image)
                .into(ingredientrank2_IV);

        Glide.with(getApplicationContext())
                .load(data.rank.get(2).milk_image)
                .into(ingredientrank3_IV);

        Glide.with(getApplicationContext())
                .load(data.rank.get(3).milk_image)
                .into(ingredientrank4_IV);
    }
}



