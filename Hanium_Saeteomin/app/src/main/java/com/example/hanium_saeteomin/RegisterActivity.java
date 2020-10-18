package com.example.hanium_saeteomin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hanium_saeteomin.homefragment.LoginFailedDialog;
import com.example.hanium_saeteomin.network.RequestRegister;
import com.example.hanium_saeteomin.network.RetrofitClient;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    EditText et_nickname;
    EditText et_email;
    EditText et_pw;
    EditText et_pw_check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_nickname = findViewById(R.id.et_nickname);
        et_email = findViewById(R.id.et_email);
        et_pw = findViewById(R.id.et_pw);
        et_pw_check = findViewById(R.id.et_pw_check);

        ImageButton btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });

        Button btn_resgister_ok = findViewById(R.id.btn_register_ok);
        btn_resgister_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((et_email.length()==0)) {
                    LoginFailedDialog loginFailedDialog = new LoginFailedDialog(RegisterActivity.this);
                    loginFailedDialog.callFunction();
                    loginFailedDialog.setText("아이디를 입력해주세요!");

                }else if(et_nickname.length()==0){
                    LoginFailedDialog loginFailedDialog = new LoginFailedDialog(RegisterActivity.this);
                    loginFailedDialog.callFunction();
                    loginFailedDialog.setText("닉네임을 입력해주세요!");

                }else if(!et_pw.getText().toString().equals(et_pw_check.getText().toString())){
                    LoginFailedDialog loginFailedDialog = new LoginFailedDialog(RegisterActivity.this);
                    loginFailedDialog.callFunction();
                    loginFailedDialog.setText("아이디와 비밀번호가 일치하지 않습니다!");

                }else if(et_pw.length()==0||et_pw_check.length()==0){
                    LoginFailedDialog loginFailedDialog = new LoginFailedDialog(RegisterActivity.this);
                    loginFailedDialog.callFunction();
                    loginFailedDialog.setText("비밀번호를 입력해주세요!");
                }else{
                    RetrofitClient retrofitClient = new RetrofitClient();
                    RequestRegister requestRegister = new RequestRegister(et_email.getText().toString(),et_nickname.getText().toString(), et_pw.getText().toString());
                    Call<JsonObject> call = retrofitClient.apiService.Register(requestRegister);
                    call.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            if (response.body().toString().contains("성공")) {
                                SharedPreferences pref2 = getSharedPreferences("user",MODE_PRIVATE);
                                SharedPreferences.Editor editor = pref2.edit();
                                editor.putString("userId",et_email.getText().toString());
                                editor.putString("userName",et_nickname.getText().toString());

                                editor.commit();
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                            }else {

                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            Log.d("register",t.getMessage());
                        }
                    });
                }
            }
        });
    }
}
