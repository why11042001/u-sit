package com.mxexample.mmyapplication;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class checkout extends AppCompatActivity implements myinterface{
    ArrayList<datacart>dataItem;
    SessionManager sessionManager;
    ArrayList<dataAddress>addresus;
    RecyclerView recyclerView;
    TextView alamt,kirimbi;
    Button chekout;
    TextView totalhargat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        loadarray();
        kirimbi=findViewById(R.id.biayakirim);
        chekout=findViewById(R.id.gascheckout);
        int totalharga=0;
        for(int i = 0;i<dataItem.size(); i++){
            totalharga= totalharga+Integer.valueOf(dataItem.get(i).getPrice());
        }
        int biaybi=0;
        for(int i = 0;i<dataItem.size(); i++){
            biaybi= biaybi+Integer.valueOf(dataItem.get(i).getJumlah());
        }
        int kir=0;
        if (biaybi==1){
            kir=3000;
        }else{
            kir=3000+((biaybi-1)*2000);
        }

        kirimbi.setText("Rp. "+Integer.toString(kir));
        totalhargat=findViewById(R.id.totalharga);
        totalhargat.setText("Rp. "+Integer.toString( totalharga+kir));
        buildrecycle();

        alamt = findViewById(R.id.alamat);
        if(addresus.size()!=0) {
            alamt.setText(addresus.get(0).getAlamat());
        }
        else{alamt.setText("Masukkan alamat");
        alamt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(checkout.this, map_Activity.class);
                startActivity(intent);

            }
        });
        }
        chekout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createOrder();
            }
        });
    }
    private void loadarray(){
        SharedPreferences sharedPreferences =getSharedPreferences("keranjang",0);
        Gson gson = new Gson();
        String arraybaru =sharedPreferences.getString("checkout", null);
        Type type = new TypeToken<ArrayList<datacart>>() {}.getType();
        dataItem =gson.fromJson(arraybaru,type);
        if(dataItem == null){
            dataItem = new ArrayList<>();
        }
        Gson gsons = new Gson();
        String arrayala = sharedPreferences.getString("alamat", null);
        Type alam= new TypeToken<ArrayList<dataAddress>>() {}.getType();
        addresus = gsons.fromJson(arrayala,alam);
        if (addresus== null){
            addresus = new ArrayList<>();
        }

    }
    private void buildrecycle(){
        recyclerView = (RecyclerView) findViewById(R.id.recycler_viewch);
        checkAdapter adapter = new checkAdapter(this, dataItem);
        RecyclerView.LayoutManager manager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }
    public void createOrder() {

        loadarray();
        if (addresus.size()>0) {
            String alamats=addresus.get(0).getAlamat();
            String url = "https://u-sit.com/database/checkout.php";
            Gson gson = new Gson();
            String arry = gson.toJson(dataItem);
            sessionManager = new SessionManager(getApplicationContext());
            HashMap<String, String> user = sessionManager.getUserDetails();
            String kode_barang = dataItem.get(0).getId();
            String jumlah_order=String.valueOf(dataItem.size());
            String [] total_hargas=totalhargat.getText().toString().split("Rp. ",2);
            String total_harga=total_hargas[1];
            Log.i(TAG, "createOrder: "+total_harga);


                String kode_user = user.get(SessionManager.kunci_email);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
                    if (response.trim().equals("success")) {
                        Log.i(TAG, "Berhasil membuat order");
                        Intent intent = new Intent(checkout.this, menu.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        Toast.makeText(checkout.this, "Berhasil membuat order", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(checkout.this, response.trim(), Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "gagal membuat order" + response.trim());
                    }

                }, error -> Log.i(TAG, "gagal membuat order" + error.toString().trim())
            /*Toast.makeText(checkout.this, error.toString().trim(), Toast.LENGTH_SHORT).show()*/) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> data = new HashMap<>();
                        data.put("dataary", arry);
                        data.put("alamat", alamats);
                        data.put("jumlah_order", jumlah_order);
                        data.put("total_harga", total_harga);
                        data.put("kode_user", kode_user);
                        data.put("kode_barang", kode_barang);
                        return data;
                    }

                };
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);
        }else{
            Toast.makeText(this,"Alamat tidak boleh kosong!!!", Toast.LENGTH_LONG).show();
            Log.i(TAG,"halooo: todak bisa");
        }
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        loadarray();
        Log.i(TAG,"resume");
        if(addresus.size()!=0) {
            alamt.setText(addresus.get(0).getAlamat());
        }
    }




    @Override
    public void callBack(String data) {
        loadarray();
        Integer totalharga=0;
        Integer biaybi=0;
        for(int i = 0;i<dataItem.size(); i++){
           totalharga= totalharga+Integer.valueOf(dataItem.get(i).getPrice());
        }
        for(int i = 0;i<dataItem.size(); i++){
            biaybi= biaybi+Integer.valueOf(dataItem.get(i).getJumlah());
        }
        Integer kir=0;
        if (biaybi==1){
            kir=3000;
        }else{
            kir=3000+((biaybi-1)*2000);
        }

        kirimbi.setText("Rp. "+Integer.toString(kir));
        totalhargat=findViewById(R.id.totalharga);
        totalhargat.setText("Rp. "+Integer.toString( totalharga+kir));
    }
}