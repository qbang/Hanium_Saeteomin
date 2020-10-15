package com.example.hanium_saeteomin.homefragment.bestword;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.hanium_saeteomin.R;

import java.util.ArrayList;

public class BestWordActivity extends AppCompatActivity {
    ArrayList<WordData> bestWordList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_best_word);

        ImageButton btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        RecyclerView recyclerView = findViewById(R.id.rv_best_word) ;
        recyclerView.setLayoutManager(new GridLayoutManager(this,2)) ;
        bestWordList = new ArrayList<WordData>();
        AdapterBestWord adapter = new AdapterBestWord(bestWordList) ;
        recyclerView.setAdapter(adapter) ;

        WordData data = new WordData("뽀쟉","Apple");

//        for(int i=0;i<6;i++){
        bestWordList.add(new WordData("에어노마드족","대기오염이 없고 공기가 꺠끗한 지역을 찾아 이주하는 사람"));
        bestWordList.add(new WordData("삼귀자","자연인들이 본격적으로 사귀기 전의 단계"));
        bestWordList.add(new WordData("자만추","자연스러운 만남 추구"));
        bestWordList.add(new WordData("알잘딱깔센","알아서 잘 딱 깔끔하고 센스있게"));
        bestWordList.add(new WordData("아바라","아이스 바닐라 라떼"));
        bestWordList.add(new WordData("오저치고","오늘 저녁 치킨 고?"));

//        }
        adapter.notifyDataSetChanged();



    }
}