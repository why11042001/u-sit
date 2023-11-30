package com.mxexample.mmyapplication;

public class datacart {
    String id,title, price, image,jumlah;
    public datacart(String id, String title, String price, String image, String jumlah) {
        this.title = title;
        this.price = price;
        this.image = image;
        this.id=id;
        this.jumlah=jumlah;
    }

    public String getId(){
        return id;
    }
    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }
    public String getJumlah(){
        return jumlah;
    }
}
