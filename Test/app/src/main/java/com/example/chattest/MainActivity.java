package com.example.chattest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.chattest.Chat.ChatDictionary;
import com.example.chattest.FriendList.FriendList;
import com.example.chattest.Question.QuestionFeed;

public class MainActivity extends AppCompatActivity implements Button.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1 = findViewById(R.id.button);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);

        button1.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        Intent intent = null;
        
        switch (view.getId()){
            case R.id.button:
                intent = new Intent(MainActivity.this, ChatDictionary.class);
                break;
            case R.id.button2:
                break;
            case R.id.button3:
                intent = new Intent(MainActivity.this, QuestionFeed.class);
                break;
            case R.id.button4:
                intent = new Intent(MainActivity.this, PersonalProfile.class);
                break;
            case R.id.button5:
                intent = new Intent(MainActivity.this, FriendList.class);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }

        startActivity(intent);
    }
}