package com.mxexample.mmyapplication;



import static android.content.ContentValues.TAG;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
.
 */
public class akun extends Fragment {
    RecyclerView recyclerView;
    AdapterRecycleView adapterRecycleView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList <dataAkun> dataset;
    List<datauser>datakus;
    SessionManager sessionManager;
    TextView EmailUser;
    RelativeLayout relativeLayout;
    TextView namauser,nohp,saldot;
    ImageView imgprofil;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    public akun() {
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
        return inflater.inflate(R.layout.fragment_akun, container, false);

    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        recyclerView =view.findViewById(R.id.listakun);
        relativeLayout=view.findViewById(R.id.detakun);
        recyclerView.setHasFixedSize(true);
        EmailUser =  view.findViewById(R.id.EmailUser);
        namauser =view.findViewById(R.id.nickName);
        nohp = view.findViewById(R.id.nohp);
        saldot=view.findViewById(R.id.saldouser);
        layoutManager =new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(layoutManager);
        dataset = new ArrayList<>();
        imgprofil = view.findViewById(R.id.profilePicture);
        datakus = new ArrayList<>();
        if (getActivity()==null){return;}
        loadall();
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),DetActivity.class);
                startActivity(intent);
            }
        });



    }

    private void loadall() {
        if (getActivity()==null){return;}
        sessionManager = new SessionManager(getActivity());
        HashMap<String, String> user = sessionManager.getUserDetails();
        String name = user.get(SessionManager.kunci_email);
        EmailUser.setText(name);
        String URL = "https://u-sit.com/database/getuser.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i =0; i<array.length(); i++) {
                                JSONObject testuser = array.getJSONObject(i);
                                datakus.add(new datauser(
                                        testuser.getString("nama"),
                                        testuser.getString("nohp"),
                                        testuser.getString("profil"),
                                        testuser.getString("saldo")
                                ));
                            }
                            if (getActivity()==null){
                                return;
                            }
                            Glide.with(getActivity())
                                    .load(datakus.get(0).getProfil())
                                    .apply(
                                            new RequestOptions()
                                                    .error(R.drawable.ic_akun_nav)
                                                    .centerCrop()
                                    )
                                    .listener(new RequestListener<Drawable>() {
                                        @Override
                                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                            //on load failed
                                            return false;
                                        }

                                        @Override
                                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                            //on load success
                                            return false;
                                        }
                                    })
                                    .into(imgprofil);
                            /*Glide.with(getActivity()).load(datakus.get(0).getProfil()).into(imgprofil);*/
                            namauser.setText(datakus.get(0).getNama());
                            nohp.setText(datakus.get(0).getNohp());
                            if (datakus.get(0).getSaldo().equals("null")){
                                saldot.setText("Rp. 0");
                            }
                            else {
                            saldot.setText("Rp. "+datakus.get(0).getSaldo());
                            }
                            Log.e(TAG,"text: "+ "Rp. "+datakus.get(0).getSaldo());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("email", name);
                return data;
            }
        };
        Volley.newRequestQueue(getActivity()).add(stringRequest);



        for (int i =0; i<listAkun.imgak.length; i++){
            dataset.add(new dataAkun(
                    listAkun.imgak[i],
                    listAkun.mainTitle[i]
            ));
        }
        adapterRecycleView = new AdapterRecycleView(dataset);
        recyclerView.setAdapter(adapterRecycleView);
    }

}