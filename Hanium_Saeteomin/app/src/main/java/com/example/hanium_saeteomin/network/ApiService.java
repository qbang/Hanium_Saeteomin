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
    Call<JsonArray> GetQuizList(@Body RequestGetQuizList body);

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

    @POST("start_question")
    Call<JsonObject> RequestQuizInfo();

    @POST("question")
    Call<JsonObject> RequestSolution(@Body RequestSolution body);

    @POST("board_delete")
    Call<JsonObject> DeleteBoard(@Body RequestDeleteBoard body);

    @POST("comment_delete")
    Call<JsonObject> DeleteComment(@Body RequestDeleteComment body);

    @POST("comment_rewrite")
    Call<JsonObject> UpdateComment(@Body RequestUpdateComment body);

    @POST("board_rewrite")
    Call<JsonObject> UpdateBoard(@Body RequestUpdateBoard body);

    @POST("board_like")
    Call<JsonObject> ClickLike(@Body RequestClickLike body );

    @POST("board_like_delete")
    Call<JsonObject> ClickLikeCancel(@Body RequestClickLike body);

//    @HTTP(method = "GET",path="login/",hasBody = true)
//    Call<JsonObject> getLogin(@Body RequestLogin body);

}
