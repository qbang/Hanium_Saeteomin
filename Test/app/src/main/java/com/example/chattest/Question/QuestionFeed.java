package com.example.chattest.Question;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.widget.ListView;

import com.example.chattest.R;

public class QuestionFeed extends AppCompatActivity {
    ListView listView;
    QuestionFeedAdapter questionFeedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_feed);

        questionFeedAdapter = new QuestionFeedAdapter();

        listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(questionFeedAdapter);

        this.addQuestionFeed();

    }
    public void addQuestionFeed(){
        questionFeedAdapter.addItem(ContextCompat.getDrawable(this, R.drawable.test), "홍길동", "10분전", "~~~가 뭔가요?", "5", "4");
        questionFeedAdapter.addItem(ContextCompat.getDrawable(this, R.drawable.test), "홍길동", "10분전", "~~~가 뭔가요?", "5", "4");
        questionFeedAdapter.addItem(ContextCompat.getDrawable(this, R.drawable.test), "홍길동", "10분전", "~~~가 뭔가요?", "5", "4");
        questionFeedAdapter.addItem(ContextCompat.getDrawable(this, R.drawable.test), "홍길동", "10분전", "~~~가 뭔가요?", "5", "4");
        questionFeedAdapter.addItem(ContextCompat.getDrawable(this, R.drawable.test), "홍길동", "10분전", "~~~가 뭔가요?", "5", "4");
        questionFeedAdapter.addItem(ContextCompat.getDrawable(this, R.drawable.test), "홍길동", "10분전", "~~~가 뭔가요?", "5", "4");
        questionFeedAdapter.addItem(ContextCompat.getDrawable(this, R.drawable.test), "홍길동", "10분전", "~~~가 뭔가요?", "5", "4");
        questionFeedAdapter.addItem(ContextCompat.getDrawable(this, R.drawable.test), "홍길동", "10분전", "~~~가 뭔가요?", "5", "4");
    }
}