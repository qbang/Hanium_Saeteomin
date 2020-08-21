package com.example.hanium_saeteomin.bottomnavigation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.hanium_saeteomin.R;
import com.example.hanium_saeteomin.fragmentboard.QuestionFeedAdapter;

public class BoardFragment extends Fragment {
    ListView listView;
    QuestionFeedAdapter questionFeedAdapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        questionFeedAdapter = new QuestionFeedAdapter();

        listView = (ListView) view.findViewById(R.id.listview);
        listView.setAdapter(questionFeedAdapter);
        this.addQuestionFeed();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_board, container, false);
    }
    public void addQuestionFeed(){
        for(int i =0;i<8;i++){
            questionFeedAdapter.addItem(ContextCompat.getDrawable(getContext(), R.drawable.test), "홍길동", "10분전", "~~~가 뭔가요?", "5", "4");
        }
    }

}