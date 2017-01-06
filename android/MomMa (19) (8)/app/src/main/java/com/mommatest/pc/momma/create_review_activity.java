package com.mommatest.pc.momma;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mommatest.pc.momma.Model.CreateReviewResult;
import com.mommatest.pc.momma.Model.ReviewResult;
import com.mommatest.pc.momma.Model.ReviewWriteInfo;
import com.mommatest.pc.momma.application.ApplicationController;
import com.mommatest.pc.momma.network.NetworkService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 지미 on 2016-12-28.
 */

public class create_review_activity extends AppCompatActivity {

    SharedPreferences setting;
    SharedPreferences.Editor editor;

    NetworkService networkService;

    RatingBar create_rating_RB;
    String CreateRate_TV;

    Button allergyCK;
    Button bowelCK;
    Button atopyCK;
    Button nonoCK;

    EditText OneLine, Good, Bad, Tip;
    TextView OneLineNum, GoodNum, BadNum, TipNum;
    ImageView createreviewback;
    ImageView createenroll;
    ImageView img;
    TextView milknm, companynm;

    boolean nonoCK_tf=true;
    boolean allergyCK_tf=true;
    boolean bowwelCK_tf=true;
    boolean atopyCK_tf=true;

    String milk_name;
    String nick_name;
    CreateReviewResult.ResultData data;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_review);

        networkService = ApplicationController.getInstance().getNetworkService();

        setting = getSharedPreferences("setting", MODE_PRIVATE);
        editor = setting.edit();
        nick_name = setting.getString("p_nickname","");

        img = (ImageView)findViewById(R.id.CreatereviewDrymilk_IV);
        milknm = (TextView)findViewById(R.id.CreatereviewDrymilk_TV);
        companynm = (TextView)findViewById(R.id.CreatereviewCompany_TV);

        create_rating_RB = (RatingBar)findViewById(R.id.CreatereviewGrade_RB);

        createenroll = (ImageView) findViewById(R.id.CreateReviewEnroll_IV);
        OneLine = (EditText) findViewById(R.id.CreateReviewTitle_ET);
        Good = (EditText) findViewById(R.id.CreateReviewGood_ET);
        Bad = (EditText) findViewById(R.id.CreateReviewBad_ET);
        Tip = (EditText) findViewById(R.id.CreateReviewHoney_ET);

        OneLineNum = (TextView)findViewById(R.id.ReviewTitleNum_TV);
        GoodNum = (TextView)findViewById(R.id.ReviewGoodNum_TV);
        BadNum = (TextView)findViewById(R.id.ReviewBadNum_TV);
        TipNum = (TextView)findViewById(R.id.ReviewHoneyNum_TV);

        createreviewback = (ImageView)findViewById(R.id.createreviewback);
        createreviewback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        final Spinner drymilksp = (Spinner)findViewById(R.id.drymilksp);

        ArrayAdapter<CharSequence> drymilkspAdapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.milk_array, R.layout.spinner_item);

        drymilkspAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        drymilksp.setAdapter(drymilkspAdapter);

        drymilksp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public int hashCode() {
                return super.hashCode();
            }

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                milk_name = drymilksp.getSelectedItem().toString();
                Call<CreateReviewResult> createreview = networkService.getcreatereviewresult(milk_name, nick_name);
                createreview.enqueue(new Callback<CreateReviewResult>() {
                    @Override
                    public void onResponse(Call<CreateReviewResult> call, Response<CreateReviewResult> response) {
                        if(response.isSuccessful()) {
                            data = response.body().result;
                            milknm.setText(milk_name);
                            companynm.setText(data.drymilk_info.milk_company);
                            Glide.with(getApplicationContext()).load(data.drymilk_info.milk_image).into(img);

                            allergyCK.setClickable(false);
                            atopyCK.setClickable(false);
                            bowelCK.setClickable(false);
                            nonoCK.setClickable(false);

                            if(!data.worry.get(0).equals("")){
                                allergyCK.setBackgroundResource(R.drawable.signup_allergy_click);}
                            if(!data.worry.get(1).equals(""))
                                atopyCK.setBackgroundResource(R.drawable.signup_atopy_click);
                            if(!data.worry.get(2).equals(""))
                                bowelCK.setBackgroundResource(R.drawable.signup_bowel_click);
                            if(data.worry.get(0).equals("")&&data.worry.get(1).equals("")&&data.worry.get(2).equals(""))
                                nonoCK.setBackgroundResource(R.drawable.signup_allergy_click);

                        }
                    }

                    @Override
                    public void onFailure(Call<CreateReviewResult> call, Throwable t) {
                        Log.i("MyTag", "실패");
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        create_rating_RB.setStepSize((float) 0.5);
        create_rating_RB.setRating((float)0);
        create_rating_RB.setIsIndicator(false);

        create_rating_RB.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Log.i("Rating", rating+"");
            }
        });

        //
        allergyCK=(Button)findViewById(R.id.allergyCK);
        allergyCK.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                if(allergyCK_tf) {
                    allergyCK.setBackgroundResource(R.drawable.signup_allergy_click);
                    if(nonoCK_tf==false){

                        nonoCK.setBackgroundResource(R.drawable.signup_no);
                    }

                    allergyCK_tf=false;


                }
                else {
                    allergyCK.setBackgroundResource(R.drawable.signup_allergy);
                    allergyCK_tf=true;

                }
            }
        });

        bowelCK=(Button)findViewById(R.id.bowelCK);
        bowelCK.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                if(bowwelCK_tf) {
                    bowelCK.setBackgroundResource(R.drawable.signup_bowel_click);
                    if(nonoCK_tf==false){

                        nonoCK.setBackgroundResource(R.drawable.signup_no);
                    }
//                    count++;
//                    if(count>1)
//                    {
//                        tf=true;
//                        noclick.setBackgroundResource(R.drawable.signup_no);
//
//                    }
                    bowwelCK_tf=false;

                }
                else {
                    bowelCK.setBackgroundResource(R.drawable.signup_bowel);
                    // count--;
                    bowwelCK_tf=true;
//                    if(count>1)
//                    {
//                        tf=true;
//                        noclick.setBackgroundResource(R.drawable.signup_no);
//
//                    }
                }
            }
        });


        atopyCK=(Button)findViewById(R.id.atopyCk);
        atopyCK.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                if(atopyCK_tf) {
                    atopyCK.setBackgroundResource(R.drawable.signup_atopy_click);
                    if(nonoCK_tf==false){

                        nonoCK.setBackgroundResource(R.drawable.signup_no);
                    }
//                    count++;
//                    if(count>1)
//                    {
//                        tf=true;
//                        noclick.setBackgroundResource(R.drawable.signup_no);
//
//                    }
                    atopyCK_tf=false;
                }
                else {
                    atopyCK.setBackgroundResource(R.drawable.signup_atopy);
                    atopyCK_tf=true;

                }
            }
        });

        nonoCK=(Button)findViewById(R.id.nonoCK);
        nonoCK.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                if(nonoCK_tf) {
                    nonoCK.setBackgroundResource(R.drawable.signup_no_click);
                    atopyCK.setBackgroundResource(R.drawable.signup_atopy);
                    bowelCK.setBackgroundResource(R.drawable.signup_bowel);
                    allergyCK.setBackgroundResource(R.drawable.signup_allergy);

                    nonoCK_tf=false;


                }
                else {
                    nonoCK.setBackgroundResource(R.drawable.signup_no);
                    atopyCK.setClickable(true);
                    bowelCK.setClickable(true);
                    allergyCK.setClickable(true);
                    nonoCK_tf=true;
                }

            }
        });

        OneLine.addTextChangedListener(new TextWatcher() {
            String strCur;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                strCur = s.toString();

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                OneLineNum.setText(String.valueOf(s.length()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Good.addTextChangedListener(new TextWatcher() {
            String strCur;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                strCur = s.toString();

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                GoodNum.setText(String.valueOf(s.length()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Bad.addTextChangedListener(new TextWatcher() {
            String strCur;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                strCur = s.toString();

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                BadNum.setText(String.valueOf(s.length()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Tip.addTextChangedListener(new TextWatcher() {
            String strCur;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                strCur = s.toString();

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                TipNum.setText(String.valueOf(s.length()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        createenroll.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                ReviewWriteInfo reviewWriteInfo = new ReviewWriteInfo();
                reviewWriteInfo.title = OneLine.getText().toString();
                reviewWriteInfo.good = Good.getText().toString();
                reviewWriteInfo.bad = Bad.getText().toString();
                reviewWriteInfo.tip = Tip.getText().toString();
                reviewWriteInfo.milk_name =milk_name;
                reviewWriteInfo.review_grade = create_rating_RB.getRating();
                reviewWriteInfo.review_writer = nick_name;
                Intent nextIntent = new Intent(getApplicationContext(), DetailReview.class);

                if( OneLine.getText().toString().length() == 0 ) {
                    Toast.makeText(getApplicationContext(), "한줄평을 입력하세요.", Toast.LENGTH_SHORT).show();
                    OneLine.requestFocus();
                    return;
                }
                if( Good.getText().toString().length() == 0 ) {
                    Toast.makeText(getApplicationContext(), "좋았던 점을 입력하세요.", Toast.LENGTH_SHORT).show();
                    Good.requestFocus();
                    return;
                }
                if( Bad.getText().toString().length() == 0 ) {
                    Toast.makeText(getApplicationContext(), "아쉬웠던 점을 입력하세요.", Toast.LENGTH_SHORT).show();
                    Bad.requestFocus();
                    return;
                }

                nextIntent.putExtra("OneLine", OneLine.getText().toString());
                nextIntent.putExtra("Good", Good.getText().toString());
                nextIntent.putExtra("Bad", Bad.getText().toString());
                nextIntent.putExtra("Tip", Tip.getText().toString());

                Log.i("OneLine", OneLine.getText().toString());

                Call<ReviewResult> reviewResultCall = networkService.getReviewWrite(reviewWriteInfo,reviewWriteInfo.milk_name,reviewWriteInfo.review_writer);
                reviewResultCall.enqueue(new Callback<ReviewResult>() {
                    @Override
                    public void onResponse(Call<ReviewResult> call, Response<ReviewResult> response) {
                        if (response.isSuccessful()) {
                            ReviewResult reviewResult = response.body();
                            Log.i("MyTag", "성공~");
                        } else {
                            int statusCode = response.code();
                            Log.i("MyTag", "응답코드 : " + statusCode);
                        }
                    }

                    @Override
                    public void onFailure(Call<ReviewResult> call, Throwable t) {
                        Log.i("MyTag", "실패~");
                    }
                });

                startActivity(nextIntent);


            }
        });

        //한줄평 50자안
        InputFilter[] FilterArray = new InputFilter[1];
        FilterArray[0] = new InputFilter.LengthFilter(50);
        OneLine.setFilters(FilterArray);


        //좋은점 500자안
        InputFilter[] FilterArray_1 = new InputFilter[1];
        FilterArray_1[0] = new InputFilter.LengthFilter(500);
        Good.setFilters(FilterArray_1);

        //나쁜점 500자안
        InputFilter[] FilterArray_2 = new InputFilter[1];
        FilterArray_2[0] = new InputFilter.LengthFilter(500);
        Bad.setFilters(FilterArray_2);

        //꿀팁 500자안
        InputFilter[] FilterArray_3 = new InputFilter[1];
        FilterArray_3[0] = new InputFilter.LengthFilter(200);
        Tip.setFilters(FilterArray_3);

    }

}