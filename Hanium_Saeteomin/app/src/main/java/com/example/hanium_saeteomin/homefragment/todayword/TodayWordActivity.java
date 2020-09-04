package com.example.hanium_saeteomin.homefragment.todayword;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hanium_saeteomin.R;
import com.example.hanium_saeteomin.homefragment.bestword.WordData;
import com.example.hanium_saeteomin.network.RequestGetTodayWord;
import com.example.hanium_saeteomin.network.RetrofitClient;
import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TodayWordActivity extends AppCompatActivity {
    final Calendar cal = Calendar.getInstance();
    ArrayList<WordData> bestWordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_word);
        bestWordList = new ArrayList<WordData>();

        ImageButton btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        AdapterVpTodayWord adapterVpTodayWord = new AdapterVpTodayWord(getSupportFragmentManager(),this);

        RetrofitClient retrofitClient = new RetrofitClient();
        //TODO 당일 데이터만 보여줄건지, 전날 다음날 데이터까지 보여줄건지 뷰 수정
        RequestGetTodayWord requestGetTodayWord = new RequestGetTodayWord("2020-09-02");
        Call<JsonArray> call = retrofitClient.apiService.GetTodayWord(requestGetTodayWord);
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                Log.d("today",response.body().toString());

            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {

            }
        });

        ImageButton btn_calendar = findViewById(R.id.btn_calendar);
        btn_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(TodayWordActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {

                        String msg = String.format("%d 년 %d 월 %d 일", year, month+1, date);
                        Toast.makeText(TodayWordActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));

                dialog.getDatePicker().setMaxDate(new Date().getTime());    //입력한 날짜 이후로 클릭 안되게 옵션
                dialog.show();
            }
        });

//        ViewPager viewPager = (ViewPager)findViewById(R.id.vp_today_word);
//        viewPager.setAdapter(adapterTodayWord);
//
//        CircleIndicator circleIndicator = (CircleIndicator)findViewById(R.id.indicator);
//        circleIndicator.setViewPager(viewPager);

//        for(int i=0;i<3;i++){
//            FragmentTodayWord fragmentTodayWord = new FragmentTodayWord();
//            AdapterTodayWord.addItem(fragmentTodayWord);
//        }
        adapterVpTodayWord.notifyDataSetChanged();


    }
}
