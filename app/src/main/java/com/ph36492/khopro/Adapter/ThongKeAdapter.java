package com.ph36492.khopro.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.ph36492.khopro.Model.MonAnDaBan;
import com.thinhnb.myapplication.R;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ThongKeAdapter extends RecyclerView.Adapter<ThongKeAdapter.ViewHolder> {

    private List<MonAnDaBan> monAnList;

    public ThongKeAdapter(List<MonAnDaBan> monAnList) {
        this.monAnList = monAnList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thongke_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MonAnDaBan monAn = monAnList.get(position);
        holder.txtTenMonAn.setText(monAn.getTenMonAn());
        holder.txtSoLuongDaBan.setText(String.valueOf(monAn.getTongSoLuong()));
    }
    public void updateList(List<MonAnDaBan> newList) {
        // Sắp xếp danh sách theo số lượng đã bán từ lớn đến bé
        Collections.sort(monAnList, new Comparator<MonAnDaBan>() {
            @Override
            public int compare(MonAnDaBan o1, MonAnDaBan o2) {
                return Integer.compare(o2.getTongSoLuong(), o1.getTongSoLuong());
            }
        });
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return monAnList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenMonAn;
        TextView txtSoLuongDaBan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenMonAn = itemView.findViewById(R.id.txtTenMonAn);
            txtSoLuongDaBan = itemView.findViewById(R.id.txtSoLuongDaBan);
        }
    }
}

