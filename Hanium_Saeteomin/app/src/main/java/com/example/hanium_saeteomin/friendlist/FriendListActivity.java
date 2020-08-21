package com.example.hanium_saeteomin.friendlist;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.hanium_saeteomin.R;


public class FriendListActivity extends AppCompatActivity {
    ListView listView;
    FriendListAdapter friendListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        friendListAdapter = new FriendListAdapter();

        listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(friendListAdapter);

        this.addFrendList();
    }

    public void addFrendList(){
        friendListAdapter.addItem(ContextCompat.getDrawable(this, R.drawable.test), "홍길동", "위치");
        friendListAdapter.addItem(ContextCompat.getDrawable(this, R.drawable.test), "홍길동", "위치");
        friendListAdapter.addItem(ContextCompat.getDrawable(this, R.drawable.test), "홍길동", "위치");
        friendListAdapter.addItem(ContextCompat.getDrawable(this, R.drawable.test), "홍길동", "위치");
        friendListAdapter.addItem(ContextCompat.getDrawable(this, R.drawable.test), "홍길동", "위치");
        friendListAdapter.addItem(ContextCompat.getDrawable(this, R.drawable.test), "홍길동", "위치");
        friendListAdapter.addItem(ContextCompat.getDrawable(this, R.drawable.test), "홍길동", "위치");
        friendListAdapter.addItem(ContextCompat.getDrawable(this, R.drawable.test), "홍길동", "위치");
    }
}