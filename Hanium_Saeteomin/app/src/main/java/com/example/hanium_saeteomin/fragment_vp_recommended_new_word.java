package com.example.hanium_saeteomin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class fragment_vp_recommended_new_word extends Fragment {

    public fragment_vp_recommended_new_word() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vp_recommended_new_word, container, false);
        ImageView imageView = view.findViewById(R.id.img_reccomended_new_word);
        TextView textView = view.findViewById(R.id.tv_recommended_new_word);
        if (getArguments() != null) {
            Bundle args = getArguments();
            // MainActivity에서 받아온 Resource를 ImageView에 셋팅
            imageView.setImageResource(args.getInt("imgRes"));
            textView.setText(args.getString("tvRes"));
        }
        return view;
    }
}