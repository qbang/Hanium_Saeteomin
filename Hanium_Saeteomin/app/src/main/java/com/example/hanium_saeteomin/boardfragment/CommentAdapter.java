package com.example.hanium_saeteomin.boardfragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hanium_saeteomin.R;
import com.example.hanium_saeteomin.homefragment.bestword.AdapterBestWord;
import com.example.hanium_saeteomin.homefragment.bestword.WordData;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{

    private ArrayList<CommentData> mList;

    public CommentAdapter(ArrayList<CommentData> list) {
        this.mList = list;
    }

    @NonNull
    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_feed_comment, parent, false);
        CommentAdapter.ViewHolder viewHolder = new CommentAdapter.ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.ViewHolder holder, int position) {

        Glide.with(holder.itemView.getContext()).load(mList.get(position).getImage()).into(holder.image);
        holder.userName.setText(mList.get(position).getUser_name());
        holder.writeDate.setText(mList.get(position).getWrite_date());
        holder.Comment.setText(mList.get(position).getComment());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        protected ImageView image;
        protected TextView userName;
        protected TextView writeDate;
        protected TextView Comment;



        public ViewHolder(View view) {
            super(view);
            this.image = (ImageView) view.findViewById(R.id.profileImg);
            this.userName = (TextView) view.findViewById(R.id.name);
            this.writeDate = (TextView) view.findViewById(R.id.timeline);
            this.Comment = (TextView) view.findViewById(R.id.question);


        }
    }
}
