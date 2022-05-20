package com.example.coding_1.shared;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.pm.PackageInfoCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.coding_1.R;

public class NameActivity extends AppCompatActivity {
    EditText et_name;
    Button bt_next;
    SharedPreferences myfref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);
        et_name=findViewById(R.id.editText17);
        bt_next=findViewById(R.id.button13);
        myfref=getApplicationContext().getSharedPreferences("MyPref",MODE_PRIVATE);
        bt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=et_name.getText().toString();
                editor.putString("Name",name);
                editor.apply();

                Intent intent=new Intent(NameActivity.this,AddressActivity.class);
                startActivity(intent);
            }
        });

    }
}