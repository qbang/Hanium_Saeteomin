package com.example.hanium_saeteomin.bottomnavigation;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hanium_saeteomin.R;
import com.example.hanium_saeteomin.boardfragment.BoardDetailActivity;
import com.example.hanium_saeteomin.boardfragment.BoardWriteActivity;
import com.example.hanium_saeteomin.boardfragment.CommentData;
import com.example.hanium_saeteomin.boardfragment.FeedData;
import com.example.hanium_saeteomin.boardfragment.QuestionFeedAdapter;
import com.example.hanium_saeteomin.homefragment.bestword.WordData;
import com.example.hanium_saeteomin.network.RequestGetComment;
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
    String userName;
    String userId;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        questionFeedAdapter = new QuestionFeedAdapter();
        FeedList = new ArrayList<FeedData>();
        listView = (ListView) view.findViewById(R.id.listview);
        listView.setAdapter(questionFeedAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String boardId = FeedList.get(position).getBoard_id();
                String url = FeedList.get(position).getImg_url();
                userName = FeedList.get(position).getUser_name();
                String content = FeedList.get(position).getContent();
                int likeCount= FeedList.get(position).getGood_count();
                int commentCount = FeedList.get(position).getComment_number();
                userId = FeedList.get(position).getUser_id();
                String writeDate = FeedList.get(position).getWrite_date();

                FeedData feedData = new FeedData(boardId,userName,url,commentCount,content,likeCount,userId,writeDate);
                Intent intent =  new Intent(getContext(), BoardDetailActivity.class);
                intent.putExtra("feedData",feedData);
                startActivity(intent);
            }
        });

        FloatingActionButton fab_add_board = view.findViewById(R.id.fab_add_board);
        fab_add_board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"됨",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), BoardWriteActivity.class);
                intent.putExtra("userId",userId);
                intent.putExtra("userName",userName);
                startActivity(intent);

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
                Log.d("board","실패");

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

