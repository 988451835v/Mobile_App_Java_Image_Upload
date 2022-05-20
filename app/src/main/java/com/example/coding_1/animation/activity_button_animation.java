package com.example.coding_1.animation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.example.coding_1.R;

public class activity_button_animation extends AppCompatActivity {
    Button btn;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_animation);
        btn=findViewById(R.id.btn_anim);
        tv=findViewById(R.id.tv_anim);

        Animation animation=AnimationUtils.loadAnimation(this,R.anim.fade);
        tv.startAnimation(animation);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim= AnimationUtils.loadAnimation(activity_button_animation.this,R.anim.fade);
                btn.startAnimation(anim);
            }
        });

    }
}