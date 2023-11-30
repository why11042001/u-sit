package com.mxexample.mmyapplication;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class transAdapter extends RecyclerView.Adapter<transAdapter.ViewHolder>{
    List<itemTrans>dataItemku;
    private Context thecon;
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle;
        TextView torder,jorder;
        ImageView imagePost;
        CardView cvtr;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            textTitle = itemView.findViewById(R.id.titletr);
            torder = itemView.findViewById(R.id.torder);
            jorder = itemView.findViewById(R.id.jOrder);
            imagePost = itemView.findViewById(R.id.icontr);
            cvtr = itemView.findViewById(R.id.cvtrans);
        }
    }
    transAdapter(Context thecon, List<itemTrans>data){
        this.thecon = thecon;
        this.dataItemku = data;}
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mytrans,parent,false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull transAdapter.ViewHolder holder, int position) {
        TextView titletr =holder.textTitle;
        Integer banyakbar = Integer.valueOf(dataItemku.get(position).getjumlah_order())-1;
        TextView jorder=holder.jorder;
        TextView torder = holder.torder;
        ImageView icontr = holder.imagePost;
        if (banyakbar==0){
            titletr.setText(dataItemku.get(position).getTitle());
        }
        else{
        titletr.setText(dataItemku.get(position).getTitle()+" dan "+banyakbar+" item lainnya");}
        jorder.setText("Rp. "+dataItemku.get(position).getTotal_harga());
        torder.setText(String.valueOf( dataItemku.get(position).getKode_order()));
        Glide.with(thecon).load(dataItemku.get(position).getImage()).into(icontr);
        holder.cvtr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(view.getContext(),transdetail.class);
                intent.putExtra("kode",String.valueOf(dataItemku.get(holder.getAdapterPosition()).getKode_order()));
                view.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return dataItemku.size();
    }
}
