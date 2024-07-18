package com.ph36492.khopro.Model;

public class ChiTietHoaDon {
    private int id_ChiTietHoaDon;
    private int id_HoaDon;
    private String tenMonAn;
    private int soLuong;
    private int giaTien;
    private int tongTien;
    private int phanTramGG;
    private String ngay;
    private String thang;

    public ChiTietHoaDon() {
    }


    public ChiTietHoaDon(int id_ChiTietHoaDon, int id_HoaDon, String tenMonAn, int soLuong, int giaTien, int tongTien, int phanTramGG, String ngay, String thang) {
        this.id_ChiTietHoaDon = id_ChiTietHoaDon;
        this.id_HoaDon = id_HoaDon;
        this.tenMonAn = tenMonAn;
        this.soLuong = soLuong;
        this.giaTien = giaTien;
        this.tongTien = tongTien;
        this.phanTramGG = phanTramGG;
        this.ngay = ngay;
        this.thang = thang;
    }

    public int getId_ChiTietHoaDon() {
        return id_ChiTietHoaDon;
    }

    public void setId_ChiTietHoaDon(int id_ChiTietHoaDon) {
        this.id_ChiTietHoaDon = id_ChiTietHoaDon;
    }

    public int getId_HoaDon() {
        return id_HoaDon;
    }

    public void setId_HoaDon(int id_HoaDon) {
        this.id_HoaDon = id_HoaDon;
    }

    public String getTenMonAn() {
        return tenMonAn;
    }

    public void setTenMonAn(String tenMonAn) {
        this.tenMonAn = tenMonAn;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(int giaTien) {
        this.giaTien = giaTien;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }

    public int getPhanTramGG() {
        return phanTramGG;
    }

    public void setPhanTramGG(int phanTramGG) {
        this.phanTramGG = phanTramGG;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getThang() {
        return thang;
    }

    public void setThang(String thang) {
        this.thang = thang;
    }


}
