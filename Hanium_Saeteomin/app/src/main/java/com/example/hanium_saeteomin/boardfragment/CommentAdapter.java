package com.example.hanium_saeteomin.boardfragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
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
    private String myName;
    public CommentAdapter(ArrayList<CommentData> list,String userName) {
        this.mList = list;
        this.myName = userName;
    }

    onClickDelete mListener ;
    onClickUpdate mListener2;

    void setListener(onClickDelete mListener){
        this.mListener = mListener;
    }
    void setListener2(onClickUpdate mListener2){
        this.mListener2 = mListener2;
    }


    @NonNull
    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_feed_comment, parent, false);
        CommentAdapter.ViewHolder viewHolder = new CommentAdapter.ViewHolder(view,myName);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.ViewHolder holder, int position) {

        Glide.with(holder.itemView.getContext()).load(mList.get(position).getImage()).into(holder.image);
        holder.userName.setText(mList.get(position).getUser_name());
        holder.writeDate.setText(mList.get(position).getWrite_date());
        holder.Comment.setText(mList.get(position).getComment());

        if(mList.get(position).getUser_name().equals(myName)){
            holder.btnDelete.setVisibility(View.VISIBLE);
            holder.btnUpdate.setVisibility(View.VISIBLE);
        }else{
            holder.btnDelete.setVisibility(View.GONE);
            holder.btnUpdate.setVisibility(View.GONE);

        }
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
        protected ImageButton btnDelete;
        protected ImageButton btnUpdate;

        public ViewHolder(View view,String myName) {
            super(view);
            this.image = (ImageView) view.findViewById(R.id.profileImg);
            this.userName = (TextView) view.findViewById(R.id.name);
            this.writeDate = (TextView) view.findViewById(R.id.timeline);
            this.Comment = (TextView) view.findViewById(R.id.question);
            btnDelete = view.findViewById(R.id.btn_delete_board);
            btnUpdate = view.findViewById(R.id.btn_update_board);



            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onClickDelete(getAdapterPosition());
                }
            });

            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener2.onClickUpdate(getAdapterPosition());
                }
            });


        }
    }
}
