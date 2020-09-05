package com.example.hanium_saeteomin.network;

public class SendQuizResult {
    String user_id;
    String date;
    int score;

    public SendQuizResult(String user_id, String date, int score){
        this.user_id = user_id;
        this.date = date;
        this.score = score;
    }
}
