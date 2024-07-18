package com.ph36492.khopro.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ph36492.khopro.Adapter.NhanVien_Adapter;
import com.ph36492.khopro.DAO.NhanVienDAO;
import com.ph36492.khopro.Model.NhanVien;
import com.thinhnb.myapplication.R;

import java.util.ArrayList;
import java.util.List;


public class QLNhanVienFragment extends Fragment {
    RecyclerView recyclerView;
    NhanVien_Adapter nhanVienAdapter;
    NhanVienDAO dao;
    ImageView img_add_nhanVien;
     ArrayList<NhanVien> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_q_l_nhan_vien, container, false);
        dao = new NhanVienDAO(getContext());

        recyclerView = view.findViewById(R.id.rc_quanLyNhanVien);
       LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
       recyclerView.setLayoutManager(layoutManager);
       list = (ArrayList<NhanVien>)dao.getAll();
        nhanVienAdapter = new NhanVien_Adapter(getActivity(),this,list);
        recyclerView.setAdapter(nhanVienAdapter);

        img_add_nhanVien = view.findViewById(R.id.img_add_nhanVien);
        img_add_nhanVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo một hộp thoại để nhập thông tin nhân viên
                update_nv();
         }
        });

        return view;
    }
    private List<NhanVien> getNhanVienData() {
        // Gọi hàm lấy dữ liệu từ cơ sở dữ liệu thông qua đối tượng DAO
        dao = new NhanVienDAO(getContext());
        return dao.getAll(); // Giả sử nhanVienDAO đã được khởi tạo và có phương thức getAll()
    }

   private void update_nv(){
       AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
       LayoutInflater inflater = getLayoutInflater();
       View dialogView = inflater.inflate(R.layout.add_nhanvien, null);
       builder.setView(dialogView);
       Dialog dialog = builder.create();
       dialog.show();

       // Ánh xạ các thành phần của hộp thoại
       EditText edmaNV = dialogView.findViewById(R.id.ed_maNhanVien);
       EditText edtHoTen = dialogView.findViewById(R.id.edhoten_nv);
       EditText edTuoi = dialogView.findViewById(R.id.ed_tuoiNhanVien);
       RadioButton rdoNam = dialogView.findViewById(R.id.rdo_Nam);
       RadioButton rdoNu = dialogView.findViewById(R.id.rdo_Nu);
       EditText edsoDienThoai = dialogView.findViewById(R.id.ed_sdtNhanVien);
       EditText edmatKhau = dialogView.findViewById(R.id.ed_matKhau_nv);
       Button btnaddnv = dialogView.findViewById(R.id.btn_themNhanVien);
       Button btnhuythemnv = dialogView.findViewById(R.id.btn_huyThemNhanVien);
       builder.setView(dialogView);

       btnhuythemnv.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
    dialog.dismiss();
           }
       });
       btnaddnv.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String maNhanVien = edmaNV.getText().toString().trim();
               String hoTen = edtHoTen.getText().toString().trim();
               String tuoi = edTuoi.getText().toString().trim();
               String gioiTinh = rdoNam.isChecked() ? "Nam" : (rdoNu.isChecked() ? "Nữ" : "");
               String sodienthoai = edsoDienThoai.getText().toString().trim();
               String matkhau = edmatKhau.getText().toString().trim();

               // Kiểm tra xem các trường dữ liệu có hợp lệ không
               if (maNhanVien.isEmpty()) {
                   edmaNV.setError("Mã nhân viên không được để trống");
               }  if (hoTen.isEmpty()) {
                   edtHoTen.setError("Họ tên không được để trống");
               }if (!TextUtils.isDigitsOnly(hoTen)) {
                                edtHoTen.setError("Họ tên không thể là số");
                            }if (TextUtils.isEmpty(tuoi)) {
                   // Chuỗi rỗng
                   edTuoi.setError("Tuổi đang trống");
               } else {
                   try {
                       // Chuyển đổi chuỗi thành số
                       double number = Double.parseDouble(tuoi);

                       // Kiểm tra xem số có là số âm không
                       if (number < 0) {
                           edTuoi.setError("Tuổi đang là số âm");
                       } else {
//                           Toast.makeText(getApplicationContext(), "Chuỗi không phải là số âm", Toast.LENGTH_SHORT).show();
                       }
                   } catch (NumberFormatException e) {
                       // Nếu có ngoại lệ, đây không phải là số
                       edTuoi.setError("Tuổi đang không là số");
                   }
               } if (gioiTinh.isEmpty()) {
                   // Đặt lỗi cho RadioButton hoặc xử lý theo cách khác tùy thuộc vào ý định của bạn
               }  if (sodienthoai.isEmpty()) {
                   edsoDienThoai.setError("Số điện thoại không được để trống");
               } if (sodienthoai.length()<10){
                   edsoDienThoai.setError("Số điện thoại không đủ 10 số");
               }if (matkhau.isEmpty()) {
                   edmatKhau.setError("Mật khẩu không được để trống");
               } if (matkhau.length() < 8) {
                   // Chuỗi có đủ 8 ký tự
                   edmatKhau.setError("Mật khẩu không đủ 8 ký tự");
               }else {
                   Integer tuoiint = Integer.parseInt(tuoi);
                   NhanVien nhanVien = new NhanVien(maNhanVien, hoTen, tuoiint, gioiTinh, sodienthoai, matkhau);
                   long result = dao.insert(nhanVien);

                   // Đặt các giá trị cho các trường khác...

                   // Gọi hàm thêm mới từ DAO
                   if (result > 0) {
                       // Thêm mới thành công, làm gì đó nếu cần
                       Toast.makeText(getActivity(), "Thêm mới thành công", Toast.LENGTH_SHORT).show();
                       // Cập nhật danh sách hiển thị nếu cần
                       capnhaplist();
                       dialog.dismiss();
                   } else {
                       edmaNV.setError("lỗi ở đây");
                       // Thêm mới thất bại, làm gì đó nếu cần
                       Toast.makeText(getActivity(), "Mã đăng nhập đã bị trùng với nhân viên cũ", Toast.LENGTH_SHORT).show();
                   }
               }
           }
       });


   }
    void capnhaplist(){
        list.clear();
        list.addAll(dao.getAll());
        nhanVienAdapter.notifyDataSetChanged();
    }
}
