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
import com.example.hanium_saeteomin.network.RequestUpdateBoard;
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
    String userPw;
    EditText etContent;

    Boolean update;
    String boardId;
    String content;
    private int WRITE_OK= 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_write);

        etContent = findViewById(R.id.et_content);

        userId = getIntent().getStringExtra("userId");
        userName = getIntent().getStringExtra("userName");

        Log.d("wuserId", userId);
        Log.d("wuserName", userName);

        update = getIntent().getBooleanExtra("update",false);
        content = getIntent().getStringExtra("beforeContent");
        boardId = getIntent().getStringExtra("boardId");



        Button btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button btn_check = findViewById(R.id.btn_check);

        if(update){
            etContent.setText(content);
        }

        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RetrofitClient retrofitClient = new RetrofitClient();
                if(update){
                    RequestUpdateBoard requestUpdateBoard = new RequestUpdateBoard(boardId,etContent.getText().toString());
                    Call<JsonObject> call = retrofitClient.apiService.UpdateBoard(requestUpdateBoard);
                    call.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            if (response.body().toString().contains("완료")) {
                                Intent intent = new Intent();
                                intent.putExtra("content",etContent.getText().toString());
                                setResult(WRITE_OK,intent);
                                finish();

                            } else {
                                Log.d("error", response.errorBody().toString());
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            Log.d("comment", "실패");

                        }
                    });
                }
                else {
                    RequestWriteFeed requestWriteFeed = new RequestWriteFeed(userId, userName, etContent.getText().toString());
                    Log.d("wwwuserId", userId);
                    Log.d("wwwuserName", userName);
//                    Log.d("useret", etContent.getText().toString());
                    Call<JsonObject> call = retrofitClient.apiService.WriteBoard(requestWriteFeed);
                    call.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            Log.d("피드 등록", response.body().toString());
                            if (response.body().toString().contains("완료")) {
                                //Todo 게시글 추가
                                finish();
                            } else {
                                Log.d("error", response.errorBody().toString());
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            Log.d("피드 등록", "실패");

                        }
                    });

                }
            }
        });


        }


    }


