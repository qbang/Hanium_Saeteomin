package com.example.hanium_saeteomin.fragmentboard;

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

public class QuestionFeedAdapter extends BaseAdapter {
    private  ArrayList<QuestionFeedData> questionFeedDataList = new ArrayList<QuestionFeedData>();

    public QuestionFeedAdapter(){

    }

    @Override
    public int getCount() {
        return questionFeedDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return questionFeedDataList.get(position);
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
            convertView = inflater.inflate(R.layout.question_feed_layout, parent, false);
        }

        ImageView profileimg = (ImageView)convertView.findViewById(R.id.profileImg);
        TextView name = (TextView)convertView.findViewById(R.id.name);
        TextView timeline = (TextView)convertView.findViewById(R.id.timeline);
        TextView question = (TextView)convertView.findViewById(R.id.question);
        TextView favorite = (TextView)convertView.findViewById(R.id.favorite);
        TextView chatNum = (TextView)convertView.findViewById(R.id.chatNum);

        QuestionFeedData questionFeedData = questionFeedDataList.get(position);

        profileimg.setBackground(new ShapeDrawable(new OvalShape()));
        profileimg.setClipToOutline(true);

        profileimg.setImageDrawable(questionFeedData.getProfileImg());
        name.setText(questionFeedData.getName());
        timeline.setText(questionFeedData.getTimeline());
        question.setText(questionFeedData.getQuestion());
        favorite.setText(questionFeedData.getFavorite());
        chatNum.setText(questionFeedData.getChatNum());

        return convertView;
    }

    public void addItem(Drawable profile_img, String name, String timeline, String question, String favorite, String chatnum){
        QuestionFeedData questionFeedData = new QuestionFeedData();

        questionFeedData.setProfileImg(profile_img);
        questionFeedData.setName(name);
        questionFeedData.setTimeline(timeline);
        questionFeedData.setQuestion(question);
        questionFeedData.setFavorite(favorite);
        questionFeedData.setChatNum(chatnum);

        questionFeedDataList.add(questionFeedData);
    }
}
