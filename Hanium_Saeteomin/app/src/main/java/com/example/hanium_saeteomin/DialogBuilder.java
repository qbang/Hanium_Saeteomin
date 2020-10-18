package com.example.hanium_saeteomin;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

public class DialogBuilder {
    private Context context;
    public TextView btnOk;
    public TextView btnCancel;
    private Dialog dlg;

    public DialogBuilder(Context context){
        this.context = context;
    }

    public void build(int layout){
        // 호출할 다이얼로그 함수를 정의한다.
        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        dlg = new Dialog(context);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg.setContentView(layout);

        Drawable back = new ColorDrawable(Color.TRANSPARENT);
        Drawable inset = new InsetDrawable(back, 70);

        dlg.getWindow().setBackgroundDrawable(inset);
        dlg.show();

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        btnOk = (TextView) dlg.findViewById(R.id.btn_ok);
        btnCancel = (TextView) dlg.findViewById(R.id.btn_cancel);
    }


    public void dismiss(){
        dlg.dismiss();
    }


}
