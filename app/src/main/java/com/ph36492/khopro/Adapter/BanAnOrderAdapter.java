package com.ph36492.khopro.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.ph36492.khopro.DAO.BanAnDAO;
import com.ph36492.khopro.Model.BanAn;
import com.ph36492.khopro.Model.OnBanAnClickListener;
import com.thinhnb.myapplication.R;

import java.util.ArrayList;

public class BanAnOrderAdapter extends ArrayAdapter<BanAn> {
    private Context context;
    private ArrayList<BanAn> list;
    private static BanAnDAO dao;

    // Thêm dòng sau đây để khai báo biến onBanAnClickListener
    private OnBanAnClickListener onBanAnClickListener;

    // Constructor của Adapter
    public BanAnOrderAdapter(Context context, ArrayList<BanAn> list) {
        super(context, R.layout.itembananorder, list);
        this.context = context;
        this.list = list;
        dao = new BanAnDAO(context);
    }

    // Các phương thức khác của Adapter

    // Thêm setter để thiết lập OnBanAnClickListener
    public void setOnBanAnClickListener(OnBanAnClickListener listener) {
        this.onBanAnClickListener = listener;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.itembananorder, parent, false);

            holder = new ViewHolder();
            holder.tv_banAn = convertView.findViewById(R.id.txtbananorder);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_banAn.setText("Bàn Số: " + String.valueOf(list.get(position).getSoBan()));

        // Các xử lý sự kiện khi nhấn vào item ở đây nếu cần
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    onBanAnClickListener.onBanAnClick(list.get(position).getSoBan());
            }
        });

        return convertView;
    }

    static class ViewHolder {
        TextView tv_banAn;
    }
}

