package com.ph36492.khopro.DAO;

import static com.ph36492.khopro.DAO.BoNhoTamThoiDAO.BoNhoTamThoiContract.KEY_TEN_MON_AN;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ph36492.khopro.Database.DbHelper;
import com.ph36492.khopro.Model.BoNhoTamThoi;

import java.util.ArrayList;
import java.util.List;

public class BoNhoTamThoiDAO {
    private SQLiteDatabase db;
    public BoNhoTamThoiDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    public void close() {
        if (db != null && db.isOpen()) {
            db.close();
        }
    }
    public long Insert(BoNhoTamThoi chiTietHoaDon){
        try {
            ContentValues values = new ContentValues();
            values.put("tenMonAn", chiTietHoaDon.getTenMonAn());
            values.put("soLuong", chiTietHoaDon.getSoLuong());
            values.put("thanhTien",chiTietHoaDon.getThanhTien());
            return db.insert("BoNhoTamThoi", null, values);
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }
    public int DeleteByTenMonAn(String tenMonAn) {
        try {
            return db.delete("BoNhoTamThoi", "tenMonAn=?", new String[]{tenMonAn});
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @SuppressLint("Range")
    public List<BoNhoTamThoi> getData(String sql , String...selectionArgs){
        List<BoNhoTamThoi> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql,selectionArgs);
        while (c.moveToNext()){
            BoNhoTamThoi chiTietHoaDon = new BoNhoTamThoi();
            chiTietHoaDon.setId_BoNho(Integer.parseInt(c.getString(c.getColumnIndex("id_BoNho"))));
            chiTietHoaDon.setTenMonAn(c.getString(c.getColumnIndex("tenMonAn")));

            chiTietHoaDon.setSoLuong(Integer.parseInt(c.getString(c.getColumnIndex("soLuong"))));
            chiTietHoaDon.setThanhTien(Integer.parseInt(c.getString(c.getColumnIndex("thanhTien"))));
            list.add(chiTietHoaDon);
        }
        return list;
    }

    public List<BoNhoTamThoi>getAll(){
        String sql = "Select * from BoNhoTamThoi";
        return getData(sql);
    }

    public BoNhoTamThoi getID(String id){
        String sql = "SELECT * FROM BoNhoTamThoi WHERE id_BoNho=?";
        List<BoNhoTamThoi> list = getData(sql, id);
        if (!list.isEmpty()) {
            return list.get(0);
        } else {
            // Trả về null hoặc giá trị mặc định tùy vào logic của bạn
            return null;
        }
    }

    public void clearAll() {
        try {
            db.delete("BoNhoTamThoi", null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void updateBoNhoTamThoi(BoNhoTamThoi boNhoTamThoi) {
        ContentValues values = new ContentValues();

        values.put("soLuong", boNhoTamThoi.getSoLuong());
        values.put("thanhTien", boNhoTamThoi.getThanhTien());

        // Xác định điều kiện WHERE dựa trên tên món ăn
        String whereClause = "tenMonAn = ?";
        String[] whereArgs = {boNhoTamThoi.getTenMonAn()};

        // Thực hiện câu lệnh cập nhật
        db.update("BoNhoTamThoi", values, whereClause, whereArgs);

    }
    public class BoNhoTamThoiContract {
        public static final String TABLE_NAME = "BoNhoTamThoi";
        public static final String KEY_ID = "id_BoNho";
        public static final String KEY_TEN_MON_AN = "tenMonAn";
        public static final String KEY_SO_LUONG = "soLuong";
        public static final String KEY_THANH_TIEN = "thanhTien";
    }
    @SuppressLint("Range")
    public BoNhoTamThoi getByName(String tenMonAn) {
        BoNhoTamThoi boNhoTamThoi = null;
        Cursor cursor = null;

        try {
            String[] columns = {BoNhoTamThoiContract.KEY_ID, KEY_TEN_MON_AN, BoNhoTamThoiContract.KEY_SO_LUONG, BoNhoTamThoiContract.KEY_THANH_TIEN};
            String selection = KEY_TEN_MON_AN + "=?";
            String[] selectionArgs = {tenMonAn};

            cursor = db.query(BoNhoTamThoiContract.TABLE_NAME, columns, selection, selectionArgs, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                boNhoTamThoi = new BoNhoTamThoi();
                boNhoTamThoi.setId_BoNho(cursor.getInt(cursor.getColumnIndex(BoNhoTamThoiContract.KEY_ID)));
                boNhoTamThoi.setTenMonAn(cursor.getString(cursor.getColumnIndex(KEY_TEN_MON_AN)));
                boNhoTamThoi.setSoLuong(cursor.getInt(cursor.getColumnIndex(BoNhoTamThoiContract.KEY_SO_LUONG)));
                boNhoTamThoi.setThanhTien(cursor.getInt(cursor.getColumnIndex(BoNhoTamThoiContract.KEY_THANH_TIEN)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return boNhoTamThoi;
    }
    public long delete(int id){
        return db.delete("BoNhoTamThoi","id_BoNho = ?",new String[]{String.valueOf(id)});
    }
    @SuppressLint("Range")
    public List<BoNhoTamThoi> layDanhSachBoNhoTamThoi() {
        List<BoNhoTamThoi> listBoNhoTamThoi = new ArrayList<>();

        String query = "SELECT * FROM BoNhoTamThoi";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                BoNhoTamThoi boNhoTamThoi = new BoNhoTamThoi();
                boNhoTamThoi.setId_BoNho(cursor.getInt(cursor.getColumnIndex("id_BoNho")));
                boNhoTamThoi.setTenMonAn(cursor.getString(cursor.getColumnIndex("tenMonAn")));
                boNhoTamThoi.setSoLuong(cursor.getInt(cursor.getColumnIndex("soLuong")));
                boNhoTamThoi.setThanhTien((int) cursor.getDouble(cursor.getColumnIndex("thanhTien")));

                listBoNhoTamThoi.add(boNhoTamThoi);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return listBoNhoTamThoi;
    }


}
