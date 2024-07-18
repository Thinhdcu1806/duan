package com.ph36492.khopro.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.ph36492.khopro.DAO.BoNhoTamThoiDAO;
import com.ph36492.khopro.DAO.ChiTietHoaDonDAO;
import com.ph36492.khopro.DAO.GiamGiaDAO;
import com.ph36492.khopro.DAO.HoaDonDAO;
import com.ph36492.khopro.Model.BoNhoTamThoi;
import com.ph36492.khopro.Model.ChiTietHoaDon;
import com.ph36492.khopro.Model.HoaDon;
import com.thinhnb.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ThanhToanFragment extends Fragment {

    Button btn_thanhToanTienMat , btn_thanhToanChuyenKhoan , btn_thanhToanTheNganHang,btn_quayLaiMonAn , btn_tiepTuc;
    EditText ed_maGiamGiaThanhToan , ed_ghiChu;
    String phuongThuc = "";
    int phanTramGiam;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_thanhtoan, container, false);

        btn_thanhToanTienMat = v.findViewById(R.id.btn_thanhToanTienMat);
        btn_thanhToanChuyenKhoan = v.findViewById(R.id.btn_thanhToanChuyenKhoan);
        btn_thanhToanTheNganHang = v.findViewById(R.id.btn_thanhToanTheNganHang);
        btn_tiepTuc = v.findViewById(R.id.btn_tiepTuc);
        ed_maGiamGiaThanhToan = v.findViewById(R.id.ed_maGiamGiaThanhToan);
        ed_ghiChu = v.findViewById(R.id.ed_giChu);

        btn_thanhToanChuyenKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phuongThuc = "Chuyển Khoản";
                btn_thanhToanTheNganHang.setBackgroundResource(R.drawable.button_custom);
                btn_thanhToanTienMat.setBackgroundResource(R.drawable.button_custom);
                btn_thanhToanChuyenKhoan.setBackgroundResource(R.drawable.custom_monanoder);
            }
        });
        btn_thanhToanTienMat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phuongThuc = "Tiền Mặt";
                btn_thanhToanTheNganHang.setBackgroundResource(R.drawable.button_custom);
                btn_thanhToanTienMat.setBackgroundResource(R.drawable.custom_monanoder);
                btn_thanhToanChuyenKhoan.setBackgroundResource(R.drawable.button_custom);
            }
        });
        btn_thanhToanTheNganHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phuongThuc = "Thẻ Ngân Hàng";
                btn_thanhToanTheNganHang.setBackgroundResource(R.drawable.custom_monanoder);
                btn_thanhToanTienMat.setBackgroundResource(R.drawable.button_custom);
                btn_thanhToanChuyenKhoan.setBackgroundResource(R.drawable.button_custom);
            }
        });


        btn_tiepTuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phuongThuc.equals("")){
                    Toast.makeText(getContext(), "Vui Lòng Chọn Phương Thức Thanh Toán", Toast.LENGTH_SHORT).show();
                }else {
                    String maGG = ed_maGiamGiaThanhToan.getText().toString().trim();
                    String ghiChu = ed_ghiChu.getText().toString().trim();


                    String manv = readMmaFromSharedPreferences();

                    int soBan = getSoBanFromSharedPreferences();

                    String NgayGio = getNgayGio();
                    String ngay = getNgay();
                    String thang = getThang();

                    saveGhiChuToSharedPreferences(ghiChu);

                    GiamGiaDAO ggDao = new GiamGiaDAO(getContext());
                    boolean maGGTonTai = ggDao.kiemTraMaGiamGiaTonTai(maGG);
                    int tongTien = getTongTienToShareFrence();
                    int TongTienafter = 0;


                    if (maGGTonTai||maGG.equals("")) {
                         phanTramGiam  = ggDao.layPhanTramGiamTuMaGiamGia(maGG);
                        ggDao.giamSoLuotDung(maGG);
                         TongTienafter=tongTien- (tongTien * phanTramGiam) / 100;


                    }else {

                        ed_maGiamGiaThanhToan.setError("Mã Giảm Giá Không Đúng!");
                        return;
                    }



                    HoaDon hoaDon = new HoaDon();
                    hoaDon.setId_NhanVien(manv);
                    hoaDon.setSoBan(soBan);
                    hoaDon.setNgayGio(NgayGio);
                    hoaDon.setKieuThanhToan(phuongThuc);
                    hoaDon.setTongTien(TongTienafter);

                    // Insert HoaDon into the database
                    HoaDonDAO hoaDonDAO = new HoaDonDAO(getContext());
                    long insertedRowId = hoaDonDAO.Insert(hoaDon);

                    // Check if the insertion was successful
                    if (insertedRowId != -1) {
                        BoNhoTamThoiDAO boNhoTamThoiDAO = new BoNhoTamThoiDAO(getContext());
                        List<BoNhoTamThoi> listBoNhoTamThoi = boNhoTamThoiDAO.layDanhSachBoNhoTamThoi();
                        // Truy xuất id_HoaDon của bản ghi mới được chèn
                        int idHoaDon = hoaDonDAO.getIdHoaDonByRowId(insertedRowId);
                        ChiTietHoaDonDAO chiTietHoaDonDAO = new ChiTietHoaDonDAO(getContext());
                        for (BoNhoTamThoi boNhoTamThoi : listBoNhoTamThoi) {
                            ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon();
                            chiTietHoaDon.setId_HoaDon(idHoaDon); // Set id_HoaDon
                            chiTietHoaDon.setTenMonAn(boNhoTamThoi.getTenMonAn());
                            chiTietHoaDon.setSoLuong(boNhoTamThoi.getSoLuong());
                            chiTietHoaDon.setGiaTien(boNhoTamThoi.getThanhTien() / boNhoTamThoi.getSoLuong());
                            chiTietHoaDon.setPhanTramGG(phanTramGiam);
                            chiTietHoaDon.setNgay(ngay);
                            chiTietHoaDon.setThang(thang);
                            // Insert vào bảng ChiTietHoaDon
                            long insertedChiTiet = chiTietHoaDonDAO.Insert(chiTietHoaDon);

                            if (insertedChiTiet != -1) {
                                boNhoTamThoiDAO.clearAll();

                                saveDataToSharedPreferences(getContext(), "maHoaDon", String.valueOf(idHoaDon));
                                String gio = getGio();
                                saveGioToSharedPreferences(gio);
                                saveNgayToSharedPreferences(ngay);

                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                                HoaDonThanhToanFragment anotherFragment = new HoaDonThanhToanFragment();

                                fragmentTransaction.replace(R.id.flContent, anotherFragment);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();

                            }
                        }
                    } else {
                        Toast.makeText(getContext(), "Không thể tạo hóa đơn", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });
        return v;
    }
    private int getSoBanFromSharedPreferences() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("SoBanSave", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("SoBan", 0); // Giá trị mặc định là 0 nếu không tìm thấy
    }
    private String readMmaFromSharedPreferences() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("mma", "");
    }
    private String getNgay() {
        // Lấy thời gian hiện tại
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        String ngay = dateFormat.format(calendar.getTime());

        return ngay ;
    }
    private String getThang(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM", Locale.getDefault());

        String thang = dateFormat.format(calendar.getTime());
        return thang;
    }
    private String getGio(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        String gio = timeFormat.format(calendar.getTime());
        return gio;

    }
    private String getNgayGio() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        String ngay = dateFormat.format(calendar.getTime());
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        String gio = timeFormat.format(calendar.getTime());
        return ngay +" "+gio;
    }
    private void saveGhiChuToSharedPreferences(String GhiChu) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("ghiChuSave", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Lưu giá trị số bàn vào SharedPreferences
        editor.putString("GhiChu", GhiChu);
        editor.apply();
    }
    private int getTongTienToShareFrence() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("TongTien", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("TongTien",0); // Giá trị mặc định là 0 nếu không tìm thấy
    }
    private void saveDataToSharedPreferences(Context context, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LuuMaHoaDon", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }
    private void saveNgayToSharedPreferences(String ngay) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("NgaySave", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Ngay", ngay);
        editor.apply();
    }
    private void saveGioToSharedPreferences(String gio) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("GioSave", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Gio", gio);
        editor.apply();
    }



}
