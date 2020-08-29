package com.example.hanium_saeteomin.network;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("login")
    Call<JsonObject> Login(@Body RequestLogin body);

    @POST("register")
    Call<JsonObject> Register(@Body RequestRegister body);

//    @HTTP(method = "GET",path="login/",hasBody = true)
//    Call<JsonObject> getLogin(@Body RequestLogin body);

}
