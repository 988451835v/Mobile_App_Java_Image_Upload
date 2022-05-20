package com.example.coding_1.tabView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.telecom.Call;

import com.example.coding_1.R;
import com.example.coding_1.adapters.pageAdapter;
import com.google.android.material.tabs.TabLayout;

public class TabActivity extends AppCompatActivity {
    TabLayout tablayout;
    ViewPager viewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        tablayout=findViewById(R.id.tabLayout);
        viewPager2=findViewById(R.id.viewPager);
        ChatFragment chatFragment=new ChatFragment();
        StatusFragment statusFragment=new StatusFragment();
        CallFragment callFragment=new CallFragment();

        tablayout.setupWithViewPager(viewPager2);
        pageAdapter pageAdapter=new pageAdapter(getSupportFragmentManager(),0);
        pageAdapter.addFragment(chatFragment,"Chat");
        pageAdapter.addFragment(statusFragment,"Status");
        pageAdapter.addFragment(callFragment,"Call");
        viewPager2.setAdapter(pageAdapter);




    }
}