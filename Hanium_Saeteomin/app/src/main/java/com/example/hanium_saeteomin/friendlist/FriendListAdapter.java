package com.example.hanium_saeteomin.friendlist;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hanium_saeteomin.R;

import java.util.ArrayList;

public class FriendListAdapter extends BaseAdapter {
    private ArrayList<FriendListData> friendListDataList = new ArrayList<FriendListData>();
    public FriendListAdapter(){

    }

    @Override
    public int getCount() {
        return friendListDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return friendListDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.freind_list_layout, parent, false);
        }

        ImageView profileimg = (ImageView)convertView.findViewById(R.id.profileImg);
        TextView name = (TextView)convertView.findViewById(R.id.name);
        TextView location = (TextView)convertView.findViewById(R.id.location);

        FriendListData friendListData = friendListDataList.get(position);

        profileimg.setBackground(new ShapeDrawable(new OvalShape()));
        profileimg.setClipToOutline(true);

        profileimg.setImageDrawable(friendListData.getProfileImg());
        name.setText(friendListData.getName());
        location.setText(friendListData.getLocation());

        return convertView;
    }

    public void addItem(Drawable profile_img, String name, String location){
        FriendListData questionFeedData = new FriendListData();

        questionFeedData.setProfileImg(profile_img);
        questionFeedData.setName(name);
        questionFeedData.setLocation(location);

        friendListDataList.add(questionFeedData);
    }
}