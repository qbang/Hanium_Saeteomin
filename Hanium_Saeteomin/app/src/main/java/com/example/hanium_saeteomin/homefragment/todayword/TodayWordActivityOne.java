package com.example.hanium_saeteomin.homefragment.todayword;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hanium_saeteomin.R;
import com.example.hanium_saeteomin.homefragment.bestword.WordData;
import com.example.hanium_saeteomin.homefragment.todayword.todaywordrecyclerview.AdapterTodayWord;
import com.example.hanium_saeteomin.network.RequestGetTodayWord;
import com.example.hanium_saeteomin.network.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TodayWordActivityOne extends AppCompatActivity {
    final Calendar cal = Calendar.getInstance();
    ArrayList<WordData> todayWordDataArrayList;
    private AdapterTodayWord adapterTodayWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_word_one);

        RecyclerView rvTodayWord = findViewById(R.id.rv_today_word);
        ImageButton btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        todayWordDataArrayList = new ArrayList<WordData>();

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        rvTodayWord.setLayoutManager(mLinearLayoutManager);
        adapterTodayWord = new AdapterTodayWord(todayWordDataArrayList);
        rvTodayWord.setAdapter(adapterTodayWord);

        WordData todayWordData = new WordData("갑통알","갑자기 통장을 보니 알바해야 할 것 같다.오아오아아린ㅇ라ㅓㄴ이ㅏㄹㄴㄹ아런인아러닝라ㅓㄴㄹㅇ러니ㅏㄹ");
        todayWordDataArrayList.add(todayWordData);
        todayWordDataArrayList.add(todayWordData);
        todayWordDataArrayList.add(todayWordData);
        todayWordDataArrayList.add(todayWordData);
        todayWordDataArrayList.add(todayWordData);
        adapterTodayWord.notifyDataSetChanged();

//        RetrofitClient retrofitClient = new RetrofitClient();
//        //TODO 이미지 처리 어떻게 할까
//        RequestGetTodayWord requestGetTodayWord = new RequestGetTodayWord("2020-09-01");
//        Call<JsonArray> call = retrofitClient.apiService.GetTodayWord(requestGetTodayWord);
//        call.enqueue(new Callback<JsonArray>() {
//            @Override
//            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
//                Log.d("today",response.body().toString());
//                Gson gson = new Gson();
//                JsonArray array = response.body().getAsJsonArray();
//
//                Log.d("today1",array.get(0).toString());
//
//                for(int i=0 ;i<response.body().size();i++){
//
//                    WordData todayWordData = gson.fromJson(array.get(i),WordData.class);
//                    todayWordDataArrayList.add(todayWordData);
//                    adapterTodayWord.notifyDataSetChanged();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<JsonArray> call, Throwable t) {
//
//            }
//        });


        ImageButton btn_calendar = findViewById(R.id.btn_calendar);
        btn_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(TodayWordActivityOne.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {

                        String msg = String.format("%d 년 %d 월 %d 일", year, month+1, date);
                        Toast.makeText(TodayWordActivityOne.this, msg, Toast.LENGTH_SHORT).show();
                    }
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));

                dialog.getDatePicker().setMaxDate(new Date().getTime());    //입력한 날짜 이후로 클릭 안되게 옵션
                dialog.show();
            }
        });
    }
}
