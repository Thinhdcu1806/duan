package com.ph36492.khopro.Model;

public class HoaDon {
    private int id_HoaDon;
    private String id_NhanVien;
    private int soBan;
    private String ngayGio;
    private String kieuThanhToan;
    private double tongTien;

    public HoaDon() {
    }


    public int getId_HoaDon() {
        return id_HoaDon;
    }

    public void setId_HoaDon(int id_HoaDon) {
        this.id_HoaDon = id_HoaDon;
    }

    public String getId_NhanVien() {
        return id_NhanVien;
    }

    public void setId_NhanVien(String id_NhanVien) {
        this.id_NhanVien = id_NhanVien;
    }


    public int getSoBan() {
        return soBan;
    }

    public void setSoBan(int soBan) {
        this.soBan = soBan;
    }

    public String getNgayGio() {
        return ngayGio;
    }

    public void setNgayGio(String ngayGio) {
        this.ngayGio = ngayGio;
    }

    public String getKieuThanhToan() {
        return kieuThanhToan;
    }

    public void setKieuThanhToan(String kieuThanhToan) {
        this.kieuThanhToan = kieuThanhToan;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public HoaDon(int id_HoaDon, String id_NhanVien, int soBan, String ngayGio, String kieuThanhToan, double tongTien) {
        this.id_HoaDon = id_HoaDon;
        this.id_NhanVien = id_NhanVien;
        this.soBan = soBan;
        this.ngayGio = ngayGio;
        this.kieuThanhToan = kieuThanhToan;
        this.tongTien = tongTien;
    }
}
