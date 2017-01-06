package com.mommatest.pc.momma;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mommatest.pc.momma.Model.IngredientInfoResult;
import com.mommatest.pc.momma.application.ApplicationController;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IngredientActivity extends Fragment {


    private ExpandableListView epListView;
    private TextView searchTV, ingredientrank1_TV1,ingredientrank1_TV2,ingredientrank2_TV1,ingredientrank2_TV2,ingredientrank3_TV1,ingredientrank3_TV2,ingredientrank4_TV1,ingredientrank4_TV2;
    private ImageView ingredientrank1_IV,ingredientrank2_IV,ingredientrank3_IV,ingredientrank4_IV;
    private LinearLayout ingredientrank1, ingredientrank2, ingredientrank3, ingredientrank4;

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    private ExpandableListAdapter adapter;
    private String strColor = "#67C570";
    public Activity activity;
    String name, usesinfo, sideinfo;
    IngredientInfoResult.ResultData data;
    TextView overview;
    Button manyIngredient_BT;
    LinearLayout manyIngredientlayout;
    boolean expend = true;


    public IngredientActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(com.mommatest.pc.momma.R.layout.ingredient, null);
        manyIngredient_BT = (Button)view.findViewById(com.mommatest.pc.momma.R.id.IngredientLIst_BT);
        manyIngredientlayout = (LinearLayout)view.findViewById(com.mommatest.pc.momma.R.id.Ingredientmilks);

        ingredientrank1 = (LinearLayout)view.findViewById(com.mommatest.pc.momma.R.id.Ingredientmilks);




        ingredientrank1_IV = (ImageView)view.findViewById(com.mommatest.pc.momma.R.id.ingretabrank1_IV);
        ingredientrank2_IV = (ImageView)view.findViewById(com.mommatest.pc.momma.R.id.ingretabrank2_IV);
        ingredientrank3_IV = (ImageView)view.findViewById(com.mommatest.pc.momma.R.id.ingretabrank3_IV);
        ingredientrank4_IV = (ImageView)view.findViewById(com.mommatest.pc.momma.R.id.ingretabrank4_IV);

        ingredientrank1_TV1 = (TextView)view.findViewById(com.mommatest.pc.momma.R.id.ingretabrank1_TV1);
        ingredientrank2_TV1 = (TextView)view.findViewById(com.mommatest.pc.momma.R.id.ingretabrank2_TV1);
        ingredientrank3_TV1 = (TextView)view.findViewById(com.mommatest.pc.momma.R.id.ingretabrank3_TV1);
        ingredientrank4_TV1 = (TextView)view.findViewById(com.mommatest.pc.momma.R.id.ingretabrank4_TV1);

        ingredientrank1_TV2 = (TextView)view.findViewById(com.mommatest.pc.momma.R.id.ingretabrank1_TV2);
        ingredientrank2_TV2 = (TextView)view.findViewById(com.mommatest.pc.momma.R.id.ingretabrank2_TV2);
        ingredientrank3_TV2 = (TextView)view.findViewById(com.mommatest.pc.momma.R.id.ingretabrank3_TV2);
        ingredientrank4_TV2 = (TextView)view.findViewById(com.mommatest.pc.momma.R.id.ingretabrank4_TV2);

        manyIngredient_BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(expend == true) {
                    manyIngredientlayout.setVisibility(View.VISIBLE);
                    manyIngredient_BT.setBackgroundResource(com.mommatest.pc.momma.R.drawable.ingredient_minus);
                    expend = false;
                }
                else{
                    manyIngredientlayout.setVisibility(View.INVISIBLE);
                    manyIngredient_BT.setBackgroundResource(com.mommatest.pc.momma.R.drawable.ingredient_add);
                    expend = true;
                }
            }
        });

        epListView = (ExpandableListView) view.findViewById(com.mommatest.pc.momma.R.id.expandablelist);

        overview = (TextView) view.findViewById(com.mommatest.pc.momma.R.id.overview);
        searchTV = (TextView)view.findViewById(com.mommatest.pc.momma.R.id.searchTV);

        final Spinner ingredientsp = (Spinner)view.findViewById(com.mommatest.pc.momma.R.id.ingredientsp);

        ArrayAdapter<CharSequence> ingredientspAdapter = ArrayAdapter.createFromResource(activity,
                com.mommatest.pc.momma.R.array.arrays, com.mommatest.pc.momma.R.layout.spinner_item);

        ingredientspAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ingredientsp.setAdapter(ingredientspAdapter);

        ingredientsp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public int hashCode() {
                return super.hashCode();
            }

            @Override
            public void onItemSelected(AdapterView<?> parent, View views, int position, long id) {
                name = ingredientsp.getSelectedItem().toString();
                Call<IngredientInfoResult> ingre_result = ApplicationController.getInstance().getNetworkService().getIngredientInfoResult(name);
                ingre_result.enqueue(new Callback<IngredientInfoResult>() {
                    @Override
                    public void onResponse(Call<IngredientInfoResult> call, Response<IngredientInfoResult> response) {
                        if(response.isSuccessful()){
                            data = response.body().result;
                            usesinfo= data.ingredient_info.uses.toString();
                            sideinfo=data.ingredient_info.side.toString();
                            overview.setText(data.ingredient_info.overview.toString());

                            ArrayList<String> arr1 = new ArrayList<String>();
                            arr1.add("Uses");
                            arr1.add("Side effects");

                            final ArrayList<ArrayList<ExpendItemDatas>> arr2 = new ArrayList<ArrayList<ExpendItemDatas>>();
                            final ArrayList<ExpendItemDatas> arrchild = new ArrayList<ExpendItemDatas>();
                            ArrayList<ExpendItemDatas> arrchild2 = new ArrayList<ExpendItemDatas>();

                            ExpendItemDatas uses = new ExpendItemDatas(usesinfo, "효능", com.mommatest.pc.momma.R.drawable.ingredient_uses);
                            ExpendItemDatas sideeffects = new ExpendItemDatas(sideinfo, "부작용", com.mommatest.pc.momma.R.drawable.ingredient_sideeffect);

                            arrchild.add(uses);
                            arrchild2.add(sideeffects);

                            arr2.add(arrchild);
                            arr2.add(arrchild2);


                            adapter = new ExpandableListAdapter(activity, arr1, arr2);
                            adapter.notifyDataSetChanged();

                            epListView.setAdapter(adapter);
                            int count = adapter.getGroupCount();
                            for (int i = 0; i < count; i++) {
                                epListView.collapseGroup(i);
                            }

                            ingredientrank1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent ingre_rank = new Intent(getActivity(), Drymilklevel.class);
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
                    }
                });
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }

    private void rank() {
        Glide.with(getActivity())
                .load(data.rank.get(0).milk_image)
                .into(ingredientrank1_IV);

        Glide.with(getActivity())
                .load(data.rank.get(1).milk_image)
                .into(ingredientrank2_IV);

        Glide.with(getActivity())
                .load(data.rank.get(2).milk_image)
                .into(ingredientrank3_IV);

        Glide.with(getActivity())
                .load(data.rank.get(3).milk_image)
                .into(ingredientrank4_IV);

    }

}




