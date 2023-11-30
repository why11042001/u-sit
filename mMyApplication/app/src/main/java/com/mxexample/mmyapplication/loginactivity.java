package com.mxexample.mmyapplication;

import static android.content.ContentValues.TAG;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Map;

public class loginactivity extends AppCompatActivity {
SessionManager sessionManager;
Button but,maillog,daftaremails;
TextView ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);
        sessionManager= new SessionManager(getApplicationContext());
        daftaremails=findViewById(R.id.btn_daftaremail);
        daftaremails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inte = new Intent(loginactivity.this, daftar_email.class);
                startActivity(inte);
            }
        });
        but=findViewById(R.id.btn_google_log);
        ref=findViewById(R.id.token);
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        GoogleSignInClient googleSignInClient= GoogleSignIn.getClient(this, googleSignInOptions);
        GoogleSignInAccount googleSignInAccount=GoogleSignIn.getLastSignedInAccount(this);
        /*if (googleSignInAccount!=null){
            startActivity(new Intent(loginactivity.this, menu.class));
            finish();
        }*/
        ActivityResultLauncher<Intent> activityResultLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                Task<GoogleSignInAccount>task= GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                handleSignintask(task);
            }
        });
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent signInIntent= new Intent(googleSignInClient.getSignInIntent());
                activityResultLauncher.launch(signInIntent);
            }
        });
        maillog=findViewById(R.id.btn_email_log);
        maillog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inte=new Intent(loginactivity.this, EmailLoginActivity.class);
                startActivity(inte);
            }
        });
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
                        ref.setText(token);
                    }
                });
    }

    private void handleSignintask(Task<GoogleSignInAccount>task){
        try {
            GoogleSignInAccount account= task.getResult(ApiException.class);
            final String tokenss=ref.getText().toString();
            final String getFullname=account.getDisplayName();
            final String getEmail= account.getEmail();
            String URL = "https://u-sit.com/database/cekemail.php";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.toString().trim().equals("failure")) {
                        Intent i=new Intent(loginactivity.this,nohp_google.class);
                        i.putExtra("email",getEmail);
                        i.putExtra("password", "NULL");
                        i.putExtra("nama",getFullname);
                        startActivity(i);
                        Log.i(TAG,"Silahkan lanjutkan pendaftaran");
                    } else if (response.toString().trim().equals("success")) {
                        Log.i(TAG,"Berhasil Login");
                        Intent i=new Intent(loginactivity.this,menu.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        sessionManager.createSession(getEmail,tokenss);
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(loginactivity.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
                    Log.i(TAG,error.toString().trim());
                }
            }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> data = new HashMap<>();
                    data.put("email", getEmail);
                    return data;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        } catch (ApiException e) {
            e.printStackTrace();
            Log.e(TAG,e.toString());
            Toast.makeText(this,"Failed or Cancelled"+e.toString(),Toast.LENGTH_SHORT).show();
        }

    }



}