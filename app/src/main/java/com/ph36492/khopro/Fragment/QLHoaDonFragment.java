package com.ph36492.khopro.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ph36492.khopro.Adapter.HoaDonChiTietAdapter;
import com.ph36492.khopro.Adapter.HoaDon_Adapter;
import com.ph36492.khopro.DAO.ChiTietHoaDonDAO;
import com.ph36492.khopro.DAO.HoaDonDAO;
import com.ph36492.khopro.Model.ChiTietHoaDon;
import com.ph36492.khopro.Model.HoaDon;
import com.thinhnb.myapplication.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class QLHoaDonFragment extends Fragment {
    HoaDonDAO dao;
    RecyclerView rc_quanLyHoaDon;
    ArrayList<HoaDon>list;
    HoaDon_Adapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_q_l_hoa_don, container, false);
        dao = new HoaDonDAO(getContext());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        rc_quanLyHoaDon = v.findViewById(R.id.rc_quanLyHoaDon);
        rc_quanLyHoaDon.setLayoutManager(layoutManager);
        list = (ArrayList<HoaDon>) dao.getAll();
        adapter = new HoaDon_Adapter(getContext(), list, this);
        adapter.updateList(list);
        rc_quanLyHoaDon.setAdapter(adapter);



        return v;
    }
    public void onHoaDonLongClick(int hoaDonId) {
        showChiTietHoaDonDialog(hoaDonId);
    }
    private void showChiTietHoaDonDialog(int idHoaDon) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View dialogView = inflater.inflate(R.layout.custom_dialog_layout_chiitiet, null);

        // Ánh xạ các View trong layout
        TextView dialogTitle = dialogView.findViewById(R.id.dialogTitle);
        ListView dialogListView = dialogView.findViewById(R.id.dialogListView);
        TextView tongTen = dialogView.findViewById(R.id.txt_tongTienchtiet);
        TextView txt_phantramgg = dialogView.findViewById(R.id.txt_phantramgg);
        TextView txtTruTien = dialogView.findViewById(R.id.txt_soTienTru);
        TextView txtTongTienPhaiTra = dialogView.findViewById(R.id.txt_soTongTien);


        // Thiết lập tiêu đề và danh sách chi tiết trong dialog
        dialogTitle.setText("Chi tiết Hoá đơn");

        ChiTietHoaDonDAO chiTietHoaDonDAO = new ChiTietHoaDonDAO(getContext());
        List<ChiTietHoaDon> listHoaDonChiTiet = chiTietHoaDonDAO.getIdChiTietHoaDonByRowId(idHoaDon);
        int Tong = calculateTotalAmount(listHoaDonChiTiet);
        int phanTramGG = 0;
        int giamTien = 0;
        int phaiTra = 0;

        for (ChiTietHoaDon chiTietHoaDon : listHoaDonChiTiet) {
            phanTramGG = chiTietHoaDon.getPhanTramGG();
        }

        // Tính toán giá trị
        giamTien = (Tong * phanTramGG) / 100;
        phaiTra = Tong - giamTien;

        // Hiển thị giá trị trên giao diện
        txt_phantramgg.setText(String.valueOf(phanTramGG)+"%");
        txtTruTien.setText(String.valueOf(formatMoney(giamTien)));
        txtTongTienPhaiTra.setText(String.valueOf(formatMoney(phaiTra)));

        tongTen.setText(""+formatMoney(Tong));


        // Tạo Adapter cho ListView và thiết lập dữ liệu
        HoaDonChiTietAdapter hoaDonChiTietAdapter = new HoaDonChiTietAdapter(getContext(), listHoaDonChiTiet);
        dialogListView.setAdapter(hoaDonChiTietAdapter);

        builder.setView(dialogView);
        builder.setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private String formatMoney(int money) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        return decimalFormat.format(money);
    }
    private int calculateTotalAmount(List<ChiTietHoaDon> chiTietHoaDonList) {
        int totalAmount = 0;
        for (ChiTietHoaDon chiTietHoaDon : chiTietHoaDonList) {
            totalAmount += (chiTietHoaDon.getGiaTien()*chiTietHoaDon.getSoLuong());
        }
        return totalAmount;
    }
}