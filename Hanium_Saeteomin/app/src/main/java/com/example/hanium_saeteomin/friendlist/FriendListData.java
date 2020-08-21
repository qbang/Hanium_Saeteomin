package com.example.hanium_saeteomin.friendlist;

import android.graphics.drawable.Drawable;

public class FriendListData {
    private Drawable profileImg;
    private String name;
    private String location;

    public void setProfileImg(Drawable drawable){
        profileImg = drawable;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setLocation(String location){
        this.location = location;
    }

    public Drawable getProfileImg(){
        return this.profileImg;
    }
    public String getName(){
        return this.name;
    }
    public String getLocation(){
        return this.location;
    }

}
