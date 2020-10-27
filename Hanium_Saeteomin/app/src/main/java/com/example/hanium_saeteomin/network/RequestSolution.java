package com.example.hanium_saeteomin.network;

import com.google.gson.JsonElement;

public class RequestSolution {
    private String assistant_id;
    private JsonElement session_id;
    private String message_input;

    public RequestSolution(String assistant_id, JsonElement session_id, String message_input){
        this.assistant_id = assistant_id;
        this.session_id = session_id;
        this.message_input = message_input;
    }

}
