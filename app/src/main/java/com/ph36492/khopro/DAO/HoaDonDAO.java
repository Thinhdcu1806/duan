package com.ph36492.khopro.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ph36492.khopro.Database.DbHelper;
import com.ph36492.khopro.Model.HoaDon;

import java.util.ArrayList;
import java.util.List;

public class HoaDonDAO {
    private com.ph36492.khopro.Database.DbHelper DbHelper;
    private SQLiteDatabase db;

    public HoaDonDAO(Context context){
        DbHelper = new DbHelper(context);
        db = DbHelper.getWritableDatabase();
    }
    public long Insert(HoaDon hoaDon){
        ContentValues values = new ContentValues();
        values.put("id_NhanVien",hoaDon.getId_NhanVien());
        values.put("soBan",hoaDon.getSoBan());
        values.put("ngayGio",hoaDon.getNgayGio());
        values.put("kieuThanhToan",hoaDon.getKieuThanhToan());
        values.put("tongTien",hoaDon.getTongTien());
        return db.insert("HoaDon",null,values);
    }
    public  int Update(HoaDon hoaDon){
        ContentValues values = new ContentValues();
        values.put("id_HoaDon", hoaDon.getId_HoaDon() );
        values.put("id_NhanVien",hoaDon.getId_NhanVien());
        values.put("soBan",hoaDon.getSoBan());
        values.put("ngayGio",hoaDon.getNgayGio());
        values.put("kieuThanhToan",hoaDon.getKieuThanhToan());
        values.put("tongTien",hoaDon.getTongTien());
        String[] dk = new String[]{String.valueOf(hoaDon.getId_HoaDon())};
        return db.update("HoaDon",values,"id_HoaDon=?",dk);
    }
    public  int Delete(HoaDon hoaDon){
        String[] dk = new String[]{String.valueOf(hoaDon.getId_HoaDon())};
        return db.delete("HoaDon","id_HoaDon=?",dk);

    }
    public List<HoaDon> getAll(){
        String sql = "SELECT * FROM HoaDon";
        return getData(sql);
    }
    public HoaDon getID(String id){
        String sql = "SELECT * FROM HoaDon WHERE id_HoaDon=?";
        List<HoaDon> list = getData(sql,id);
        return list.get(0);
    }

    @SuppressLint("Range")
    public List<HoaDon> getData(String sql , String...selectionArgs){
        List<HoaDon> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql,selectionArgs);
        while (c.moveToNext()){
            HoaDon hd = new HoaDon();
            hd.setId_HoaDon(Integer.parseInt(c.getString(c.getColumnIndex("id_HoaDon"))));
            hd.setId_NhanVien(c.getString(c.getColumnIndex("id_NhanVien")));

            String baan = c.getString(c.getColumnIndex("soBan"));
            hd.setSoBan(baan!=null?Integer.parseInt(baan):0);
            hd.setNgayGio(c.getString(c.getColumnIndex("ngayGio")));
            String tongTien = c.getString(c.getColumnIndex("tongTien"));
            hd.setTongTien(tongTien != null ? Integer.parseInt((tongTien)) : (int) 0.0);
            hd.setKieuThanhToan(c.getString(c.getColumnIndex("kieuThanhToan")));
            list.add(hd);
        }
        return list;
    }

    @SuppressLint("Range")
    public int getIdHoaDonByRowId(long rowId) {
        int idHoaDon = -1; // Giá trị mặc định nếu không tìm thấy

        Cursor cursor = null;

        try {
            String[] columns = {"id_HoaDon"};
            String selection = "ROWID = ?";
            String[] selectionArgs = {String.valueOf(rowId)};

            cursor = db.query("HoaDon", columns, selection, selectionArgs, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                idHoaDon = cursor.getInt(cursor.getColumnIndex("id_HoaDon"));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return idHoaDon;
    }

}
