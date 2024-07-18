package com.ph36492.khopro.Model;

public class BanAn {
    private int id_BanAn;
    private int soBan;

    public BanAn() {
    }

    public BanAn(int id_BanAn, int soBan) {
        this.id_BanAn = id_BanAn;
        this.soBan = soBan;
    }

    public int getId_BanAn() {
        return id_BanAn;
    }

    public void setId_BanAn(int id_BanAn) {
        this.id_BanAn = id_BanAn;
    }

    public int getSoBan() {
        return soBan;
    }

    public void setSoBan(int soBan) {
        this.soBan = soBan;
    }
}
