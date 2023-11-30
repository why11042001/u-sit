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

public class EmailLoginActivity extends AppCompatActivity {
    SessionManager sessionManager;
    private EditText user_email, user_password;
    TextView tokens;
    private String email, password,tokensend;
    final String URL = "https://u-sit.com/database/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_login);
        email = password = "";
        user_email = findViewById(R.id.editTextTextEmailAddress);
        user_password = findViewById(R.id.editTextTextPassword);
        sessionManager = new SessionManager(getApplicationContext());
        tokens=findViewById(R.id.tokenusr);
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
                        tokens.setText(token);
                        Log.d(TAG, "registrationn code: "+token);

                    }
                });

    }

    public void Login(View view) {
        email = user_email.getText().toString().trim();
        password = user_password.getText().toString().trim();
        tokensend=tokens.getText().toString();
        if (!email.equals("") && !password.equals("")) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e(TAG,response.toString());
                    if (response.toString().trim().equals("success")) {
                        sessionManager.createSession(user_email.getText().toString(),tokensend);
                        Intent intent = new Intent(EmailLoginActivity.this, menu.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    } else if (response.toString().trim().equals("failure")) {
                        Toast.makeText(EmailLoginActivity.this, "Information incorrect", Toast.LENGTH_SHORT).show();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(EmailLoginActivity.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
                }
            }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> data = new HashMap<>();
                    data.put("email", email);
                    data.put("password", password);
                    data.put("token", tokensend);
                    return data;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        } else {
            Toast.makeText(this, "field can not be empty!", Toast.LENGTH_SHORT).show();
        }
    }

}