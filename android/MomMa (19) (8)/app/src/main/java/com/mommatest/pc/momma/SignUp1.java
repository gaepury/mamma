package com.mommatest.pc.momma;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp1 extends AppCompatActivity {

    private EditText m_email, m_name, m_pw1, m_pw2, m_birth, m_nickname;
    private CheckBox total_CHB;
    private CheckBox agreeUI_CHB;
    private CheckBox PI_CHB;
    private ImageView imageView;
    private TextView Next_TV,check;
    private String new_birth;
    private String m_birth_y;
    private String m_birth_m;
    private String m_birth_d;

    private EditText etEmail; //이메일
    private EditText etPassword; //비밀번호
    private EditText etPasswordConfirm; //비밀번호 확인




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup1);

        total_CHB=(CheckBox)findViewById(R.id.total_CHB);
        agreeUI_CHB=(CheckBox)findViewById(R.id.agreeUI_CHB);
        PI_CHB=(CheckBox)findViewById(R.id.PI_CHB);

        check=(TextView) findViewById(R.id.check);
        Next_TV=(TextView) findViewById(R.id.Next_TV);
        imageView=(ImageView)findViewById(R.id.imageView);

        m_email = (EditText) findViewById(R.id.signemail);
        m_name = (EditText) findViewById(R.id.signname);
        m_pw1 = (EditText) findViewById(R.id.signpw1);
        m_pw2 = (EditText) findViewById(R.id.signpw2);
        m_birth = (EditText) findViewById(R.id.signbirth);
        m_nickname = (EditText) findViewById(R.id.signnickname);

        etEmail = (EditText) findViewById(R.id.signemail); //레이아웃에서 이메일
        etPassword = (EditText) findViewById(R.id.signpw1); //비밀번호
        etPasswordConfirm = (EditText) findViewById(R.id.signpw2); //비밀번호 확인


        etPasswordConfirm.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String password = etPassword.getText().toString();
                String confirm = etPasswordConfirm.getText().toString();

                if (hasFocus == false) {
                    Log.i("jimi pw : ", password);
                    Log.i("jimi pwconfirm : ", confirm);
                    if (password.equals(confirm)) {
                        Log.i("jimi 1 : ", " Same ");
                    } else {
                        Log.i("jimi 1 : ", " No ");
                        etPasswordConfirm.setText("");
                        Toast.makeText(getApplicationContext(), "비밀번호가 다릅니다.", Toast.LENGTH_SHORT).show();

                    }

                    /*
                    etPasswordConfirm.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            String password = etPassword.getText().toString();
                            String confirm = etPasswordConfirm.getText().toString();
                            Log.i("jimi 1 : ",password);
                            Log.i("jimi 2 : ",confirm);
                            if (password.equals(confirm)) {
                                Log.i("jimi 3 : ", " in");
                            } else {
                                etPasswordConfirm.setText("");
                                Toast.makeText(getApplicationContext(), "비밀번호가 다릅니다.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                }

                */
                }}
        });




        etEmail.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                if(hasFocus == false){

                    if (checkEmail(etEmail.getText().toString())) {
                        Log.i("gid ok : ", "OK");}
                    else {
                        Log.i("gid ok : ", "Fail");
                        etEmail.setText("");
                        Toast.makeText(getApplicationContext(), "이메일형식(@)을 지켜주세요.", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            public boolean checkEmail(String email){
                String regex = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";
                Pattern p = Pattern.compile(regex);
                Matcher m = p.matcher(email);
                boolean isNormal = m.matches();
                return isNormal;
            }

        });
//        etEmail.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                Log.i("gid text : ", etEmail.getText().toString() );
//                if (checkEmail(etEmail.getText().toString())) {
//                    Log.i("gid ok : ", "OK");}
//                else {
//                    Log.i("gid ok : ", "Fail");
//                    etEmail.setText("");
//                }
//            }
//
//
//            public boolean checkEmail(String email){
//                String regex = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";
//                Pattern p = Pattern.compile(regex);
//                Matcher m = p.matcher(email);
//                boolean isNormal = m.matches();
//                return isNormal;
//            }
//
//        });




        imageView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent backIntent = new Intent(getApplicationContext(), Login.class);
                startActivity(backIntent);
            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "전체 동의를 해주십시오.", Toast.LENGTH_SHORT).show();
            }
        });


        Next_TV.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent nextIntent = new Intent(getApplicationContext(), SignUp2.class);
                if(!m_pw1.getText().toString().equals(m_pw2.getText().toString()))
                    Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();

                nextIntent.putExtra("p_email", m_email.getText().toString());
                nextIntent.putExtra("p_name", m_name.getText().toString());
                nextIntent.putExtra("p_pw1", m_pw1.getText().toString());
                nextIntent.putExtra("p_birth", m_birth.getText().toString());
                nextIntent.putExtra("p_nickname", m_nickname.getText().toString());

                Log.i("email", m_email.getText().toString());

                // 이메일 입력 확인
                if( etEmail.getText().toString().length() == 0 ) {
                    Toast.makeText(getApplicationContext(), "Email을 입력하세요.", Toast.LENGTH_SHORT).show();
                    etEmail.requestFocus();
                    return;
                }

                //이름 입력 확인
                if( m_name.getText().toString().length() == 0 ) {
                    Toast.makeText(getApplicationContext(), "이름을 입력하세요.", Toast.LENGTH_SHORT).show();
                    m_name.requestFocus();
                    return;
                }


                // 비밀번호 입력 확인
                if( etPassword.getText().toString().length() == 0 ) {
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                    etPassword.requestFocus();
                    return;
                }
                else if( etPassword.getText().toString().length() < 4) {
                    Toast.makeText(getApplicationContext(), "비밀번호을 4자이상 입력하세요.", Toast.LENGTH_SHORT).show();
                    etPassword.requestFocus();
                    return;
                }

                // 비밀번호 확인 입력 확인
                if( etPasswordConfirm.getText().toString().length() == 0 ) {
                    Toast.makeText(getApplicationContext(), "비밀번호 확인을 입력하세요.", Toast.LENGTH_SHORT).show();
                    etPasswordConfirm.requestFocus();
                    return;
                }

                // 비밀번호 일치 확인
                if( !etPassword.getText().toString().equals(etPasswordConfirm.getText().toString()) ) {
                    Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다!", Toast.LENGTH_SHORT).show();
                    etPassword.setText("");
                    etPasswordConfirm.setText("");
                    etPassword.requestFocus();
                    return;
                }

                //희수추가


                if( m_birth.getText().toString().length() == 0 ) {
                    Toast.makeText(getApplicationContext(), "생년월일을 입력하시오.", Toast.LENGTH_SHORT).show();
                    m_birth.requestFocus();
                    return;
                }else if( m_birth.getText().toString().length() < 8) {
                    Toast.makeText(getApplicationContext(), "형식에 맞게 입력해주세요.", Toast.LENGTH_SHORT).show();
                    m_birth.requestFocus();
                    return;
                }

                if( m_nickname.getText().toString().length() == 0 ) {
                    Toast.makeText(getApplicationContext(), "닉네임을 입력하세요.", Toast.LENGTH_SHORT).show();
                    m_nickname.requestFocus();
                    return;

                }else if( m_nickname.getText().toString().length() < 2) {
                    Toast.makeText(getApplicationContext(), "닉네임을 2자이상 입력하세요.", Toast.LENGTH_SHORT).show();
                    m_nickname.requestFocus();
                    return;
                }



                startActivity(nextIntent);
            }

        });


        Next_TV.setClickable(false);

        total_CHB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked) {
                    agreeUI_CHB.setChecked(true);
                    PI_CHB.setChecked(true);
                    check.setClickable(false);
                    if(total_CHB.isChecked()==true&&agreeUI_CHB.isChecked()==true&&PI_CHB.isChecked()==true)
                        Next_TV.setClickable(true);

                }


                else if(isChecked==false&&(agreeUI_CHB.isChecked()==false)&&(PI_CHB.isChecked()==true)){
                    agreeUI_CHB.setChecked(false);
                    PI_CHB.setChecked(true);
                    total_CHB.setChecked(false);
                    Next_TV.setClickable(false);
                }

                else if(isChecked==false&&(agreeUI_CHB.isChecked()==true)&&(PI_CHB.isChecked()==false)){
                    agreeUI_CHB.setChecked(true);
                    PI_CHB.setChecked(false);
                    total_CHB.setChecked(false);
                    Next_TV.setClickable(false);
                }

                else if(isChecked==false){
                    agreeUI_CHB.setChecked(false);
                    PI_CHB.setChecked(false);
                    Next_TV.setClickable(false);

                    //Toast.makeText(getApplicationContext(),"false",Toast.LENGTH_SHORT).show()
                }

            }
        });


        total_CHB.setOnClickListener(onCheckBoxClickListener);
        agreeUI_CHB.setOnClickListener(onCheckBoxClickListener);
        PI_CHB.setOnClickListener(onCheckBoxClickListener);

        //이름 : 글자수 제한 20으로
        InputFilter[] FilterArray = new InputFilter[1];
        FilterArray[0] = new InputFilter.LengthFilter(20);
        m_name.setFilters(FilterArray);
        etPassword.requestFocus();

        //생년월일 : 글자수제한 8로하고 스트링을 1990-01-01로 바꿔서 new_birth로 저장
        InputFilter[] FilterArray_1 = new InputFilter[1];
        FilterArray_1[0] = new InputFilter.LengthFilter(8);
        m_birth.setFilters(FilterArray_1);
        new_birth = m_birth.toString();
        m_birth_y = new_birth.substring(0,4);
        m_birth_m = new_birth.substring(4,6);
        m_birth_d = new_birth.substring(6,8);
        new_birth = m_birth_y + "-" + m_birth_m + "-" + m_birth_d;
        m_nickname.requestFocus();


        //닉네임임 글자수제한 2~12 로
        InputFilter[] FilterArray_3 = new InputFilter[1];
        FilterArray_3[0] = new InputFilter.LengthFilter(12);
        m_nickname.setFilters(FilterArray_3);




    }



    private View.OnClickListener onCheckBoxClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(isTwoChecked()){
                total_CHB.setChecked(true);
            }
            else
                total_CHB.setChecked(false);

            if (isAllChecked()) {
                if(agreeUI_CHB.isChecked()==false){
                    total_CHB.setChecked(false);
                    PI_CHB.setChecked(true);
                }
                else if(PI_CHB.isChecked()==false){
                    total_CHB.setChecked(false);
                    agreeUI_CHB.setChecked(true);
                }

            }

        }

    };


    private boolean isTwoChecked(){
        return (agreeUI_CHB.isChecked() && PI_CHB.isChecked() ? true : false);
    }

    private boolean isAllChecked(){
        return (total_CHB.isChecked()&&agreeUI_CHB.isChecked() && PI_CHB.isChecked() ? true : false);
    }

}