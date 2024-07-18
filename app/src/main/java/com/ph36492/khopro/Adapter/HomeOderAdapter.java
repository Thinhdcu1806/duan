package com.ph36492.khopro.Adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;


import com.ph36492.khopro.DAO.DanhMucDAO;
import com.ph36492.khopro.Fragment.DanhMucOrderFragment;
import com.ph36492.khopro.Fragment.MonAnOrderFragment;
import com.ph36492.khopro.Model.DanhMucMonAn;
import com.thinhnb.myapplication.R;

import java.util.ArrayList;

public class HomeOderAdapter  extends RecyclerView.Adapter<HomeOderAdapter.ViewHolder>{
    EditText ed_tenDanhMuc_update;
    Context context;
    ArrayList<DanhMucMonAn> listDM;
    static DanhMucDAO danhMucDAO;

    DanhMucOrderFragment fragment;

    public HomeOderAdapter(Context context, DanhMucOrderFragment fragment, ArrayList<DanhMucMonAn> listDM) {
        this.context = context;
        this.listDM = listDM;
        this.fragment = fragment;
        danhMucDAO = new DanhMucDAO(context);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =((Activity)context).getLayoutInflater();
        View v = inflater.inflate(R.layout.item_danhmucorder,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_tenDanhMuc.setText(listDM.get(position).getTenDanhMuc());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lấy id danh mục được chọn
                int selectedCategoryId = listDM.get(position).getId_DanhMuc();

                // Chuyển sang DanhSachMonAnFragment và chuyển id danh mục qua
                FragmentManager fragmentManager = fragment.getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                MonAnOrderFragment danhSachMonAnFragment = new MonAnOrderFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("CategoryId", selectedCategoryId);
                danhSachMonAnFragment.setArguments(bundle);

                fragmentTransaction.replace(R.id.flContent, danhSachMonAnFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }


    @Override
    public int getItemCount() {
        return listDM.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_tenDanhMuc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_tenDanhMuc = itemView.findViewById(R.id.txtdanhmucorder);
        }
    }
}
