package com.example.coding_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    RecyclerView rv_lists;
    AdapterClass adapterClass;
    ArrayList<ModelClass> modelClasses;
    LinearLayoutManager linearLayoutManager;
    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        rv_lists = findViewById(R.id.rv_list);
        initData();
//        setAdapter();


    }

//    private void setAdapter() {
//        linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
//        rv_lists.setLayoutManager(linearLayoutManager);
//        adapterClass = new AdapterClass(modelClasses);
//        rv_lists.setAdapter(adapterClass);
//
//    }

    private void initData() {
        //get data from the database

        modelClasses = new ArrayList<>();
//        modelClasses.add(new ModelClass(R.drawable.student2,"A.s.Sithu","Colombo" ));
//        modelClasses.add(new ModelClass(R.drawable.student2,"A.s.Mithu","Polombo" ));
//        modelClasses.add(new ModelClass(R.drawable.student2,"A.s.Sithu","Lolombo" ));
//        modelClasses.add(new ModelClass(R.drawable.student2,"A.s.Sithu","Oolombo" ));





    }





}