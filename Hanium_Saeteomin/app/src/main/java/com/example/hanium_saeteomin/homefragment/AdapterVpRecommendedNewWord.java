package com.example.hanium_saeteomin.homefragment;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.hanium_saeteomin.R;

import java.util.ArrayList;

public class AdapterVpRecommendedNewWord extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragments = new ArrayList<>();
    Context context;
    public AdapterVpRecommendedNewWord(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
//        switch (position) {
//            case 0:
//                Intent intent = new Intent(context,activity_today_word.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
//
//            case 1:
//                Intent intent2 = new Intent(context,activity_best_word.class);
//                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent2);
//        }

    return fragments.get(position);

    }


    @Override
    public int getCount() {
        return fragments.size();
    }

    public void addItem(Fragment fragment) {
        fragments.add(fragment);
    }
}
