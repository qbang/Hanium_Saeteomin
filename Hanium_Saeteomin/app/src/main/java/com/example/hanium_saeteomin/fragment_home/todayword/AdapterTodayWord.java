package com.example.hanium_saeteomin.fragment_home.todayword;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
public class AdapterTodayWord extends FragmentPagerAdapter {
    private static  ArrayList<Fragment> fragments = new ArrayList<>();
    private static int NUM_ITEMS = 3;
    Context context;

    public AdapterTodayWord(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentYesterdayWord();
            case 1:
                return new FragmentTodayWord();
            case 2:
                return new FragmentTomorrowWord();
            default:
                return null;
        }
        //return fragments.get(position);
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

     public static void addItem(Fragment fragment){
        fragments.add(fragment);
    }
}
