package com.ph36492.khopro.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.ph36492.khopro.DAO.DanhMucDAO;
import com.ph36492.khopro.Fragment.QLDanhMucFragment;
import com.ph36492.khopro.Model.DanhMucMonAn;
import com.thinhnb.myapplication.R;

import java.util.ArrayList;

public class DanhMuc_Adapter extends RecyclerView.Adapter<DanhMuc_Adapter.ViewHolder> {

    Context context;
    ArrayList<DanhMucMonAn> listDM;
    static DanhMucDAO danhMucDAO;

    QLDanhMucFragment fragment;

    EditText ed_maDanhMuc_update, ed_tenDanhMuc_update;

    public DanhMuc_Adapter(Context context, QLDanhMucFragment fragment, ArrayList<DanhMucMonAn> listDM) {
        this.context = context;
        this.listDM = listDM;
        this.fragment = fragment;
        danhMucDAO = new DanhMucDAO(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_danhmuc, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_tenDanhMuc.setText(listDM.get(position).getTenDanhMuc());
        holder.img_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                view = inflater.inflate(R.layout.update_danhmuc, null);
                builder.setView(view);
                Dialog dialog = builder.create();
                dialog.show();


                Button btn_themDanhMuc_update, btn_huyThemDanhMuc_update;

                ed_tenDanhMuc_update = view.findViewById(R.id.ed_tenDanhMuc_update);
                btn_themDanhMuc_update = view.findViewById(R.id.btn_themDanhMuc_update);
                btn_huyThemDanhMuc_update = view.findViewById(R.id.btn_huyThemDanhMuc_update);

                ed_tenDanhMuc_update.setText(listDM.get(position).getTenDanhMuc());

                btn_themDanhMuc_update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        danhMucDAO = new DanhMucDAO(context);
                        if (validate() > 0) {
                            String ten_danhmuc = ed_tenDanhMuc_update.getText().toString();
                            DanhMucMonAn danhMucMonAn = listDM.get(position);
                            danhMucMonAn.setTenDanhMuc(ten_danhmuc);
                            int result = (int) danhMucDAO.update(danhMucMonAn);
                            if (result > 0) {
                                listDM.set(position, danhMucMonAn);
                                notifyDataSetChanged();
                                dialog.dismiss();
                            }
                        }
                    }
                });

                btn_huyThemDanhMuc_update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xóa danh mục");
                builder.setCancelable(false);
                builder.setMessage("Bạn có chắc chắn muốn xoá không ?");

                builder.setNegativeButton("Huỷ", null);

                builder.setPositiveButton("Xoá", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            int result = (int) danhMucDAO.delete(Integer.parseInt(String.valueOf(listDM.get(position).getId_DanhMuc())));
                            if (result > 0) {
                                listDM.remove(position);
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
        return listDM.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_tenDanhMuc;
        ImageView img_edit, img_delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_tenDanhMuc = itemView.findViewById(R.id.tv_tenDanhMuc);
            img_edit = itemView.findViewById(R.id.img_edit_danhMuc);
            img_delete = itemView.findViewById(R.id.img_delete_danhMuc);
        }
    }


    public int validate() {
        int check = 1;
        if (ed_tenDanhMuc_update.getText().length() == 0) {
            Toast.makeText(context, "Không bỏ trống", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;
    }
}
