package com.example.hanium_saeteomin.homefragment.todayword.todaywordrecyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hanium_saeteomin.R;
import com.example.hanium_saeteomin.homefragment.bestword.WordData;

import java.util.ArrayList;

public class AdapterTodayWord extends RecyclerView.Adapter<AdapterTodayWord.ViewHolder> {
    private ArrayList<WordData> mList;

    public AdapterTodayWord(ArrayList<WordData> list) {
        this.mList = list;
    }

    @NonNull
    @Override
    public AdapterTodayWord.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_today_word, parent, false);
        AdapterTodayWord.ViewHolder viewHolder = new AdapterTodayWord.ViewHolder(view);
        return viewHolder;

    }


    @Override
    public void onBindViewHolder(@NonNull AdapterTodayWord.ViewHolder holder, int position) {
        Glide.with(holder.itemView.getContext()).load(R.drawable.speaking).into(holder.imgWord);
        holder.wordName.setText(mList.get(position).getWordName());
        holder.wordMean.setText(mList.get(position).getWordMean());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        protected TextView wordName;
        protected TextView wordMean;
        protected ImageView imgWord;

        public ViewHolder(View view) {
            super(view);
            this.imgWord = (ImageView) view.findViewById(R.id.img_today_word);
            this.wordName = (TextView) view.findViewById(R.id.tv_word_name);
            this.wordMean = (TextView) view.findViewById(R.id.tv_word_mean);
        }
    }
}
