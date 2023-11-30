package com.mxexample.mmyapplication;

public class itemModel {
    String title, price, image;
    Integer id;
    public itemModel(Integer id, String title, String price, String image) {
        this.title = title;
        this.price = price;
        this.image = image;
        this.id=id;
    }

    public Integer getId(){
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
}
