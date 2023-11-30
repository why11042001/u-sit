package com.mxexample.mmyapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class checkAdapter extends RecyclerView.Adapter<checkAdapter.ViewHolder>{
    List<datacart> dataItem;
    private Context thecon;
    private static myinterface intr;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle;
        TextView textPrice;
        EditText juml;


        ImageView imagePost;
        CardView cvpr;
        ImageButton kurang,tambah;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            kurang = itemView.findViewById(R.id.tombolkurang);
            tambah = itemView.findViewById(R.id.tomboltambah);
            textTitle =  itemView.findViewById(R.id.titlecri);
            textPrice = itemView.findViewById(R.id.hargacr);
            imagePost = itemView.findViewById(R.id.imgcr);
            cvpr = itemView.findViewById(R.id.cvcart);
            juml = itemView.findViewById(R.id.jumlahorder);


        }
    }

    checkAdapter(Context thecon, List<datacart>data){
        this.thecon = thecon;
        this.dataItem = data;}

    @NonNull
    @Override
    public checkAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.checkgrid, parent,false);
        return new checkAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull checkAdapter.ViewHolder holder, int position){
        Integer hargap = Integer.valueOf(dataItem.get(position).getPrice());
        TextView titleProduct=holder.textTitle;
        TextView priceProduct=holder.textPrice;

        ImageView imageProduct=holder.imagePost;
        EditText jproduc=holder.juml;
        titleProduct.setText(dataItem.get(position).getTitle());
        jproduc.setText(dataItem.get(position).getJumlah());
        priceProduct.setText(String.format("Rp. %s", dataItem.get(position).getPrice()));
        Glide.with(thecon)
                .load(dataItem.get(position).getImage())
                .into(imageProduct);
        holder.tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("keranjang", 0);
                SharedPreferences.Editor edity = sharedPreferences.edit();
                Gson gson = new Gson();
                String arraybaru =sharedPreferences.getString("checkout", null);
                Type type = new TypeToken<ArrayList<datacart>>() {}.getType();
                dataItem=gson.fromJson(arraybaru,type);
                Integer countp = Integer.valueOf(String.valueOf(jproduc.getText()));
                countp++;
                jproduc.setText(Integer.toString(countp));
                String totalharga =  Integer.toString( countp*hargap);
                priceProduct.setText(String.format("Rp. %s", totalharga));
                dataItem.add(holder.getAdapterPosition(),new datacart(
                        dataItem.get(holder.getAdapterPosition()).getId(),
                        dataItem.get(holder.getAdapterPosition()).getTitle(),
                        totalharga,
                        dataItem.get(holder.getAdapterPosition()).getImage(),
                        String.valueOf(jproduc.getText())
                ));
                dataItem.remove(holder.getAdapterPosition()+1);
                String jsons = gson.toJson(dataItem);
                edity.putString("checkout",jsons);
                edity.apply();
                intr = (myinterface) thecon;
                intr.callBack("berubah");
            }
        });
        holder.kurang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("keranjang", 0);
                SharedPreferences.Editor edity = sharedPreferences.edit();
                Gson gson = new Gson();
                String arraybaru =sharedPreferences.getString("checkout", null);
                Type type = new TypeToken<ArrayList<datacart>>() {}.getType();
                dataItem=gson.fromJson(arraybaru,type);
                Integer countp = Integer.valueOf(String.valueOf(jproduc.getText()));
                countp--;

                if (countp == 0){
                    ((checkout)thecon).finish();

                }
                else{
                    jproduc.setText(Integer.toString(countp));
                    String totalharga =  Integer.toString( countp*hargap);
                    priceProduct.setText(String.format("Rp. %s", totalharga));
                    dataItem.add(holder.getAdapterPosition(),new datacart(
                            dataItem.get(holder.getAdapterPosition()).getId(),
                            dataItem.get(holder.getAdapterPosition()).getTitle(),
                            totalharga,
                            dataItem.get(holder.getAdapterPosition()).getImage(),
                            String.valueOf(jproduc.getText())
                    ));
                    dataItem.remove(holder.getAdapterPosition()+1);
                    String jsons = gson.toJson(dataItem);
                    edity.putString("checkout",jsons);
                    edity.apply();
                    intr = (myinterface) thecon;
                    intr.callBack("berubah");
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return dataItem.size();
    }


}
