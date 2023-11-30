package com.mxexample.mmyapplication;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Map;

public class konfirmasi_nomor extends AppCompatActivity {
    String kode;
    EditText ed;
    TextView eds;
    SessionManager ses;
    String email,password,no,nama,reff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konfirmasi_nomor);
        ed=findViewById(R.id.kodek);
        eds=findViewById(R.id.scm);
        //requestkode();
        ses=new SessionManager(getApplicationContext());
        Intent i=getIntent();
        email=i.getStringExtra("email");
        password=i.getStringExtra("password");
        nama=i.getStringExtra("nama");
        no=i.getStringExtra("nohp");
        reff=i.getStringExtra("ref");

        FirebaseApp.initializeApp(this);
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        Log.d(TAG, "registrationn code: "+token);
//                        Toast.makeText(konfirmasi_nomor.this, "Your registration code = "+token, Toast.LENGTH_SHORT).show();
                        eds.setText(token);
                    }
                });
        if (reff.equals("")){
            reff="null";
        }
        String tokens=eds.getText().toString();
        String URL = "https://u-sit.com/database/daftar.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.toString().trim().equals("success")) {
                    Log.i(TAG,"Berhasil membuat order");
                    ses.createSession(email,tokens);
                    Intent inte = new Intent(konfirmasi_nomor.this,menu.class);
                    inte.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    inte.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    inte.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(inte);
                    finish();

                } else {
                    Log.i(TAG,"salah"+ response.trim());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(konfirmasi_nomor.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
                Log.i(TAG,"Error:"+ error.toString());
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("email", email);
                data.put("password",password);
                data.put("nohp",no);
                data.put("nama",nama);
                data.put("token",tokens);
                data.put("ref",reff);
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }
 /*   public void requestkode(){

        String URL = "https://u-sit.com/database/req_kode.php?";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, response ->
        {
            kode=response.toString().trim();
            Toast.makeText(konfirmasi_nomor.this,kode,Toast.LENGTH_LONG).show();
            Log.e(TAG,"kode "+kode);
        }, error -> {
            // Do something when error occurred

        }

        );
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);

    }*/

/*    public void cek(View view) {
        if (reff.equals("")){
            reff="null";
        }
        String tokens=eds.getText().toString();
        *//*if(kode.equals(ed.getText().toString())){*//*
            String URL = "https://u-sit.com/database/daftar.php";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.toString().trim().equals("success")) {
                        Log.i(TAG,"Berhasil membuat order");
                        ses.createSession(email,tokens);
                        Intent inte = new Intent(konfirmasi_nomor.this,menu.class);
                        startActivity(inte);
                        finish();

                    } else {
                        Log.i(TAG,"salah"+ response.trim());
                      }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(konfirmasi_nomor.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
                    Log.i(TAG,"Error:"+ error.toString());
                }
            }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> data = new HashMap<>();
                    data.put("email", email);
                    data.put("password",password);
                    data.put("nohp",no);
                    data.put("nama",nama);
                    data.put("token",tokens);
                    data.put("ref",reff);
                    return data;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        *//*}
        else{Toast.makeText(konfirmasi_nomor.this,"salah",Toast.LENGTH_SHORT).show();}*//*
    }*/
}