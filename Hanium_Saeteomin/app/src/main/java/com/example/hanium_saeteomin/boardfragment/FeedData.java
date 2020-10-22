package com.example.hanium_saeteomin.boardfragment;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.versionedparcelable.ParcelField;

import java.io.Serializable;

public class FeedData implements Serializable {
    String board_id;
    String user_name;
    String img_url;
    int comment_number;
    String content;
    public int good_count;
    String user_id;
    String to_char;
    int good_count_ox = 0;

    public FeedData() {
    }

    public FeedData(String board_id, String user_name, String img_url, int comment_number, String content, int good_count, String user_id, String to_char) {
        this.board_id = board_id;
        this.user_name = user_name;
        this.img_url = img_url;
        this.comment_number = comment_number;
        this.content = content;
        this.good_count = good_count;
        this.user_id = user_id;
        this.to_char = to_char;
    }

    public int getGood_count_ox() {
        return good_count_ox;
    }

    public void setGood_count_ox(int good_count_ox) {
        this.good_count_ox = good_count_ox;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getBoard_id() {
        return board_id;
    }

    public void setBoard_id(String board_id) {
        this.board_id = board_id;
    }

    public int getComment_number() {
        return comment_number;
    }

    public void setComment_number(int comment_number) {
        this.comment_number = comment_number;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getGood_count() {
        return good_count;
    }

    public void setGood_count(int good_count) {
        this.good_count = good_count;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getWrite_date() {
        return to_char;
    }

    public void setWrite_date(String write_date) {
        this.to_char = write_date;
    }

}
