package com.example.hanium_saeteomin.network;

public class RequestLogin {
    String user_id;
    String user_password;

    public RequestLogin(String user_id, String user_password){
        this.user_id = user_id;
        this.user_password = user_password;
    }
}
