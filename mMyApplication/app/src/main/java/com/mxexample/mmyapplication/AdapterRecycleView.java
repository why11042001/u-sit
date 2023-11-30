package com.mxexample.mmyapplication;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.util.ArrayList;

public class AdapterRecycleView extends RecyclerView.Adapter<AdapterRecycleView.ViewHolder>{
    ArrayList<dataAkun>datalist;
    SessionManager sessionManager;


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textJudul;
        ImageView posterAkun;
        CardView cvakuni;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textJudul = itemView.findViewById(R.id.titleAkun);
            posterAkun = itemView.findViewById(R.id.icon);
            cvakuni = itemView.findViewById(R.id.cvakun);
        }
    }
    AdapterRecycleView(ArrayList<dataAkun>dataset){this.datalist = dataset;}
    @NonNull
    @Override
    public AdapterRecycleView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mylist, parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull AdapterRecycleView.ViewHolder holder, int position) {
        TextView titleAkun=holder.textJudul;
        ImageView icon= holder.posterAkun;

         String name = datalist.get(position).getName();

            Log.d(TAG, "bababa: sama kok");


        titleAkun.setText(datalist.get(position).getName());
        icon.setImageResource(datalist.get(position).getPoster());
        holder.cvakuni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if ( name =="Keranjang") {
                Intent intent = null;
                intent = new Intent(view.getContext(), cart.class);
                view.getContext().startActivity(intent);
            }
            else if (name =="Term of Service"){
                Toast.makeText(view.getContext(), "This Is Term Of Service", Toast.LENGTH_LONG).show();
            }
            else if (name =="Keluar"){
                sessionManager = new SessionManager(view.getContext());
                GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail().build();
                GoogleSignInClient googleSignInClient= GoogleSignIn.getClient(view.getContext(), googleSignInOptions);
                GoogleSignInAccount googleSignInAccount=GoogleSignIn.getLastSignedInAccount(view.getContext());
                if (googleSignInAccount!=null){
                    googleSignInClient.signOut();}
                sessionManager.logout();

            }
            else if (name =="Alamat"){
                Intent intent = null;
                intent = new Intent(view.getContext(), alamat.class);
                view.getContext().startActivity(intent);
            }
            else if (name =="Refferal"){
                Intent intent = null;
                intent = new Intent(view.getContext(), referalActivity.class);
                view.getContext().startActivity(intent);
            }
            }
        });
    }
    @Override
    public int getItemCount() {
        return datalist.size();
    }

}
