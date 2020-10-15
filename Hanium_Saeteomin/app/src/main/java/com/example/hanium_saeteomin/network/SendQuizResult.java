package com.example.hanium_saeteomin.network;

public class SendQuizResult {
    String user_id;
    int score;

    public SendQuizResult(String user_id, int score){
        this.user_id = user_id;
        this.score = score;
    }
}
