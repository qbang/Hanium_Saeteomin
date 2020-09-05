package com.example.hanium_saeteomin.chatfragment;

import android.content.Context;
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
import com.example.hanium_saeteomin.network.RetrofitClient;
import com.example.hanium_saeteomin.network.SendQuizResult;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatQuizActivity extends AppCompatActivity {
    ListView listview;
    ChatQuizActivity.ChatQuizAdapter adapter;
    Button verify;
    TextView question;
    HttpURLConnection conn;
    // 버튼 누를 때마다 연결 요청할 수 없으니 count
    int connectionCount = 0;

    // 리턴이 스트링으로 된다 치면
    String result = null;
    String[] quizlist = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_quiz);
        question = (TextView) findViewById(R.id.question);
        verify = (Button) findViewById(R.id.verify);
        verify.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                connectionCount ++;
                String params = question.getText().toString();
                adapter.addItem(params,0);
                final RetrofitClient retrofitClient1 = new RetrofitClient();
                final RetrofitClient retrofitClient2 = new RetrofitClient();
//                String user_id = "abc@abc.com";
//                String user_pw = "1234";
                Call<JsonArray> call = retrofitClient1.apiService.GetQuizList();
                call.enqueue(new Callback<JsonArray>() {
                    @Override
                    public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                        Log.d("quizzzzzzzzzzz",response.body().toString());
                        SendQuizResult sendQuizResult = new SendQuizResult("abc", "2020-09-15", 80);
                        Call<JsonObject> call2 = retrofitClient2.apiService.QuizResult(sendQuizResult);
                        call2.enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call2, Response<JsonObject> response2) {
                                Log.d("successs",response2.body().toString());
                            }

                            @Override
                            public void onFailure(Call<JsonObject> call2, Throwable t2) {
                                Log.d("register",t2.getMessage());
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<JsonArray> call, Throwable t) {
//                        Log.d("login",t.getMessage());

                    }
                });
//                new ChatQuizActivity.RestAPITask("http://10.0.2.2:5000/"+params).execute();
            }
        });

        adapter = new ChatQuizActivity.ChatQuizAdapter();
        listview = (ListView) findViewById(R.id.listview);
        listview.setAdapter(adapter);
        listview.setSelection(adapter.getCount() - 1);
        listview.setAdapter(adapter);
    }

//    class RestAPITask extends AsyncTask<Integer, Void, Void> {
//        protected String mURL;
//
//        public RestAPITask(String url) {
//            mURL = url;
//        }
//        protected Void doInBackground(Integer... params) {
//            try {
//                URL url = new URL(mURL);
//                if(connectionCount == 1 || connectionCount>10){
//                    if(connectionCount>10){
//                        connectionCount = 1;
//                    }
//                    conn = (HttpURLConnection) url.openConnection();
//                    conn.setRequestMethod("GET");
//                    InputStream is = conn.getInputStream();
//
//                    StringBuilder builder = new StringBuilder();
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
//                    String line;
//                    while ((line = reader.readLine()) != null) {
//                        builder.append(line);
//                    }
//                    result = builder.toString();
//                    // 1,2,3,4 이런식으로 넘어온다고 가정
//                    quizlist = result.split(",");
//                    System.out.println("connection");
//                }
//                System.out.println("횟수"+connectionCount);
//                adapter.addItem(result,1);
//            }
//            catch (Exception e) {
//                Log.e("REST_API", "GET method failed: " + e.getMessage());
//                e.printStackTrace();
//            }
//            return null;
//        }
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            adapter.notifyDataSetChanged();
//        }
//    }
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