package com.ph36492.khopro.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ph36492.khopro.Database.DbHelper;
import com.ph36492.khopro.Model.BanAn;

import java.util.ArrayList;
import java.util.List;

public class BanAnDAO {
   private SQLiteDatabase db;

   public BanAnDAO(Context context){
       DbHelper dbHelper = new DbHelper(context);
       db = dbHelper.getWritableDatabase();
   }
    public long insert(BanAn obj){
        try {
            ContentValues values = new ContentValues();
            values.put("soBan",obj.getSoBan());
            return db.insertOrThrow("BanAn",null,values);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int update(BanAn obj){
        try {
            ContentValues values = new ContentValues();
            values.put("soBan",obj.getSoBan());
            return db.update("BanAn",values,"id_BanAn = ?",new String[]{String.valueOf(obj.getId_BanAn())});
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int delete(String id){
        try {
            return db.delete("BanAn","id_BanAn=?",new String[]{id});
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    @SuppressLint("Range")
    public List<BanAn> getData(String sql, String...selectionArgs){
        List<BanAn> list = new ArrayList<>();
        try {
            Cursor c = db.rawQuery(sql, selectionArgs);
            while (c.moveToNext()){
                BanAn obj = new BanAn();
                obj.setId_BanAn(Integer.parseInt(c.getString(c.getColumnIndex("id_BanAn"))));
                obj.setSoBan(Integer.parseInt(c.getString(c.getColumnIndex("soBan"))));
                list.add(obj);
            }
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
   public List<BanAn>getAll(){
       String sql = "Select * from BanAn";
       return getData(sql);
   }
    @SuppressLint("Range")
    public BanAn getBanAnByID(int id_BanAn) {
        String query = "SELECT * FROM BanAn WHERE id_BanAn = ?";
        String[] selectionArgs = {String.valueOf(id_BanAn)};

        Cursor cursor = db.rawQuery(query, selectionArgs);

        if (cursor.moveToFirst()) {
            BanAn banan = new BanAn();
            banan.setId_BanAn(cursor.getInt(cursor.getColumnIndex("id_BanAn")));
            banan.setSoBan(Integer.parseInt(cursor.getString(cursor.getColumnIndex("soBan"))));
            cursor.close();
            return banan;
        } else {
            cursor.close();
            return null; // Hoặc xử lý nếu không tìm thấy mã loại sách.
        }
    }
}
