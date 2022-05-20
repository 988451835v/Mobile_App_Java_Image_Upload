package com.example.coding_1.intentdataPass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.coding_1.R;

public class SecondActivity extends AppCompatActivity {
    TextView tv_name;
    Button btn_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        tv_name=findViewById(R.id.textView16);
        btn_show=findViewById(R.id.button4);

        Intent intent=getIntent();

        String name=intent.getStringExtra("Name");
        btn_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_name.setText(name);
            }
        });
    }
}