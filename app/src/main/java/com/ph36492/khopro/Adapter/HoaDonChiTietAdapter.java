package com.ph36492.khopro.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.ph36492.khopro.Model.ChiTietHoaDon;
import com.thinhnb.myapplication.R;

import java.text.DecimalFormat;
import java.util.List;

public class HoaDonChiTietAdapter extends BaseAdapter {
    private Context context;
    private List<ChiTietHoaDon> listHoaDonChiTiet;

    public HoaDonChiTietAdapter(Context context, List<ChiTietHoaDon> listHoaDonChiTiet) {
        this.context = context;
        this.listHoaDonChiTiet = listHoaDonChiTiet;
    }

    @Override
    public int getCount() {
        return listHoaDonChiTiet.size();
    }

    @Override
    public Object getItem(int position) {
        return listHoaDonChiTiet.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_hoadonthanhtoan, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.txtTenMonAn = convertView.findViewById(R.id.tvTenMonAn_hd);
            viewHolder.txtSoLuong = convertView.findViewById(R.id.tvSoLuong_hd);
            viewHolder.txtGiaTien = convertView.findViewById(R.id.tvGiatien_hd);
            viewHolder.txtTongTien = convertView.findViewById(R.id.tvThanhTien_hd);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ChiTietHoaDon hoaDonChiTiet = listHoaDonChiTiet.get(position);
        viewHolder.txtTenMonAn.setText(hoaDonChiTiet.getTenMonAn());
        viewHolder.txtSoLuong.setText(String.valueOf(hoaDonChiTiet.getSoLuong()));
        viewHolder.txtGiaTien.setText(String.valueOf(formatMoney(hoaDonChiTiet.getGiaTien())));
        viewHolder.txtTongTien.setText(String.valueOf(formatMoney(hoaDonChiTiet.getGiaTien()*hoaDonChiTiet.getSoLuong())));

        return convertView;
    }

    static class ViewHolder {
        TextView txtTenMonAn;
        TextView txtSoLuong;
        TextView txtGiaTien;
        TextView txtTongTien;
    }
    private String formatMoney(int money) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        return decimalFormat.format(money);
    }
}

