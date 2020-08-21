package com.example.hanium_saeteomin.fragmenthome.todayword;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.hanium_saeteomin.R;

import me.relex.circleindicator.CircleIndicator;

public class ActivityTodayWord extends AppCompatActivity {
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
