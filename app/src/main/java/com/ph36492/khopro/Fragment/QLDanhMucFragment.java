package com.ph36492.khopro.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.ph36492.khopro.Adapter.DanhMuc_Adapter;
import com.ph36492.khopro.DAO.DanhMucDAO;
import com.ph36492.khopro.Model.DanhMucMonAn;
import com.thinhnb.myapplication.R;

import java.util.ArrayList;

public class QLDanhMucFragment extends Fragment {

    RecyclerView rc_danhMuc;
    ImageView img_add_DanhMuc;
    DanhMucDAO danhMucDAO;
    ArrayList<DanhMucMonAn> list;
    DanhMuc_Adapter danhMucAdapter;

    EditText ed_tenDanhMuc;

    Button btn_themDanhMuc, btn_huyThemDanhMuc;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_q_l_danh_muc, container, false);
        rc_danhMuc = view.findViewById(R.id.rc_quanLyDanhMuc);
        img_add_DanhMuc = view.findViewById(R.id.img_add_DanhMuc);
        danhMucDAO = new DanhMucDAO(getContext());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rc_danhMuc.setLayoutManager(layoutManager);

        list = (ArrayList<DanhMucMonAn>) danhMucDAO.getAll();
        danhMucAdapter = new DanhMuc_Adapter(getActivity(), this, list);
        rc_danhMuc.setAdapter(danhMucAdapter);

        img_add_DanhMuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAddDanhMuc();
            }
        });

        return view;
    }

    private void dialogAddDanhMuc() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.add_danhmuc, null);
        builder.setView(v);
        Dialog dialog = builder.create();
        dialog.show();

        ed_tenDanhMuc = v.findViewById(R.id.ed_tenDanhMuc);
        btn_themDanhMuc = v.findViewById(R.id.btn_themDanhMuc);
        btn_huyThemDanhMuc = v.findViewById(R.id.btn_huyThemDanhMuc);

        btn_themDanhMuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenDanhMuc = ed_tenDanhMuc.getText().toString().trim();
                if (!tenDanhMuc.isEmpty()){
                    DanhMucMonAn danhMucMonAn = new DanhMucMonAn();
                    danhMucMonAn.setTenDanhMuc(tenDanhMuc);
                    danhMucDAO.insert(danhMucMonAn);
                    capNhatList();
                    Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });

        btn_huyThemDanhMuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    void capNhatList() {
        list.clear();
        list.addAll(danhMucDAO.getAll());
        danhMucAdapter.notifyDataSetChanged();
    }
}