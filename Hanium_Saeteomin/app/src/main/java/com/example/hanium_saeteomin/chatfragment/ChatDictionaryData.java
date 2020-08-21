package com.example.hanium_saeteomin.chatfragment;

public class ChatDictionaryData {
    private int type;
    private String response;
    private String request;

    public void setType(int type){
        this.type = type;
    }
    public void setResponse(String response){
        this.response = response;
    }
    public void setRequest(String request){
        this.request = request;
    }

    public int getType(){
        return this.type;
    }
    public String getResponse(){
        return this.response;
    }
    public String getRequest(){
        return this.request;
    }
}
