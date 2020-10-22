package com.example.hanium_saeteomin.boardfragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.hanium_saeteomin.R;
import com.example.hanium_saeteomin.network.RequestClickLike;
import com.example.hanium_saeteomin.network.RetrofitClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoardFragment extends Fragment implements clickLike {
    //listview 안에는 button click 이벤트 클릭이 안됨!
    ListView listView;
    QuestionFeedAdapter questionFeedAdapter;
    ArrayList<FeedData> FeedList;

    String currentUserName;
    String currentUserId;

    String userName;
    String userId;

    public BoardFragment(){

    }

    public BoardFragment(String userName, String userId) {
        this.currentUserName = userName;
        this.currentUserId = userId;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        questionFeedAdapter = new QuestionFeedAdapter();
        questionFeedAdapter.setListener(this);
        FeedList = new ArrayList<FeedData>();

        listView = (ListView) view.findViewById(R.id.listview);
        listView.setAdapter(questionFeedAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                String boardId = FeedList.get(position).getBoard_id();
//                String url = FeedList.get(position).getImg_url();
//                 userName = FeedList.get(position).getUser_name();
//                String content = FeedList.get(position).getContent();
//                int likeCount= FeedList.get(position).getGood_count();
//                int commentCount = FeedList.get(position).getComment_number();
//                 userId = FeedList.get(position).getUser_id();
//                String writeDate = FeedList.get(position).getWrite_date();
//
//                FeedData feedData = new FeedData(boardId,userName,url,commentCount,content,likeCount,userId,writeDate);

                Intent intent =  new Intent(getContext(), BoardDetailActivity.class);
                intent.putExtra("boardId",FeedList.get(position).board_id);
                intent.putExtra("userId",currentUserId);
                intent.putExtra("userName",currentUserName);
                intent.putExtra("feedData",FeedList.get(position));
                startActivity(intent);
            }
        });

        FloatingActionButton fab_add_board = view.findViewById(R.id.fab_add_board);
        fab_add_board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), BoardWriteActivity.class);
                intent.putExtra("userId",currentUserId);
                intent.putExtra("userName",currentUserName);
                Log.d("user",currentUserId);
                Log.d("user",currentUserName);

                startActivity(intent);

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        questionFeedAdapter.getQuestionFeedDataList().clear();
        getBoardList();
    }

    private void getBoardList(){
        RetrofitClient retrofitClient = new RetrofitClient();
        Call<JsonArray> call = retrofitClient.apiService.GetBoardList();
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                FeedList.clear();
                for (int i = 0; i < response.body().size(); i++) {
                    Log.d("board",response.body().toString());
                    Gson gson = new Gson();
                    JsonArray array = response.body().getAsJsonArray();
                    FeedData feedData = gson.fromJson(array.get(i), FeedData.class);
//
//                    String url = feedData.getImg_url();
//                    String userName = feedData.getUser_name();
//                    String content = feedData.getContent();
//                    int likeCount= feedData.getGood_count();
//                    int commentCount = feedData.getComment_number();
//                    String writeDate = feedData.getWrite_date();

//                    questionFeedAdapter.addItem(url, userName,writeDate, content,likeCount,commentCount);

                    FeedList.add(feedData);
                    Collections.reverse(FeedList);
                    questionFeedAdapter.addItem(FeedList);
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


    @Override
    public void clickLike(final int position) {
       // 같은 패키지 안에 있어야 접근 가능하다 !
       // TODO board isLike(Boolean) 보고 좋아요,좋아요취소 api쓰기
        RetrofitClient retrofitClient = new RetrofitClient();
        if(FeedList.get(position).good_count_ox==0){
            RequestClickLike boardId = new RequestClickLike(FeedList.get(position).board_id);
            Call<JsonObject> call = retrofitClient.apiService.ClickLike(boardId);
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.body().toString().contains("완료")) {
                            FeedList.get(position).setGood_count_ox(1);
                            getBoardList();
                    } else {
                        Log.d("error", response.errorBody().toString());
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Log.d("comment", "실패");

                }
            });
        }else{
                RequestClickLike boardId = new RequestClickLike(FeedList.get(position).board_id);
                Call<JsonObject> call = retrofitClient.apiService.ClickLikeCancel(boardId);
                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.body().toString().contains("취소")) {
                            FeedList.get(position).setGood_count_ox(0);
                            getBoardList();
                        } else {
                            Log.d("error", response.errorBody().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Log.d("comment", "실패");

                    }
                });
            }


    }
}

interface clickLike{
    void clickLike(int position);
}

