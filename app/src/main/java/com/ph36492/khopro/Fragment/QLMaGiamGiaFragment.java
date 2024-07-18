package com.ph36492.khopro.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ph36492.khopro.Adapter.GiamGia_Adapter;
import com.ph36492.khopro.DAO.GiamGiaDAO;
import com.ph36492.khopro.Model.GiamGia;
import com.thinhnb.myapplication.R;

import java.util.List;


public class QLMaGiamGiaFragment extends Fragment {
    List<GiamGia> list;
    GiamGiaDAO giamGiaDAO;
    GiamGia_Adapter giamGia_adapter;
    RecyclerView rc_quanLyMaGiamGia;
    ImageView img_add_MaGiamGia;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_q_l_ma_giam_gia, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rc_quanLyMaGiamGia = view.findViewById(R.id.rc_quanLyMaGiamGia);
        img_add_MaGiamGia = view.findViewById(R.id.img_add_MaGiamGia);

        giamGiaDAO = new GiamGiaDAO(getContext());
        list =giamGiaDAO.getAll();
        giamGia_adapter =new GiamGia_Adapter(getContext(),list);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL,false);
        rc_quanLyMaGiamGia.setLayoutManager(linearLayoutManager);
        rc_quanLyMaGiamGia.setAdapter(giamGia_adapter);

        img_add_MaGiamGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogAdd();
            }
        });
    }
    public void DialogAdd(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = ((Activity)getContext()).getLayoutInflater();
        View v = inflater.inflate(R.layout.add_giamgia,null);
        builder.setView(v);
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();


        EditText ed_Magiamgia= v.findViewById(R.id.ed_Magiamgia);
        EditText ed_Mucgiamgia= v.findViewById(R.id.ed_Mucgiamgia);
        EditText edLuotGiamGia = v.findViewById(R.id.ed_soLuotDung_add);
        Button btn_themGG = v.findViewById(R.id.btn_themGG);
        Button btn_huythemGG = v.findViewById(R.id.btn_huyThemGG);

        GiamGiaDAO giamGiaDAO1 = new GiamGiaDAO(getContext());
        List<GiamGia> list1 = giamGiaDAO1.getAll();
        btn_themGG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String magiamgia = ed_Magiamgia.getText().toString();
                String mucgiamgia = (ed_Mucgiamgia.getText().toString());
                String soluotdung = (edLuotGiamGia.getText().toString());
                
                if(magiamgia.isEmpty()){
                    ed_Magiamgia.setError("Mã giảm giá đang trống");
                }
                if (TextUtils.isEmpty(mucgiamgia)) {
                    // Chuỗi rỗng
                    ed_Mucgiamgia.setError("Mức đang trống");
                } else {
                    try {
                        // Chuyển đổi chuỗi thành số
                        double number = Double.parseDouble(mucgiamgia);

                        // Kiểm tra xem số có là số âm không
                        if (number < 0) {
                            ed_Mucgiamgia.setError("Mức giảm giá đang là số âm");
                        } else {
//                           Toast.makeText(getApplicationContext(), "Chuỗi không phải là số âm", Toast.LENGTH_SHORT).show();
                        }
                    } catch (NumberFormatException e) {
                        // Nếu có ngoại lệ, đây không phải là số
                        ed_Mucgiamgia.setError("Mức giảm giá đang không là số");
                    }
                }
                if (TextUtils.isEmpty(soluotdung)) {
                    // Chuỗi rỗng
                    edLuotGiamGia.setError("Lượt dùng đang trống");
                } else {
                    try {
                        // Chuyển đổi chuỗi thành số
                        double number = Double.parseDouble(soluotdung);

                        // Kiểm tra xem số có là số âm không
                        if (number < 0) {
                            edLuotGiamGia.setError("Lượt dùng đang là số âm");
                        } else {
//                           Toast.makeText(getApplicationContext(), "Chuỗi không phải là số âm", Toast.LENGTH_SHORT).show();
                        }
                    } catch (NumberFormatException e) {
                        // Nếu có ngoại lệ, đây không phải là số
                        edLuotGiamGia.setError("Lượt dùng đang không là số");
                    }
                }
                int mucgiamgia2 = Integer.parseInt(ed_Mucgiamgia.getText().toString());
                int soluotdung2 = Integer.parseInt(edLuotGiamGia.getText().toString());

                giamGiaDAO = new GiamGiaDAO(getContext());
                GiamGia giamGia = new GiamGia();
                giamGia.setPhanTramGiam(mucgiamgia2);
                giamGia.setMaGiamGia(magiamgia);
                giamGia.setSoLuotDung(soluotdung2);
                
                long kq = giamGiaDAO.Insert(giamGia);
                if(kq>0){
                    Toast.makeText(getContext(), "Thêm thành công ", Toast.LENGTH_SHORT).show();
                    list.clear();
                    list.addAll(giamGiaDAO.getAll());
                    giamGia_adapter.notifyDataSetChanged();
                    ed_Magiamgia.setText("");
                    ed_Mucgiamgia.setText("");
                    dialog.dismiss();
                }else {
                    Toast.makeText(getContext(), "ko thêm được", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_huythemGG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "hủy thêm", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
