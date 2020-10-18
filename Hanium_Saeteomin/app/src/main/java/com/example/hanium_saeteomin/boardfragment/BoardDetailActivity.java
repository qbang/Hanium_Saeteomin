package com.example.hanium_saeteomin.boardfragment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hanium_saeteomin.DialogBuilder;
import com.example.hanium_saeteomin.R;
import com.example.hanium_saeteomin.bottomnavigation.BoardFragment;
import com.example.hanium_saeteomin.homefragment.AdapterQuiz;
import com.example.hanium_saeteomin.network.RequestDeleteBoard;
import com.example.hanium_saeteomin.network.RequestDeleteComment;
import com.example.hanium_saeteomin.network.RequestGetComment;
import com.example.hanium_saeteomin.network.RequestWriteComment;
import com.example.hanium_saeteomin.network.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoardDetailActivity extends AppCompatActivity implements onClickDelete{

    ArrayList<CommentData> commentList;
    CommentAdapter adapter;
    TextView tv_comment_nothing;
    RecyclerView rv_board_comment;
    FeedData feedData;
    TextView tvCommentCount;
    String boardId;
    String userId;
    String userName;
    TimeConverter timeConverter = new TimeConverter();

    @Override
    protected void onResume() {
        super.onResume();
        getCommentList();
    }

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

        ImageView commentImage = itemView.findViewById(R.id.imageView3);
        TextView commentCount = itemView.findViewById(R.id.chatNum);

        tvCommentCount = findViewById(R.id.tv_comment_count);

        feedData = (FeedData)getIntent().getSerializableExtra("feedData");
        boardId = getIntent().getStringExtra("boardId");
        userId = getIntent().getStringExtra("userId");
        userName = getIntent().getStringExtra("userName");

        Button btnDelete = itemView.findViewById(R.id.btn_delete_board);

        if(feedData.getUser_name().equals(userName)){
            btnDelete.setVisibility(View.VISIBLE);
        }else{
            btnDelete.setVisibility(View.GONE);
        }

        final EditText editText = findViewById(R.id.editText);
        ImageButton btnSend = findViewById(R.id.btn_send_message);


        Glide.with(getApplicationContext()).load(feedData.getImg_url()).into(tvImgUrl);
        tvUserName.setText(feedData.getUser_name());
        tvTimeLine.setText(timeConverter.toFormat(feedData.getWrite_date()));

        tvContent.setText(feedData.getContent());
        likeCount.setText(String.valueOf(feedData.getGood_count()));

        commentList = new ArrayList<CommentData>();

        tvCommentCount.setText("댓글 "+ commentList.size());

        commentImage.setVisibility(View.INVISIBLE);
        commentCount.setVisibility(View.INVISIBLE);

        container.addView(itemView);


        tv_comment_nothing = findViewById(R.id.tv_comment_nothing);
        rv_board_comment = findViewById(R.id.rv_board_comment);

        rv_board_comment.setLayoutManager(new LinearLayoutManager(getApplicationContext())) ;
        adapter = new CommentAdapter(commentList,userName) ;
        adapter.setListener(this);
        rv_board_comment.setAdapter(adapter);

//        getCommentList();
            //Todo 클릭한 board id 를 가져와서 넣어주기

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Log.d("user",boardId);
//                Log.d("user",userId);
//                Log.d("user",userName);
//                Log.d("user",editText.getText().toString());

                RetrofitClient retrofitClient = new RetrofitClient();
                RequestWriteComment requestWriteComment = new RequestWriteComment(boardId,userId,userName,editText.getText().toString());
                Call<JsonObject> call = retrofitClient.apiService.WriteComment(requestWriteComment);
                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        Log.d("피드 등록",response.body().toString());
                        if(response.body().toString().contains("완료")) {

                            editText.setText("");
                            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                            getCommentList();

                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Log.d("피드 등록","실패");

                    }
                });

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogBuilder dialogBuilder = new DialogBuilder(BoardDetailActivity.this);
                dialogBuilder.build(R.layout.dialog_base);
                dialogBuilder.btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        RetrofitClient retrofitClient = new RetrofitClient();
                        RequestDeleteBoard boardId = new RequestDeleteBoard(feedData.board_id);
                        Call<JsonObject> call = retrofitClient.apiService.DeleteBoard(boardId);
                        call.enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                if (response.body().toString().contains("완료")) {
                                } else {
                                    Log.d("error", response.errorBody().toString());
                                }
                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {
                                Log.d("comment", "실패");

                            }
                        });
                        dialogBuilder.dismiss();
                        finish();
                    }
                });
                dialogBuilder.btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogBuilder.dismiss();
                    }
                });

            }
        });
    }

    private void getCommentList(){
        RetrofitClient retrofitClient = new RetrofitClient();
        RequestGetComment commentId = new RequestGetComment(feedData.board_id);
        Call<JsonArray> call = retrofitClient.apiService.GetBoardCommentList(commentId);
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if(response.body().size()<1){
                    tv_comment_nothing.setVisibility(View.VISIBLE);
                    rv_board_comment.setVisibility(View.GONE);
                }else{
                    tv_comment_nothing.setVisibility(View.GONE);
                    rv_board_comment.setVisibility(View.VISIBLE);
                }

                commentList.clear();
                for (int i = 0; i < response.body().size(); i++) {

                    Log.d("comment",response.body().toString());
                    Gson gson = new Gson();
                    JsonArray array = response.body().getAsJsonArray();
                    CommentData commentData = gson.fromJson(array.get(i), CommentData.class);

                    commentList.add(commentData);
                    adapter.notifyDataSetChanged();
                }
                tvCommentCount.setText("댓글 " +commentList.size());

            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.d("comment","실패");

            }
        });

    }

    @Override
    public void onClickDelete(final int position) {

        final DialogBuilder dialogBuilder = new DialogBuilder(BoardDetailActivity.this);
        dialogBuilder.build(R.layout.dialog_base);
        dialogBuilder.btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RetrofitClient retrofitClient = new RetrofitClient();
                RequestDeleteComment requestDeleteComment = new RequestDeleteComment(boardId,userId, commentList.get(position).getComment());
                Call<JsonObject> call = retrofitClient.apiService.DeleteComment(requestDeleteComment);
                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.body().toString().contains("완료")) {
                            adapter.notifyItemRemoved(position);
                            getCommentList();
                            //TODO 삭제했을때 바로 반영
                        } else {
                            Log.d("error", response.errorBody().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Log.d("comment", "실패");

                    }
                });
                dialogBuilder.dismiss();
            }
        });
        dialogBuilder.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder.dismiss();
            }
        });

    }
}

interface onClickDelete{
    void onClickDelete(int position);
}