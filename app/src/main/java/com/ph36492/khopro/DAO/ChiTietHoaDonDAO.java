package com.ph36492.khopro.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ph36492.khopro.Database.DbHelper;
import com.ph36492.khopro.Model.ChiTietHoaDon;
import com.ph36492.khopro.Model.MonAnDaBan;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ChiTietHoaDonDAO {
    private static final String TABLE_NAME = "ChiTietHoaDon";
    private static final String COLUMN_ID = "id_ChiTietHoaDon";
    private static final String COLUMN_HOA_DON_ID = "id_HoaDon";
    // Thêm các cột khác của bảng ChiTietHoaDon

    DbHelper dbHelper;
    SQLiteDatabase db;

    public ChiTietHoaDonDAO(Context context) {
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long Insert(ChiTietHoaDon chiTietHoaDon) {
        ContentValues values = new ContentValues();
        values.put("tenMonAn", chiTietHoaDon.getTenMonAn());
        values.put("soLuong", chiTietHoaDon.getSoLuong());
        values.put("giaTien", chiTietHoaDon.getGiaTien());
        values.put("tongTien", chiTietHoaDon.getTongTien());
        values.put("phanTramGG", chiTietHoaDon.getPhanTramGG());
        values.put("ngay",chiTietHoaDon.getNgay());
        values.put("thang",chiTietHoaDon.getThang());
        // Set giá trị cho các cột trong bảng ChiTietHoaDon
        values.put(COLUMN_HOA_DON_ID, chiTietHoaDon.getId_HoaDon());
        // Thêm các giá trị cho các cột khác của bảng ChiTietHoaDon

        long insertedRowId = db.insert(TABLE_NAME, null, values);

        // Kiểm tra khi insert thành công
        if (insertedRowId != -1) {
            // Gọi phương thức kiểm tra
            boolean isSuccess = checkInsertSuccess(insertedRowId);
            if (isSuccess) {
                Log.d("Insert ChiTietHoaDon", "Thành công");
            } else {
                Log.d("Insert ChiTietHoaDon", "Thất bại");
            }
        } else {
            Log.d("Insert ChiTietHoaDon", "Thất bại");
        }

        return insertedRowId;
    }

    private boolean checkInsertSuccess(long insertedRowId) {
        String[] columns = {COLUMN_ID};
        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(insertedRowId)};

        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        boolean isSuccess = cursor.moveToFirst();
        cursor.close();
        return isSuccess;
    }



    public int Delete(ChiTietHoaDon chiTietHoaDon) {
        try {
            return db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(chiTietHoaDon.getId_ChiTietHoaDon())});
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @SuppressLint("Range")
    public List<ChiTietHoaDon> getData(String sql, String... selectionArgs) {
        List<ChiTietHoaDon> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()) {
            ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon();
            chiTietHoaDon.setId_ChiTietHoaDon(c.getInt(c.getColumnIndex(COLUMN_ID)));

            String idHoaDon = c.getString(c.getColumnIndex(COLUMN_HOA_DON_ID));
            chiTietHoaDon.setId_HoaDon(idHoaDon != null ? Integer.parseInt(idHoaDon) : 0);

            list.add(chiTietHoaDon);
        }
        c.close();
        return list;
    }

    public List<ChiTietHoaDon> getAll() {
        String sql = "SELECT * FROM " + TABLE_NAME;
        return getData(sql);
    }

    public ChiTietHoaDon getID(String id) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + "=?";
        List<ChiTietHoaDon> list = getData(sql, id);
        return list.get(0);
    }

    @SuppressLint("Range")
    public List<ChiTietHoaDon> getIdChiTietHoaDonByRowId(int hoadonid) {
        List<ChiTietHoaDon> danhsach = new ArrayList<>();
        String query = "SELECT * FROM ChiTietHoaDon WHERE id_HoaDon = " + hoadonid;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") int id_ChiTietHoaDon = cursor.getInt(cursor.getColumnIndex("id_ChiTietHoaDon"));
                @SuppressLint("Range") String tenMonAn = cursor.getString(cursor.getColumnIndex("tenMonAn"));
                @SuppressLint("Range") int soLuong = cursor.getInt(cursor.getColumnIndex("soLuong"));
                @SuppressLint("Range") int giaTien = cursor.getInt(cursor.getColumnIndex("giaTien"));
                @SuppressLint("Range") int tongTien = cursor.getInt(cursor.getColumnIndex("tongTien"));
                @SuppressLint("Range") int phanTramGG = cursor.getInt(cursor.getColumnIndex("phanTramGG"));
                @SuppressLint("Range") int id_HoaDon = cursor.getInt(cursor.getColumnIndex("id_HoaDon"));
                @SuppressLint("Range") String ngay = cursor.getString(cursor.getColumnIndex("ngay"));
                @SuppressLint("Range") String thang = cursor.getString(cursor.getColumnIndex("thang"));



                ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon(id_ChiTietHoaDon, id_HoaDon, tenMonAn, soLuong, giaTien, tongTien, phanTramGG,ngay,thang);
                danhsach.add(chiTietHoaDon);
            }
            cursor.close();
        }

        return danhsach;
    }

    public List<MonAnDaBan> getStatisticsByDate(String date) {
        List<MonAnDaBan> statisticsList = new ArrayList<>();

        // Define the SQL query
        String query = "SELECT tenMonAn, SUM(soLuong) AS totalQuantity, SUM(tongTien) AS totalRevenue, " +
                "SUM(giaTien) AS totalAmount " +
                "FROM " + TABLE_NAME +
                " WHERE ngay = ? " +
                "GROUP BY tenMonAn";

        // Execute the query
        Cursor cursor = db.rawQuery(query, new String[]{date});

        // Process the query result
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String tenMonAn = cursor.getString(cursor.getColumnIndex("tenMonAn"));
            @SuppressLint("Range") int totalQuantity = cursor.getInt(cursor.getColumnIndex("totalQuantity"));
            @SuppressLint("Range") int totalRevenue = cursor.getInt(cursor.getColumnIndex("totalRevenue"));
            @SuppressLint("Range") int totalAmount = cursor.getInt(cursor.getColumnIndex("totalAmount"));

            // Create a MonAnDaBan object to store the statistics
            MonAnDaBan monAnDaBan = new MonAnDaBan(tenMonAn, totalQuantity, totalRevenue, totalAmount);
            statisticsList.add(monAnDaBan);
        }

        // Close the cursor
        cursor.close();

        return statisticsList;
    }
    public List<MonAnDaBan> getStatisticsByMonth() {
        List<MonAnDaBan> statisticsList = new ArrayList<>();

        // Lấy tháng hiện tại dưới dạng chuỗi "MM"
        String currentMonth = getCurrentMonth();

        // Định nghĩa câu truy vấn SQL
        String query = "SELECT tenMonAn, SUM(soLuong) AS totalQuantity, SUM(tongTien) AS totalRevenue, " +
                "SUM(giaTien) AS totalAmount " +
                "FROM " + TABLE_NAME +
                " WHERE thang = ? " +
                "GROUP BY tenMonAn";

        Cursor cursor = db.rawQuery(query, new String[]{currentMonth});

        // Xử lý kết quả truy vấn
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String tenMonAn = cursor.getString(cursor.getColumnIndex("tenMonAn"));
            @SuppressLint("Range") int totalQuantity = cursor.getInt(cursor.getColumnIndex("totalQuantity"));
            @SuppressLint("Range") int totalRevenue = cursor.getInt(cursor.getColumnIndex("totalRevenue"));
            @SuppressLint("Range") int totalAmount = cursor.getInt(cursor.getColumnIndex("totalAmount"));

            // Tạo đối tượng MonAnDaBan để lưu trữ thống kê
            MonAnDaBan monAnDaBan = new MonAnDaBan(tenMonAn, totalQuantity, totalRevenue, totalAmount);
            statisticsList.add(monAnDaBan);
        }

        // Đóng con trỏ
        cursor.close();

        return statisticsList;
    }


    private String getCurrentMonth() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM", Locale.getDefault());
        return dateFormat.format(calendar.getTime());
    }




}
