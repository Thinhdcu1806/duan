package com.ph36492.khopro.Model;

public class MonAnDaBan {
    private String tenMonAn;
    private int tongSoLuong;
    private double tongDoanhThu;
    private int giaTien;

    public MonAnDaBan() {
    }


    public MonAnDaBan(String tenMonAn, int tongSoLuong, double tongDoanhThu, int giaTien) {
        this.tenMonAn = tenMonAn;
        this.tongSoLuong = tongSoLuong;
        this.tongDoanhThu = tongDoanhThu;
        this.giaTien = giaTien;
    }

    public String getTenMonAn() {
        return tenMonAn;
    }

    public void setTenMonAn(String tenMonAn) {
        this.tenMonAn = tenMonAn;
    }

    public int getTongSoLuong() {
        return tongSoLuong;
    }

    public void setTongSoLuong(int tongSoLuong) {
        this.tongSoLuong = tongSoLuong;
    }

    public double getTongDoanhThu() {
        return tongDoanhThu;
    }

    public void setTongDoanhThu(double tongDoanhThu) {
        this.tongDoanhThu = tongDoanhThu;
    }

    public int getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(int giaTien) {
        this.giaTien = giaTien;
    }
}


