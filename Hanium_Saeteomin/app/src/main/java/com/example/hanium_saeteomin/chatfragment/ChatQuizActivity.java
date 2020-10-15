package com.example.hanium_saeteomin.chatfragment;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.hanium_saeteomin.R;
import com.example.hanium_saeteomin.network.RequestGetQuizList;
import com.example.hanium_saeteomin.network.RetrofitClient;
import com.example.hanium_saeteomin.network.SendQuizResult;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.example.hanium_saeteomin.LoginActivity.user_id;

public class ChatQuizActivity extends AppCompatActivity implements Button.OnClickListener{
    private ListView listview;
    private ChatQuizActivity.ChatQuizAdapter adapter;
    private Button verify;
    private TextView question;
    private int score;
    private String uid = user_id;
    boolean check = true;
    private RetrofitClient retrofitClient;
    private RequestGetQuizList requestGetQuizList;
    private JSONArray qlist;
    private int num;
    private JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_quiz);

        question = (TextView) findViewById(R.id.question);
        verify = (Button) findViewById(R.id.verify);

        adapter = new ChatQuizActivity.ChatQuizAdapter();
        listview = (ListView) findViewById(R.id.listview);
        listview.setAdapter(adapter);
        listview.setSelection(adapter.getCount() - 1);
        listview.setAdapter(adapter);

        retrofitClient = new RetrofitClient();
        requestGetQuizList = new RequestGetQuizList(uid);

        verify.setOnClickListener(this);

        if(startQuizTask()){
            adapter.addItem("이미 오늘의 퀴즈를 다 푸셨습니다.",1);
        }else{
            adapter.addItem("퀴즈를 시작합니다.",1);
            botTask();
        }
    }

    @Override
    public void onClick(View view) {
        try {
            String params = question.getText().toString();
            adapter.addItem(params,0);
            if(jsonObject != null){
                if (params.equals(jsonObject.getString("answer")) && check==false) {
                    adapter.addItem("정답입니다.",1);
                    score += 10;
                }else{
                    adapter.addItem("정답이 아닙니다. 정답은 "+jsonObject.getString("answer")+"번 입니다.",1);
                }
            }

            adapter.notifyDataSetChanged();
            num ++;
            botTask();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private boolean startQuizTask(){
        Call<JsonObject> call = retrofitClient.apiService.GetQuizList(requestGetQuizList);
        try{
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if(response.isSuccessful()){
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().toString());
                            JSONArray jsonArray = jsonObject.getJSONArray("result");
                            Gson gson = new Gson();
                            ArrayList<ChatQuizVO> quiz_list = new ArrayList<>();

                            int index = 0;
                            while(index < jsonArray.length()){
                                ChatQuizVO chatQuizVO = gson.fromJson(jsonArray.get(index).toString(), ChatQuizVO.class);
                                quiz_list.add(chatQuizVO);

                                index++;
                            }
//                        qlist = new JSONArray(response.body());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else{
                        Log.d("응답받아오는","뎅 실패");
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call3, Throwable t3) {
                    check = false;
                    Log.d("faillllll",t3.toString());
                }
            });
        }catch (Exception e){
            check = false;
        }

        return check;
    }

    private void botTask(){
        if(jsonObject != null && num != 10) {
            try {
                jsonObject = qlist.getJSONObject(num);
                String str = jsonObject.getString("question\n")
                        + "\n1)" + jsonObject.getString("E1")
                        + "\n2)" + jsonObject.getString("E2")
                        + "\n3)" + jsonObject.getString("E3")
                        + "\n4)" + jsonObject.getString("E4");
                adapter.addItem(str, 1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            SendQuizResult sendQuizResult = new SendQuizResult(uid, score);
            Call<JsonObject> call2 = retrofitClient.apiService.SaveScore(sendQuizResult);
            call2.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    Log.d("success",response.body().toString());
                    adapter.addItem("점수는 "+score+"점입니다. 수고하셨습니다.", 1);
                    check = true;
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Log.d("fail",t.toString());
                    adapter.addItem("점수를 등록하는데 실패하였습니다. 다시 시도해주세요.", 1);
                }
            });
        }
        adapter.notifyDataSetChanged();
    }

    public static class ChatQuizAdapter extends BaseAdapter {
        private static final int ITEM_VIEW_TYPE_REQ = 0;
        private static final int ITEM_VIEW_TYPE_RES = 1;
        private static final int ITEM_VIEW_TYPE_MAX = 2;

        private ArrayList<ChatQuizData> chatQuizDataList = new ArrayList<ChatQuizData>();

        public ChatQuizAdapter(){}

        @Override
        public int getCount(){
            return chatQuizDataList.size();
        }
        @Override
        public int getViewTypeCount(){
            return ITEM_VIEW_TYPE_MAX;
        }
        @Override
        public int getItemViewType(int position) {
            return chatQuizDataList.get(position).getType() ;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            final Context context = parent.getContext();
            int viewType = getItemViewType(position) ;
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE) ;

            if (v == null) {
                v = inflater.inflate(R.layout.question_feed_layout, null);
                ChatQuizActivity.ChatQuizAdapter.ViewHolder holder = new ChatQuizActivity.ChatQuizAdapter.ViewHolder();
                holder.request = (TextView)v.findViewById(R.id.request);
                holder.response = (TextView)v.findViewById(R.id.response);
                v.setTag(holder);
            }
            ChatQuizData chatQuizData = chatQuizDataList.get(position);

            if(chatQuizData != null){
                ChatQuizActivity.ChatQuizAdapter.ViewHolder holder = (ChatQuizActivity.ChatQuizAdapter.ViewHolder)v.getTag();
                switch (viewType) {
                    case ITEM_VIEW_TYPE_REQ:
                        convertView = inflater.inflate(R.layout.chat_quiz_req, parent, false);

                        TextView req = (TextView) convertView.findViewById(R.id.request);
                        req.setText(chatQuizData.getRequest());

                        break;
                    case ITEM_VIEW_TYPE_RES:
                        convertView = inflater.inflate(R.layout.chat_quiz_res, parent, false);

                        TextView res = (TextView) convertView.findViewById(R.id.response);
                        res.setText(chatQuizData.getResponse());

                        break;
                }
            }

            return convertView;
        }

        @Override
        public long getItemId(int position) {
            return position ;
        }

        @Override
        public Object getItem(int position) {
            return chatQuizDataList.get(position) ;
        }

        public void addItem(String str, int type) {
            ChatQuizData item = new ChatQuizData();

            if(type == 0){
                item.setType(ITEM_VIEW_TYPE_REQ) ;
                item.setRequest(str);
            }else{
                item.setType(ITEM_VIEW_TYPE_RES);
                item.setResponse(str);
            }

            chatQuizDataList.add(item) ;
        }

        public class ViewHolder{
            TextView response;
            TextView request;
        }
    }
}