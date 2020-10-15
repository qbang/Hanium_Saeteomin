package com.example.hanium_saeteomin.network;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @POST("login")
    Call<JsonObject> Login(@Body RequestLogin body);

    @POST("register")
    Call<JsonObject> Register(@Body RequestRegister body);

    @POST("new_word")
    Call<JsonArray> GetTodayWord(@Body RequestGetTodayWord body);

    @POST("quiz")
    Call<JsonObject> GetQuizList(@Body RequestGetQuizList body);

    @POST("save_score")
    Call<JsonObject> SaveScore(@Body SendQuizResult body);

    @POST("board")
    Call<JsonArray> GetBoardList();

    @POST("comment")
    Call<JsonArray> GetBoardCommentList(@Body RequestGetComment body);

    @POST("board_write")
    Call<JsonObject> WriteBoard(@Body RequestWriteFeed body);

    @POST("comment_write")
    Call<JsonObject> WriteComment(@Body RequestWriteComment body);
    @POST("test")
    Call<JsonObject> RequestSolution(@Body RequestSolution body);


//    @HTTP(method = "GET",path="login/",hasBody = true)
//    Call<JsonObject> getLogin(@Body RequestLogin body);

}
