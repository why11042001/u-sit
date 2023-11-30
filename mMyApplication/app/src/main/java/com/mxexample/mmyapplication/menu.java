package com.mxexample.mmyapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;



import androidx.fragment.app.Fragment;



public class menu extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        bottomNavigationView = findViewById(R.id.bottomnav);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        loadFragment( new home());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){
        int id = item.getItemId();
        if(id==R.id.home){
            loadFragment(new home());
        } else if (id==R.id.akun) {
            loadFragment(new akun());
        } else if (id==R.id.transaksi) {
            loadFragment(new transaksi());
        }

        return true;
    }
    void loadFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.relativelayout, fragment).commit();
    }

}