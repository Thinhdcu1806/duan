package com.ph36492.khopro.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.ph36492.khopro.Adapter.BanAnAdapter;
import com.ph36492.khopro.DAO.BanAnDAO;
import com.ph36492.khopro.Model.BanAn;
import com.thinhnb.myapplication.R;

import java.util.ArrayList;

public class QLBanAnFragment extends Fragment {

    ImageView img_addBanAn;
    RecyclerView rc_qlba;
    BanAnDAO banAnDAO;
    ArrayList<BanAn> list;
    BanAnAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_q_l_ban_an, container, false);
        img_addBanAn = v.findViewById(R.id.img_add_banAn);
        rc_qlba = v.findViewById(R.id.rc_quanLyBanAn);
        banAnDAO = new BanAnDAO(getContext());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rc_qlba.setLayoutManager(layoutManager);

        list = (ArrayList<BanAn>) banAnDAO.getAll();
        adapter = new BanAnAdapter(getActivity(), this, list);
        rc_qlba.setAdapter(adapter);
//        rc_qlba.setLayoutManager(new GridLayoutManager(getActivity(),2));

        img_addBanAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAddBanAn();
            }
        });

        return v;
    }

    private void dialogAddBanAn() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.add_banan, null);
        builder.setView(v);
        Dialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();

        EditText ed_banAn = v.findViewById(R.id.ed_tenBanAn);
        Button btn_them = v.findViewById(R.id.btn_themBanAn);
        Button btn_huy = v.findViewById(R.id.btn_huyThemBanAn);

        btn_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Xử lý thêm bàn ăn vào database
                String tenBanAn = ed_banAn.getText().toString().trim();
                if (TextUtils.isEmpty(tenBanAn)) {
                    // Chuỗi rỗng
                    ed_banAn.setError("Số bàn ăn đang trống");
                } else {
                    try {
                        // Chuyển đổi chuỗi thành số
                        double number = Double.parseDouble(tenBanAn);

                        // Kiểm tra xem số có là số âm không
                        if (number < 0) {
                            ed_banAn.setError("Số bàn ăn đang là số âm");
                        } else {
//                           Toast.makeText(getApplicationContext(), "Chuỗi không phải là số âm", Toast.LENGTH_SHORT).show();
                        }
                    } catch (NumberFormatException e) {
                        // Nếu có ngoại lệ, đây không phải là số
                        ed_banAn.setError("Số bàn ăn đang không là số");
                    }
                }
                if (!tenBanAn.isEmpty()) {
                    // Kiểm tra xem số bàn đã tồn tại chưa
                    boolean isTableNumberExists = isTableNumberExists(Integer.parseInt(tenBanAn));

                    if (isTableNumberExists) {
                        ed_banAn.setError("Bàn Ăn Này Đã Tồn Tại");
                    } else {
                        // Tiếp tục thêm bàn nếu nó chưa tồn tại
                        BanAn banAn = new BanAn();
                        banAn.setSoBan(Integer.parseInt(tenBanAn));
                        banAnDAO.insert(banAn);
                        capNhatList();
                        dialog.dismiss();
                    }
                } else {
                    ed_banAn.setError("Không Được Bỏ Trống");
                }


            }
        });

        btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    void capNhatList() {
        list.clear();
        list.addAll(banAnDAO.getAll());
        adapter.notifyDataSetChanged();
    }
    private boolean isTableNumberExists(int tableNumber) {
        for (BanAn banAn : list) {
            if (banAn.getSoBan() == tableNumber) {
                return true;
            }
        }
        return false;
    }
}
