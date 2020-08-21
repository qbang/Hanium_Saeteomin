package com.example.hanium_saeteomin.bottomnavigation;

import android.content.Context;
import android.content.Intent;
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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class ChattingFragment extends Fragment implements Button.OnClickListener{

    ListView listview;
    static ChatDictionaryAdapter adapter;
    ImageView goQuiz;
    Button verify;
    TextView question;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        question = (TextView) view.findViewById(R.id.question);

        goQuiz = (ImageView)view.findViewById(R.id.goQuiz);
        verify = (Button) view.findViewById(R.id.verify);
        goQuiz.setOnClickListener(this);
        verify.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                String params = question.getText().toString();
                adapter.addItem(params,0);
                new RestAPITask("http://10.0.2.2:5000/"+params).execute();
            }
        });

        adapter = new ChatDictionaryAdapter();
        listview = (ListView) view.findViewById(R.id.listview);
        listview.setAdapter(adapter);
        listview.setSelection(adapter.getCount() - 1);
        listview.setAdapter(adapter);

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
            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }
    }

    public static class RestAPITask extends AsyncTask<Integer, Void, Void> {
        protected String mURL;

        public RestAPITask(String url) {
            mURL = url;
        }

        protected Void doInBackground(Integer... params) {
            String result = null;

            try {
                URL url = new URL(mURL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                InputStream is = conn.getInputStream();

                StringBuilder builder = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }

                result = builder.toString();
                adapter.addItem(result,1);
                adapter.notifyDataSetChanged();
            }
            catch (Exception e) {
                Log.e("REST_API", "GET method failed: " + e.getMessage());
                e.printStackTrace();
            }
            return null;
        }
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