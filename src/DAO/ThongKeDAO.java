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
            + "       SUM(SoLuong) AS 'SoLuong',\n"
            + "	   GiaNhap,\n"
            + "       SUM(SoLuong) * GiaNhap AS 'TongChi'\n"
            + "FROM dbo.NguyenLieu\n"
            + "WHERE NgayMua\n"
            + "      BETWEEN ? AND ?\n"
            + "      AND TrangThai = 1\n"
            + "GROUP BY MaNL,\n"
            + "         TenNL,\n"
            + "         GiaNhap\n"
            + "ORDER BY TongChi DESC;";
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
    String SQL_TCN = "SELECT TOP 1\n"
            + "       SUM(SoLuong * Gia)\n"
            + "FROM dbo.HoaDonChiTiet\n"
            + "    JOIN dbo.HoaDon\n"
            + "        ON HoaDon.MaHD = HoaDonChiTiet.MaHD\n"
            + "WHERE HoaDon.TrangThai = 1\n"
            + "      AND NgayTao\n"
            + "      BETWEEN ? AND ?\n"
            + "GROUP BY HoaDon.MaHD\n"
            + "ORDER BY SUM(SoLuong * Gia) DESC;";
    String SQL_CCN = "SELECT TOP 1\n"
            + "       SUM(SoLuong * GiaNhap)\n"
            + "FROM dbo.NguyenLieu\n"
            + "WHERE TrangThai = 1\n"
            + "      AND NgayMua\n"
            + "      BETWEEN ? AND ?\n"
            + "GROUP BY TenNL\n"
            + "ORDER BY SUM(SoLuong * GiaNhap) DESC;";
    String SQL_TT = "SELECT SUM(SoLuong * Gia)\n"
            + "FROM dbo.HoaDonChiTiet\n"
            + "    JOIN dbo.HoaDon\n"
            + "        ON HoaDon.MaHD = HoaDonChiTiet.MaHD\n"
            + "WHERE HoaDon.TrangThai = 1\n"
            + "      AND NgayTao\n"
            + "      BETWEEN ? AND ?;";
    String SQL_TC = "SELECT SUM(SoLuong * GiaNhap)\n"
            + "FROM dbo.NguyenLieu\n"
            + "WHERE TrangThai = 1\n"
            + "      AND NgayMua\n"
            + "      BETWEEN ? AND ?;";

    public Object[] getTKDT(String begin, String end) {
        try {
            Object obj[] = new Object[4];
            ResultSet rs = Xjdbc.query(SQL_TCN, begin, end);
            while (rs.next()) {
                obj[0] = rs.getInt(1);
            }
            rs = Xjdbc.query(SQL_CCN, begin, end);
            while (rs.next()) {
                obj[1] = rs.getInt(1);
            }
            rs = Xjdbc.query(SQL_TT, begin, end);
            while (rs.next()) {
                obj[2] = rs.getInt(1);
            }
            rs = Xjdbc.query(SQL_TC, begin, end);
            while (rs.next()) {
                obj[3] = rs.getInt(1);
            }
            return obj;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

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
                list.add(new Object[]{rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5)});
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
