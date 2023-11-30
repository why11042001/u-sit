package com.mxexample.mmyapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    SessionManager sessionManager;
    TextView sysdk;
    CheckBox chtysdk;
    Button buttons;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sessionManager= new SessionManager(getApplicationContext());
        HashMap<String, String> user=sessionManager.getUserDetails();
        String name = user.get(SessionManager.kunci_email);
        sysdk=findViewById(R.id.sysdk);
        buttons=findViewById(R.id.button);
        chtysdk=findViewById(R.id.checksdk);
        chtysdk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chtysdk.isChecked()){
                    buttons.setVisibility(View.VISIBLE);
                }
                else{
                    buttons.setVisibility(View.INVISIBLE);
                }
            }
        });
        buttons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, loginactivity.class);
                startActivity(intent);
            }
        });
        sysdk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            instructionAlert();
            }
        });

    }

    private void instructionAlert() {
        AlertDialog.Builder ad=new AlertDialog.Builder(this);
        ad.setTitle("Syarat Dan Ketentuan");
        ad.setView(LayoutInflater.from(this).inflate(R.layout.alertview,null));
        ad.setPositiveButton("Baiklah", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        ad.show();

    }


}