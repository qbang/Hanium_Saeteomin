package com.example.hanium_saeteomin.boardfragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hanium_saeteomin.R;
import com.example.hanium_saeteomin.bottomnavigation.BoardFragment;
import com.example.hanium_saeteomin.homefragment.AdapterQuiz;
import com.example.hanium_saeteomin.network.RequestGetComment;
import com.example.hanium_saeteomin.network.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.lang.reflect.Array;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoardDetailActivity extends AppCompatActivity{

    ArrayList<CommentData> commentList;
    CommentAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_detail);

        FrameLayout container = findViewById(R.id.layout_board);
        View itemView = LayoutInflater.from(this).inflate(R.layout.question_feed_layout,container, false);
        ImageView tvImgUrl = itemView.findViewById(R.id.profileImg);
        TextView tvUserName= itemView.findViewById(R.id.name);
        TextView tvTimeLine = itemView.findViewById(R.id.timeline);
        TextView tvContent = itemView.findViewById(R.id.question);
//        ImageView imgLike = itemView.findViewById(R.id.imageView2);
        TextView likeCount = itemView.findViewById(R.id.favorite);
        TextView commentCount = itemView.findViewById(R.id.chatNum);

        FeedData feedData = (FeedData)getIntent().getSerializableExtra("feedData");

        Glide.with(getApplicationContext()).load(feedData.getImg_url()).into(tvImgUrl);
        tvUserName.setText(feedData.getUser_name());
        tvTimeLine.setText(feedData.getWrite_date());
        tvContent.setText(feedData.getContent());
        likeCount.setText(String.valueOf(feedData.getGood_count()));
        commentCount.setText(String.valueOf(feedData.getComment_number()));

        container.addView(itemView);

        commentList = new ArrayList<CommentData>();
        TextView tv_comment_nothing = findViewById(R.id.tv_comment_nothing);
        RecyclerView rv_board_comment = findViewById(R.id.rv_board_comment);
        if(commentList.size()==0){
            tv_comment_nothing.setVisibility(View.VISIBLE);
            rv_board_comment.setVisibility(View.GONE);
        }else{
            tv_comment_nothing.setVisibility(View.GONE);
            rv_board_comment.setVisibility(View.VISIBLE);
        }

        rv_board_comment.setLayoutManager(new LinearLayoutManager(getApplicationContext())) ;
        adapter = new CommentAdapter(commentList) ;
        rv_board_comment.setAdapter(adapter);

            //Todo 클릭한 board id 를 가져와서 넣어주기
        RetrofitClient retrofitClient = new RetrofitClient();
        RequestGetComment commentId = new RequestGetComment(feedData.board_id);
        Call<JsonArray> call = retrofitClient.apiService.GetBoardCommentList(commentId);
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                for (int i = 0; i < response.body().size(); i++) {
                    Log.d("comment",response.body().toString());
                    Gson gson = new Gson();
                    JsonArray array = response.body().getAsJsonArray();
                    CommentData commentData = gson.fromJson(array.get(i), CommentData.class);

                    String boardId = commentData.getBoard_id();
                    String comment = commentData.getComment();
                    String image = commentData.getImage();
                    String userId= commentData.getUser_id();
                    String userName = commentData.getUser_name();
                    String writeDate = commentData.getWrite_date();

                    commentList.add(commentData);
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.d("comment","실패");

            }
        });

    }
}