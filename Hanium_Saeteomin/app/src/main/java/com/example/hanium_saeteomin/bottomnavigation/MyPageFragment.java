package com.example.hanium_saeteomin.bottomnavigation;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hanium_saeteomin.LoginActivity;
import com.example.hanium_saeteomin.WebViewActivity;
import com.example.hanium_saeteomin.R;
import com.example.hanium_saeteomin.friendlist.FriendListActivity;

public class MyPageFragment extends Fragment {
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView profileimg = view.findViewById(R.id.profile);
        profileimg.setImageResource(R.drawable.test) ;
        profileimg.setBackground(new ShapeDrawable(new OvalShape()));
        profileimg.setClipToOutline(true);

        TextView tv_web1 = view.findViewById(R.id.tv_web1);
        TextView tv_web2 = view.findViewById(R.id.tv_web2);
        TextView tv_web3 = view.findViewById(R.id.tv_web3);
        TextView tv_friend_list = view.findViewById(R.id.tv_friend_list);
        TextView tv_logout = view.findViewById(R.id.tv_logout);

        tv_web1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), WebViewActivity.class);
                intent.putExtra("webCategory","1");
                startActivity(intent);
            }
        });

        tv_web2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), WebViewActivity.class);
                intent.putExtra("webCategory","2");
                startActivity(intent);
            }
        });
        tv_web3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), WebViewActivity.class);
                intent.putExtra("webCategory","3");
                startActivity(intent);
            }
        });

        tv_friend_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), FriendListActivity.class);
                startActivity(intent);
            }
        });

        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), LoginActivity.class);
                startActivity(intent);
                SharedPreferences pref = getActivity().getSharedPreferences("mine", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                //editor.clear()는 auto에 들어있는 모든 정보를 기기에서 지웁니다.
                editor.clear();
                editor.commit();
                Toast.makeText(view.getContext(), "로그아웃", Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_personal_profile, container, false);



    }
}