package com.mxexample.mmyapplication;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class map_Activity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap gmap;
    FusedLocationProviderClient fusedLocationProviderClient;
    FrameLayout map;
    SearchView searchViewmp;
    double latii,longtt;
    ArrayList<dataAddress> addr;
    int marker=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        addr = new ArrayList<>();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getlocation();
        map = findViewById(R.id.map);
        searchViewmp = findViewById(R.id.searmap);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(map_Activity.this);
        searchViewmp.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String location = searchViewmp.getQuery().toString();
                List<Address> addressList = null;
                if (location != null) {
                    Geocoder geocoder = new Geocoder(map_Activity.this);
                    try {
                        addressList = geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    marker=0;
                    gmap.clear();
                    try {
                        Address address = addressList.get(0);
                        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

                        gmap.addMarker(new MarkerOptions().position(latLng).title(location));
                        latii = address.getLatitude();
                        longtt = address.getLongitude();
                        marker =1;

                        gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }



                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.gmap = googleMap;


        gmap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(@NonNull LatLng point) {
                marker=0;
                latii = point.latitude;
                longtt= point.longitude;
                gmap.clear();
                gmap.addMarker(new MarkerOptions().position(point));
                gmap.moveCamera(CameraUpdateFactory.newLatLng(point));
                marker=1;
                searchViewmp.setQueryHint(String.valueOf( point));
            }
        });

    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==10){
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(),"izin lokasi tidak aktif",Toast.LENGTH_SHORT).show();
            }else{
                getlocation();
            }
        }
    }

    public void getlocation(){
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                },10);
            }

        }else {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                ;
                @Override
                public void onSuccess(Location location) {
                    if (location!=null){
                        latii = location.getLatitude();
                        longtt =location.getLongitude();
                        LatLng jsk = new LatLng(latii, longtt);
                        gmap.addMarker(new MarkerOptions().position(jsk));
                        gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(jsk, 18));
                        marker=1;

                    }else{
                        Toast.makeText(getApplicationContext(),"lokasi tidak aktif", Toast.LENGTH_SHORT).show();

                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        }


    }

    public void masukkan(View view) {
        String apasih;
        List<Address> addresssus = null;
        Geocoder geo;
        geo= new Geocoder(map_Activity.this);
        try {
            addresssus = geo.getFromLocation(latii,longtt,1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        addr.add(new dataAddress(
                addresssus.get(0).getAddressLine(0)

        ));
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("keranjang", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        apasih = gson.toJson(addr);
        editor.putString("alamat", apasih);
        editor.apply();
        editor.commit();
        //Toast.makeText(this, "Alamat Ditambahkan ", Toast.LENGTH_SHORT).show();
        //Intent intent = new Intent(getApplicationContext(), alamat.class);
        //startActivity(intent);
        finish();
    }
}