package com.example.hanium_saeteomin.network;

public class RequestUpdateBoard {
    String board_id;
    String content;

    public RequestUpdateBoard(String board_id, String content) {
        this.board_id = board_id;
        this.content = content;
    }
}
