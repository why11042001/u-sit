package com.mxexample.mmyapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.Proxy;
import java.util.ArrayList;

public class cart extends AppCompatActivity {
    private ArrayList<datacart>dataItem;
    private cartAdapter adapter;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager manager;
    ScrollView scrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("keranjang", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("checkout");
        editor.apply();

        scrollView = (ScrollView) findViewById(R.id.scrollk);
        int tinggi = (int) (getResources().getDisplayMetrics().heightPixels*0.895);
        scrollView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, tinggi));
        loadarray();
        buildrecycle();
    }

    private void buildrecycle(){
        recyclerView = (RecyclerView) findViewById(R.id.cartgrid);
        adapter = new cartAdapter(this, dataItem);
        manager = new GridLayoutManager(getApplicationContext(),1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }
    private void loadarray(){
        SharedPreferences sharedPreferences =getSharedPreferences("keranjang",0);
        Gson gson = new Gson();
        String arraybaru =sharedPreferences.getString("keranjang", null);
        Type type = new TypeToken<ArrayList<datacart>>() {}.getType();
        dataItem =gson.fromJson(arraybaru,type);
        if(dataItem == null){
            dataItem = new ArrayList<>();
        }
    }

    public void gascheck(View view) {
        Intent intent =new Intent(getApplicationContext(),checkout.class);
        startActivity(intent);
    }
}