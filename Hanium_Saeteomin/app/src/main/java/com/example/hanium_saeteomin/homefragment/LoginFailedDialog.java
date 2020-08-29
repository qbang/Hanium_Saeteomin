package com.example.hanium_saeteomin.homefragment;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.hanium_saeteomin.R;

public class LoginFailedDialog {

    private Context context;
    Dialog dlg;

    public LoginFailedDialog(Context context) {
        this.context = context;
    }

    // 호출할 다이얼로그 함수를 정의한다.
    public void callFunction() {

        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        dlg = new Dialog(context);

        // 액티비티의 타이틀바를 숨긴다.
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg.setContentView(R.layout.dialog_login_failed);

        // 커스텀 다이얼로그를 노출한다.
        dlg.show();


    }

    public void setText(String text){
        final TextView tv_text = (TextView) dlg.findViewById(R.id.tv_text);
        tv_text.setText(text);
        dlg.show();

    }
}
