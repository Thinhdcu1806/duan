package com.ph36492.khopro.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.ph36492.khopro.Adapter.BanAnOrderAdapter;
import com.ph36492.khopro.DAO.BanAnDAO;
import com.ph36492.khopro.Model.BanAn;
import com.ph36492.khopro.Model.OnBanAnClickListener;
import com.thinhnb.myapplication.R;

import java.util.ArrayList;

public class BanAnOrderFragment extends Fragment implements OnBanAnClickListener{
    BanAnDAO dao;
    GridView gridView;
    ArrayList<BanAn> list;
    BanAnOrderAdapter adapter;
    TextView txtMangVe;
    private OnBanAnClickListener onBanAnClickListener;

    public void setOnBanAnClickListener(OnBanAnClickListener listener) {
        this.onBanAnClickListener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ban_an_order, container, false);
        dao = new BanAnDAO(getContext());
        gridView = v.findViewById(R.id.gridView);

        list = (ArrayList<BanAn>) dao.getAll();
        adapter = new BanAnOrderAdapter(getActivity(), list);
        adapter.setOnBanAnClickListener(this);


        gridView.setAdapter(adapter);

        txtMangVe = v.findViewById(R.id.txtMangVe);
        txtMangVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soBan = 0;
                saveSoBanToSharedPreferences(soBan);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // Tạo một Fragment mới và chuyển dữ liệu số bàn qua
                DanhMucOrderFragment anotherFragment = new DanhMucOrderFragment();
                fragmentTransaction.replace(R.id.flContent, anotherFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return v;
    }
    @Override
    public void onBanAnClick(int soBan) {
        Log.d("BanAnOrderFragment", "Clicked on BanAn: " + soBan);
        saveSoBanToSharedPreferences(soBan);

        // Xử lý khi người dùng nhấn vào item, ví dụ: chuyển sang Fragment khác
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        DanhMucOrderFragment anotherFragment = new DanhMucOrderFragment();

        fragmentTransaction.replace(R.id.flContent, anotherFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    private void saveSoBanToSharedPreferences(int soBan) {
        // Sử dụng tên SharedPreferences là "MyPrefs" (bạn có thể đặt tên khác)
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("SoBanSave", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Lưu giá trị số bàn vào SharedPreferences
        editor.putInt("SoBan", soBan);
        editor.apply();
    }

}
