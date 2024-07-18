package com.ph36492.khopro.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.ph36492.khopro.DAO.NhanVienDAO;
import com.ph36492.khopro.Model.NhanVien;
import com.thinhnb.myapplication.R;

public class DoiMatKhauFragment extends Fragment {

    EditText ed_manguoidung_changePass, ed_username_changePass, ed_mkCu, ed_mkMoi;
    Button btn_cancel_changePass, btn_confirm_changPass;
    NhanVienDAO dao;
    NhanVien nhanVien;
    String ma;
    String mk;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_doi_mat_khau, container, false);
        ed_manguoidung_changePass = v.findViewById(R.id.ed_manguoidung_changePass);
        ed_username_changePass = v.findViewById(R.id.ed_username_changePass);
        ed_mkCu = v.findViewById(R.id.ed_passwordChange);
        ed_mkMoi = v.findViewById(R.id.ed_passwordNew);
        btn_confirm_changPass = v.findViewById(R.id.btn_confirm_changPass);
        btn_cancel_changePass = v.findViewById(R.id.btn_cancel_changePass);

        btn_cancel_changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ed_username_changePass.setText("");
                ed_manguoidung_changePass.setText("");
                ed_mkMoi.setText("");
                ed_mkCu.setText("");
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                ProfileFragment anotherFragment = new ProfileFragment();

                fragmentTransaction.replace(R.id.flContent, anotherFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        btn_confirm_changPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            checkpass_manv();
            }
        });
        return v;
    }
    public void checkpass_manv() {
        ma = ed_manguoidung_changePass.getText().toString();
        dao = new NhanVienDAO(getContext());
        nhanVien = dao.getID(ma);

        if (nhanVien == null) {
            Toast.makeText(getContext(), "Mã người dùng không đúng", Toast.LENGTH_SHORT).show();
            return;
        }

        mk = nhanVien.getMatKhau();
        String oldPass = ed_mkCu.getText().toString();
        String newPass = ed_mkMoi.getText().toString();

        if(newPass.length()<8){
            ed_mkMoi.setError("Mật khẩu không đủ 8 ký tự");
        }if (ma.isEmpty() || oldPass.isEmpty() || newPass.isEmpty() || newPass.length()<8 ) {
            Toast.makeText(getContext(), "Mã người dùng và mật khẩu không được bỏ trống", Toast.LENGTH_SHORT).show();
        } else {
            if (mk.equals(oldPass)) {
                // Tạo đối tượng NhanVien mới với thông tin mới
                NhanVien newNhanVien = new NhanVien();
                newNhanVien.setMaNhanVien(ma);
                newNhanVien.setMatKhau(newPass);
                // Cập nhật mật khẩu trong cơ sở dữ liệu
                int updateResult = dao.updatePass(newNhanVien);

                if (updateResult > 0) {
                    ImageView ivSuccess = getView().findViewById(R.id.ivSuccess);
                    ivSuccess.setVisibility(View.VISIBLE);

                    // Sử dụng Handler để ẩn ảnh dấu tích sau 2 giây
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ivSuccess.setVisibility(View.INVISIBLE);
                        }
                    }, 2000);
                    ed_username_changePass.setText("");
                    ed_manguoidung_changePass.setText("");
                    ed_mkMoi.setText("");
                    ed_mkCu.setText("");
                    Toast.makeText(getContext(), "Đổi Mật Khẩu Thành Công", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(getContext(), "Đổi Mật Khẩu Thất Bại", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Mật khẩu cũ không đúng", Toast.LENGTH_SHORT).show();
                ImageView iverorr = getView().findViewById(R.id.iverorr);
                iverorr.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        iverorr.setVisibility(View.INVISIBLE);
                    }
                }, 2000);
            }
        }
    }




}
