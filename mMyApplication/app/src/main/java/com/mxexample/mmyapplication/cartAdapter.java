package com.mxexample.mmyapplication;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class cartAdapter extends RecyclerView.Adapter<cartAdapter.ViewHolder>{
    ArrayList<datacart>datacarts;
    ArrayList<datacart>datacartss;
    List<datacart> dataItem;
    private Context thecon;
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle,textPrice,dapi;
        EditText juml;
        CheckBox ckbk;

        ImageView imagePost;
        CardView cvpr;
        ImageButton kurang,tambah;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            ckbk = itemView.findViewById(R.id.dicek);
            dapi = itemView.findViewById(R.id.data_picker);
            kurang = itemView.findViewById(R.id.tombolkurang);
            tambah = itemView.findViewById(R.id.tomboltambah);
            textTitle =  itemView.findViewById(R.id.titlecri);
            textPrice = itemView.findViewById(R.id.hargacr);
            imagePost = itemView.findViewById(R.id.imgcr);
            cvpr = itemView.findViewById(R.id.cvcart);
            juml = itemView.findViewById(R.id.jumlahorder);
        }
    }

    cartAdapter(Context thecon, List<datacart>data){
        this.thecon = thecon;
        this.dataItem = data;}

    @NonNull
    @Override
    public cartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cartgrid, parent,false);
        return new cartAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull cartAdapter.ViewHolder holder, int position){
        String idb = String.valueOf(dataItem.get(position).getId());
        Integer hargap = Integer.valueOf(dataItem.get(position).getPrice());
        datacartss = new ArrayList<>();
        datacarts = new ArrayList<>();

        TextView titleProduct=holder.textTitle;
        TextView priceProduct=holder.textPrice;
        ImageView imageProduct=holder.imagePost;
        EditText jproduc=holder.juml;
        titleProduct.setText(dataItem.get(position).getTitle());
        jproduc.setText(dataItem.get(position).getJumlah());
        priceProduct.setText("Rp"+dataItem.get(position).getPrice());
        Glide.with(thecon)
                .load(dataItem.get(position).getImage())
                .into(imageProduct);

        holder.tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer countp = Integer.valueOf(String.valueOf(jproduc.getText()));
                countp++;
                jproduc.setText(Integer.toString(countp));
                priceProduct.setText("Rp. "+Integer.toString( countp*hargap));
            }
        });
        holder.kurang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("keranjang", 0);
                SharedPreferences.Editor edity = sharedPreferences.edit();
                Gson gson = new Gson();
                String arraybaru =sharedPreferences.getString("keranjang", null);
                Type type = new TypeToken<ArrayList<datacart>>() {}.getType();
                dataItem=gson.fromJson(arraybaru,type);
                Integer countp = Integer.valueOf(String.valueOf(jproduc.getText()));
                countp--;
                jproduc.setText(Integer.toString(countp));
                priceProduct.setText("Rp. "+Integer.toString( countp*hargap));

                if (countp == 0){
                    dataItem.remove(holder.getAdapterPosition());

                    String jsons = gson.toJson(dataItem);
                    edity.putString("keranjang",jsons);
                    edity.apply();
                    notifyItemRemoved(holder.getAdapterPosition());
                }
            }
        });
        holder.ckbk.setOnClickListener(new View.OnClickListener() {
            Integer jumlahs=Integer.valueOf(jproduc.getText().toString());
            @Override
            public void onClick(View view) {
                if(holder.ckbk.isChecked()){
                    SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("keranjang", 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    Gson gson = new Gson();
                    String arraybaru =sharedPreferences.getString("checkout", null);
                    Type type = new TypeToken<ArrayList<datacart>>() {}.getType();
                    datacarts=gson.fromJson(arraybaru,type);
                    datacartss.add(new datacart(
                            dataItem.get(holder.getAdapterPosition()).getId(),
                            dataItem.get(holder.getAdapterPosition()).getTitle(),
                            String.valueOf(Integer.valueOf(dataItem.get(holder.getAdapterPosition()).getPrice())*Integer.valueOf(jproduc.getText().toString())),
                            dataItem.get(holder.getAdapterPosition()).getImage(),
                            String.valueOf(jproduc.getText())
                    ));
                    if(datacarts!=null){
                        datacarts.addAll(datacartss);
                        String apasih = gson.toJson(datacartss);
                        editor.putString("checkout", apasih);
                        editor.apply();
                        editor.commit();
                    }else{
                    String apasih = gson.toJson(datacartss);
                    editor.putString("checkout", apasih);
                    editor.apply();
                    editor.commit();
                    }

                }
                else {
                    SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("keranjang", 0);
                    SharedPreferences.Editor edity = sharedPreferences.edit();
                    Gson gson = new Gson();
                    String arraybaru =sharedPreferences.getString("checkout", null);
                    Type type = new TypeToken<ArrayList<datacart>>() {}.getType();
                    datacarts=gson.fromJson(arraybaru,type);
                    for(int i=0; i<datacarts.size();i++){
                        if(datacarts.get(i).getId().equals(dataItem.get(holder.getAdapterPosition()).getId())){
                            datacarts.remove(i);
                            String jsons = gson.toJson(datacarts);
                            edity.putString("keranjang",jsons);
                            edity.apply();
                        }
                    }

                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return dataItem.size();
    }

}
