package com.example.hanium_saeteomin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private Fragment fragment_home = new fragment_home();
    private Fragment fragment_chatting = new fragment_chatting();
    private Fragment fragment_board = new fragment_board();
    private Fragment fragment_mypage = new fragment_mypage();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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