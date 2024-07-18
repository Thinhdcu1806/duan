package com.ph36492.khopro.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.ph36492.khopro.DAO.BoNhoTamThoiDAO;
import com.ph36492.khopro.DAO.GiamGiaDAO;
import com.ph36492.khopro.DAO.MonAnDAO;
import com.ph36492.khopro.Model.BoNhoTamThoi;
import com.ph36492.khopro.Model.GiamGia;
import com.ph36492.khopro.Model.MonAn;
import com.thinhnb.myapplication.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MonAnOrder_Adapter extends RecyclerView.Adapter<MonAnOrder_Adapter.ViewHolder> {
    Context context;
    ArrayList<MonAn> list;
    MonAnDAO monAnDAO;
    MonAn monAn;
    BoNhoTamThoiDAO boNhoTamThoiDAO;
    public int soLuong = 0;
    int i=0;


    private ArrayList<BoNhoTamThoi> danhSachTamThoi = new ArrayList<BoNhoTamThoi>();
    public MonAnOrder_Adapter(Context context , ArrayList<MonAn> list){
        this.context = context;
        this.list = list;
        monAnDAO = new MonAnDAO(context);
        boNhoTamThoiDAO = new BoNhoTamThoiDAO(context);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_order_monan, parent, false);
        return new ViewHolder(view);
    }
    private String formatMoney(int money) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        return decimalFormat.format(money);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final MonAn monAn1 = list.get(position);
        holder.tv_tenMonAnOrder.setText(list.get(position).getTenMonAn());
        GiamGiaDAO giamGiaDAO = new GiamGiaDAO(context);
        GiamGia gg = giamGiaDAO.getID(String.valueOf(monAn1.getId_GiamGia()));
        if (gg!=null){
            Integer discountPercentage = gg.getPhanTramGiam();
            int discountedPrice = (int) (list.get(position).getGiaTien() - (list.get(position).getGiaTien() * discountPercentage / 100));
            holder.tv_giaMonAnOrder.setText(String.valueOf(formatMoney(discountedPrice)+"đ"));
            holder.tv_giaMonAnOrder.setTextColor(ContextCompat.getColor(context, R.color.red));


        }else {
            holder.tv_giaMonAnOrder.setText(formatMoney((int) list.get(position).getGiaTien())+"đ");
        }

        BoNhoTamThoi temporaryItem = findItemInTemporaryList(list.get(position).getTenMonAn());
        String ma = list.get(position).getTenMonAn();
        if (temporaryItem == null) {
            // Kiểm tra xem mục có tồn tại trong cơ sở dữ liệu hay không
            BoNhoTamThoi existingItem = boNhoTamThoiDAO.getByName(ma);

            if (existingItem != null) {
                // Nếu đã tồn tại, set số lượng từ cơ sở dữ liệu lên TextView
                monAn1.setSoLuong(existingItem.getSoLuong());
                holder.tv_soLuong.setText(String.valueOf(monAn1.getSoLuong()));
            } else {
                monAn1.setSoLuong(0);
                holder.tv_soLuong.setText(String.valueOf(monAn1.getSoLuong()));

            }
        }

        holder.btn_themDoAnOrder.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                monAn1.setSoLuong(monAn1.getSoLuong() + 1);
                updateTemporaryList(position, monAn1.getSoLuong());
                holder.tv_soLuong.setText(String.valueOf(monAn1.getSoLuong()));
            }
        });

        holder.btn_giamDoAnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monAn1.setSoLuong(Math.max(0, monAn1.getSoLuong() - 1));
                holder.tv_soLuong.setText(String.valueOf(monAn1.getSoLuong()));
                updateTemporaryList(position, monAn1.getSoLuong());
            }
        });
    }




    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_tenMonAnOrder , tv_giaMonAnOrder , tv_soLuong;
        ImageButton btn_giamDoAnOrder , btn_themDoAnOrder;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_tenMonAnOrder = itemView.findViewById(R.id.tv_tenMonAnOrder);
            tv_soLuong = itemView.findViewById(R.id.tv_soLuong);
            tv_giaMonAnOrder = itemView.findViewById(R.id.tv_giaMonAnOrder);

            btn_themDoAnOrder = itemView.findViewById(R.id.btn_themDoAnOrder);
            btn_giamDoAnOrder = itemView.findViewById(R.id.btn_giamDoAnOrder);
        }
    }

    private BoNhoTamThoi findItemInTemporaryList(String tenMonAn) {
        for (BoNhoTamThoi boNhoTamThoi : danhSachTamThoi) {
            if (boNhoTamThoi.getTenMonAn().equals(tenMonAn)) {
                return boNhoTamThoi;
            }
        }
        return null;
    }

    private void updateTemporaryList(int position, int quantity) {
        MonAn selectedMonAn = list.get(position);

        // Tìm kiếm xem món ăn đã tồn tại trong danh sách hay chưa
        BoNhoTamThoi existingItem = findItemInTemporaryList(selectedMonAn.getTenMonAn());

        if (existingItem != null) {
            // Nếu đã tồn tại, cập nhật thông tin nếu số lượng khác 0
            if (quantity > 0) {
                existingItem.setSoLuong(quantity);
                GiamGiaDAO giamGiaDAO = new GiamGiaDAO(context);
                GiamGia gg = giamGiaDAO.getID(String.valueOf(selectedMonAn.getId_GiamGia()));
                if (gg != null) {
                    Integer discountPercentage = gg.getPhanTramGiam();
                    int discountedPrice = (int) (selectedMonAn.getGiaTien() - (selectedMonAn.getGiaTien() * discountPercentage / 100));
                    existingItem.setThanhTien(discountedPrice * quantity);
                } else {
                    existingItem.setThanhTien((int) (selectedMonAn.getGiaTien() * quantity));
                }
            } else {
                // Nếu số lượng là 0, xóa khỏi danh sách
                danhSachTamThoi.remove(existingItem);
            }
        } else if (quantity > 0) {
            // Nếu chưa tồn tại và số lượng khác 0, thêm mới vào danh sách
            BoNhoTamThoi boNhoTamThoi = new BoNhoTamThoi();
            boNhoTamThoi.setTenMonAn(selectedMonAn.getTenMonAn());
            boNhoTamThoi.setSoLuong(quantity);
            GiamGiaDAO giamGiaDAO = new GiamGiaDAO(context);
            GiamGia gg = giamGiaDAO.getID(String.valueOf(selectedMonAn.getId_GiamGia()));
            if (gg != null) {
                Integer discountPercentage = gg.getPhanTramGiam();
                int discountedPrice = (int) (selectedMonAn.getGiaTien() - (selectedMonAn.getGiaTien() * discountPercentage / 100));
                boNhoTamThoi.setThanhTien(discountedPrice * quantity);
            } else {
                boNhoTamThoi.setThanhTien((int) (selectedMonAn.getGiaTien() * quantity));
            }


            danhSachTamThoi.add(boNhoTamThoi);
        }

    }
    public void saveTemporaryListToDatabase() {
        if (danhSachTamThoi.isEmpty()) {
            Toast.makeText(context, "Bạn chưa thêm gì cả", Toast.LENGTH_SHORT).show();
        } else {
            // Danh sách có dữ liệu, kiểm tra xem các mục trong danh sách đã tồn tại trong cơ sở dữ liệu hay chưa
            for (BoNhoTamThoi boNhoTamThoi : danhSachTamThoi) {
                // Kiểm tra xem mục có tồn tại trong cơ sở dữ liệu hay không
                BoNhoTamThoi existingItem = boNhoTamThoiDAO.getByName(boNhoTamThoi.getTenMonAn());

                if (existingItem != null) {
                    // Nếu đã tồn tại, thực hiện cập nhật thông tin
                    try {
                        existingItem.setSoLuong(boNhoTamThoi.getSoLuong());
                        existingItem.setThanhTien( boNhoTamThoi.getThanhTien());
                        boNhoTamThoiDAO.updateBoNhoTamThoi(existingItem);
                        Toast.makeText(context, "Đã cập nhật dữ liệu trong cơ sở dữ liệu", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    // Nếu không tồn tại, thực hiện thêm mới vào cơ sở dữ liệu
                    try {
                        boNhoTamThoiDAO.Insert(boNhoTamThoi);
                        Toast.makeText(context, "Đã thêm mới dữ liệu vào cơ sở dữ liệu", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }




}
