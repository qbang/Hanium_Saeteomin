package com.example.hanium_saeteomin.homefragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hanium_saeteomin.R;

import java.util.ArrayList;

public class AdapterQuiz extends RecyclerView.Adapter<AdapterQuiz.ViewHolder> {

    private ArrayList<String> mData = null ;

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_quiz ;

        ViewHolder(final View itemView) {
            super(itemView) ;

            tv_quiz = itemView.findViewById(R.id.tv_quiz) ;
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = getAdapterPosition() ;
                        if (pos != RecyclerView.NO_POSITION) {
                            //Log.d("data값",mData.get(pos)); 이거 자체가 아이템 텍스트뷰
                            //TODO 해당하는 단어의 뜻을 어떻게 다이얼로그로 넘겨줄것인가(hashmap 이용해서 저장해두기?)

                            //FIXME 다이얼로그 레이아웃 수정하기
                            QuizDialog dialog_quiz = new QuizDialog(itemView.getContext());
                            dialog_quiz.callFunction();


                        }
                    }
                });
        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    public AdapterQuiz(ArrayList<String> list) {
        mData = list ;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public AdapterQuiz.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.item_quiz, parent, false) ;
        AdapterQuiz.ViewHolder vh = new AdapterQuiz.ViewHolder(view) ;

        return vh ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(AdapterQuiz.ViewHolder holder, int position) {
        String text = mData.get(position) ;
        holder.tv_quiz.setText(text) ;
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size() ;
    }
}
