package com.mxexample.mmyapplication;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;


public class alamatAdapter extends RecyclerView.Adapter<alamatAdapter.ViewHolder>{

    List<dataAddress> addresses;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textAlamat;
        CardView cardview;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            textAlamat = itemView.findViewById(R.id.alamat);
            cardview = itemView.findViewById(R.id.cvakun);
        }
    }

    alamatAdapter(List<dataAddress> data){
        this.addresses = data;}

    @NonNull
    @Override
    public alamatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alamatgrid, parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull alamatAdapter.ViewHolder holder, int position){
        TextView titleAlamat=holder.textAlamat;
        titleAlamat.setText(addresses.get(position).getAlamat());
        CardView cv=holder.cardview;
        holder.cardview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                addresses.remove(holder.getAdapterPosition());
                SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("keranjang", 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();
                String jsons = gson.toJson(addresses);
                editor.putString("alamat", jsons);
                editor.apply();
                editor.commit();
                notifyItemRemoved(holder.getAdapterPosition());
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return addresses.size();
    }
}
