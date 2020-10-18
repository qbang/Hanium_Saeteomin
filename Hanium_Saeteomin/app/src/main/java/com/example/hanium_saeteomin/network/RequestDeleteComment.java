package com.example.hanium_saeteomin.network;

public class RequestDeleteComment {
    String board_id;
    String user_id;
    String comment;

    public RequestDeleteComment(String board_id, String user_id, String comment) {
        this.board_id = board_id;
        this.user_id = user_id;
        this.comment = comment;
    }
}
