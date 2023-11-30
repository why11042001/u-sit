package com.mxexample.mmyapplication;

public class itemModelp {
    String title, price, image, deskripsi,nama,profil;

    public itemModelp(String nama, String profil, String title, String price, String image, String deskripsi) {
        this.title = title;
        this.price = price;
        this.image = image;
        this.nama=nama;
        this.profil = profil;
        this.deskripsi=deskripsi;
    }

    public String getNama(){
        return nama;
    }
    public String getProfil() {return profil;}
    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }
    public String getDekripsi(){return deskripsi;}


}
