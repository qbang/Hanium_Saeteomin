package com.example.hanium_saeteomin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.hanium_saeteomin.homefragment.LoginFailedDialog;
import com.example.hanium_saeteomin.network.RequestLogin;
import com.example.hanium_saeteomin.network.RetrofitClient;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText et_id;
    EditText et_pw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btn_login = findViewById(R.id.btn_login);
        Button btn_register = findViewById(R.id.btn_register);
        et_id = findViewById(R.id.et_id);
        et_pw = findViewById(R.id.et_pw);
        final CheckBox checkBox = findViewById(R.id.checkbox);

        SharedPreferences pref = getSharedPreferences("mine",MODE_PRIVATE);

        if(pref.getBoolean("check",false))
        {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
//            String id = pref.getString("id","");
//            String pw = pref.getString("pw","");
//            Log.d("id",id);
//            Log.d("pw",pw);
        }

        String id = pref.getString("id","");
        String pw = pref.getString("pw","");
        et_id.setText(id);
        et_pw.setText(pw);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((et_id.length()==0||et_pw.length()==0)) {
                    Log.d("login","길이");

                    LoginFailedDialog loginFailedDialog = new LoginFailedDialog(LoginActivity.this);
                    loginFailedDialog.callFunction();
                    loginFailedDialog.setText("아이디와 비밀번호 둘 다 입력해주세요!");

                }else{
                RetrofitClient retrofitClient = new RetrofitClient();
                RequestLogin requestLogin = new RequestLogin(et_id.getText().toString(),et_pw.getText().toString());
                Call<JsonObject> call = retrofitClient.apiService.Login(requestLogin);
                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        Log.d("login",response.body().toString());
                        if(response.body().toString().contains("성공")) {
                            if(checkBox.isChecked()){
                                setAutoLogin();
                            }
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);


                        }else{
                            LoginFailedDialog loginFailedDialog = new LoginFailedDialog(LoginActivity.this);
                            loginFailedDialog.callFunction();
                            loginFailedDialog.setText("가입되지 않은 유저입니다.");
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Log.d("login",t.getMessage());

                    }
                });
                }
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
            }
        });



    }

    public void setAutoLogin(){
        SharedPreferences pref = getSharedPreferences("mine",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("id",et_id.getText().toString());
        editor.putString("pw",et_pw.getText().toString());
        editor.putBoolean("check",true);
        Log.d("login_id",et_id.getText().toString());
        Log.d("login_pw",et_pw.getText().toString());
        editor.commit();
    }


}