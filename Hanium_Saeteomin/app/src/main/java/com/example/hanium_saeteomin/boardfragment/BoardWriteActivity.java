package com.example.hanium_saeteomin.boardfragment;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.hanium_saeteomin.R;
import com.example.hanium_saeteomin.network.RequestWriteFeed;
import com.example.hanium_saeteomin.network.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoardWriteActivity extends AppCompatActivity {
    String userId;
    String userName;
    EditText etContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_write);

        userId = getIntent().getStringExtra("userId");
        userName = getIntent().getStringExtra("userName");

        etContent = findViewById(R.id.et_content);
        Button btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button btn_check = findViewById(R.id.btn_check);
        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Todo user_id, user_name 받아오기
                RetrofitClient retrofitClient = new RetrofitClient();
                RequestWriteFeed requestWriteFeed = new RequestWriteFeed(userId,userName,etContent.getText().toString());
                Call<JsonObject> call = retrofitClient.apiService.WriteBoard(requestWriteFeed);
                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        Log.d("피드 등록",response.body().toString());
                        if(response.body().toString().contains("완료")) {
                            //Todo 게시글 추가
                            Intent returnIntent = new Intent();
                            returnIntent.putExtra("result",response.body().toString());
                            setResult(Activity.RESULT_OK,returnIntent);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Log.d("피드 등록","실패");

                    }
                });


            }
        });



    }
}