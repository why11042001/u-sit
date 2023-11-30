package com.mxexample.mmyapplication;


import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


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

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class home extends Fragment {
    List <itemModel> dataItem;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutmanager;
    SessionManager sessionManager;
    ImageButton carts;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        recyclerView =  view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutmanager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(layoutmanager);
        carts= view.findViewById(R.id.cartg);
        dataItem = new ArrayList<>();
        carts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in =new Intent(view.getContext(),cart.class);
                view.getContext().startActivity(in);
            }
        });
        loadProducts();
        SwipeRefreshLayout swipeRefreshLayout =  view.findViewById(R.id.refreshLayout);
        swipeRefreshLayout.setOnRefreshListener(
                () -> {
                    try {
                        Clear();
                        loadProducts();
                        swipeRefreshLayout.setRefreshing(false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
        );
    }

    private void loadProducts() {
        sessionManager= new SessionManager(getActivity());
        HashMap<String, String> user = sessionManager.getUserDetails();
        String email = user.get(SessionManager.kunci_email);
        String URL = "https://u-sit.com/database/product.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, response ->
        {
            try {
                JSONArray array = new JSONArray(response);
                for (int i =0; i<array.length(); i++){
                    JSONObject product = array.getJSONObject(i);
                    dataItem.add(new itemModel(
                            product.getInt("id"),
                            product.getString("title"),
                            product.getString("price"),
                            product.getString("image")
                    ));
                }
                ProductAdapter adapter = new ProductAdapter(getActivity(), dataItem);
                recyclerView.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            // Do something when error occurred
            Log.i(TAG, String.valueOf(error));
        }
        ) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("email", email);
                return data;
            }
        };
        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }
    public void Clear(){
        dataItem.clear();
    }


}