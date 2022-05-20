package com.example.coding_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.pm.PackageInfoCompat;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {
    EditText edit_text,edit_password;
    RelativeLayout btn_login,btn_signup;
    TextView tv_reset;
    String email,password;
    String EmailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String PasswordPattern= "[a-zA-Z0-9\\\\!\\\\@\\\\#\\\\$]{8,24}";
    FirebaseAuth mAuth;
    String Uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
            edit_text=findViewById(R.id.edit_text);
            edit_password=findViewById(R.id.edit_password);
            btn_login=findViewById(R.id.btn_login);
            btn_signup=findViewById(R.id.btn_signup);
            tv_reset=findViewById(R.id.tv_reset);
            mAuth=FirebaseAuth.getInstance();

            if(mAuth.getCurrentUser() != null){
                startActivity(new Intent(login.this,FragementActivity.class));
            }

            tv_reset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(login.this,ResetPassword.class));
                }
            });
            btn_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String email,password;
                    email = edit_text.getText().toString().trim();
                    password = edit_password.getText().toString().trim();
                    validateFields(email,password);
                    //Context context;
                    //Toast.makeText( login.this, "Email is" + email + " "+"Password is" + password,Toast.LENGTH_SHORT).show();
                    //new StringBuilder().append("Email is").append(email).append(" &").append(" Password is").append(password).toString();



                }
            });
    }

    private void validateFields(String email, String password) {
       if(!(email.isEmpty()) || !(password.isEmpty())){
            if(email.matches(EmailPattern)) {
                if (password.matches(PasswordPattern)) {
                    Toast.makeText( login.this, " Validation Successfull........",Toast.LENGTH_SHORT).show();
                    logUser(email, password);

                } else {
                    edit_password.setError("Password should be 8-24 characters");
                }

            }else{
               edit_text.setError("Email is incorrect!!!");
            }

       } else{
           Toast.makeText( login.this, "Please fill this all fields",Toast.LENGTH_SHORT).show();
           new StringBuilder().append("Email is").append(email).append(" &").append(" Password is").append(password).toString();


       }
    }

    private void logUser(String email,String password){
        //code for fire base
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(login.this, "Logged in Succesfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(login.this, FragementActivity.class));
                }
                else{
                    Toast.makeText(login.this, "Error occured 1", Toast.LENGTH_SHORT).show();

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(login.this, "Error occured 2", Toast.LENGTH_SHORT).show();

            }
        });

    }
}