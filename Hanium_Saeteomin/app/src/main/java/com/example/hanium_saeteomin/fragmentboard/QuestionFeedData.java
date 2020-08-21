package com.example.hanium_saeteomin.fragmentboard;

import android.graphics.drawable.Drawable;

public class QuestionFeedData {
    private Drawable profileImg;
    private String name;
    private String timeline;
    private String question;
    private String favorite;
    private String chatNum;

    public void setProfileImg(Drawable drawable){
        profileImg = drawable;
    }
    public void setName(String n){
        name = n;
    }
    public void setTimeline(String t){
        timeline = t;
    }
    public void setQuestion(String q){
        question = q;
    }
    public void setFavorite(String f){
        favorite = f;
    }
    public void setChatNum(String c){
        chatNum = c;
    }

    public Drawable getProfileImg(){
        return this.profileImg;
    }
    public String getName(){
        return this.name;
    }
    public String getTimeline(){
        return this.timeline;
    }
    public String getQuestion(){
        return this.question;
    }
    public String getFavorite(){
        return this.favorite;
    }
    public String getChatNum(){
        return this.chatNum;
    }
}
