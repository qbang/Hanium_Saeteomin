package com.example.hanium_saeteomin.network;

import com.example.hanium_saeteomin.network.ApiService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    OkHttpClient httpClient = new OkHttpClient.Builder()
            .callTimeout(50000, TimeUnit.SECONDS)
            .connectTimeout(50000, TimeUnit.SECONDS)
            .readTimeout(50000, TimeUnit.SECONDS)
            .writeTimeout(50000, TimeUnit.SECONDS)
            .build();

    Retrofit retrofit = new Retrofit.Builder()
            //서버 url설정
//            .baseUrl("http://3.35.100.212:5000/")
            .baseUrl("http://10.0.2.2:5000")
            //데이터 파싱 설정
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            //객체정보 반환
            .build();

    public ApiService apiService = retrofit.create(ApiService.class);
}
