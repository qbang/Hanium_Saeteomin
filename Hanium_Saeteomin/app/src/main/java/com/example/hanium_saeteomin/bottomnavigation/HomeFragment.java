package com.example.hanium_saeteomin.bottomnavigation;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.hanium_saeteomin.homefragment.bestword.BestWordActivity;
import com.example.hanium_saeteomin.homefragment.todayword.TodayWordActivity;
import com.example.hanium_saeteomin.homefragment.AdapterQuiz;
import com.example.hanium_saeteomin.homefragment.AdapterVpRecommendedNewWord;
import com.example.hanium_saeteomin.homefragment.VpRecommendedNewWordFragment;
import com.example.hanium_saeteomin.R;
import com.example.hanium_saeteomin.homefragment.todayword.TodayWordActivityOne;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    ViewPager viewPager;
    AdapterVpRecommendedNewWord fragmentAdapter;
    ArrayList<Integer> listImage;
    ArrayList<String> listTextview;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LottieAnimationView img_today_word = view.findViewById(R.id.img_today_word);
        LottieAnimationView img_best_word = view.findViewById(R.id.img_best_word);

        img_today_word.playAnimation();
        img_today_word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), TodayWordActivityOne.class);
                startActivity(intent);
            }
        });

        img_best_word.playAnimation();
        img_best_word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),BestWordActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        listImage = new ArrayList<>();
//        listImage.add(R.drawable.book);
//        listImage.add(R.drawable.king);
//
//        listTextview = new ArrayList<>();
//        listTextview.add("오늘의 신조어");
//        listTextview.add("베스트 신조어");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //뷰페이저 리스트에 아이템 추
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
//        viewPager = view.findViewById(R.id.vp_recommended_new_word);
//        fragmentAdapter = new AdapterVpRecommendedNewWord(getChildFragmentManager(),getActivity().getApplicationContext());
//        viewPager.setAdapter(fragmentAdapter);
//
//        viewPager.setClipToPadding(false);
//        int dpValue = 100;
//        float d = getResources().getDisplayMetrics().density;
//        int margin = (int) (dpValue * d);
//        viewPager.setPadding(100, 0, margin, 0);
//        viewPager.setPageMargin(margin/2);
//
//        for (int i = 0; i < listImage.size(); i++) {
//            VpRecommendedNewWordFragment fragment_vp_recommended_new_word = new VpRecommendedNewWordFragment();
//            Bundle bundle = new Bundle();
//            bundle.putInt("imgRes", listImage.get(i));
//            bundle.putString("tvRes",listTextview.get(i));
//            fragment_vp_recommended_new_word.setArguments(bundle);
//            fragmentAdapter.addItem(fragment_vp_recommended_new_word);
//        }
//        fragmentAdapter.notifyDataSetChanged();
//
//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
//        {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
//            {
//                Log.d("ITPANGPANG","onPageScrolled : "+position);
//            }
//
//            @Override
//            public void onPageSelected(int position)
//            {
//                //Log.d("ITPANGPANG","onPageSelected : "+position);
////                if(position==0){
////                    Intent intent = new Intent(getContext(), TodayWordActivity.class);
////                    startActivity(intent);
////                }else if(position ==1){
////                    Intent intent = new Intent(getContext(), BestWordActivity.class);
////                    startActivity(intent);
////                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state)
//            {
//                Log.d("ITPANGPANG","onPageScrollStateChanged : "+state);
//            }
//
//        });


        //퀴즈내역
        ArrayList<String> list = new ArrayList<>();
        for (int i=0; i<10; i++) {
            list.add(String.format("TEXT %d", i)) ;
        }
        RecyclerView recyclerView = view.findViewById(R.id.rv_quiz) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext())) ;
        AdapterQuiz adapter = new AdapterQuiz(list) ;
        recyclerView.setAdapter(adapter) ;


        //최근검색
        ArrayList<String> list2 = new ArrayList<>();
        for (int i=0; i<10; i++) {
            list.add(String.format("TEXT %d", i)) ;
        }
        RecyclerView recyclerView2 = view.findViewById(R.id.rv_recent_word) ;
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext())) ;
        AdapterQuiz adapter2 = new AdapterQuiz(list) ;
        recyclerView2.setAdapter(adapter2) ;

        return view;
    }
}