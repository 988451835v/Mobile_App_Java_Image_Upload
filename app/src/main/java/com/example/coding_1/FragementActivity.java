package com.example.coding_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.text.Layout;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FragementActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragement);

        bottomNavigationView=findViewById(R.id.bottom_nav_view);
        bottomNavigationView.setOnItemSelectedListener(bottomNavMethod);
        getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment,new HomeFragment()).commit();

    }


    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment=null;
            switch(item.getItemId()){
                case R.id.home:
                    fragment=new HomeFragment();
                    break;

                case R.id.bio:
                    fragment=new BioFragment();
                    break;


                case R.id.maths:
                        fragment=new MathsFragment();
                        break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment,fragment).commit();
            return true;

        }
    };


}