package com.example.coding_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class signup1 extends AppCompatActivity {
    EditText edit_text;
    EditText edit_password;
    EditText confirm_password;
    RelativeLayout btn_login,btn_signup;
    String email,password,con_password;
    String EmailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String PasswordPattern= "[a-zA-Z0-9\\\\!\\\\@\\\\#\\\\$]{8,24}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup1);
        edit_text=findViewById(R.id.edit_text);
        confirm_password=findViewById(R.id.confirm_password);
        btn_login=findViewById(R.id.btn_login);
        btn_signup=findViewById(R.id.btn_signup);

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = edit_text.getText().toString().trim();
                password = edit_password.getText().toString().trim();
                con_password = confirm_password.getText().toString().trim();
                validateFields(email,password,con_password);
                //Context context;
                //Toast.makeText( login.this, "Email is" + email + " "+"Password is" + password,Toast.LENGTH_SHORT).show();
                //new StringBuilder().append("Email is").append(email).append(" &").append(" Password is").append(password).toString();



            }
        });

    }

    public void onRadioButtonClicked(View view) {
    }

    private void validateFields(String email, String password, String con_password) {
        if(!(email.isEmpty()) || !(password.isEmpty())){
            if(email.matches(EmailPattern)) {
                if (password.matches(PasswordPattern) ) {
                    if(con_password.matches(PasswordPattern) && con_password.matches(password)) {
                        Toast.makeText(signup1.this, "Successfull........", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    edit_password.setError("Password should be 8-24 characters");
                }

            }else{
                edit_text.setError("Email is incorrect!!!");
            }

        } else{
            Toast.makeText( signup1.this, "Please fill this all fields",Toast.LENGTH_SHORT).show();
            new StringBuilder().append("Email is").append(email).append(" &").append(" Password is").append(password).toString();


        }
    }
}