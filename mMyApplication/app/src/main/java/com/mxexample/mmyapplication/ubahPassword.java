package com.mxexample.mmyapplication;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class ubahPassword extends AppCompatActivity {
    EditText lama,baru1,baru2;
    Button ganti;
    String plama,pbaru1,pbaru2;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_password);
        lama = findViewById(R.id.passwordlama);
        baru1=findViewById(R.id.passwordb1);
        baru2=findViewById(R.id.passwordb2);
        ganti=findViewById(R.id.gantipass);

        ganti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gantigass();
            }
        });

    }

    private void gantigass() {
        plama=lama.getText().toString();
        pbaru1=baru1.getText().toString();
        pbaru2=baru2.getText().toString();
        if (pbaru1.equals(pbaru2)) {
            sessionManager = new SessionManager(getApplicationContext());
            HashMap<String, String> user = sessionManager.getUserDetails();
            String name = user.get(SessionManager.kunci_email);
            String token = user.get(SessionManager.key_token);
            String URL = "https://u-sit.com/database/ganpas.php";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            if (response.toString().trim().equals("success")) {
                                finish();
                            } else {
                                Log.e(TAG, response.toString());
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Log.e(TAG, error.toString());
                }

            }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> data = new HashMap<>();
                    data.put("email", name);
                    data.put("token", token);
                    data.put("plama", plama);
                    data.put("pbaru1", pbaru1);
                    return data;
                }
            };
            Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
        }
    }
}