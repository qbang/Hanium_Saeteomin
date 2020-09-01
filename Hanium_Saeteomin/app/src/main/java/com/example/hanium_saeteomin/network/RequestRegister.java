package com.example.hanium_saeteomin.network;

public class RequestRegister {
    String user_id;
    String user_name;
    String user_password;

    public RequestRegister(String user_id, String user_name, String user_password){
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_password = user_password;
    }
}
