package com.ph36492.khopro.Model;

public class DanhMucMonAn {
    private int id_DanhMuc;
    private String tenDanhMuc;

    public DanhMucMonAn() {
    }

    public DanhMucMonAn(int id_DanhMuc, String tenDanhMuc) {
        this.id_DanhMuc = id_DanhMuc;
        this.tenDanhMuc = tenDanhMuc;
    }

    public int getId_DanhMuc() {
        return id_DanhMuc;
    }

    public void setId_DanhMuc(int id_DanhMuc) {
        this.id_DanhMuc = id_DanhMuc;
    }

    public String getTenDanhMuc() {
        return tenDanhMuc;
    }

    public void setTenDanhMuc(String tenDanhMuc) {
        this.tenDanhMuc = tenDanhMuc;
    }
}
