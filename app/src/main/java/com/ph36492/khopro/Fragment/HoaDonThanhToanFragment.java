package com.ph36492.khopro.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


import com.ph36492.khopro.Adapter.HoaDonChiTietAdapter;
import com.ph36492.khopro.Adapter.ListNhaBepAdapter;
import com.ph36492.khopro.DAO.ChiTietHoaDonDAO;
import com.ph36492.khopro.DAO.NhanVienDAO;
import com.ph36492.khopro.Model.ChiTietHoaDon;
import com.ph36492.khopro.Model.NhanVien;
import com.thinhnb.myapplication.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class HoaDonThanhToanFragment extends Fragment {
    TextView tv_maHoaDon_hoaDon, tv_soBan_hoaDon, tv_gioTao_hoaDon, tv_maNhanVien_hoaDon, txtghichu,tv_ngayTao_hoaDon,id_hoadonso, tv_thanhTien_hoaDon, tv_giamGia_hoaDon, tv_truTien_hoaDon, tv_tongTien_hoaDon,giotaonhabep,sobannhabep;

    ListView lv_hoaDon_ThanhToan,lv_hoadonnhabep;
    private ArrayList<ChiTietHoaDon> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_hoa_don_thanh_toan, container, false);
        tv_maHoaDon_hoaDon = v.findViewById(R.id.tv_maHoaDon_hoaDon);
        tv_soBan_hoaDon = v.findViewById(R.id.tv_soBan_hoaDon);
        tv_gioTao_hoaDon = v.findViewById(R.id.tv_gioTao_hoaDon);
        tv_maNhanVien_hoaDon = v.findViewById(R.id.tv_maNhanVien_hoaDon);
        tv_ngayTao_hoaDon = v.findViewById(R.id.tv_ngayTao_hoaDon);
        tv_thanhTien_hoaDon = v.findViewById(R.id.tv_thanhTien_hoaDon);
        tv_giamGia_hoaDon = v.findViewById(R.id.tv_giamGia_hoaDon);
        tv_truTien_hoaDon = v.findViewById(R.id.tv_truTien_hoaDon);
        tv_tongTien_hoaDon = v.findViewById(R.id.tv_tongTien_hoaDon);
        lv_hoaDon_ThanhToan = v.findViewById(R.id.lv_hoaDon_ThanhToan);
        lv_hoadonnhabep = v.findViewById(R.id.lv_hoadonnhabep);
        id_hoadonso = v.findViewById(R.id.id_hoadonso);
        txtghichu = v.findViewById(R.id.txt_ghchunhabep);
        sobannhabep = v.findViewById(R.id.soBannhabep);
        giotaonhabep = v.findViewById(R.id.edthoigiamnhabep);


        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LuuMaHoaDon", Context.MODE_PRIVATE);

        String maHoaDonChiTiet = sharedPreferences.getString("maHoaDon", "-1");
        int maHoaDonint = Integer.parseInt(maHoaDonChiTiet);

        ChiTietHoaDonDAO hoaDonChiTietDAO = new ChiTietHoaDonDAO(getContext());


        if (maHoaDonint!=-1){
            List<ChiTietHoaDon> listHoaDonChiTiet = hoaDonChiTietDAO.getIdChiTietHoaDonByRowId(maHoaDonint);
            if (!listHoaDonChiTiet.isEmpty()) {
                HoaDonChiTietAdapter hoaDonChiTietAdapter = new HoaDonChiTietAdapter(getContext(), listHoaDonChiTiet);
                lv_hoaDon_ThanhToan.setAdapter(hoaDonChiTietAdapter);
                ListNhaBepAdapter nhaBepAdapter = new ListNhaBepAdapter(getContext(),listHoaDonChiTiet);
                lv_hoadonnhabep.setAdapter(nhaBepAdapter);
                int totalAmount = calculateTotalAmount(listHoaDonChiTiet);
                tv_thanhTien_hoaDon.setText(String.valueOf(formatMoney(totalAmount)));
                int phantramgg=0;
                for (ChiTietHoaDon chiTietHoaDon : listHoaDonChiTiet) {
                    phantramgg = chiTietHoaDon.getPhanTramGG();
                }
                tv_giamGia_hoaDon.setText(String.valueOf(phantramgg));
                int tongtien = (totalAmount * phantramgg) / 100;
                tv_truTien_hoaDon.setText(String.valueOf(formatMoney(tongtien)));
                int giamtien = totalAmount-tongtien;
                tv_tongTien_hoaDon.setText(String.valueOf(formatMoney(giamtien)));


            } else {
                Toast.makeText(getContext(), "Rỗng", Toast.LENGTH_SHORT).show();
            }


        }


        displayDataFromSharedPreferences();

        return v;
    }

    private void displayDataFromSharedPreferences() {
        // Lấy dữ liệu từ SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LuuMaHoaDon", Context.MODE_PRIVATE);
        SharedPreferences getNV = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences getSB = getActivity().getSharedPreferences("SoBanSave", Context.MODE_PRIVATE);
        SharedPreferences getGio = getActivity().getSharedPreferences("GioSave", Context.MODE_PRIVATE);
        SharedPreferences getNgay = getActivity().getSharedPreferences("NgaySave", Context.MODE_PRIVATE);




        // Lấy và hiển thị mã hóa đơn
        String maHoaDon = sharedPreferences.getString("maHoaDon", "");
        tv_maHoaDon_hoaDon.setText(maHoaDon);
        id_hoadonso.setText("Hóa Đơn Số: "+maHoaDon);


        SharedPreferences svghichu = getActivity().getSharedPreferences("ghiChuSave", Context.MODE_PRIVATE);
        String ghiChu = svghichu.getString("GhiChu", "");
        txtghichu.setText(ghiChu);

        String manv = getNV.getString("mma","");
        NhanVienDAO nvDao = new NhanVienDAO(getContext());
        NhanVien nv = nvDao.getID(manv);
        String tenNV = nv.getHoTen();
        tv_maNhanVien_hoaDon.setText(tenNV);

        String ngay = getNgay.getString("Ngay","");
        String gio = getGio.getString("Gio","");
        int soBan = getSB.getInt("SoBan",0);
        if (soBan==0){
            tv_soBan_hoaDon.setText("Mang Về");

        }else {
            tv_soBan_hoaDon.setText(String.valueOf(soBan));
        }
        tv_gioTao_hoaDon.setText(gio);
        tv_ngayTao_hoaDon.setText(ngay);
        sobannhabep.setText("Bàn Số: "+String.valueOf(soBan));
        giotaonhabep.setText("Thời Gian: "+gio);





    }
    private int calculateTotalAmount(List<ChiTietHoaDon> chiTietHoaDonList) {
        int totalAmount = 0;
        for (ChiTietHoaDon chiTietHoaDon : chiTietHoaDonList) {
            totalAmount += (chiTietHoaDon.getGiaTien()*chiTietHoaDon.getSoLuong());
        }
        return totalAmount;
    }
    private String formatMoney(int money) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        return decimalFormat.format(money);
    }

}
