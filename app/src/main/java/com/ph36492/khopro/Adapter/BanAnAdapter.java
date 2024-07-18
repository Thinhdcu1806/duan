package com.ph36492.khopro.Adapter;

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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ph36492.khopro.DAO.BanAnDAO;
import com.ph36492.khopro.Fragment.QLBanAnFragment;
import com.ph36492.khopro.Model.BanAn;
import com.thinhnb.myapplication.R;

import java.util.ArrayList;

public class BanAnAdapter extends RecyclerView.Adapter<BanAnAdapter.ViewHolder>{
    Context context;
    ArrayList<BanAn> list;
    static BanAnDAO dao;
    QLBanAnFragment fragment;

    public BanAnAdapter(Context context, QLBanAnFragment fragment, ArrayList<BanAn> list) {
        this.context = context;
        this.fragment = fragment;
        this.list = list;
        dao = new BanAnDAO(context);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_banan,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_banAn.setText(String.valueOf(list.get(position).getSoBan()));

        holder.img_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                view = inflater.inflate(R.layout.update_banan, null);
                builder.setView(view);
                Dialog dialog = builder.create();
                dialog.show();

                EditText ed_tenBanAn = view.findViewById(R.id.ed_tenBanAn_update);
                Button btn_sua = view.findViewById(R.id.btn_suaBanAn_update);
                Button btn_huy = view.findViewById(R.id.btn_huyThemBanAn_update);

                ed_tenBanAn.setText(String.valueOf(list.get(position).getSoBan()));
                String tenBA = ed_tenBanAn.getText().toString();
                btn_sua.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                       if (TextUtils.isEmpty(tenBA)) {
                            // Chuỗi rỗng
                            ed_tenBanAn.setError("Số bàn ăn đang trống");
                        } else {
                            try {
                                double number = Double.parseDouble(tenBA);

                                if (number < 0) {
                                    ed_tenBanAn.setError("Số bàn ăn đang là số âm");
                                } else {
                                }
                            } catch (NumberFormatException e) {
                                // Nếu có ngoại lệ, đây không phải là số
                                ed_tenBanAn.setError("Số bàn ăn đang không là số");
                            }
                        }
                        if (!tenBA.isEmpty()){
                            try {
                                int soBanMoi = Integer.parseInt(ed_tenBanAn.getText().toString());
                                BanAn banAn = list.get(position);
                                banAn.setSoBan(soBanMoi);
                                int result = dao.update(banAn);
                                if (result > 0) {
                                    list.set(position, banAn);
                                    notifyDataSetChanged();
                                    dialog.dismiss();
                                } else {
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
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
        });

        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xóa bàn ăn");
                builder.setCancelable(false);
                builder.setMessage("Bạn có chắc chắn muốn xoá không ?");

                builder.setNegativeButton("Huỷ", null);

                builder.setPositiveButton("Xoá", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            int result = dao.delete(String.valueOf(list.get(position).getId_BanAn()));
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

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_banAn;
        ImageView img_edit, img_delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_banAn = itemView.findViewById(R.id.txtsoBan_Banan);
            img_edit = itemView.findViewById(R.id.img_edit);
            img_delete = itemView.findViewById(R.id.img_delete);
        }
    }
}
