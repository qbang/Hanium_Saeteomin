package com.example.hanium_saeteomin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.hanium_saeteomin.boardfragment.BoardFragment;
import com.example.hanium_saeteomin.bottomnavigation.ChattingFragment;
import com.example.hanium_saeteomin.bottomnavigation.HomeFragment;
import com.example.hanium_saeteomin.bottomnavigation.MyPageFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private Fragment fragment_home = new HomeFragment();
    private Fragment fragment_chatting = new ChattingFragment();
    private Fragment fragment_board;
    private Fragment fragment_mypage = new MyPageFragment();

    String userId;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        userName = intent.getStringExtra("userName");

//        Log.d("muserId", userId);
//        Log.d("muserName", userName);

        fragment_board = new BoardFragment(userName,userId);

        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout,fragment_home).commitAllowingStateLoss();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch (item.getItemId()){
                    case R.id.home:
                        transaction.replace(R.id.frameLayout,fragment_home).commitAllowingStateLoss();
                        break;
                    case R.id.chatting:
                        transaction.replace(R.id.frameLayout,fragment_chatting).commitAllowingStateLoss();
                        break;
                    case R.id.board:
                        transaction.replace(R.id.frameLayout,fragment_board).commitAllowingStateLoss();
                        break;
                    case R.id.mypage:
                        transaction.replace(R.id.frameLayout,fragment_mypage).commitAllowingStateLoss();
                        break;



                }
                return true;
            }
        });

    }
}