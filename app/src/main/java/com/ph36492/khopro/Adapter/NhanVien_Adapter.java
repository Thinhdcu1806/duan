package com.ph36492.khopro.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.ph36492.khopro.DAO.NhanVienDAO;
import com.ph36492.khopro.Fragment.QLNhanVienFragment;
import com.ph36492.khopro.Model.NhanVien;
import com.thinhnb.myapplication.R;

import java.util.ArrayList;

public class NhanVien_Adapter extends RecyclerView.Adapter<NhanVien_Adapter.ViewHolder> {
    private Context context;
    private ArrayList<NhanVien> list;
    static NhanVienDAO dao;
    QLNhanVienFragment fragment;

    public NhanVien_Adapter(Context context,QLNhanVienFragment fragment, ArrayList<NhanVien> list) {
        this.context = context;
        this.fragment = fragment;
        this.list = list;
        dao = new NhanVienDAO(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_nhanvien, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        NhanVien nhanVien = list.get(position);

        // Set data to views
        holder.tvMaNhanVien.setText("Mã Nhân Viên: " + nhanVien.getMaNhanVien());
        holder.tvHoTen.setText("Họ Tên: " + nhanVien.getHoTen());
        holder.tvTuoi.setText("Tuổi: " + String.valueOf(nhanVien.getTuoi()));
        holder.tvGioiTinh.setText("Giới Tính: " + nhanVien.getGioiTinh());
        holder.tvSoDienThoai.setText("Số Điện Thoại: " + nhanVien.getSoDienThoai());
        holder.tvMatKhau.setText("Mật Khẩu: " + nhanVien.getMatKhau());

        // Set click listeners for edit and delete buttons
        holder.btimgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View dialogView) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                dialogView = inflater.inflate(R.layout.update_nhanvien, null);
                builder.setView(dialogView);
                Dialog dialog = builder.create();
                dialog.show();

                EditText edmaNV = dialogView.findViewById(R.id.ed_maNhanVien_update);
                EditText edtHoTen = dialogView.findViewById(R.id.ed_tennhanvien_update);
                EditText edTuoi = dialogView.findViewById(R.id.ed_tuoiNhanVien_update);
                RadioButton rdoNam = dialogView.findViewById(R.id.rdo_Nam_update);
                RadioButton rdoNu = dialogView.findViewById(R.id.rdo_Nu_update);
                EditText edsoDienThoai = dialogView.findViewById(R.id.ed_sdtNhanVien_update);
                EditText edmatKhau = dialogView.findViewById(R.id.edmatkhau_nv_update);
                Button btnaddnv = dialogView.findViewById(R.id.btn_themNhanVien_update);
                Button btnhuythemnv = dialogView.findViewById(R.id.btn_huyThemNhanVien_update);

                String gioiTinh = list.get(position).getGioiTinh();
                rdoNam.setChecked(gioiTinh.equals("Nam"));
                rdoNu.setChecked(gioiTinh.equals("Nữ"));
                edmaNV.setText(list.get(position).getMaNhanVien());
                edtHoTen.setText(list.get(position).getHoTen());
                edTuoi.setText(String.valueOf(list.get(position).getTuoi()));
                edsoDienThoai.setText(list.get(position).getSoDienThoai());
                edmatKhau.setText(list.get(position).getMatKhau());


                btnhuythemnv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                btnaddnv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            String maNhanVien = edmaNV.getText().toString().trim();
                            String hoTen = edtHoTen.getText().toString().trim();
                            String tuoi = edTuoi.getText().toString().trim();
                            String gioiTinh = rdoNam.isChecked() ? "Nam" : (rdoNu.isChecked() ? "Nữ" : "");
                            String sodienthoai = edsoDienThoai.getText().toString().trim();
                            String matkhau = edmatKhau.getText().toString().trim();
                            if (maNhanVien.isEmpty()) {
                                edmaNV.setError("Mã nhân viên không được để trống");
                            }  if (hoTen.isEmpty()) {
                                edtHoTen.setError("Họ tên không được để trống");
                            } if (!TextUtils.isDigitsOnly(hoTen)) {
                                edtHoTen.setError("Họ tên không thể là số");
                            }if (TextUtils.isEmpty(tuoi)) {
                                // Chuỗi rỗng
                                edTuoi.setError("Tuổi đang trống");
                            } else {
                                try {
                                    // Chuyển đổi chuỗi thành số
                                    double number = Double.parseDouble(tuoi);

                                    // Kiểm tra xem số có là số âm không
                                    if (number < 0) {
                                        edTuoi.setError("Tuổi đang là số âm");
                                    } else {
//                           Toast.makeText(getApplicationContext(), "Chuỗi không phải là số âm", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (NumberFormatException e) {
                                    // Nếu có ngoại lệ, đây không phải là số
                                    edTuoi.setError("Tuổi đang không là số");
                                }
                            } if (gioiTinh.isEmpty()) {
                                // Đặt lỗi cho RadioButton hoặc xử lý theo cách khác tùy thuộc vào ý định của bạn
                            }  if (sodienthoai.isEmpty()) {
                                edsoDienThoai.setError("Số điện thoại không được để trống");
                            } if (sodienthoai.length()<10){
                                edsoDienThoai.setError("Số điện thoại không đủ 10 số");
                            } if (matkhau.isEmpty()) {
                                edmatKhau.setError("Mật khẩu không được để trống");
                            } if (matkhau.length() < 8) {
                                // Chuỗi có đủ 8 ký tự
                                edmatKhau.setError("Mật khẩu không đủ 8 ký tự");
                            }else {

                                int tuoiInt = Integer.parseInt(tuoi);

                                // Tạo đối tượng NhanVien mới với thông tin đã chỉnh sửa
                                NhanVien updatedNhanVien = new NhanVien(maNhanVien, hoTen, tuoiInt, gioiTinh, sodienthoai, matkhau);

                                // Cập nhật dữ liệu trong danh sách và cơ sở dữ liệu
                                list.set(position, updatedNhanVien);
                                dao.update_nv(updatedNhanVien);

                                // Cập nhật giao diện RecyclerView
                                notifyDataSetChanged();

                                // Đóng dialog sau khi cập nhật thành công
                                dialog.dismiss();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });

        holder.btimgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xóa loại sách");
                builder.setCancelable(false);
                builder.setMessage("Bạn có chắc chắn muốn xoá không ?");

                builder.setNegativeButton("Huỷ", null);

                builder.setPositiveButton("Xoá", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            int result = dao.delete(String.valueOf(list.get(position).getMaNhanVien()));
                            if (result > 0) {
                                list.remove(position);
                                notifyDataSetChanged();
                            } else {
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMaNhanVien, tvHoTen, tvTuoi, tvGioiTinh, tvSoDienThoai, tvMatKhau;
        ImageButton btimgEdit, btimgDelete;
        CardView cardView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaNhanVien = itemView.findViewById(R.id.tvMaNhanVien);
            tvHoTen = itemView.findViewById(R.id.tvHoTen);
            tvTuoi = itemView.findViewById(R.id.tvTuoi);
            tvGioiTinh = itemView.findViewById(R.id.tvGioiTinh);
            tvSoDienThoai = itemView.findViewById(R.id.tvSoDienThoai);
            tvMatKhau = itemView.findViewById(R.id.tvMatKhau);
            btimgEdit = itemView.findViewById(R.id.btimg_edit);
            btimgDelete = itemView.findViewById(R.id.btimg_delete);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }

}
