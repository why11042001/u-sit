package com.mxexample.mmyapplication;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.List;

public class detailTransAdapter extends RecyclerView.Adapter<detailTransAdapter.ViewHolder> {
    List<datatransdet>dataitemku;
    private Context thecon;
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageprd;
        TextView titl,tangg,stat,tota;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            titl=itemView.findViewById(R.id.titlep);
            tangg=itemView.findViewById(R.id.tanggalp);
            stat=itemView.findViewById(R.id.statusp);
            tota=itemView.findViewById(R.id.totalhargap);
            imageprd=itemView.findViewById(R.id.imageP);
        }
    }
    detailTransAdapter(Context thecon, List<datatransdet>data){
        this.thecon=thecon;
        this.dataitemku=data;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.dettrans_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull detailTransAdapter.ViewHolder holder, int position) {
        TextView title=holder.titl;
        TextView tangal=holder.tangg;
        TextView status=holder.stat;
        TextView total=holder.tota;
        ImageView imagepr=holder.imageprd;

        title.setText(dataitemku.get(position).getTitle());
        tangal.setText("Tanggal Transaksi: "+dataitemku.get(position).getT_order());
        status.setText("Status: "+dataitemku.get(position).getStatus());
        total.setText( String.valueOf( Integer.valueOf(dataitemku.get(position).getPrice())*Integer.valueOf(dataitemku.get(position).getJ_order())));

        Glide.with(thecon).load(dataitemku.get(position).getImage()).into(imagepr);

    }

    @Override
    public int getItemCount() {
 return dataitemku.size();
    }
}
