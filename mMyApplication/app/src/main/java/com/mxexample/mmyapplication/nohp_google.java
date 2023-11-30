package com.mxexample.mmyapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class nohp_google extends AppCompatActivity {
    EditText nohpss,ref;
    String nohps,reffer;
    TextView salah;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nohp_google);
        nohpss= findViewById(R.id.nohp);
        salah=findViewById(R.id.passal);
        ref=findViewById(R.id.refferal);
    }

    public void daftar(View view) {
        nohps=nohpss.getText().toString().trim();
        reffer=ref.getText().toString().trim();
        if (!nohps.equals("")) {

            Intent i = getIntent();
            Intent o = new Intent(nohp_google.this, konfirmasi_nomor.class);
            o.putExtra("email", i.getStringExtra("email"));
            o.putExtra("password", i.getStringExtra("password"));
            o.putExtra("nama", i.getStringExtra("nama"));
            o.putExtra("nohp", nohps);
            o.putExtra("ref",reffer);
            startActivity(o);
        }else {
            salah.setVisibility(View.VISIBLE);
        }

    }
}