package com.ph36492.khopro.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.ph36492.khopro.Adapter.DanhMucSpinner_Adapter;
import com.ph36492.khopro.Adapter.GiamGia_Spinner_Adapter;
import com.ph36492.khopro.Adapter.MonAn_Adapter;
import com.ph36492.khopro.DAO.DanhMucDAO;
import com.ph36492.khopro.DAO.GiamGiaDAO;
import com.ph36492.khopro.DAO.MonAnDAO;
import com.ph36492.khopro.Model.DanhMucMonAn;
import com.ph36492.khopro.Model.GiamGia;
import com.ph36492.khopro.Model.MonAn;
import com.thinhnb.myapplication.R;

import java.util.ArrayList;

public class QLMonanFragment extends Fragment {
    ImageView img_add_MonAn;
    RecyclerView rc_quanLyMonAn;
    EditText ed_maMonAn, ed_tenMonAn, ed_giaMon;
    Button btn_themMonAn, btn_huyThemMonAn;
    MonAnDAO monAnDAO;
    Spinner ed_maGiamGia_MonAn, spndanhmuc_addmonan;
    ArrayList<MonAn> list;
    Dialog dialog;
    MonAn_Adapter monAnAdapter;

    GiamGia_Spinner_Adapter adapterspinner;
    GiamGiaDAO giamgiadao;
    ArrayList<GiamGia> listgiamgia;

    DanhMucSpinner_Adapter adapterspinner_dm;
    DanhMucDAO danhMucDAO;
    ArrayList<DanhMucMonAn>listdanhmuc;
    boolean userSelectedDiscount = false;
    int indexOfDefaultItem = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_q_l_monan, container, false);
        rc_quanLyMonAn = v.findViewById(R.id.rc_quanLyMonAn);
        img_add_MonAn = v.findViewById(R.id.img_add_MonAn);

        monAnDAO = new MonAnDAO(getContext());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rc_quanLyMonAn.setLayoutManager(layoutManager);

        list = (ArrayList<MonAn>) monAnDAO.getAll();
        monAnAdapter = new MonAn_Adapter(getActivity(), list);
        rc_quanLyMonAn.setAdapter(monAnAdapter);

        img_add_MonAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(getContext(), 0);
            }
            public int validate() {
                int check = 1;
                if ( ed_tenMonAn.getText().length() == 0
                        || ed_giaMon.getText().length() == 0) {
                    Toast.makeText(getContext(), "Không bỏ trống", Toast.LENGTH_SHORT).show();
                    check = -1;
                }
                if (TextUtils.isDigitsOnly(ed_tenMonAn.getText().toString())) {
                    // Chuỗi là số
                    ed_tenMonAn.setError("tên món ăn không thể là số");
                    check = -1;
                }
                if (ed_giaMon.getText().length() == 0){
                    ed_giaMon.setError("Giá món ăn đang trống");
                    check = -1;
                }else {
                    try {
                        // Chuyển đổi chuỗi thành số
                        double number = Double.parseDouble(ed_giaMon.getText().toString());

                        // Kiểm tra xem số có là số âm không
                        if (number < 0) {
                            ed_giaMon.setError("Giá đang là số âm");
                            check = -1;
                        } else {
//                           Toast.makeText(getApplicationContext(), "Chuỗi không phải là số âm", Toast.LENGTH_SHORT).show();
                        }
                    } catch (NumberFormatException e) {
                        // Nếu có ngoại lệ, đây không phải là số
                        ed_giaMon.setError("Giá đang không là số");
                        check = -1;
                    }
                }
                return check;
            }
            protected void openDialog(final Context context, final int type) {
                dialog = new Dialog(context);
                dialog.setContentView(R.layout.add_monan);

                ed_tenMonAn = dialog.findViewById(R.id.ed_tenMonAn);
                spndanhmuc_addmonan = dialog.findViewById(R.id.spndanhmuc_addmonan);
                ed_maGiamGia_MonAn = dialog.findViewById(R.id.spngiamgia_addmonan);
                ed_giaMon = dialog.findViewById(R.id.ed_giaMon);

                giamgiadao = new GiamGiaDAO(context);
                listgiamgia = new ArrayList<GiamGia>();
                listgiamgia = (ArrayList<GiamGia>) giamgiadao.getAll();
                // Thêm một mục giảm giá mặc định với giá trị null
                GiamGia defaultGiamGia = new GiamGia();
                defaultGiamGia.setId_GiamGia(0); // Hoặc bạn có thể sử dụng một giá trị duy nhất không trùng lặp
                defaultGiamGia.setPhanTramGiam(0);
                defaultGiamGia.setMaGiamGia("Không giảm giá");
                listgiamgia.add(defaultGiamGia);
                adapterspinner = new GiamGia_Spinner_Adapter(context, listgiamgia);
                ed_maGiamGia_MonAn.setAdapter(adapterspinner);
                if (!userSelectedDiscount) {
                    int indexOfDefaultItem = listgiamgia.indexOf(defaultGiamGia);
                    ed_maGiamGia_MonAn.setSelection(indexOfDefaultItem);
                }
                ed_maGiamGia_MonAn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                        // Nếu người dùng đã chọn giảm giá, cập nhật biến
                        if (position != indexOfDefaultItem) {
                            userSelectedDiscount = true;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        // Do nothing
                    }
                });

                danhMucDAO = new DanhMucDAO(context);
                listdanhmuc = new ArrayList<DanhMucMonAn>();
                listdanhmuc = (ArrayList<DanhMucMonAn>) danhMucDAO.getAll();
                adapterspinner_dm = new DanhMucSpinner_Adapter(context, listdanhmuc);
                spndanhmuc_addmonan.setAdapter(adapterspinner_dm);

                Button btnThem = dialog.findViewById(R.id.btn_themMonAn);
                Button btnHuy = dialog.findViewById(R.id.btn_huyThemMonAn);
                btnThem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (validate() > 0) {
                            MonAn monAn = new MonAn();
                            monAn.setTenMonAn(ed_tenMonAn.getText().toString());
                            monAn.setId_DanhMuc(listdanhmuc.get(spndanhmuc_addmonan.getSelectedItemPosition()).getId_DanhMuc());

                            // Kiểm tra xem mục giảm giá có phải là mục mặc định không
                            GiamGia selectedGiamGia = (GiamGia) ed_maGiamGia_MonAn.getSelectedItem();
                            if (selectedGiamGia.getPhanTramGiam() != 0) {
                                monAn.setId_GiamGia(selectedGiamGia.getId_GiamGia());
                            } else {
                                monAn.setId_GiamGia(null);
                            }

                            monAn.setGiaTien(Double.parseDouble(ed_giaMon.getText().toString()));

                            long result = monAnDAO.Insert(monAn);

                            if (result > 0) {
                                Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                                list.clear();
                                list.addAll(monAnDAO.getAll());
                                monAnAdapter.notifyDataSetChanged();
                                dialog.dismiss();
                            } else {
                                Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });


                btnHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.setCancelable(false);
                dialog.show();
            }
        });
        return v;
    }

}
