package com.ph36492.khopro.Model;

public class MonAn {
    private int id_MonAn;
    private String tenMonAn;
    private int id_DanhMuc;
    private Integer id_GiamGia; // Sử dụng Integer để cho phép giá trị null
    private double giaTien;
    private int soLuong;

    public MonAn() {
    }

    public MonAn(int id_MonAn, String tenMonAn, int id_DanhMuc, Integer id_GiamGia, double giaTien) {
        this.id_MonAn = id_MonAn;
        this.tenMonAn = tenMonAn;
        this.id_DanhMuc = id_DanhMuc;
        this.id_GiamGia = id_GiamGia;
        this.giaTien = giaTien;
    }

    public int getId_MonAn() {
        return id_MonAn;
    }

    public void setId_MonAn(int id_MonAn) {
        this.id_MonAn = id_MonAn;
    }

    public String getTenMonAn() {
        return tenMonAn;
    }

    public void setTenMonAn(String tenMonAn) {
        this.tenMonAn = tenMonAn;
    }

    public int getId_DanhMuc() {
        return id_DanhMuc;
    }

    public void setId_DanhMuc(int id_DanhMuc) {
        this.id_DanhMuc = id_DanhMuc;
    }

    public Integer getId_GiamGia() {
        return id_GiamGia;
    }

    public void setId_GiamGia(Integer id_GiamGia) {
        this.id_GiamGia = id_GiamGia;
    }

    public double getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(double giaTien) {
        this.giaTien = giaTien;
    }
    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
