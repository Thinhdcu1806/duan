package com.ph36492.khopro.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ph36492.khopro.Database.DbHelper;
import com.ph36492.khopro.Model.GiamGia;

import java.util.ArrayList;
import java.util.List;

public class GiamGiaDAO {
    private com.ph36492.khopro.Database.DbHelper DbHelper;
    private SQLiteDatabase db;

    public GiamGiaDAO(Context context){
        DbHelper = new DbHelper(context);
        db = DbHelper.getWritableDatabase();
    }

    public long Insert(GiamGia giamGia){
        ContentValues values = new ContentValues();
        values.put("maGiamgia", giamGia.getMaGiamGia() );
        values.put("phanTramGiam",giamGia.getPhanTramGiam());
        values.put("soLuotDung",giamGia.getSoLuotDung());
        return db.insert("GiamGia",null,values);
    }
    public  int Update(GiamGia giamGia){
        ContentValues values = new ContentValues();
        values.put("maGiamgia", giamGia.getMaGiamGia() );
        values.put("phanTramGiam",giamGia.getPhanTramGiam());
        values.put("soLuotDung",giamGia.getSoLuotDung());
        String[] dk = new String[]{String.valueOf(giamGia.getId_GiamGia())};
        return db.update("GiamGia",values,"id_GiamGia=?",dk);
    }
    public  int Delete(GiamGia giamGia){
        String[] dk = new String[]{String.valueOf(giamGia.getId_GiamGia())};
        return db.delete("GiamGia","id_GiamGia=?",dk);

    }

    public List<GiamGia> getAll(){
        String sql = "SELECT * FROM GiamGia";
        return getData(sql);
    }

    public GiamGia getID(String id) {
        String sql = "SELECT * FROM GiamGia WHERE id_GiamGia=?";
        List<GiamGia> list = getData(sql, id);

        if (!list.isEmpty()) {
            return list.get(0);
        } else {
            // Trả về null hoặc xử lý theo ý bạn.
            return null;
        }
    }
    public List<GiamGia> getData(String sql , String...selectionArgs){
        List<GiamGia> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql,selectionArgs);
        if(c!=null && c.getCount()>0){
            c.moveToFirst();
            do {
                int id_GiamGia = c.getInt(0);
                String maGiamGia = c.getString(1);
                int PhanTramGiam = c.getInt(2);
                int SoLuotDung = c.getInt(3);
                GiamGia giamGia = new GiamGia();
                giamGia.setId_GiamGia(id_GiamGia);
                giamGia.setMaGiamGia(maGiamGia);
                giamGia.setPhanTramGiam(PhanTramGiam);
                giamGia.setSoLuotDung(SoLuotDung);
                list.add(giamGia);
            }while (c.moveToNext());
        }
        return list;
    }
    public boolean kiemTraMaGiamGiaTonTai(String maGiamGia) {
        String[] columns = { "maGiamGia" };
        String selection = "maGiamGia=?";
        String[] selectionArgs = { maGiamGia };

        Cursor cursor = db.query("GiamGia", columns, selection, selectionArgs, null, null, null);

        boolean tonTai = cursor.moveToFirst();

        cursor.close();

        return tonTai;
    }

    @SuppressLint("Range")
    public int layPhanTramGiamTuMaGiamGia(String maGiamGia) {
        String[] columns = { "phanTramGiam" };
        String selection = "maGiamGia=?";
        String[] selectionArgs = { maGiamGia };

        Cursor cursor = db.query("GiamGia", columns, selection, selectionArgs, null, null, null);

        int phanTramGiam = 0;
        if (cursor.moveToFirst()) {
            phanTramGiam = cursor.getInt(cursor.getColumnIndex("phanTramGiam"));
        }

        cursor.close();

        return phanTramGiam;
    }
    public void giamSoLuotDung(String maGiamGia) {
        SQLiteDatabase db = DbHelper.getWritableDatabase();

        // Lấy số lượt dùng hiện tại
        int soLuotDungHienTai = laySoLuotDungTuMaGiamGia(maGiamGia);

        // Kiểm tra nếu số lượt dùng đã là 0 thì xóa dòng
        if (soLuotDungHienTai > 1) {
            // Giảm số lượt dùng đi 1
            int soLuotDungMoi = soLuotDungHienTai - 1;

            // Cập nhật số lượt dùng mới vào cơ sở dữ liệu
            ContentValues values = new ContentValues();
            values.put("soLuotDung", soLuotDungMoi);
            String whereClause = "maGiamGia=?";
            String[] whereArgs = {maGiamGia};
            db.update("GiamGia", values, whereClause, whereArgs);
        } else  {
            // Nếu số lượt dùng là 0, xóa dòng từ bảng
            String whereClause = "maGiamGia=?";
            String[] whereArgs = {maGiamGia};
            db.delete("GiamGia", whereClause, whereArgs);
        }

    }


    @SuppressLint("Range")
    public int laySoLuotDungTuMaGiamGia(String maGiamGia) {
        String[] columns = { "soLuotDung" };
        String selection = "maGiamGia=?";
        String[] selectionArgs = { maGiamGia };

        Cursor cursor = db.query("GiamGia", columns, selection, selectionArgs, null, null, null);

        int soLuotDung = 0;
        if (cursor.moveToFirst()) {
            soLuotDung = cursor.getInt(cursor.getColumnIndex("soLuotDung"));
        }

        return soLuotDung;
    }



}
