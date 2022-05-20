package com.example.coding_1.intentdataPass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.coding_1.R;

public class FirstActivity extends AppCompatActivity {
    EditText et_name;
    Button btn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        et_name=findViewById(R.id.editText2);
        btn_next=findViewById(R.id.button2);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=et_name.getText().toString();
                Intent intent=new Intent(FirstActivity.this,SecondActivity.class);
                intent.putExtra("Name",name);
                startActivity(intent);
            }
        });
    }
}