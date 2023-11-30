package com.mxexample.mmyapplication;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class transdetail extends AppCompatActivity {
    SessionManager sessionManager;
    List<datatransdet>dataitemku;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transdetail_activity);
        dataitemku=new ArrayList<>();
        recyclerView=findViewById(R.id.recycletr);
        recyclerView.setHasFixedSize(true);
        layoutManager=new GridLayoutManager(getApplicationContext(),1);
        recyclerView.setLayoutManager(layoutManager);
        loadtransaksi();
    }
    private void loadtransaksi() {
        Intent inten=getIntent();
        String kode=inten.getStringExtra("kode");
        Log.i(TAG,kode);
        sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> user = sessionManager.getUserDetails();
        String name = user.get(SessionManager.kunci_email);
        String URL = "https://u-sit.com/database/transdetail.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, response ->
        {
            try {
                Log.i(TAG,response);
                JSONArray array = new JSONArray(response);
                for (int i =0; i<array.length(); i++){
                    JSONObject product = array.getJSONObject(i);
                    dataitemku.add(new datatransdet(
                            product.getString("j_order"),
                            product.getString("title"),
                            product.getString("t_order"),
                            product.getString("price"),
                            product.getString("image"),
                            product.getString("status")
                    ));
                    Log.e(TAG, "loadProducts: "+dataitemku);
                }
                detailTransAdapter adapter = new detailTransAdapter(getApplicationContext(), dataitemku);
                recyclerView.setAdapter(adapter);
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
                data.put("email", name);
                data.put("kode",kode);
                return data;
            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
    }
}