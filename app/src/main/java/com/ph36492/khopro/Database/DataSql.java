package com.ph36492.khopro.Database;

public class DataSql {
    public static final String INSERT_TB_NHANVIEN = "INSERT INTO NhanVien(maNhanVien, hoTen,tuoi,gioiTinh,soDienThoai, matKhau, loaiTaiKhoan) VALUES" +
            "('admin', 'Nguyễn Trí Toán',48,'Nam','0862613348', '123456789', 'Admin')," +
            "('nhanvien1', 'Nguyễn Trí Toán',19,'Nam','0862613348', '123456789', 'NhanVien')," +
            "('nhanvien2', 'Nguyễn Hiếu ',19,'Nam','0862613348', '123456789', 'NhanVien'),"+
            "('nhanvien3','Nguyễn Thị Trinh',20,'Nữ','0862613348','123456789','NhanVien')";

    public static final String INSERT_TB_DANHMUC = "INSERT INTO DanhMuc(tenDanhMuc) VALUES" +
            "('Món Chính')," +
            "('Món Phụ')," +
            "('Đồ Uống'),"+
            "('Thêm Chả')";

    public static final String INSERT_TB_MONAN = "INSERT INTO MonAn(tenMonAn, id_DanhMuc, id_GiamGia, giaTien) VALUES" +
            "('Bún Chả Miếng', 1, NULL, 45000.0)," +
            "('Bún Chả Băm', 1, 1, 40000.0)," +
            "('Bún Chả Nem', 1, NULL, 43000.0)," +
            "('Bún Chả Truyền Thống', 1, NULL, 50000.0)," +
            "('Bún Chả Đặc Biệt', 1, NULL, 65000.0)," +
            "('Bún Chả Chan', 1, 1, 35000.0)," +
            "('Trà Ngô', 3, NULL, 15000.0)," +
            "('Trà Dứa', 3, 2, 17000.0)," +
            "('Trà Xanh', 3, 1, 10000.0)," +
            "('Nước Suối', 3, NULL, 10000.0)," +
            "('Nem Hải Sản', 2, NULL, 25000.0)," +
            "('Nem Truyền Thống', 2, NULL, 10000.0)," +
            "('Bánh Tôm', 2, 2, 25000.0)," +
            "('Chả Miếng', 4, NULL, 8000.0)," +
            "('Chả Băm', 4, NULL, 8000.0)," +
            "('Coca cola',3,NULL,15000.0)";

    public static final String INSERT_TB_BANAN = "INSERT INTO BanAn(soBan) VALUES" +
            "(1)," +
            "(2)," +
            "(3)," +
            "(4)," +
            "(5)," +
            "(6)," +
            "(7)," +
            "(8)," +
            "(9)," +
            "(10)";
    public static final String INSERT_TB_GIAMGIA = "INSERT INTO GiamGia(maGiamGia, phanTramGiam, soLuotDung) VALUES" +
            "('GG1', 10, 4)," +
            "('GG2', 20, 3)," +
            "('GG3', 15, 1),"+
            "('GG4',30, 2)";
    public static final String INSERT_TB_HOADON = "INSERT INTO HOADON(id_HoaDon, id_NhanVien, soBan, ngayGio, kieuThanhToan, tongTien) VALUES " +
            "(1, 'admin', 1, '2023-01-01 12:00:00', 'Tiền Mặt', 50000.0)," +
            "(2, 'nhanvien1', 2, '2023-01-02 14:30:00', 'Chuyển Khoản', 100000.0)," +
            "(3, 'nhanvien2', 3, '2023-01-03 18:45:00', 'Thẻ Ngân Hàng', 60000.0)," +
            "(4, 'nhanvien3', 1, '2023-01-04 20:00:00', 'Chuyển Khoản', 15000.0)";
    public static final String INSERT_TB_CHITIETHOADON = "INSERT INTO ChiTietHoaDon(id_HoaDon, tenMonAn, soLuong, giaTien, phanTramGG, tongTien, ngay, thang) VALUES" +
            "(1, 'Bún Chả Băm', 2, 50000.0, 10, 90000, '01/12/2023', '12')," +
            "(2, 'Nem Hải Sản', 1, 100000.0, 20, 120000, '02/12/2023', '12')," +
            "(3, 'Bún Chả Chan', 3, 60000.0, 0, 180000, '06/11/2023', '11')," +
            "(3, 'Bún Chả Băm', 3, 60000.0, 0, 180000, '06/11/2023', '11')," +
            "(5, 'Nem Hải Sản', 3, 60000.0, 0, 180000, '06/11/2023', '11')," +
            "(3, 'Coca cola', 2, 15000.0, 10, 30000, '06/11/2023', '11')";




}

