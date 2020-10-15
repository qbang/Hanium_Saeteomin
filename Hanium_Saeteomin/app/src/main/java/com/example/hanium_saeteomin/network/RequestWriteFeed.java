package com.example.hanium_saeteomin.network;

public class RequestWriteFeed {
    String user_id;
    String user_name;
    String content;

    public RequestWriteFeed(String user_id, String user_name, String content) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.content = content;
    }
}
