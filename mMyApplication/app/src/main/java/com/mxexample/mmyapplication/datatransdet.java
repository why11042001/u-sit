package com.mxexample.mmyapplication;

public class datatransdet {
    String title,j_order,t_order,price,image,status;



    public datatransdet(String j_order, String title,String t_order, String price, String image, String status) {
        this.j_order=j_order;
        this.title = title;
        this.t_order=t_order;
        this.price=price;
        this.image=image;
        this.status=status;


    }

    public String getTitle() {
        return title;
    }
    public  String getJ_order(){return j_order;}
    public  String getT_order(){return t_order;}
    public  String getPrice(){return price;}
    public  String getImage(){return image;}
    public  String getStatus(){return status;}


}
