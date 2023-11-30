package com.mxexample.mmyapplication;

public class itemTrans {
    String jumlah_order, total_harga, image, title,kode_order;

    public itemTrans(String kode_order, String title , String jumlah_order, String total_harga, String image) {
        this.jumlah_order = jumlah_order;
        this.title = title;
        this.total_harga = total_harga;
        this.image = image;
        this.kode_order=kode_order;
    }

    public String getKode_order(){
        return kode_order;
    }
    public String getTitle() {
        return title;
    }
    public String getjumlah_order() {
        return jumlah_order;
    }

    public String getTotal_harga() {
        return total_harga;
    }

    public String getImage() {
        return image;
    }
}
