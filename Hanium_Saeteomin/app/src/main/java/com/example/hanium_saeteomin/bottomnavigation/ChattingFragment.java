package com.example.hanium_saeteomin.bottomnavigation;

import android.content.Context;
import android.content.Intent;
import android.icu.text.Edits;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.example.hanium_saeteomin.R;
import com.example.hanium_saeteomin.chatfragment.ChatDictionaryData;
import com.example.hanium_saeteomin.chatfragment.ChatQuizActivity;
import com.example.hanium_saeteomin.network.RequestSolution;
import com.example.hanium_saeteomin.network.RetrofitClient;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChattingFragment extends Fragment implements Button.OnClickListener{

    private ListView listview;
    static ChatDictionaryAdapter adapter;
    private ImageView goQuiz;
    private Button verify;
    private TextView question;
    private String[] rsp;
    private RetrofitClient retrofitClient;
    private JsonElement session_id;
    private String assistant_id = "4f98009d-9878-4aa7-a115-dff718d05e4c";

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        question = (TextView) view.findViewById(R.id.question);
        goQuiz = (ImageView)view.findViewById(R.id.goQuiz);
        verify = (Button) view.findViewById(R.id.verify);

        goQuiz.setOnClickListener(this);
        verify.setOnClickListener(this);

        adapter = new ChatDictionaryAdapter();
        retrofitClient = new RetrofitClient();

        listview = (ListView) view.findViewById(R.id.listview);
        listview.setAdapter(adapter);
        listview.setSelection(adapter.getCount() - 1);
        listview.setAdapter(adapter);
        listview.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);

        adapter.addItem("질문을 입력해주세요.",1);
        adapter.notifyDataSetChanged();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chatting, container, false);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;

        switch (view.getId()){
            case R.id.goQuiz:
                intent = new Intent(getContext(), ChatQuizActivity.class);
                startActivity(intent);
                break;
            case R.id.verify:
                final String params = question.getText().toString();
                adapter.addItem(params,0);
                adapter.notifyDataSetChanged();

                if(session_id == null){
                    startBotTask(params);
                }else{
                    callBotTask(params);
                }

                break;
            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }
    }

    public void startBotTask(final String params){
        Call<JsonObject> call = retrofitClient.apiService.RequestQuizInfo();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                assistant_id = String.valueOf(response.body().get("assistant_id"));
                session_id = response.body().get("session_id");
                callBotTask(params);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                adapter.addItem("응답을 받아오는데 실패하였습니다.",1);
                Log.d("faillllll",t.toString());
            }
        });
        adapter.notifyDataSetChanged();
    }

    private void callBotTask(String params){
        RequestSolution requestSolution = new RequestSolution(assistant_id,session_id,params);
        Call<JsonObject> call2 = retrofitClient.apiService.RequestSolution(requestSolution);
        call2.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call2, Response<JsonObject> response2) {
                rsp = response2.body().get("output").getAsJsonArray().get(0).toString().split("\"");
                Log.d("eeeeeeeeeeeeeeeee", Arrays.toString(rsp));
                adapter.addItem(rsp[3],1);
            }

            @Override
            public void onFailure(Call<JsonObject> call2, Throwable t2) {
                adapter.addItem("응답을 받아오는데 실패하였습니다.",1);
                Log.d("faillllll",t2.toString());
            }
        });

        adapter.notifyDataSetChanged();
    }

    public static class ChatDictionaryAdapter extends BaseAdapter {
        private static final int ITEM_VIEW_TYPE_REQ = 0;
        private static final int ITEM_VIEW_TYPE_RES = 1;
        private static final int ITEM_VIEW_TYPE_MAX = 2;

        private ArrayList<ChatDictionaryData> chatDictionaryDataList = new ArrayList<ChatDictionaryData>();

        public ChatDictionaryAdapter(){}

        @Override
        public int getCount(){
            return chatDictionaryDataList.size();
        }
        @Override
        public int getViewTypeCount(){
            return ITEM_VIEW_TYPE_MAX;
        }
        @Override
        public int getItemViewType(int position) {
            return chatDictionaryDataList.get(position).getType() ;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            final Context context = parent.getContext();
            int viewType = getItemViewType(position) ;
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

            if (v == null) {
                v = inflater.inflate(R.layout.question_feed_layout, null);
                ViewHolder holder = new ViewHolder();
                holder.request = (TextView)v.findViewById(R.id.request);
                holder.response = (TextView)v.findViewById(R.id.response);
                v.setTag(holder);
            }
            ChatDictionaryData chatDictionaryData = chatDictionaryDataList.get(position);

            if(chatDictionaryData != null){
                ViewHolder holder = (ViewHolder)v.getTag();
                switch (viewType) {
                    case ITEM_VIEW_TYPE_REQ:
                        convertView = inflater.inflate(R.layout.chat_dictionary_req, parent, false);

                        TextView req = (TextView) convertView.findViewById(R.id.request);
                        req.setText(chatDictionaryData.getRequest());

                        break;
                    case ITEM_VIEW_TYPE_RES:
                        convertView = inflater.inflate(R.layout.chat_dictionary_res, parent, false);

                        TextView res = (TextView) convertView.findViewById(R.id.response);
                        res.setText(chatDictionaryData.getResponse());

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
            return chatDictionaryDataList.get(position) ;
        }

        public void addItem(String str, int type) {
            ChatDictionaryData item = new ChatDictionaryData();

            if(type == 0){
                item.setType(ITEM_VIEW_TYPE_REQ) ;
                item.setRequest(str);
            }else{
                item.setType(ITEM_VIEW_TYPE_RES);
                item.setResponse(str);
            }

            chatDictionaryDataList.add(item) ;
        }

        public class ViewHolder{
            TextView response;
            TextView request;
        }
    }

}