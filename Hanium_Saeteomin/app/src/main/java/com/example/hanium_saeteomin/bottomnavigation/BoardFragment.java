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
import android.widget.Toast;

import com.example.hanium_saeteomin.R;
import com.example.hanium_saeteomin.boardfragment.FeedData;
import com.example.hanium_saeteomin.boardfragment.QuestionFeedAdapter;
import com.example.hanium_saeteomin.homefragment.bestword.WordData;
import com.example.hanium_saeteomin.network.RetrofitClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
//        this.addQuestionFeed();

        FloatingActionButton fab_add_board = view.findViewById(R.id.fab_add_board);
        fab_add_board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"됨",Toast.LENGTH_SHORT).show();
            }
        });
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

                        String url = feedData.getImg_url();
                        String userName = feedData.getUser_name();
                        String content = feedData.getContent();
                        int likeCount= feedData.getGood_count();
                        int commentCount = feedData.getComment_number();
                        String writeDate = feedData.getWrite_date();

                        questionFeedAdapter.addItem(url, userName,writeDate, content,likeCount,commentCount);

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

}