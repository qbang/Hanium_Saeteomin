package com.example.hanium_saeteomin.boardfragment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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

import com.bumptech.glide.Glide;
import com.example.hanium_saeteomin.DialogBuilder;
import com.example.hanium_saeteomin.R;
import com.example.hanium_saeteomin.network.RequestClickLike;
import com.example.hanium_saeteomin.network.RequestDeleteBoard;
import com.example.hanium_saeteomin.network.RequestDeleteComment;
import com.example.hanium_saeteomin.network.RequestGetComment;
import com.example.hanium_saeteomin.network.RequestUpdateBoard;
import com.example.hanium_saeteomin.network.RequestUpdateComment;
import com.example.hanium_saeteomin.network.RequestWriteComment;
import com.example.hanium_saeteomin.network.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoardDetailActivity extends AppCompatActivity implements onClickDelete,onClickUpdate{

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
    String content;
    TextView tvContent;
    TextView tvTimeLine;
     EditText editText;
     Boolean commentUpdate;

    private int WRITE_OK= 1;
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
        tvTimeLine = itemView.findViewById(R.id.timeline);
        tvContent = itemView.findViewById(R.id.question);
        final ImageView imgLike = itemView.findViewById(R.id.imageView2);
        final TextView likeCount = itemView.findViewById(R.id.favorite);

        ImageView commentImage = itemView.findViewById(R.id.imageView3);
        final TextView commentCount = itemView.findViewById(R.id.chatNum);

        tvCommentCount = findViewById(R.id.tv_comment_count);

        feedData = (FeedData)getIntent().getSerializableExtra("feedData");
        boardId = getIntent().getStringExtra("boardId");
        userId = getIntent().getStringExtra("userId");
        userName = getIntent().getStringExtra("userName");

        ImageButton btnDelete = itemView.findViewById(R.id.btn_delete_board);
        ImageButton btnUpdate = itemView.findViewById(R.id.btn_update_board);

        commentUpdate = false;

        if(feedData.getUser_name().equals(userName)){
            btnDelete.setVisibility(View.VISIBLE);
            btnUpdate.setVisibility(View.VISIBLE);
        }else{
            btnDelete.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.GONE);
        }

        if(feedData.getGood_count_ox()==1){
            Glide.with(this).load(R.drawable.ic_baseline_favorite_24).into(imgLike);
        }else{
            Glide.with(this).load(R.drawable.ic_baseline_favorite_border_24).into(imgLike);
        }

        editText = findViewById(R.id.editText);
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
        adapter.setListener2(this);
        rv_board_comment.setAdapter(adapter);

//        getCommentList();
            //Todo 클릭한 board id 를 가져와서 넣어주기

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RetrofitClient retrofitClient = new RetrofitClient();
                if(commentUpdate) {
                    RequestUpdateComment requestUpdateComment = new RequestUpdateComment(boardId,userId,editText.getText().toString());
                    Call<JsonObject> call = retrofitClient.apiService.UpdateComment(requestUpdateComment);
                    call.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            if (response.body().toString().contains("완료")) {
                                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                                editText.setText("");
                                getCommentList();
                            } else {
                                Log.d("error", response.errorBody().toString());
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            Log.d("comment", "실패");

                        }
                    });

                }else {
                    RequestWriteComment requestWriteComment = new RequestWriteComment(boardId, userId, userName, editText.getText().toString());
                    Call<JsonObject> call = retrofitClient.apiService.WriteComment(requestWriteComment);
                    call.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            Log.d("피드 등록", response.body().toString());
                            if (response.body().toString().contains("완료")) {

                                editText.setText("");
                                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                                getCommentList();

                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            Log.d("피드 등록", "실패");

                        }
                    });
                }
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

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 게시글 수정
                Intent intent = new Intent(BoardDetailActivity.this,BoardWriteActivity.class);
                intent.putExtra("beforeContent",feedData.getContent());
                intent.putExtra("update",true);
                intent.putExtra("boardId",boardId);
                intent.putExtra("userId",userId);
                intent.putExtra("userName",userName);
                startActivityForResult(intent,WRITE_OK);

            }
        });

        imgLike.setOnClickListener(new View.OnClickListener() {
            int count = feedData.getGood_count();
            @Override
            public void onClick(View view) {
                RetrofitClient retrofitClient = new RetrofitClient();
                if(feedData.good_count_ox==0){
                    final RequestClickLike boardId = new RequestClickLike(feedData.board_id);
                    Call<JsonObject> call = retrofitClient.apiService.ClickLike(boardId);
                    call.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            if (response.body().toString().contains("완료")) {
                                feedData.setGood_count_ox(1);
                                ++count;
                                likeCount.setText(String.valueOf(count));
                                Log.d("check1",String.valueOf(count));
                                if(feedData.getGood_count_ox()==1){
                                    Glide.with(getApplicationContext()).load(R.drawable.ic_baseline_favorite_24).into(imgLike);

                                }else{
                                    Glide.with(getApplicationContext()).load(R.drawable.ic_baseline_favorite_border_24).into(imgLike);
                                }

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
                    RequestClickLike boardId = new RequestClickLike(feedData.board_id);
                    Call<JsonObject> call = retrofitClient.apiService.ClickLikeCancel(boardId);
                    call.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            if (response.body().toString().contains("취소")) {
                                feedData.setGood_count_ox(0);
                                --count;
                                likeCount.setText(String.valueOf(count));
                                Log.d("check2",String.valueOf(count));
                                if(feedData.getGood_count_ox()==1){
                                    Glide.with(getApplicationContext()).load(R.drawable.ic_baseline_favorite_24).into(imgLike);
                                }else{
                                    Glide.with(getApplicationContext()).load(R.drawable.ic_baseline_favorite_border_24).into(imgLike);
                                }

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == WRITE_OK){
            content = data.getStringExtra("content");

            tvContent.setText(content);
            tvTimeLine.setText(timeConverter.toFormat(feedData.getWrite_date()));
        }
    }

    @Override
    public void onClickUpdate(int position) {
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, 0);
        commentUpdate = true;
    }
}

interface onClickDelete{
    void onClickDelete(int position);
}

interface onClickUpdate{
    void onClickUpdate(int position);
}