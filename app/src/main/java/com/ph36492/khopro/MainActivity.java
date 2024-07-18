package com.ph36492.khopro;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.ph36492.khopro.DAO.NhanVienDAO;
import com.ph36492.khopro.Fragment.BanAnOrderFragment;
import com.ph36492.khopro.Fragment.ProfileFragment;
import com.ph36492.khopro.Fragment.QLBanAnFragment;
import com.ph36492.khopro.Fragment.QLDanhMucFragment;
import com.ph36492.khopro.Fragment.QLHoaDonFragment;
import com.ph36492.khopro.Fragment.QLMaGiamGiaFragment;
import com.ph36492.khopro.Fragment.QLMonanFragment;
import com.ph36492.khopro.Fragment.QLNhanVienFragment;
import com.ph36492.khopro.Fragment.ThongKeFragment;
import com.ph36492.khopro.Model.NhanVien;
import com.thinhnb.myapplication.R;

public class MainActivity extends AppCompatActivity {

    private static final int NAV_QLMONAN = R.id.nav_QLMonAn;
    private static final int NAV_QLDANHMUC = R.id.nav_QLDanhMuc;
    private static final int NAV_QLBANAN = R.id.nav_QLBanAn;
    private static final int NAV_QLMAGIAMGIA = R.id.nav_QLMaGiamGia;
    private static final int NAV_QLNHANVIEN = R.id.nav_QLNhanVien;
    private static final int NAV_THONGKE = R.id.nav_ThongKe;

    private static final int BT_HOME = R.id.home01;
    private static final int BT_HOADON = R.id.chat;
    private static final int BT_PROFILE = R.id.contact;


    DrawerLayout drawer;
    Toolbar toolbar;
    View mHeaderView;
    TextView edUser;
    Button check;

    SharedPreferences sharedPreferences;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawer = findViewById(R.id.my_drawer_layout);
        toolbar = findViewById(R.id.toolbar1);

        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setHomeAsUpIndicator(R.drawable.menu);
            ab.setDisplayHomeAsUpEnabled(true);
        }

        NavigationView nv = findViewById(R.id.nvView);

        mHeaderView = nv.getHeaderView(0);
//        edUser = mHeaderView.findViewById(R.id.txtUser);
//        Intent i = getIntent();
//        String user = i.getStringExtra("user");
        //viết hàm để lấy tên

//        ttDao = new ThuThuDAO(this);
//        ThuThu thuThu = ttDao.getID(user);
//        String username = thuThu.getHoTen();
//        edUser.setText("Chào mừng "+username+" đến với LibraPro!");

        setTitle("Trang Chủ");
        Fragment fragment = new BanAnOrderFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flContent, fragment); // R.id.flContent là ID của layout container cho fragment
        fragmentTransaction.commit();


        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String ma = sharedPreferences.getString("mma", "");

        NhanVienDAO dao = new NhanVienDAO(this);
        NhanVien nv1 = dao.getID(ma);

        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                String title = "";


                String loaitk = nv1.getLoaiTaiKhoan();

                if (!"Admin".equals(loaitk)) {
                    if (item.getItemId() == NAV_QLBANAN ||
                            item.getItemId() == NAV_QLDANHMUC ||
                            item.getItemId() == NAV_QLMONAN ||
                            item.getItemId() == NAV_QLMAGIAMGIA ||
                            item.getItemId() == NAV_QLNHANVIEN) {

                        Toast.makeText(MainActivity.this, "Bạn không có quyền truy cập vào mục này.", Toast.LENGTH_SHORT).show();
                        drawer.closeDrawers();
                        return false;
                    }
                    else if (item.getItemId() == NAV_THONGKE) {
                        setTitle("Thống Kê");
                        fragment = new ThongKeFragment();
                    }
                }else {
                    if (item.getItemId() == NAV_QLMONAN) {
                        setTitle("Quản Lý Món Ăn");
                        fragment = new QLMonanFragment();
                    } else if (item.getItemId() == NAV_QLDANHMUC) {
                        setTitle("Quản Lý Danh Mục");
                        fragment = new QLDanhMucFragment();
                    } else if (item.getItemId() == NAV_QLBANAN) {
                        setTitle("Quản Lý Bàn Ăn");
                        fragment = new QLBanAnFragment();
                    } else if (item.getItemId() == NAV_QLMAGIAMGIA) {
                        setTitle("Quản Lý Mã Giảm Giá");
                        fragment = new QLMaGiamGiaFragment();
                    } else if (item.getItemId() == NAV_QLNHANVIEN) {
                        setTitle("Quản Lý Nhân Viên");
                        fragment = new QLNhanVienFragment();
                    } else if (item.getItemId() == NAV_THONGKE) {
                        setTitle("Thống Kê");
                        fragment = new ThongKeFragment();
                    }
                }
//

                if (fragment != null) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.flContent, fragment); // R.id.fragment_container là ID của layout container cho fragment
                    fragmentTransaction.commit();
                }

                drawer.closeDrawers();
                return false;
            }
        });



        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Xử lý sự kiện khi một mục được chọn
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;

           if (item.getItemId()==BT_HOME){
                    selectedFragment = new BanAnOrderFragment();
                    setTitle("Trang Chủ");
                    }
                else if (item.getItemId()==BT_HOADON) {
               selectedFragment = new QLHoaDonFragment();
                    setTitle("Hóa Đơn");
                }else if (item.getItemId()==BT_PROFILE) {
                    setTitle("Thông Tin Cá Nhân");
               selectedFragment = new ProfileFragment();
           }

            // Thay thế Fragment hiện tại bằng Fragment được chọn
            getSupportFragmentManager().beginTransaction().replace(R.id.flContent,
                    selectedFragment).commit();

            return true;
        });




    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==android.R.id.home)
            drawer.openDrawer(GravityCompat.START);
        return super.onOptionsItemSelected(item);
    }
}