package com.mommatest.pc.momma;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mommatest.pc.momma.Model.LoginInfo;
import com.mommatest.pc.momma.Model.LoginResult;
import com.mommatest.pc.momma.application.ApplicationController;
import com.mommatest.pc.momma.network.NetworkService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 지미 on 2016-12-28.
 */

public class Login extends AppCompatActivity {

    private Button Login_BT;
    private ImageView Signup_BT;
    private EditText id_ET, password_ET;
    NetworkService networkService;
    private CheckBox saveid, autologin;
    SharedPreferences setting;
    SharedPreferences.Editor editor;
    String uri1, uri2, uri3, uri4, uri5, uri6;
    LoginResult.ResultData data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        networkService = ApplicationController.getInstance().getNetworkService();

        id_ET = (EditText) findViewById(R.id.Id_ET);
        password_ET = (EditText) findViewById(R.id.Password_ET);
        saveid = (CheckBox) findViewById(R.id.SaveId_CB);
        autologin = (CheckBox) findViewById(R.id.AutoLogin_CB);

        setting = getSharedPreferences("setting", MODE_PRIVATE);
        editor = setting.edit();

        if (setting.getBoolean("Auto_Login_enabled", false)) {
            id_ET.setText(setting.getString("ID", ""));
            password_ET.setText(setting.getString("PW", ""));
            autologin.setChecked(true);
            networkLogin();
            Toast.makeText(getApplicationContext(), "자동 로그인 되었습니다.", Toast.LENGTH_LONG).show();
        }
        autologin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                // TODO Auto-generated method stub

                if (isChecked) {
                    String ID = id_ET.getText().toString();
                    String PW = password_ET.getText().toString();
                    editor.putString("ID", ID);
                    editor.putString("PW", PW);
                    editor.putBoolean("Auto_Login_enabled", true);
                    editor.commit();


                } else {
			editor.remove("ID");
			editor.remove("PW");
			editor.remove("Auto_Login_enabled");
                    editor.clear();
                    editor.commit();

                }
            }
        });



        Signup_BT = (ImageView) findViewById(R.id.SignUp_BT);
        Login_BT = (Button) findViewById(R.id.Login_BT);

        Login_BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(id_ET.getText().toString()) || TextUtils.isEmpty(password_ET.getText().toString())) {
                    Toast.makeText(Login.this, "아이디와 비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                networkLogin();
            }
        });

        Signup_BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backIntent = new Intent(getApplicationContext(), SignUp1.class);
                startActivity(backIntent);
            }
        });


    }

    private void networkLogin() {
        final LoginInfo login = new LoginInfo();
        login.p_email = id_ET.getText().toString();
        login.p_password = password_ET.getText().toString();
        Call<LoginResult> loginResultCall = networkService.getLoginResult(login);
        loginResultCall.enqueue(new Callback<LoginResult>() {
            @Override
            public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                if (response.isSuccessful()) {
                    data = response.body().result;
                    if (data.signresult.signin == true) {
                        editor.putString("p_nickname", data.signresult.p_nickname);
                        editor.commit();
                        Intent homeview = new Intent(getApplicationContext(), FragmentMain.class);
                        homeview.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        homeview.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(homeview);

                    } else {
                        Toast.makeText(getApplicationContext(), "아이디 혹은 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<LoginResult> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed to load", Toast.LENGTH_LONG).show();
                Log.i("MyTag", "에러내용 : " + t.getMessage());
                finish();
            }
        });
    }


}

