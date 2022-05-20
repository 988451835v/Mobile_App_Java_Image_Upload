package com.example.coding_1.shared;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.coding_1.R;

public class AddressActivity extends AppCompatActivity {

    EditText et_address;
    Button bt_next;
    SharedPreferences myfref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        et_address=findViewById(R.id.editAddress);
        bt_next=findViewById(R.id.button13);
        myfref=getApplicationContext().getSharedPreferences("MyPref",MODE_PRIVATE);
        bt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address=et_address.getText().toString();
                editor.putString("Address",address);
                editor.apply();

                Intent intent=new Intent(AddressActivity.this,PhoneNumberActivity.class);
                startActivity(intent);
            }
        });

    }
}