package com.example.hanium_saeteomin.bottomnavigation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.hanium_saeteomin.R;
import com.example.hanium_saeteomin.boardfragment.FeedData;
import com.example.hanium_saeteomin.boardfragment.QuestionFeedAdapter;
import com.example.hanium_saeteomin.homefragment.bestword.WordData;
import com.example.hanium_saeteomin.network.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoardFragment extends Fragment {
    ListView listView;
    QuestionFeedAdapter questionFeedAdapter;
    ArrayList<FeedData> FeedList;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        questionFeedAdapter = new QuestionFeedAdapter();
        FeedList = new ArrayList<FeedData>();
        listView = (ListView) view.findViewById(R.id.listview);
        listView.setAdapter(questionFeedAdapter);
        this.addQuestionFeed();

        RetrofitClient retrofitClient = new RetrofitClient();
        Call<JsonArray> call = retrofitClient.apiService.GetBoardList();
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                    for (int i = 0; i < response.body().size(); i++) {
                        Log.d("board",response.body().toString());
                        Gson gson = new Gson();
                        JsonArray array = response.body().getAsJsonArray();
                        FeedData feedData = gson.fromJson(array.get(i), FeedData.class);
                        feedData.getBoard_id();
                        FeedList.add(feedData);
                        questionFeedAdapter.notifyDataSetChanged();
                    }

            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {

            }
        });



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