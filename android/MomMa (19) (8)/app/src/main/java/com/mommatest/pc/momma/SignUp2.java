package com.mommatest.pc.momma;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.mommatest.pc.momma.Model.SignUpResult;
import com.mommatest.pc.momma.Model.UserInfo;
import com.mommatest.pc.momma.application.ApplicationController;
import com.mommatest.pc.momma.network.NetworkService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by 지미 on 2016-12-27.
 */

public class SignUp2 extends AppCompatActivity {

    ImageView back_BT;
    Button allergy;
    Button bowel;
    Button girl;
    Button boy;
    Button atopy;
    Button noclick;
    ImageView ok_IN;
    boolean tf=true;
    boolean allergy_tf=true;
    boolean bowwel_tf=true;
    boolean atopy_tf=true;
    boolean girl_tf=true;
    boolean boy_tf=true;
    String sex, worry1, worry2, worry3;
    EditText b_name, b_birth;
    String email, name, passwd, nickname, birth, p_passcheck;
    NetworkService networkService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);
        networkService = ApplicationController.getInstance().getNetworkService();
        backClick();

        b_name = (EditText)findViewById(R.id.b_name);
        b_birth = (EditText)findViewById(R.id.b_birth);

        Intent signup = getIntent();
        email = signup.getExtras().getString("p_email");
        name = signup.getExtras().getString("p_name");
        passwd = signup.getExtras().getString("p_pw1");
        nickname = signup.getExtras().getString("p_nickname");
        birth = signup.getExtras().getString("p_birth");



        ok_IN = (ImageView) findViewById(R.id.ok_IM);

        ok_IN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserInfo user = new UserInfo();
                user.p_name = name;
                user.p_email = email;
                user.p_birth= birth;
                user.p_nickname = nickname;
                user.p_password = passwd;
                user.b_birth = b_birth.getText().toString();
                user.b_name = b_name.getText().toString();
                if(worry1==null)
                    worry1="";
                if(worry2==null)
                    worry2="";
                if(worry3==null)
                    worry3="";
                user.worry1 = worry1;
                user.worry2 = worry2;
                user.worry3 = worry3;
                user.sex = sex;


                Log.i("email", user.p_email);
                if( b_name.getText().toString().length() == 0 ) {
                    Toast.makeText(getApplicationContext(), "이름을 입력하세요.", Toast.LENGTH_SHORT).show();
                    b_name.requestFocus();
                    return;
                }

                if( b_birth.getText().toString().length() == 0 ) {
                    Toast.makeText(getApplicationContext(), "생년월일을 입력하시오.", Toast.LENGTH_SHORT).show();
                    b_birth.requestFocus();
                    return;
                }else if( b_birth.getText().toString().length() < 8) {
                    Toast.makeText(getApplicationContext(), "형식에 맞게 입력해주세요.", Toast.LENGTH_SHORT).show();
                    b_birth.requestFocus();
                    return;
                }


                Call<SignUpResult> signupResultCall = networkService.getSignResult(user);
                signupResultCall.enqueue(new Callback<SignUpResult>() {
                    @Override
                    public void onResponse(Call<SignUpResult> call, Response<SignUpResult> response) {
                        Log.i("MyTag", response.message());
                       Log.i("MyTag", "response code : " + response.code());

                        if (response.isSuccessful()) {
                            SignUpResult signinResult = response.body();
                            Log.i("MyTag", "결과 : " + signinResult);
                        } else {
                            int statusCode = response.code();
                            Log.i("MyTag", "응답코드 : " + statusCode);
                        }
                    }

                    @Override
                    public void onFailure(Call<SignUpResult> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Failed to load", Toast.LENGTH_LONG).show();
                        Log.i("MyTag", "에러내용 : " + t.getMessage());
                        finish();
                    }
                });



                Intent LoginIntent = new Intent(getApplicationContext(), Login.class);
                LoginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                LoginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(LoginIntent);

            }
        });





        //
        allergy=(Button)findViewById(R.id.allergy_BT);
        bowel=(Button)findViewById(R.id.bowel_BT);
        girl=(Button)findViewById(R.id.girl_BT);
        boy=(Button)findViewById(R.id.boy_BT);
        atopy=(Button)findViewById(R.id.atopy_BT);
        noclick=(Button)findViewById(R.id.signupno_BT);

        if(girl_tf==false&&boy_tf==false){
            ok_IN.setClickable(false);
        }

        allergy.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                if(allergy_tf) {
                    allergy.setBackgroundResource(R.drawable.signup_allergy_click);
                    worry1="알레르기";
                    if(tf==false){
                        noclick.setBackgroundResource(R.drawable.signup_no);
                    }

                    allergy_tf=false;


                }
                else {

                    allergy.setBackgroundResource(R.drawable.signup_allergy);
                    worry1="";
                    allergy_tf=true;

                }
            }
        });


        bowel.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                if(bowwel_tf) {
                    bowel.setBackgroundResource(R.drawable.signup_bowel_click);
                    worry3 = "배변";
                    if(tf==false){

                        noclick.setBackgroundResource(R.drawable.signup_no);
                    }
                    bowwel_tf=false;

                }
                else {
                    bowel.setBackgroundResource(R.drawable.signup_bowel);
                    worry3 = "";
                    bowwel_tf=true;

                }
            }
        });


        girl.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                if(girl_tf) {
                    girl.setBackgroundResource(R.drawable.signup_girl_click);
                    sex = "여";
                    if(boy_tf==false){
                        boy.setBackgroundResource(R.drawable.signup_boy);
                    }
                    girl_tf=false;
                }
                else {
                    girl.setBackgroundResource(R.drawable.signup_girl);
                    girl_tf=true;
                }
            }
        });


        boy.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                if(boy_tf) {
                    boy.setBackgroundResource(R.drawable.signup_boy_click);
                    sex = "남";
                    if(girl_tf==false){
                        girl.setBackgroundResource(R.drawable.signup_girl);
                    }
                    boy_tf=false;
                }
                else {
                    boy.setBackgroundResource(R.drawable.signup_boy);
//                    count--;
                    boy_tf=true;
                }
            }
        });


        atopy.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                if(atopy_tf) {
                    atopy.setBackgroundResource(R.drawable.signup_atopy_click);
                    worry2="아토피";
                    if(tf==false){

                        noclick.setBackgroundResource(R.drawable.signup_no);
                    }
//                    count++;
//                    if(count>1)
//                    {
//                        tf=true;
//                        noclick.setBackgroundResource(R.drawable.signup_no);
//
//                    }
                    atopy_tf=false;
                }
                else {
                    atopy.setBackgroundResource(R.drawable.signup_atopy);
                    worry2="";
                    atopy_tf=true;

                }
            }
        });


        noclick.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                if(tf) {
                    noclick.setBackgroundResource(R.drawable.signup_no_click);
                    atopy.setBackgroundResource(R.drawable.signup_atopy);
                    bowel.setBackgroundResource(R.drawable.signup_bowel);
                    allergy.setBackgroundResource(R.drawable.signup_allergy);
                    worry1 = "";
                    worry2 = "";
                    worry3 = "";

                    tf=false;





                }
                else {
                    noclick.setBackgroundResource(R.drawable.signup_no);
                    atopy.setClickable(true);
                    bowel.setClickable(true);
                    allergy.setClickable(true);
                    tf=true;
                }

            }
        });

    }

    public void backClick() {
        back_BT=(ImageView) findViewById(R.id.back_IM);

        back_BT.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent backIntent=new Intent(getApplicationContext(),SignUp1.class);
                startActivity(backIntent);
            }
        });
    }




}