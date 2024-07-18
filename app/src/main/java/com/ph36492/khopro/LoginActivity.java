package com.ph36492.khopro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.ph36492.khopro.DAO.NhanVienDAO;
import com.thinhnb.myapplication.R;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    EditText edmaNhanVien, edmatKhau;
    CheckBox chk_forgotPass;
    String strmanv, strPass;
    NhanVienDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        chk_forgotPass = findViewById(R.id.chk_forgotPass);
        btnLogin = findViewById(R.id.btndangnhap_login);
        edmaNhanVien = findViewById(R.id.ed_manguoidung_login);
        edmatKhau = findViewById(R.id.ed_password_login);
        dao = new NhanVienDAO(this);

        SharedPreferences pref = getSharedPreferences("USER_FILE",MODE_PRIVATE);
        String user = pref.getString("USERNAME","");
        String pas = pref.getString("PASSWORD","");
        Boolean rem = pref.getBoolean("REMEMBER",false);

        edmaNhanVien.setText(user);
        edmatKhau.setText(pas);
        chk_forgotPass.setChecked(rem);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });

    }

    public void rememberUser(String u, String p, boolean status) {
        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        if (!status) {
            //xóa tình trạng lưu trữ trước đó
            edit.clear();
        } else {
            //lưu dữ liệu
            edit.putString("USERNAME", u);
            edit.putString("PASSWORD", p);
            edit.putBoolean("REMEMBER", status);
            edit.apply();
        }
        //lưu lại toàn bộ
        edit.commit();
    }

    public void checkLogin() {
        strmanv = edmaNhanVien.getText().toString();
        strPass = edmatKhau.getText().toString();
        if (strmanv.isEmpty() || strPass.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Mã người dùng và mật khẩu không được bỏ trống", Toast.LENGTH_SHORT).show();
        } if(strPass.length()<8){
            edmatKhau.setError("Mật khẩu không đủ 8 ký tự");
        }else {
            if (dao.checkLogin(strmanv, strPass) > 0) {
                Toast.makeText(getApplicationContext(), "Login thành công", Toast.LENGTH_SHORT).show();
                rememberUser(strmanv, strPass, chk_forgotPass.isChecked());
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra("user",strmanv);
                saveMmaToSharedPreferences(strmanv);
                startActivity(i);
                finish();
            } else {
                Toast.makeText(this, "Tên đăng nhập và mật khẩu không đúng", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void saveMmaToSharedPreferences(String mma) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("mma", mma);
        editor.apply();
    }



}
