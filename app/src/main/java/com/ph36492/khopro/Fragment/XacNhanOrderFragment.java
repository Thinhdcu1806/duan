package com.ph36492.khopro.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.ph36492.khopro.Adapter.BoNhoTamThoiAdapter;
import com.ph36492.khopro.DAO.BoNhoTamThoiDAO;
import com.ph36492.khopro.Model.BoNhoTamThoi;
import com.thinhnb.myapplication.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class XacNhanOrderFragment extends Fragment {

    ImageView img_back_cfOrder;
    TextView tv_soBan_cfOrder, tv_thanhTien, tv_giamGia_cfOrder, tv_truTien, tv_tongTien;
    ListView lv_confirm_order;
    Button btn_confirm_order, btn_confirm_order_print;
    private ArrayList<BoNhoTamThoi> dn;
    int backValue = 1; // Đặt giá trị backValue thành 1
    public void onItemDeleted() {
        BoNhoTamThoiDAO boNhoTamThoiDAO = new BoNhoTamThoiDAO(getContext());
        // Xử lý sự kiện khi một mục được xóa
        double tongTien = calculateTotalAmount(boNhoTamThoiDAO.getAll());
        tv_tongTien.setText(String.valueOf(formatMoney((int) tongTien)));
        saveTongTienToSharedPreferences((int) tongTien);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_xac_nhan_order, container, false);
        img_back_cfOrder = v.findViewById(R.id.img_back_cfOrder);
        tv_soBan_cfOrder = v.findViewById(R.id.tv_soBan_cfOrder);
        tv_tongTien = v.findViewById(R.id.tv_tongTien);

        btn_confirm_order_print = v.findViewById(R.id.btn_confirm_order_print);

        lv_confirm_order = v.findViewById(R.id.lv_confirm_order);
        // Lấy dữ liệu từ SQLite và tạo danh sách BoNhoTamThoi
        BoNhoTamThoiDAO boNhoTamThoiDAO = new BoNhoTamThoiDAO(getContext());
        List<BoNhoTamThoi> danhSachBoNhoTamThoi = boNhoTamThoiDAO.getAll();

        // Tạo adapter và gán cho ListView
        // Trong XacNhanOrderFragment
        BoNhoTamThoiAdapter adapter = new BoNhoTamThoiAdapter(getContext(), R.layout.row_bonhotamthoi, danhSachBoNhoTamThoi, this);

        lv_confirm_order.setAdapter(adapter);
        int soBan = getSoBanFromSharedPreferences();
        if (soBan!=0) {
            tv_soBan_cfOrder.setText(String.valueOf(soBan));
        }else {
            tv_soBan_cfOrder.setText("Mang Về");
        }
        double tongTien = calculateTotalAmount(danhSachBoNhoTamThoi);
        tv_tongTien.setText(String.valueOf(formatMoney((int) tongTien)));
        saveTongTienToSharedPreferences((int) tongTien);





        img_back_cfOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Khởi tạo fragment mới
                MonAnOrderFragment monAnOrderFragment = new MonAnOrderFragment();

                // Tạo Bundle nếu cần truyền dữ liệu giữa các fragment
                Bundle bundle = new Bundle();
                bundle.putInt("backValue", backValue);
                monAnOrderFragment.setArguments(bundle);

                // Thêm fragment vào stack và chuyển tới
                getFragmentManager().beginTransaction()
                        .replace(R.id.flContent, monAnOrderFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        btn_confirm_order_print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý khi người dùng nhấn vào item, ví dụ: chuyển sang Fragment khác
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                ThanhToanFragment anotherFragment = new ThanhToanFragment();

                fragmentTransaction.replace(R.id.flContent, anotherFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        return v;
    }
    private int getSoBanFromSharedPreferences() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("SoBanSave", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("SoBan", 0); // Giá trị mặc định là 0 nếu không tìm thấy
    }
    private double calculateTotalAmount(List<BoNhoTamThoi> danhSachBoNhoTamThoi) {
        double totalAmount = 0;
        for (BoNhoTamThoi item : danhSachBoNhoTamThoi) {
            totalAmount += item.getThanhTien();
        }
        return totalAmount;
    }
    private String formatMoney(int money) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        return decimalFormat.format(money);
    }
    private void saveTongTienToSharedPreferences(int TongTien) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("TongTien", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("TongTien", TongTien);
        editor.apply();
    }







}