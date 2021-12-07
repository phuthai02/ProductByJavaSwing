/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Untils.Xjdbc;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author doanp
 */
public class ThongKeDAO {

    String SQL_SP = "SELECT TOP 5\n"
            + "       HoaDonChiTiet.MaSP,\n"
            + "       TenSanPham,\n"
            + "	   DonGia,\n"
            + "       SUM(SoLuong) AS 'SoLuong',\n"
            + "       SUM(SoLuong) * DonGia AS 'DoanhSo'\n"
            + "FROM dbo.HoaDonChiTiet\n"
            + "    JOIN dbo.SanPham\n"
            + "        ON SanPham.MaSP = HoaDonChiTiet.MaSP  JOIN dbo.HoaDon ON HoaDon.MaHD = HoaDonChiTiet.MaHD\n"
            + "		WHERE SanPham.TrangThai = 1 AND NgayTao BETWEEN ? AND ?\n"
            + "GROUP BY HoaDonChiTiet.MaSP,\n"
            + "         TenSanPham,\n"
            + "         DonGia\n"
            + "ORDER BY DoanhSo DESC;";
    String SQL_NL = "SELECT TOP 5\n"
            + "       MaNL,\n"
            + "       TenNL,\n"
            + "       SUM(SoLuong) AS 'TongSL'\n"
            + "FROM dbo.NguyenLieu\n"
            + "WHERE Ngaymua\n"
            + "BETWEEN ? AND ? AND TrangThai = 1\n"
            + "GROUP BY MaNL,\n"
            + "         TenNL\n"
            + "ORDER BY TongSL DESC;";
    String SQL_KH = "SELECT TOP 5\n"
            + "       HoaDon.MaKH,\n"
            + "       TenKH,\n"
            + "       COUNT(DISTINCT HoaDon.MaHD) AS 'LuotSD',\n"
            + "       SUM(SoLuong * Gia) AS 'DoanhThuMangLai'\n"
            + "FROM dbo.KhachHang\n"
            + "    JOIN dbo.HoaDon\n"
            + "        ON HoaDon.MaKH = KhachHang.MaKH\n"
            + "    JOIN dbo.HoaDonChiTiet\n"
            + "        ON HoaDonChiTiet.MaHD = HoaDon.MaHD\n"
            + "WHERE KhachHang.TrangThai = 1\n"
            + "      AND NgayTao\n"
            + "      BETWEEN ? AND ?\n"
            + "GROUP BY HoaDon.MaKH,\n"
            + "         TenKH\n"
            + "ORDER BY DoanhThuMangLai DESC;";
    String SQL_NV = "SELECT TOP 5\n"
            + "       HoaDon.MaNV,\n"
            + "       TenNV,\n"
            + "       COUNT(MaHD) AS 'SoHD'\n"
            + "FROM dbo.NhanVien\n"
            + "    JOIN dbo.HoaDon\n"
            + "        ON HoaDon.MaNV = NhanVien.MaNV\n"
            + "		WHERE NhanVien.TrangThai = 1 AND NgayTao BETWEEN ? AND ?\n"
            + "GROUP BY HoaDon.MaNV,\n"
            + "         TenNV\n"
            + "ORDER BY SoHD DESC;";
    String SQL_DT = "";

    public List<Object[]> getTKSP(String begin, String end) {
        List<Object[]> list = new ArrayList<>();
        try {
            ResultSet rs = Xjdbc.query(SQL_SP, begin, end);
            while (rs.next()) {
                list.add(new Object[]{rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5)});
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Object[]> getTKNV(String begin, String end) {
        List<Object[]> list = new ArrayList<>();
        try {
            ResultSet rs = Xjdbc.query(SQL_NV, begin, end);
            while (rs.next()) {
                list.add(new Object[]{rs.getString(1), rs.getString(2), rs.getInt(3)});
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Object[]> getTKNL(String begin, String end) {
        List<Object[]> list = new ArrayList<>();
        try {
            ResultSet rs = Xjdbc.query(SQL_NL, begin, end);
            while (rs.next()) {
                list.add(new Object[]{rs.getString(1), rs.getString(2), rs.getInt(3)});
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Object[]> getTKKH(String begin, String end) {
        List<Object[]> list = new ArrayList<>();
        try {
            ResultSet rs = Xjdbc.query(SQL_KH, begin, end);
            while (rs.next()) {
                list.add(new Object[]{rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4)});
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
