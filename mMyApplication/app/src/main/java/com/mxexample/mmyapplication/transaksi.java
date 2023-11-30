package com.mxexample.mmyapplication;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class transaksi extends Fragment {
    List <itemTrans> dataitemku;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    SessionManager sessionManager;

    public transaksi() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transaksi, container, false);
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);


        recyclerView = (RecyclerView) view.findViewById(R.id.recycletr);
        recyclerView.setHasFixedSize(true);
        layoutManager=new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(layoutManager);
        dataitemku = new ArrayList<>();
       loadtransaksi();
    }

    private void loadtransaksi() {
        sessionManager = new SessionManager(getActivity());
        HashMap<String, String> user = sessionManager.getUserDetails();
        String name = user.get(SessionManager.kunci_email);
        String URL = "https://u-sit.com/database/transaksi.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, response ->
        {
            try {
                JSONArray array = new JSONArray(response);
                for (int i =0; i<array.length(); i++){
                    JSONObject product = array.getJSONObject(i);
                    dataitemku.add(new itemTrans(
                            product.getString("kode_order"),
                            product.getString("title"),
                            product.getString("jumlah_order"),
                            product.getString("total_harga"),
                            product.getString("image")
                    ));
                    Log.d(TAG, "loadProducts: "+dataitemku);
                }
                transAdapter adapter = new transAdapter(getActivity(), dataitemku);
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
                return data;
            }
        };
        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }
}