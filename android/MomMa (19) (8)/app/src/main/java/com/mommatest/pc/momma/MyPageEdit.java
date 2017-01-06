package com.mommatest.pc.momma;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mommatest.pc.momma.Model.MyPageEditResult;
import com.mommatest.pc.momma.Model.MyPageInfo;
import com.mommatest.pc.momma.application.ApplicationController;
import com.mommatest.pc.momma.network.NetworkService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyPageEdit extends AppCompatActivity {

    Button EditGirl;
    Button EditBoy;
    Button EditNo;
    Button EditAllergy;
    Button EditAtopy;
    Button EditBowel;
    TextView EditMypage1;
    TextView EditMypage2;
    TextView p_name, b_birth, b_name, p_nickname;
    EditText edit_birth;
    int b_month;

    boolean EditGirl_tf = true;
    boolean EditBoy_tf = true;
    boolean EditNo_tf = true;
    boolean EditAllergy_tf = true;
    boolean EditAtopy_tf = true;
    boolean EditBowel_tf = true;
    ImageView mypageback, mpok;
    NetworkService networkService;
    String nickname;
    MyPageInfo myPageInfo;
    MyPageEditResult data;
    String w1, w2, w3;
    //  MyPageResult.ResultData exData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypageedit);
        networkService = ApplicationController.getInstance().getNetworkService();

        // findViewById 정의한곳
        initView();

        // 마이페이지에서 기본 정보 받는 부분
        Intent intent = getIntent();
        p_name.setText(intent.getStringExtra("p_name"));
        p_nickname.setText(intent.getStringExtra("p_nickname"));
        nickname = intent.getStringExtra("nickname");
        b_birth.setText(intent.getStringExtra("b_birth"));
        b_name.setText(intent.getStringExtra("b_name"));
        w1 = intent.getStringExtra("b_worry1");
        w2 = intent.getStringExtra("b_worry2");
        w3 = intent.getStringExtra("b_worry3");

        // 아이 성별에 따라 버튼 색 바뀜
        if (intent.getStringExtra("b_sex").equals("남")) {
            EditBoy.setBackgroundResource(R.drawable.signup_boy_click);
            EditBoy_tf = true;
            EditGirl_tf = false;
        } else {
            EditGirl.setBackgroundResource(R.drawable.signup_girl_click);
            EditBoy_tf = false;
            EditGirl_tf = true;
        }

        // 아이 고민에 따라 버튼 색 활성화
        if (w1.equals("") && w2.equals("") && w3.equals("")) {
            EditBowel.setBackgroundResource(R.drawable.signup_bowel_click);
            EditNo_tf = true;
            EditBowel_tf = false;
            EditAtopy_tf = false;
            EditAllergy_tf = false;
        }
        if (w1.toString().equals("알레르기")) {
            EditAllergy.setBackgroundResource(R.drawable.signup_allergy_click);
            EditNo_tf = false;
            EditBowel_tf = false;
            EditAtopy_tf = false;
            EditAllergy_tf = true;
        }
        if (w2.toString().equals("아토피")) {
            EditAtopy.setBackgroundResource(R.drawable.signup_atopy_click);
            EditNo_tf = false;
            EditBowel_tf = false;
            EditAtopy_tf = true;
            EditAllergy_tf = false;
        }
        if (w3.equals("배변")) {
            EditBowel.setBackgroundResource(R.drawable.signup_bowel_click);
            EditNo_tf = false;
            EditBowel_tf = true;
            EditAtopy_tf = false;
            EditAllergy_tf = false;
        }


        mypageback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mypageback = new Intent(getApplicationContext(), MyPage.class);
                mypageback.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mypageback.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mypageback);
            }
        });


        mpok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getnetwork();

                Intent mypageok = new Intent(getApplicationContext(), FragmentMain.class);
                mypageok.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mypageok.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mypageok);
            }
        });


        EditMypage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "서비스 준비중입니다..", Toast.LENGTH_SHORT).show();

            }
        });


        EditMypage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "서비스 준비중입니다..", Toast.LENGTH_SHORT).show();

            }
        });


        EditGirl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (EditGirl_tf) {  // 이미 여아가 선택되어 있는 경우
                    EditGirl.setBackgroundResource(R.drawable.signup_girl); // 선택해제
                    EditBoy.setBackgroundResource(R.drawable.signup_boy_click);

                    EditGirl_tf = false;
                    EditBowel_tf = true;
                } else {
                    EditGirl.setBackgroundResource(R.drawable.signup_girl_click);
                    EditBoy.setBackgroundResource(R.drawable.signup_boy);
                    EditGirl_tf = true;
                    EditBoy_tf = false;
                }
            }
        });


        EditBoy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (EditBoy_tf) {
                    EditBoy.setBackgroundResource(R.drawable.signup_boy);
                    EditGirl.setBackgroundResource(R.drawable.signup_girl_click);
                    EditBoy_tf = false;
                    EditGirl_tf = true;
                } else {
                    EditBoy.setBackgroundResource(R.drawable.signup_boy_click);
                    EditGirl.setBackgroundResource(R.drawable.signup_girl);
                    EditBoy_tf = true;
                    EditGirl_tf = false;
                }
            }
        });


        EditAllergy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (EditAllergy_tf) {
                    EditAllergy.setBackgroundResource(R.drawable.signup_allergy);
                    EditAllergy_tf = false;
                } else {
                    EditAllergy.setBackgroundResource(R.drawable.signup_allergy_click);
                    EditNo.setBackgroundResource(R.drawable.signup_no);
                    EditNo_tf = false;
                    EditAllergy_tf = true;
                }
            }
        });


        EditAtopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (EditAtopy_tf) {
                    EditAtopy.setBackgroundResource(R.drawable.signup_atopy);
                    EditAtopy_tf = false;
                } else {
                    EditAtopy.setBackgroundResource(R.drawable.signup_atopy_click);
                    EditNo.setBackgroundResource(R.drawable.signup_no);
                    EditAtopy_tf = true;
                    EditNo_tf = false;
                }
            }
        });


        EditBowel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (EditBowel_tf) {
                    EditBowel.setBackgroundResource(R.drawable.signup_bowel);
                    EditBowel_tf = false;
                } else {
                    EditBowel.setBackgroundResource(R.drawable.signup_bowel_click);
                    EditNo.setBackgroundResource(R.drawable.signup_no);
                    EditBowel_tf = true;
                    EditNo_tf = false;
                }
            }
        });


        EditNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!EditNo_tf) { // false인 경우
                    EditNo.setBackgroundResource(R.drawable.signup_no_click);
                    EditAtopy.setBackgroundResource(R.drawable.signup_atopy);
                    EditBowel.setBackgroundResource(R.drawable.signup_bowel);
                    EditAllergy.setBackgroundResource(R.drawable.signup_allergy);

                    EditNo_tf = true;
                    EditAtopy_tf = false;
                    EditAllergy_tf = false;
                    EditBowel_tf = false;

                } else {
                    EditNo.setBackgroundResource(R.drawable.signup_no_click);

                    EditNo_tf = false;
                }

            }
        });
    }

    private void initView() {
        p_nickname = (TextView) findViewById(R.id.p_nickname);
        edit_birth = (EditText) findViewById(R.id.birth);
        p_name = (TextView) findViewById(R.id.p_name);
        b_birth = (TextView) findViewById(R.id.b_birth);
        b_name = (TextView) findViewById(R.id.b_name);
        mypageback = (ImageView) findViewById(R.id.mypageback);
        EditMypage1 = (TextView) findViewById(R.id.EditMypage1);
        EditMypage2 = (TextView) findViewById(R.id.EditMypage2);
        EditGirl = (Button) findViewById(R.id.EditGirl);
        EditBoy = (Button) findViewById(R.id.EditBoy);
        EditAllergy = (Button) findViewById(R.id.EditAllergy);
        EditAtopy = (Button) findViewById(R.id.EditAtopy);
        EditBowel = (Button) findViewById(R.id.EditBowel);
        EditNo = (Button) findViewById(R.id.EditNo);
        mpok = (ImageView) findViewById(R.id.mpok);
    }

    public void getnetwork() {
        myPageInfo = new MyPageInfo();

        myPageInfo.b_birth = edit_birth.getText().toString();
        if (EditBoy_tf) {
            myPageInfo.sex = "남";
        } else {
            myPageInfo.sex = "여";
        }
        if (EditAllergy_tf) {
            myPageInfo.worry1 = "알레르기";
        } else {
            myPageInfo.worry1 = "";
        }
        if (EditAtopy_tf) {
            myPageInfo.worry2 = "아토피";
        } else {
            myPageInfo.worry2 = "";
        }
        if (EditBowel_tf) {
            myPageInfo.worry3 = "배변";
        } else {
            myPageInfo.worry3 = "";
        }

        Call<MyPageEditResult> mypage = networkService.getMyPageEditResult(nickname, myPageInfo);
        mypage.enqueue(new Callback<MyPageEditResult>() {
            @Override
            public void onResponse(Call<MyPageEditResult> call, Response<MyPageEditResult> response) {
                if (response.isSuccessful()) {
                    data = response.body();

                    if (data.isResult() == true) {
                        Log.i("MyTag3", "수정성공");
                    } else {
                        Log.i("MyTag3", "수정실패");
                    }

                } else {
                    int statusCode = response.code();
                    Log.i("MyTag3", "응답코드 : " + statusCode);
                }
            }

            @Override
            public void onFailure(Call<MyPageEditResult> call, Throwable t) {
                Log.i("MyTag3", "실패");
            }
        });

    }


}