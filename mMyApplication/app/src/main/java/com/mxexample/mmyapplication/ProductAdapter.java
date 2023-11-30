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

import org.w3c.dom.Text;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    List<itemModel>dataItem;
    private Context thecon;



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle;
        TextView textPrice;
        ImageView imagePost;
        TextView textid;
        CardView cvpr;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            textTitle = itemView.findViewById(R.id.titleProduct);
            textPrice = itemView.findViewById(R.id.priceProduct);
            textid = itemView.findViewById(R.id.idbar);
            imagePost = itemView.findViewById(R.id.imageProduct);
            cvpr = itemView.findViewById(R.id.cvprod);

        }
    }

    ProductAdapter(Context thecon, List<itemModel>data){
        this.thecon = thecon;
        this.dataItem = data;}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position){
        String idb = String.valueOf(dataItem.get(position).getId());
        TextView titleProduct=holder.textTitle;
        TextView priceProduct=holder.textPrice;
        TextView titleid = holder.textid;
        ImageView imageProduct=holder.imagePost;
        titleProduct.setText(dataItem.get(position).getTitle());
        priceProduct.setText("Rp"+dataItem.get(position).getPrice());
        titleid.setText(String.valueOf(dataItem.get(position).getId()));
        Glide.with(thecon)
                .load(dataItem.get(position).getImage())
                .into(imageProduct);
        holder.cvpr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(view.getContext(), productDetail.class);
                intent.putExtra("extra", idb);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
return dataItem.size();
    }

}
