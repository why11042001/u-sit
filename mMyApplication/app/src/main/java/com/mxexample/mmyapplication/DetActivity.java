package com.mxexample.mmyapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetActivity extends AppCompatActivity {
    TextView nama,notel;
    ImageView prof;
    LinearLayout passch;
    SessionManager sessionManager;
    List<datauser> datakus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_det);
        nama=findViewById(R.id.namad);
        notel=findViewById(R.id.notel);
        prof=findViewById(R.id.profilePicture);
        datakus=new ArrayList<>();
        loaddata();
        passch=findViewById(R.id.changpas);
        passch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(DetActivity.this, ubahPassword.class);
                startActivity(i);
            }
        });
    }

    private void loaddata() {
        sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> user = sessionManager.getUserDetails();
        String name = user.get(SessionManager.kunci_email);
        String URL = "https://u-sit.com/database/getuser.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i =0; i<array.length(); i++) {
                                JSONObject testuser = array.getJSONObject(i);
                                datakus.add(new datauser(
                                        testuser.getString("nama"),
                                        testuser.getString("nohp"),
                                        testuser.getString("profil"),
                                        testuser.getString("saldo")
                                ));
                            }
                            Glide.with(getApplicationContext())
                                    .load(datakus.get(0).getProfil())
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
                                    .into(prof);
                            /*Glide.with(getActivity()).load(datakus.get(0).getProfil()).into(imgprofil);*/
                            nama.setText(datakus.get(0).getNama());
                            notel.setText(datakus.get(0).getNohp());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("email", name);
                return data;
            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);

    }
}