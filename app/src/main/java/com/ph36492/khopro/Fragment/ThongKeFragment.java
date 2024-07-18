package com.ph36492.khopro.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ph36492.khopro.Adapter.ThongKeAdapter;
import com.ph36492.khopro.DAO.ChiTietHoaDonDAO;
import com.ph36492.khopro.Model.MonAnDaBan;
import com.thinhnb.myapplication.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ThongKeFragment extends Fragment {

    TextView tv_chonngay, tv_thangnay, tv_thongDoanhThu,tv_homnay;
    Button btn_doanhThu;
    RecyclerView lv_doanhThu;
    private String selectedDate = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_thong_ke, container, false);

        tv_chonngay = v.findViewById(R.id.tv_chonngay);
        tv_homnay = v.findViewById(R.id.tv_HomNay);
        tv_thangnay = v.findViewById(R.id.tv_thangnay);
        tv_thongDoanhThu = v.findViewById(R.id.tv_tongDoanhThu);
        lv_doanhThu = v.findViewById(R.id.lv_doanhThu);

        lv_doanhThu.setLayoutManager(new LinearLayoutManager(getContext()));
        ChiTietHoaDonDAO dao = new ChiTietHoaDonDAO(getContext());

        String ngayHomNay = getNgay();
        List<MonAnDaBan> doanhThuList = dao.getStatisticsByDate(ngayHomNay);
        ThongKeAdapter doanhThuAdapter = new ThongKeAdapter(doanhThuList);
        doanhThuAdapter.updateList(doanhThuList);
        lv_doanhThu.setAdapter(doanhThuAdapter);


        int tongDoanhThu = calculateTotalRevenue(doanhThuList);

        tv_thongDoanhThu.setText(formatMoney(tongDoanhThu) + " VNĐ");

        Calendar calendar = Calendar.getInstance();

        tv_homnay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_homnay.setBackgroundResource(R.drawable.custom_monanoder);
                tv_chonngay.setBackgroundResource(R.drawable.custtom_khung_lv);
                tv_thangnay.setBackgroundResource(R.drawable.custtom_khung_lv);
                String ngayHomNay = getNgay();
                List<MonAnDaBan> doanhThuList = dao.getStatisticsByDate(ngayHomNay);
                ThongKeAdapter doanhThuAdapter = new ThongKeAdapter(doanhThuList);
                lv_doanhThu.setAdapter(doanhThuAdapter);
                doanhThuAdapter.updateList(doanhThuList);
                updateTotalRevenueAndAdapter(doanhThuList);
            }
        });

        tv_thangnay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tv_homnay.setBackgroundResource(R.drawable.custtom_khung_lv);
                tv_chonngay.setBackgroundResource(R.drawable.custtom_khung_lv);
                tv_thangnay.setBackgroundResource(R.drawable.custom_monanoder);
                List<MonAnDaBan> doanhThuList = dao.getStatisticsByMonth();
                ThongKeAdapter doanhThuAdapter = new ThongKeAdapter(doanhThuList);
                lv_doanhThu.setAdapter(doanhThuAdapter);
                doanhThuAdapter.notifyDataSetChanged();
                doanhThuAdapter.updateList(doanhThuList);
                updateTotalRevenueAndAdapter(doanhThuList);

            }
        });

        tv_chonngay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                showDatePickerDialog(tv_chonngay, calendar);
                tv_homnay.setBackgroundResource(R.drawable.custom_monanoder);
                tv_chonngay.setBackgroundResource(R.drawable.custtom_khung_lv);
                tv_thangnay.setBackgroundResource(R.drawable.custtom_khung_lv);
                updateTotalRevenueAndAdapter(doanhThuList);

            }
        });

        return v;
    }


    private String getNgay() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return dateFormat.format(calendar.getTime());
    }
    private int calculateTotalRevenue(List<MonAnDaBan> doanhThuList) {
        int totalRevenue = 0;
        for (MonAnDaBan monAnDaBan : doanhThuList) {
            totalRevenue += monAnDaBan.getGiaTien() * monAnDaBan.getTongSoLuong();
        }
        return totalRevenue;
    }
    private String formatMoney(int money) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        return decimalFormat.format(money);
    }
    private void showDatePickerDialog(TextView textView, Calendar calendar) {
        DatePickerDialog dialog = new DatePickerDialog(
                getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String ngay = (dayOfMonth < 10) ? "0" + dayOfMonth : String.valueOf(dayOfMonth);
                        String thang = ((month + 1) < 10) ? "0" + (month + 1) : String.valueOf(month + 1);
                        String ngayChon = ngay + "/" + thang + "/" + year;
                        textView.setText(ngayChon);

                        // Gọi lại hàm xử lý khi ngày thay đổi
                        handleDateSelected(ngayChon);
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        dialog.show();
    }

    private void handleDateSelected(String selectedDate) {
        ChiTietHoaDonDAO dao = new ChiTietHoaDonDAO(getContext());
        List<MonAnDaBan> doanhThuList = dao.getStatisticsByDate(selectedDate);
        ThongKeAdapter doanhThuAdapter = new ThongKeAdapter(doanhThuList);
        lv_doanhThu.setAdapter(doanhThuAdapter);
        doanhThuAdapter.updateList(doanhThuList);
        int tongDoanhThu = calculateTotalRevenue(doanhThuList);
        tv_thongDoanhThu.setText(formatMoney(tongDoanhThu) + " VNĐ");
        doanhThuAdapter.notifyDataSetChanged();
    }
    private void updateTotalRevenueAndAdapter(List<MonAnDaBan> doanhThuList) {
        int tongDoanhThu = calculateTotalRevenue(doanhThuList);
        tv_thongDoanhThu.setText(formatMoney(tongDoanhThu) + " VNĐ");

        ThongKeAdapter doanhThuAdapter = new ThongKeAdapter(doanhThuList);
        lv_doanhThu.setAdapter(doanhThuAdapter);
        doanhThuAdapter.updateList(doanhThuList);
        doanhThuAdapter.notifyDataSetChanged();
    }



}
