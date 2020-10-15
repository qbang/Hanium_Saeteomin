package com.example.hanium_saeteomin.chatfragment;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class ChatQuizVO {
    private String E1;
    private String E2;
    private String E3;
    private String E4;
    private String answer;
    private String question;

    public String getE1(){
        return E1;
    }
    public String getE2(){
        return E2;
    }
    public String getE3(){
        return E3;
    }
    public String getE4(){
        return E4;
    }
    public String getAnswer(){
        return answer;
    }
    public String getQuestion(){
        return question;
    }

    public void setE1(String e1) {
        E1 = e1;
    }

    public void setE2(String e2) {
        E2 = e2;
    }

    public void setE3(String e3) {
        E3 = e3;
    }

    public void setE4(String e4) {
        E4 = e4;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @NonNull
    @Override
    public String toString() {
        return "ChatQuizVO"+
                "E1='"+E1+'\''+
                ",E2='"+E2+'\''+
                ",E3='"+E3+'\''+
                ",E4='"+E4+'\''+
                ",answer='"+answer+'\''+
                ",question='"+question+'\''+'}';
    }
}
