package com.ph36492.khopro.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.ph36492.khopro.DAO.DanhMucDAO;
import com.ph36492.khopro.DAO.GiamGiaDAO;
import com.ph36492.khopro.DAO.MonAnDAO;
import com.ph36492.khopro.Model.DanhMucMonAn;
import com.ph36492.khopro.Model.GiamGia;
import com.ph36492.khopro.Model.MonAn;
import com.thinhnb.myapplication.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MonAn_Adapter extends RecyclerView.Adapter<MonAn_Adapter.ViewHolder>{
    Context context;
    private ArrayList<MonAn> list;
    MonAnDAO monAnDAO;
    MonAn_Adapter monAnAdapter;
    DanhMucDAO danhMucDAO;
    GiamGiaDAO giamGiaDAO;
    Spinner ed_maGiamGia_MonAn, spndanhmuc_addmonan;

    GiamGia_Spinner_Adapter adapterspinner;
    ArrayList<GiamGia> listgiamgia;

    DanhMucSpinner_Adapter adapterspinner_dm;
    ArrayList<DanhMucMonAn>listdanhmuc;
    int positionGG, positionDM;
    MonAn monAn;

    public MonAn_Adapter(Context context, ArrayList<MonAn> list) {
        this.context = context;
        this.list = list;
        monAnDAO = new MonAnDAO(context);
        monAnAdapter = this; // Thêm dòng này
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_monan, parent, false);
        return new ViewHolder(view);
    }
    private String formatMoney(int money) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        return decimalFormat.format(money);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final MonAn monAn1 = list.get(position);
        if (monAn1 != null) {
            holder.tv_maMonAn.setText(String.valueOf(list.get(position).getId_MonAn()));
            holder.tv_tenMonAn.setText(list.get(position).getTenMonAn());

            danhMucDAO = new DanhMucDAO(context);
            DanhMucMonAn dm = danhMucDAO.getID(String.valueOf(monAn1.getId_DanhMuc()));
            holder.tv_loaiMon.setText(dm.getTenDanhMuc());

            giamGiaDAO = new GiamGiaDAO(context);
            GiamGia gg = giamGiaDAO.getID(String.valueOf(monAn1.getId_GiamGia()));
            if (gg != null) {
                holder.tv_giamGiaMon.setText(String.valueOf(gg.getPhanTramGiam()) + "%");

                Integer discountPercentage = gg.getPhanTramGiam();
                if (discountPercentage != null) {
                    double giaTien = list.get(position).getGiaTien();
                    double discountedPrice = giaTien - (giaTien * discountPercentage / 100);

                    String giaTienStrikethrough = String.valueOf((int) giaTien) + " vnd";
                    Spannable spannable = new SpannableString(giaTienStrikethrough);
                    spannable.setSpan(new StrikethroughSpan(), 0, giaTienStrikethrough.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                    holder.tv_giaMonAn.setText(spannable);
                    holder.tv_giatiengg.setText(formatMoney((int) discountedPrice) + " vnd");
                    holder.tv_giatiengg.setTextColor(ContextCompat.getColor(context, R.color.red));
                } else {
                    holder.tv_giamGiaMon.setText("Không có mã giảm giá");
                    holder.tv_giaMonAn.setText(formatMoney((int) list.get(position).getGiaTien()) + " vnd");
                    holder.tv_giatiengg.setText("");
                }
            } else {
                holder.tv_giamGiaMon.setText("Không có mã giảm giá");
                holder.tv_giaMonAn.setText(formatMoney((int) list.get(position).getGiaTien()) + " vnd");
                holder.tv_giatiengg.setText("");
            }
        }

            holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               dialogXoaMonAn(monAn1);
            }
        });

        holder.img_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogSuaMonAn(monAn1);
            }


        });
    }

    private void dialogXoaMonAn(MonAn monAn) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(android.R.drawable.ic_delete);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có muốn xóa không?");
        builder.setCancelable(false);
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                monAnDAO = new MonAnDAO(context);
                int kq = monAnDAO.Delete(monAn);
                if(kq>0){
                    Toast.makeText(context, "xóa thành công", Toast.LENGTH_SHORT).show();
                    list.clear();
                    list.addAll(monAnDAO.getAll());
                    notifyDataSetChanged();
                    dialog.dismiss();
                }else {
                    Toast.makeText(context, "xóa không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "hủy xóa", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();


    }

    private void dialogSuaMonAn(MonAn monAn) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View v = inflater.inflate(R.layout.update_monan,null);
        builder.setView(v);
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();

        EditText ed_tenMonAn_update = v.findViewById(R.id.ed_tenMonAn_update);
        ed_maGiamGia_MonAn = v.findViewById(R.id.spngiamgia_updatemonan);
        spndanhmuc_addmonan = v.findViewById(R.id.spndanhmuc_update);
        EditText ed_giaMon_update = v.findViewById(R.id.ed_giaMon_update);

        Button btn_updateMA = v.findViewById(R.id.btn_themMonAn_update);
        Button btn_huyupdateMA = v.findViewById(R.id.btn_huyThemMonAn_update);

        MonAnDAO monAnDAO1 = new MonAnDAO(context);
        List<MonAn> list1 = monAnDAO1.getAll();
        ed_tenMonAn_update.setText(monAn.getTenMonAn());

        giamGiaDAO = new GiamGiaDAO(context);
        listgiamgia = new ArrayList<GiamGia>();
        listgiamgia = (ArrayList<GiamGia>)giamGiaDAO.getAll();
        adapterspinner = new GiamGia_Spinner_Adapter(context,listgiamgia);
        ed_maGiamGia_MonAn.setAdapter(adapterspinner);

        danhMucDAO = new DanhMucDAO(context);
        listdanhmuc = new ArrayList<DanhMucMonAn>();
        listdanhmuc = (ArrayList<DanhMucMonAn>) danhMucDAO.getAll();
        adapterspinner_dm = new DanhMucSpinner_Adapter(context, listdanhmuc);
        spndanhmuc_addmonan.setAdapter(adapterspinner_dm);

        for (int i = 0; i < listgiamgia.size(); i++) {
            if (monAn.getId_GiamGia().equals(listgiamgia.get(i).getId_GiamGia())) {
                positionGG = i;
                break;
            }
        }
        ed_maGiamGia_MonAn.setSelection(positionGG);

        for (int i = 0; i < listdanhmuc.size(); i++) {
            if (monAn.getId_DanhMuc() == listdanhmuc.get(i).getId_DanhMuc()) {
                positionDM = i;
                break;
            }
        }
        spndanhmuc_addmonan.setSelection(positionDM);

        ed_giaMon_update.setText(monAn.getGiaTien()+"");

        btn_updateMA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    String tenMonan = ed_tenMonAn_update.getText().toString();
                    String idDanhMuc = String.valueOf(listdanhmuc.get(spndanhmuc_addmonan.getSelectedItemPosition()).getId_DanhMuc());

                    // Kiểm tra xem có giảm giá được chọn không
                    GiamGia selectedGiamGia = (GiamGia) ed_maGiamGia_MonAn.getSelectedItem();
                    String idgiamgia = String.valueOf((selectedGiamGia != null) ? selectedGiamGia.getId_GiamGia() : 0);

                    Log.d("DEBUG", "idDanhMuc: " + idDanhMuc);
                    Log.d("DEBUG", "idgiamgia: " + idgiamgia);

                    String giamon = ed_giaMon_update.getText().toString();
                    
                    Double giamondb = Double.parseDouble(giamon);
                    Integer idgg = Integer.parseInt(idgiamgia);
                    Integer iddm = Integer.parseInt(idDanhMuc);
                    int check = 1;
                    if (TextUtils.isDigitsOnly(tenMonan)) {
                        // Chuỗi là số
                        ed_tenMonAn_update.setError("tên món ăn không thể là số");
                        check = -1;
                    }
                    if (giamon.isEmpty()){
                        ed_giaMon_update.setError("Giá món ăn đang trống");
                        check = -1;
                    }else {
                        try {
                            // Chuyển đổi chuỗi thành số
                            double number = Double.parseDouble(giamon);

                            // Kiểm tra xem số có là số âm không
                            if (number < 0) {
                                ed_giaMon_update.setError("Giá đang là số âm");
                                check = -1;
                            } else {
//                           Toast.makeText(getApplicationContext(), "Chuỗi không phải là số âm", Toast.LENGTH_SHORT).show();
                            }
                        } catch (NumberFormatException e) {
                            // Nếu có ngoại lệ, đây không phải là số
                            ed_giaMon_update.setError("Giá đang không là số");
                            check = -1;
                        }
                    }
                    monAn.setTenMonAn(tenMonan);
                    monAn.setId_DanhMuc(iddm);
                    if (idgg!=1) {
                        monAn.setId_GiamGia(idgg);
                    } else {
                        monAn.setId_GiamGia(null);
                    }
                    monAn.setGiaTien(giamondb);



                    if(check ==1){
                        long result = monAnDAO.Update(monAn);
                        // Kiểm tra kết quả cập nhật
                        if (result > 0) {
                            // Nếu cập nhật thành công, thông báo và làm mới danh sách
                            Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                            list.clear();
                            list.addAll(monAnDAO.getAll());
                            if (monAnAdapter != null) {
                                monAnAdapter.notifyDataSetChanged();
                            }
                            dialog.dismiss();
                        } else {
                            // Nếu cập nhật thất bại, thông báo lỗi
                            Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });




        btn_huyupdateMA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "hủy update", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_maMonAn, tv_tenMonAn, tv_loaiMon, tv_giamGiaMon, tv_giaMonAn, tv_giatiengg;
        ImageView img_edit, img_delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_maMonAn = itemView.findViewById(R.id.tv_maMonAn);
            tv_tenMonAn = itemView.findViewById(R.id.tv_tenMonAn);
            tv_loaiMon = itemView.findViewById(R.id.tv_loaiMonAn);
            tv_giamGiaMon = itemView.findViewById(R.id.tv_giamGiaMon);
            tv_giaMonAn = itemView.findViewById(R.id.tv_giaTienMon);
            tv_giatiengg = itemView.findViewById(R.id.tv_giaTienduocgg);

            img_delete = itemView.findViewById(R.id.img_delete_monAn);
            img_edit = itemView.findViewById(R.id.img_edit_monAn);

        }
    }


}
