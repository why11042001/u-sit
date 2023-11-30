package com.mxexample.mmyapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class productDetail extends AppCompatActivity {
    List<itemModelp>dataItemp;
    ArrayList<datacart>ambil;
    TextView titlepr;
    TextView pricep;
    TextView deskripsi;
    ImageView imgprs;
    ImageView profilp;
    TextView namap;
    ScrollView scrollview;
    String apasih;
    List <datacart>cart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int tinggi = (int) (getResources().getDisplayMetrics().heightPixels*0.895);
        setContentView(R.layout.producdetail_activity);
        dataItemp= new ArrayList<>();
        cart=new ArrayList<>();
        scrollview = findViewById(R.id.scrollvie);
        titlepr = findViewById(R.id.titleP_r);
        namap = findViewById(R.id.nickName);
        profilp =findViewById(R.id.profilePicture);
        imgprs = findViewById(R.id.imgprod);
        deskripsi = findViewById(R.id.deskripsi);
        pricep = findViewById(R.id.pricePr);
        scrollview.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, tinggi));
        Intent intent = getIntent();
        String idbarang = intent.getStringExtra("extra");
        String jumlah="1";
        SharedPreferences sharedPreferences = getSharedPreferences("keranjang", 0);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("keranjang", null);
        Type type = new TypeToken<ArrayList<datacart>>() {}.getType();
        cart = gson.fromJson(json, type);
        if (cart == null) {
            cart = new ArrayList<>();
        }
        if (ambil == null) {
            ambil = new ArrayList<>();
        }


        String URL = "https://u-sit.com/database/pdetails.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, response ->
        {
            try {
                JSONArray array = new JSONArray(response);
                for (int i =0; i<array.length(); i++){
                    JSONObject product = array.getJSONObject(i);
                    dataItemp.add(new itemModelp(
                            product.getString("nama"),
                            product.getString("profil"),
                            product.getString("title"),
                            product.getString("price"),
                            product.getString("image"),
                            product.getString("deskripsi")
                    ));
                }
                ambil.add(new datacart(
                        idbarang,
                        dataItemp.get(0).getTitle(),
                        dataItemp.get(0).getPrice(),
                        dataItemp.get(0).getImage(),
                        jumlah


                ));
                titlepr.setText(dataItemp.get(0).getTitle());
                pricep.setText(String.format("Rp. %s", dataItemp.get(0).getPrice()));
                deskripsi.setText(dataItemp.get(0).getDekripsi());
                namap.setText(dataItemp.get(0).getNama());
                Glide.with(getApplicationContext()).load(dataItemp.get(0).getImage()).into(imgprs);
                //Glide.with(getApplicationContext()).load(dataItemp.get(0).getProfil()).into(profilp);
                Glide.with(getApplicationContext())
                        .load(dataItemp.get(0).getProfil())
                        .apply(
                                new RequestOptions()
                                        .error(R.drawable.ic_akun_nav)
                                        .centerCrop()
                        )
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                //on load failed
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                //on load success
                                return false;
                            }
                        })
                        .into(profilp);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            // Do something when error occurred

        }
        ){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("id", idbarang);
                return data;
            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
    }

    public void checkout(View view) {

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("keranjang", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        apasih = gson.toJson(ambil);
        editor.remove("checkout");
        editor.apply();
        editor.putString("checkout", apasih);
        editor.apply();
        editor.commit();
        Intent intent = new Intent(productDetail.this, checkout.class);
        startActivity(intent);
        //createOrder();
        //Intent cintent = new Intent(getApplicationContext(), checkout.class);
        //startActivity(cintent);

    }

    public void tkeranjang(View view) {
        ambil.addAll(cart);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("keranjang", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        apasih = gson.toJson(ambil);
        editor.putString("keranjang", apasih);
        editor.apply();
        editor.commit();
        Toast.makeText(this, "Berhasil ditambahkan ke keranjang", Toast.LENGTH_SHORT).show();
    }
}