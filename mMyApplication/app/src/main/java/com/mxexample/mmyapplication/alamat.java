package com.mxexample.mmyapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class alamat extends AppCompatActivity {
    private ArrayList<dataAddress>addresses;
    private alamatAdapter adapter;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alamat);
        loadarray();
        buildrecycle();
    }
    private void buildrecycle(){
        recyclerView = (RecyclerView) findViewById(R.id.alamatre);
        adapter = new alamatAdapter(addresses);
        manager = new GridLayoutManager(getApplicationContext(),1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }
    private void loadarray() {
        SharedPreferences sharedPreferences = getSharedPreferences("keranjang", 0);
        Gson gson = new Gson();
        String arraybaru = sharedPreferences.getString("alamat", null);
        Type type = new TypeToken<ArrayList<dataAddress>>() {}.getType();
        addresses = gson.fromJson(arraybaru, type);
        if (addresses == null) {
            addresses = new ArrayList<>();
        }
    }
    protected void onResume(){
        super.onResume();
        ((alamatAdapter)adapter).notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        Toast.makeText(getApplicationContext(),"tst",Toast.LENGTH_LONG);
    }
    protected void onRestart(){
        super.onRestart();
        ((alamatAdapter)adapter).notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        Toast.makeText(getApplicationContext(),"retst",Toast.LENGTH_LONG);
    }
    public void tambahA(View view) {
        Intent intent = new Intent(getApplicationContext(), map_Activity.class);
        startActivity(intent);
    }
}