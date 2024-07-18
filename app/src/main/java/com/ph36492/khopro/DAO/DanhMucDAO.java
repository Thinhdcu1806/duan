package com.ph36492.khopro.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ph36492.khopro.Database.DbHelper;
import com.ph36492.khopro.Model.DanhMucMonAn;

import java.util.ArrayList;
import java.util.List;

public class DanhMucDAO {
    DbHelper dbHelper;
    SQLiteDatabase db;

    public DanhMucDAO(Context context){
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public ArrayList<DanhMucMonAn> getListDanhMuc(){
        ArrayList<DanhMucMonAn> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from DanhMuc", null);
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            do{
                list.add(new DanhMucMonAn(cursor.getInt(0), cursor.getString(1)));
            }while (cursor.moveToNext());
        }
        return list;
    }

    public long insert(DanhMucMonAn obj){
        try {
            ContentValues values = new ContentValues();
            values.put("tenDanhMuc",obj.getTenDanhMuc());
            return db.insertOrThrow("DanhMuc",null,values);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public long delete(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete("DanhMuc","id_DanhMuc = ?",new String[]{String.valueOf(id)});

    }

    public long update(DanhMucMonAn danhMuc){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenDanhMuc", danhMuc.getTenDanhMuc());
        return db.update("DanhMuc",values,"id_DanhMuc = ?",new String[]{String.valueOf(danhMuc.getId_DanhMuc())});
    }

    public DanhMucMonAn getID(String id){
        String sql = "Select * from DanhMuc where id_DanhMuc=?";
        List<DanhMucMonAn> list = getData(sql, id);

        if (!list.isEmpty()) {
            return list.get(0);
        } else {
            return null; // Trả về null nếu danh sách rỗng
        }
    }

    @SuppressLint("Range")
    public List<DanhMucMonAn> getData(String sql , String...selectionArgs){
        List<DanhMucMonAn> list = new ArrayList<>();
        try {
            Cursor c = db.rawQuery(sql,selectionArgs);
            while (c.moveToNext()){
                DanhMucMonAn dm = new DanhMucMonAn();
                dm.setId_DanhMuc(Integer.parseInt(c.getString(c.getColumnIndex("id_DanhMuc"))));
                dm.setTenDanhMuc(c.getString(c.getColumnIndex("tenDanhMuc")));
                list.add(dm);
            }
            c.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public List<DanhMucMonAn> getAll(){
        String sql = "SELECT * FROM DanhMuc";
        return getData(sql);
    }
}
