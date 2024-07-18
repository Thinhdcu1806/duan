package com.ph36492.khopro.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.ph36492.khopro.DAO.NhanVienDAO;
import com.ph36492.khopro.LoginActivity;
import com.ph36492.khopro.Model.NhanVien;
import com.thinhnb.myapplication.R;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {
    TextView ed_hoTenNhanVienTT, ed_tuoiNhanVienTT, ed_gioiTinhNhanVienTT, ed_sdtNhanVienTT, tv_update;
    Button btn_doiMatKhau;
    ImageView gioiTinh;
    ImageButton img_logOut;
    NhanVien nhanVien;
    NhanVienDAO nhanVienDAO;
    Context context;
    ArrayList<NhanVien> list;

    private String mma;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ed_hoTenNhanVienTT = view.findViewById(R.id.ed_hoTenNhanVienTT);
        ed_tuoiNhanVienTT = view.findViewById(R.id.ed_tuoiNhanVienTT);
        ed_gioiTinhNhanVienTT = view.findViewById(R.id.ed_gioiTinhNhanVienTT);
        ed_sdtNhanVienTT = view.findViewById(R.id.ed_sdtNhanVienTT);
        tv_update = view.findViewById(R.id.tv_updateProfile);
        btn_doiMatKhau = view.findViewById(R.id.btn_doiMatKhau);
        img_logOut = view.findViewById(R.id.img_logOut);
        gioiTinh = view.findViewById(R.id.img_daiDien);
        mma = readMmaFromSharedPreferences();
        nhanVienDAO = new NhanVienDAO(getContext());
        nhanVien = nhanVienDAO.getID(mma);


        ed_hoTenNhanVienTT.setText(String.valueOf(nhanVien.getHoTen()));
        ed_tuoiNhanVienTT.setText(String.valueOf(nhanVien.getTuoi()));
        if ("Nam".equals(nhanVien.getGioiTinh())) {
            gioiTinh.setImageResource(R.drawable.nguoidung);
        } else {
            gioiTinh.setImageResource(R.drawable.woman);
        }

        ed_gioiTinhNhanVienTT.setText(nhanVien.getGioiTinh());
        ed_sdtNhanVienTT.setText(nhanVien.getSoDienThoai());

        btn_doiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                DoiMatKhauFragment anotherFragment = new DoiMatKhauFragment();

                fragmentTransaction.replace(R.id.flContent, anotherFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        img_logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

        tv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogUpdateProfile();
            }
        });

    }

    public void dialogUpdateProfile() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
        View v = inflater.inflate(R.layout.update_profile, null);
        builder.setView(v);
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();

        EditText ed_hoTenNhanVienTT_update = v.findViewById(R.id.ed_hoTenNhanVienTT_update);
        EditText ed_tuoiNhanVienTT_update = v.findViewById(R.id.ed_tuoiNhanVienTT_update);
        RadioButton rdoNam = v.findViewById(R.id.rdo_Nam_pr);
        RadioButton rdoNu = v.findViewById(R.id.rdo_Nu_pr);
        EditText ed_sdtNhanVienTT_update = v.findViewById(R.id.ed_sdtNhanVienTT_update);
        Button btn_updateProfile = v.findViewById(R.id.btn_update_profile);
        Button btn_cancelUpdate = v.findViewById(R.id.btn_update_profile_cancel);


        ed_hoTenNhanVienTT_update.setText(ed_hoTenNhanVienTT.getText().toString());
        ed_tuoiNhanVienTT_update.setText(ed_tuoiNhanVienTT.getText().toString());
        ed_sdtNhanVienTT_update.setText(ed_sdtNhanVienTT.getText().toString());
        String gioiTinh = nhanVien.getGioiTinh();
        rdoNam.setChecked(gioiTinh.equals("Nam"));
        rdoNu.setChecked(gioiTinh.equals("Nữ"));


        btn_updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenNV = ed_hoTenNhanVienTT_update.getText().toString();
                int tuoiNV = Integer.parseInt(ed_tuoiNhanVienTT_update.getText().toString());
                String gioiTinh = rdoNam.isChecked() ? "Nam" : (rdoNu.isChecked() ? "Nữ" : "");
                String sdtNV = ed_sdtNhanVienTT_update.getText().toString();

                if (tenNV.isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng nhập họ tên", Toast.LENGTH_SHORT).show();
                } else if (tuoiNV <= 0) {
                    Toast.makeText(getContext(), "Tuổi phải là số dương", Toast.LENGTH_SHORT).show();
                } else if (sdtNV.isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show();
                }
                try {
                    String manv = readMmaFromSharedPreferences();
                    // Tạo đối tượng NhanVien mới với thông tin mới
                    NhanVien newNhanVien = new NhanVien();
                    newNhanVien.setMaNhanVien(manv);
                    newNhanVien.setHoTen(tenNV);
                    newNhanVien.setTuoi(tuoiNV);
                    newNhanVien.setGioiTinh(gioiTinh);
                    newNhanVien.setSoDienThoai(sdtNV);

                    int updateResult = nhanVienDAO.update_Profile(newNhanVien);

                    if (updateResult > 0) {
                        Toast.makeText(getContext(), "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                        onResume();
                    } else {
                        Toast.makeText(getContext(), "Cập nhật thông tin thất bại", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    throw e;
                }
                dialog.dismiss();
            }
        });

        btn_cancelUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });


        dialog.show();
    }


    private String readMmaFromSharedPreferences() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("mma", "");
    }
    @Override
    public void onResume() {
        super.onResume();

        // Cập nhật dữ liệu mỗi khi Fragment được hiển thị lại

        // Ví dụ: Lấy lại dữ liệu từ cơ sở dữ liệu và cập nhật TextView
        NhanVienDAO nhanVienDAO = new NhanVienDAO(getContext());
        NhanVien updatedNhanVien = nhanVienDAO.getID(readMmaFromSharedPreferences());

        ed_hoTenNhanVienTT.setText(updatedNhanVien.getHoTen());
        ed_tuoiNhanVienTT.setText(String.valueOf(updatedNhanVien.getTuoi()));
        ed_gioiTinhNhanVienTT.setText(updatedNhanVien.getGioiTinh());
        ed_sdtNhanVienTT.setText(updatedNhanVien.getSoDienThoai());
    }

}