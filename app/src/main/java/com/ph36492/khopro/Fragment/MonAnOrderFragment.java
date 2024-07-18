package com.ph36492.khopro.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.ph36492.khopro.Adapter.MonAnOrder_Adapter;
import com.ph36492.khopro.DAO.MonAnDAO;
import com.ph36492.khopro.Model.BoNhoTamThoi;
import com.ph36492.khopro.Model.MonAn;
import com.thinhnb.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class MonAnOrderFragment extends Fragment {

    private MonAnDAO monAnDAO;
    private ArrayList<MonAn> list;
    private MonAnOrder_Adapter monAnOrder_adapter;
    private RecyclerView rc_monAnOrder;
    private Button checkBill,backDM;
    private ArrayList<BoNhoTamThoi> selectedItems = new ArrayList<>();
    private int check = 0; // Mặc định là 0

    public void setCheckValue(int value) {
        this.check = value;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.order_monan, container, false);

        rc_monAnOrder = v.findViewById(R.id.rc_monAnOrder);
        checkBill = v.findViewById(R.id.btn_checkBill);
        backDM = v.findViewById(R.id.btn_backDanhMuc);

        monAnDAO = new MonAnDAO(getContext());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rc_monAnOrder.setLayoutManager(layoutManager);

        list = (ArrayList<MonAn>) monAnDAO.getAll();
        monAnOrder_adapter = new MonAnOrder_Adapter(getActivity(), list);
        rc_monAnOrder.setAdapter(monAnOrder_adapter);






        int categoryId = getArguments().getInt("CategoryId", -1);

        if (categoryId != -1) {
            // Nếu có id danh mục, thực hiện truy vấn database để lấy danh sách món ăn
            List<MonAn> danhSachMonAn = monAnDAO.getMonAnByCategoryId(categoryId);

            // Cập nhật danh sách món ăn và thông báo cho Adapter
            list.clear();
            list.addAll(danhSachMonAn);
            monAnOrder_adapter.notifyDataSetChanged();
            for (MonAn monAn : danhSachMonAn) {
                Log.d("DanhSachMonAnFragment", "MonAn: " + monAn.getTenMonAn());
            }
        }



        checkBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                XacNhanOrderFragment fragment2 = new XacNhanOrderFragment();
//                ((XacNhanOrderFragment) fragment2).setCheckValue(1);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.flContent, fragment2);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                monAnOrder_adapter.saveTemporaryListToDatabase();

            }
        });
        backDM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DanhMucOrderFragment fragment2 = new DanhMucOrderFragment();
//                ((XacNhanOrderFragment) fragment2).setCheckValue(1);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.flContent, fragment2);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                monAnOrder_adapter.saveTemporaryListToDatabase();
            }
        });

        return v;
    }

    @Override
    public void onDestroy() {
        if (monAnDAO != null) {
            monAnDAO.close();
        }
        super.onDestroy();
    }

    public void handleBackButton(int backValue) {
        Log.d("MonAnOrderFragment", "Received backValue: " + backValue);

        // Ví dụ: Hiển thị Toast
        Toast.makeText(getContext(), "Received backValue: " + backValue, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onResume() {
        super.onResume();
        handleBackButton(check);
    }

}


