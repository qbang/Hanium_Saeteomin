package com.example.hanium_saeteomin.network;

public class RequestWriteComment {
    String board_id;
    String user_id;
    String user_name;
    String comment;

    public RequestWriteComment(String boardId, String userId, String userName, String comment) {
        board_id = boardId;
        user_id = userId;
        user_name = userName;
        this.comment = comment;
    }
}
