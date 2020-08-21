package com.example.hanium_saeteomin.homefragment.todayword;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.hanium_saeteomin.R;

import java.util.Calendar;
import java.util.Date;

import me.relex.circleindicator.CircleIndicator;

public class TodayWordActivity extends AppCompatActivity {
    final Calendar cal = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_word);

        ImageButton btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        AdapterTodayWord adapterTodayWord = new AdapterTodayWord(getSupportFragmentManager(),this);

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

        ViewPager viewPager = (ViewPager)findViewById(R.id.vp_today_word);
        viewPager.setAdapter(adapterTodayWord);

        CircleIndicator circleIndicator = (CircleIndicator)findViewById(R.id.indicator);
        circleIndicator.setViewPager(viewPager);

//        for(int i=0;i<3;i++){
//            FragmentTodayWord fragmentTodayWord = new FragmentTodayWord();
//            AdapterTodayWord.addItem(fragmentTodayWord);
//        }
        adapterTodayWord.notifyDataSetChanged();


    }
}
