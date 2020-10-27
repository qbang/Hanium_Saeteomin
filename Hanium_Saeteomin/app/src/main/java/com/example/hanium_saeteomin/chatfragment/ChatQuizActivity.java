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
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.json.JSONObject;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatQuizActivity extends AppCompatActivity implements Button.OnClickListener{
    private ListView listview;
    private ChatQuizActivity.ChatQuizAdapter adapter;
    private Button verify;
    private TextView question;
    private int score;
//    private String uid = user_id;
    private boolean check;
    private RetrofitClient retrofitClient;
    private JsonArray qlist;
    private int count;
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

        count = 0;
        check = false;

        verify.setOnClickListener(this);

        requestQuizList();
    }

    @Override
    public void onClick(View view) {
        String params = question.getText().toString();
        adapter.addItem(params,0);

        String ans = qlist.get(count-1).getAsJsonObject().get("answer").getAsString();

        if (params.equals(ans) && check==false) {
            adapter.addItem("정답입니다.",1);
            score += 10;
        }else if(check == true){
            adapter.addItem("이미 오늘의 퀴즈를 모두 푸셨습니다.",1);
        }else{
            adapter.addItem("정답이 아닙니다. 정답은 "+ans+"번 입니다.",1);
        }
        adapter.notifyDataSetChanged();
        startQuizTask();
    }

    public void requestQuizList(){
        System.out.println("퀴즈 리스트 요청");
        retrofitClient = new RetrofitClient();
        RequestGetQuizList requestGetQuizList = new RequestGetQuizList("abc@abc.com");
        Call<JsonArray> call = retrofitClient.apiService.GetQuizList(requestGetQuizList);
            try{
                call.enqueue(new Callback<JsonArray>() {
                    @Override
                    public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                        qlist = response.body();
                        Log.d("sucessss",qlist.toString());
                        adapter.addItem("퀴즈를 시작합니다.",1);
                        startQuizTask();
                    }
                    @Override
                    public void onFailure(Call<JsonArray> call3, Throwable t3) {
                        Log.d("faillllll",t3.toString());
                        adapter.addItem("이미 오늘의 퀴즈를 모두 푸셨습니다.",1);
                    }
            });
        }catch (Exception e){
                e.printStackTrace();
        }
        adapter.notifyDataSetChanged();
    }

    public void startQuizTask(){
        try{
            if(count<10){
                String str = (count+1) + "번째 문제입니다. "
                        + qlist.get(count).getAsJsonObject().get("question").toString() + "\n"
                        + "1. " + qlist.get(count).getAsJsonObject().get("E1").toString() + "\n"
                        + "2. " + qlist.get(count).getAsJsonObject().get("E2").toString() + "\n"
                        + "3. " + qlist.get(count).getAsJsonObject().get("E3").toString() + "\n"
                        + "4. " + qlist.get(count).getAsJsonObject().get("E4").toString();
                adapter.addItem(str,1);
                adapter.notifyDataSetChanged();

                count ++;
            }else{
                adapter.addItem("퀴즈를 모두 푸셨습니다. 점수는 "+score+"점 입니다.", 1);
                adapter.notifyDataSetChanged();
                //점수 올리기
                retrofitClient = new RetrofitClient();
                SendQuizResult sendQuizResult = new SendQuizResult("abc@abc.com", score);
                Call<JsonObject> call2 = retrofitClient.apiService.SaveScore(sendQuizResult);
                try{
                    call2.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            Log.d("sucessss",response.body().toString());
                            check = true;
                        }
                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            Log.d("faillllll",t.toString());
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
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