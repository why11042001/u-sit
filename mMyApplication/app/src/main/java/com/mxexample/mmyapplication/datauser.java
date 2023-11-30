package com.mxexample.mmyapplication;

public class datauser {
    String nama, nohp,profil,saldo;

    public datauser(String nama, String nohp, String profil,String saldo) {
        this.nama = nama;
        this.nohp = nohp;
        this.profil = profil;
        this.saldo = saldo;
    }



    public String getNama() {
        return nama;
    }

    public String getNohp() {
        return nohp;
    }
    public String getProfil() {
        return profil;
    }
    public String getSaldo() {
        return saldo;
    }
}
