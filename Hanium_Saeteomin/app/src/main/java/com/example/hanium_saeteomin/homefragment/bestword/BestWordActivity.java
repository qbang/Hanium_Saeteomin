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

        for(int i=0;i<6;i++){
            bestWordList.add(data);
        }
        adapter.notifyDataSetChanged();



    }
}