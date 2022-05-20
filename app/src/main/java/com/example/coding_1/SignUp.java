package com.example.coding_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {
    TextView tv_login;
    ConstraintLayout bt_sign_up;
    EditText et_username,et_email,et_phone,et_town,et_password;
    private FirebaseAuth mAuth;
    String UserID;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        tv_login = findViewById(R.id.tv_login_screen);
        bt_sign_up = findViewById(R.id.bt_sign_up);
        et_username = findViewById(R.id.et_user_name);
        et_email = findViewById(R.id.et_email);
        et_phone = findViewById(R.id.et_contact_no);
        et_town = findViewById(R.id.et_home_town);
        et_password = findViewById(R.id.et_password);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

//        String sign_up_text = "<font>Already have an account?</font> <font color =#E26912><b> LOGIN</b></font>";
//        tv_login.setText(sign_up_text);

        bt_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateFields();
            }
        });
    }

    private void validateFields() {
        //do validation
        String user_name,email,phone,town,password;
        user_name =et_username.getText().toString();
        email = et_email.getText().toString();
        phone = et_phone.getText().toString();
        town = et_town.getText().toString();
        password = et_password.getText().toString();
        saveUser(user_name,email,phone,town,password);
    }

    private void saveUser(String user_name, String email, String phone, String town, String password) {
        //firebase code
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //add data to the firestore
                UserID = mAuth.getCurrentUser().getUid();
                DocumentReference documentReference = db.collection("USER").document(UserID);
                Map<String, Object> user = new HashMap<>();
                user.put("USER NAME", user_name);
                user.put("EMAIL",email );
                user.put("PHONE", phone);
                user.put("TOWN", town);
                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(SignUp.this, "Login Success Data saved!!!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignUp.this,login.class));

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignUp.this, "Login Success data not saved!!!", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignUp.this, "Login not successfull !!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}