package com.mxexample.mmyapplication;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class daftar_email extends AppCompatActivity {
    EditText namae,Emails,password,repassword,nohpss,refs;
    TextView salah;
    private String nama,email, passwords,repass,nohps,reffer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_email);

        Emails=findViewById(R.id.editTextTextEmailAddress);
        password=findViewById(R.id.editTextTextPassword1);
        repassword=findViewById(R.id.retypepassword);
        nohpss= findViewById(R.id.nohp);
        salah=findViewById(R.id.passal);
        namae=findViewById(R.id.ednama);
        refs=findViewById(R.id.reff);
    }

    public void daftar(View view) {
        email=Emails.getText().toString().trim();
        passwords=password.getText().toString().trim();
        repass=repassword.getText().toString().trim();
        nohps=nohpss.getText().toString().trim();
        nama=namae.getText().toString().trim();
        reffer=refs.getText().toString().trim();
        String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        Boolean b = email.matches(EMAIL_REGEX);
        Log.e(TAG,passwords+" "+repass);
        if (!passwords.equals(repass)){
            salah.setVisibility(view.VISIBLE);
        }else{
            if (b==true){

                String URL = "https://u-sit.com/database/cekemail.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.toString().trim().equals("failure")) {
                            Log.i(TAG,"Berhasil membuat order");
                            Toast.makeText(daftar_email.this, "success", Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(daftar_email.this,konfirmasi_nomor.class);
                            i.putExtra("email",email);
                            i.putExtra("password", passwords);
                            i.putExtra("nohp",nohps);
                            i.putExtra("nama",nama);
                            i.putExtra("ref",reffer);
                            startActivity(i);
                        } else if (response.toString().trim().equals("success")) {
                            Log.i(TAG,"Berhasil membuat orders");
                            Toast.makeText(daftar_email.this, "Email Sudah terdaftar, silahkan Login", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(daftar_email.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
                    }
                }) {

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> data = new HashMap<>();
                        data.put("email", email);
                        return data;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);


            }else{Toast.makeText(daftar_email.this, "email tidak benar", Toast.LENGTH_SHORT).show();
            }
        }

    }
}