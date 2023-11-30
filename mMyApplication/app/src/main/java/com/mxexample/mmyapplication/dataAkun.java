package com.mxexample.mmyapplication;

public class dataAkun {
    String titleAkun;
    int imgak;



    public dataAkun(int imgak, String titleAkun) {
        this.titleAkun = titleAkun;
        this.imgak = imgak;
    }

    public String getName() {
        return titleAkun;
    }


    public int getPoster() {
        return imgak;
    }
}
