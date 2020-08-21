package com.example.hanium_saeteomin.fragmenthome.bestword;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hanium_saeteomin.R;

import java.util.ArrayList;

public class AdapterBestWord extends RecyclerView.Adapter<AdapterBestWord.ViewHolder> {
    private ArrayList<BestWordData> mList;

    public AdapterBestWord(ArrayList<BestWordData> list) {
        this.mList = list;
    }

    @NonNull
    @Override
    public AdapterBestWord.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_best_word, parent, false);
        AdapterBestWord.ViewHolder viewHolder = new AdapterBestWord.ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull AdapterBestWord.ViewHolder holder, int position) {
        holder.wordName.setGravity(Gravity.CENTER);
        holder.wordMean.setGravity(Gravity.CENTER);

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


        public ViewHolder(View view) {
            super(view);
            this.wordName = (TextView) view.findViewById(R.id.tv_word_name);
            this.wordMean = (TextView) view.findViewById(R.id.tv_word_mean);
        }
    }
}
