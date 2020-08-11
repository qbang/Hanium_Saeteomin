package com.example.chattest;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.widget.ImageView;

public class PersonalProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_profile);

        ImageView profileimg = (ImageView)findViewById(R.id.profile);
        profileimg.setImageResource(R.drawable.test) ;
        profileimg.setBackground(new ShapeDrawable(new OvalShape()));
        profileimg.setClipToOutline(true);


    }
}