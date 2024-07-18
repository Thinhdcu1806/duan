package com.ph36492.khopro.DAO;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ph36492.khopro.Database.DbHelper;
import com.ph36492.khopro.Model.NhanVien;

import java.util.ArrayList;
import java.util.List;

public class NhanVienDAO {
    private SQLiteDatabase db;
    SharedPreferences sharedPreferences;
    DbHelper dbHelper;
    List<NhanVien> list;

    public NhanVienDAO(Context context) {
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
        sharedPreferences = context.getSharedPreferences("THONGTIN", MODE_PRIVATE);

    }

    public int updatePass(NhanVien obj) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("matKhau", obj.getMatKhau());
        int result = db.update("NhanVien", values, "maNhanVien=?", new String[]{obj.getMaNhanVien()});
        // db.close();  // Do not close the database here; let the calling code handle it
        return result;
    }

    public int delete(String id) {
        return db.delete("NhanVien", "maNhanVien=?", new String[]{id});
    }

    @SuppressLint("Range")
    public List<NhanVien> getData(String sql, String... selectionArgs) {
        List<NhanVien> list = new ArrayList<>();
        try {
            Cursor c = db.rawQuery(sql, selectionArgs);
            while (c.moveToNext()) {
                NhanVien obj = new NhanVien();
                obj.setMaNhanVien(c.getString(c.getColumnIndex("maNhanVien")));
                obj.setHoTen(c.getString(c.getColumnIndex("hoTen")));
                obj.setTuoi(c.getInt(c.getColumnIndex("tuoi")));
                obj.setGioiTinh(c.getString(c.getColumnIndex("gioiTinh")));
                obj.setSoDienThoai(c.getString(c.getColumnIndex("soDienThoai")));
                obj.setMatKhau(c.getString(c.getColumnIndex("matKhau")));
                obj.setLoaiTaiKhoan(c.getString(c.getColumnIndex("loaiTaiKhoan")));
                // Thêm các trường mới vào đây nếu cần
                list.add(obj);
            }
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<NhanVien> getAll() {
        String sql = "Select * from NhanVien ";
        return getData(sql);
    }

    public NhanVien getID(String id) {
        String sql = "Select * from NhanVien where maNhanVien=?";
        List<NhanVien> list = getData(sql, id);

        if (!list.isEmpty()) {
            return list.get(0);
        } else {
            return null; // Trả về null nếu danh sách rỗng
        }
    }

    public int checkLogin(String id, String password) {
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            String[] columns = {"maNhanVien", "matKhau", "loaitaikhoan"};
            String selection = "maNhanVien = ? AND matKhau = ?";
            String[] selectionArgs = {id, password};

            db = dbHelper.getReadableDatabase();
            cursor = db.query("NhanVien", columns, selection, selectionArgs, null, null, null);

            if (cursor.moveToFirst()) {
                @SuppressLint("Range") String loaitaikhoan = cursor.getString(cursor.getColumnIndex("loaiTaiKhoan"));

                // Lưu loại tài khoản vào SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("loaitaikhoan", loaitaikhoan);
                editor.apply();

                return 1;
            } else {
                return -1;
            }
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }

    public long insert(NhanVien nhanVien) {
        ContentValues values = new ContentValues();
        values.put("maNhanVien", nhanVien.getMaNhanVien());
        values.put("hoTen", nhanVien.getHoTen());
        values.put("tuoi", nhanVien.getTuoi());
        values.put("gioiTinh", nhanVien.getGioiTinh());
        values.put("soDienThoai", nhanVien.getSoDienThoai());
        values.put("matKhau", nhanVien.getMatKhau());

        return db.insert("NhanVien", null, values);

    }

    public int update_nv(NhanVien obj) {
        ContentValues values = new ContentValues();
        values.put("hoTen", obj.getHoTen());
        values.put("tuoi", obj.getTuoi());
        values.put("gioiTinh", obj.getGioiTinh());
        values.put("soDienThoai", obj.getSoDienThoai());
        values.put("matKhau", obj.getMatKhau());
        values.put("loaiTaiKhoan", obj.getLoaiTaiKhoan());

        return db.update("NhanVien", values, "maNhanVien=?", new String[]{obj.getMaNhanVien()});
    }

    public int update_Profile(NhanVien obj) {
        ContentValues values = new ContentValues();
        values.put("hoTen", obj.getHoTen());
        values.put("tuoi", obj.getTuoi());
        values.put("gioiTinh", obj.getGioiTinh());
        values.put("soDienThoai", obj.getSoDienThoai());

        return db.update("NhanVien", values, "maNhanVien=?", new String[]{obj.getMaNhanVien()});
    }

}

