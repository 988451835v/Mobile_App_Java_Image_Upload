package com.example.coding_1.shared;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.coding_1.R;

public class SharedDataActivity extends AppCompatActivity {
    TextView tv_name,tv_address,tv_phone;
    Button btn_show;
    SharedPreferences myfref;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_data);

        tv_name=findViewById(R.id.textView10);
        tv_address=findViewById(R.id.textView12);
        tv_phone=findViewById(R.id.textView15);
        btn_show=findViewById(R.id.btn_show);
        myfref=getApplicationContext().getSharedPreferences("MyPref",MODE_PRIVATE);
        editor=myfref.edit();
        btn_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=myfref.getString("Name","No name");
                String address=myfref.getString("Address","No Address");
                int phone=myfref.getInt("Phone",000000000);
                setValue(name,address,phone);

            }
        });
    }
    private void setValue(String name,String address,int phone){
        tv_name.setText(name);
        tv_address.setText(address);
        tv_phone.setText(String.valueOf(phone));

        editor.remove("Name");
        editor.commit();

        editor.clear();
        editor.commit();
    }
}