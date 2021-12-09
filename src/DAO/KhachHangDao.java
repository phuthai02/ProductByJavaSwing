/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entity.KhachHang;
import Untils.Xjdbc;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class KhachHangDao implements SystemDAO<KhachHang, Integer> {

    String SQL_Insert = "insert into KhachHang values(?,?,?,?,?,?,1)";
    String SQL_Update = "update KhachHang set TenKH=?, SDT=?, Email=?, NgaySinh=?,GioiTinh=?,TrangThai=1 WHERE MaKH =?";
    String SQL_Delete = "update KhachHang set TrangThai=0 where MaKH=?";
    String SQL_SelectPaging = "SELECT * FROM dbo.KhachHang WHERE TrangThai = ?  AND Manv like ? ORDER BY MaKH OFFSET ?*15 ROWS  FETCH NEXT 15 ROWS ONLY";
    String SQL_SelectID = "select * from KhachHang where MaKH=?";
    String SQL_SelectHoTenNV = "select MaNV,TenNV from NhanVien";
    String SQL_SelectSDT = "select * from KhachHang where SDT like ?";
    String SQL_InsertNoID = "insert into  KhachHang(TenKH,SDT,Email,NgaySinh,GioiTinh,MaNV,TrangThai) values(?,?,?,?,?,?,1)";
    String SQL_SelectHD = "SELECT HoaDon.MaHD,\n"
            + "       NgayTao,\n"
            + "       MaSKKM,\n"
            + "       MaBan,\n"
            + "       MaNV,\n"
            + "       SUM(SoLuong * Gia)\n"
            + "FROM dbo.HoaDon\n"
            + "    JOIN dbo.HoaDonChiTiet\n"
            + "        ON HoaDonChiTiet.MaHD = HoaDon.MaHD\n"
            + "		WHERE MaKH = ?\n"
            + "GROUP BY HoaDon.MaHD,\n"
            + "         NgayTao,\n"
            + "         MaSKKM,\n"
            + "         MaBan,\n"
            + "         MaNV\n"
            + "ORDER BY HoaDon.MaHD DESC;";
    String SQL_SelectSN = "select * from KhachHang where NgaySinh like ?";

    @Override
    public int insert(KhachHang entity) {
        return Xjdbc.update(SQL_Insert,
                entity.getTenKH(),
                entity.getSDT(),
                entity.getEmail(),
                entity.getNgaysinh(),
                entity.isGioiTinh(),
                entity.getMaNV());
    }

    public int insertNoID(KhachHang entity) {
        return Xjdbc.update(SQL_InsertNoID,
                entity.getTenKH(),
                entity.getSDT(),
                entity.getEmail(),
                entity.getNgaysinh(),
                entity.isGioiTinh(),
                entity.getMaNV());
    }

    @Override
    public int update(KhachHang entity) {
        return Xjdbc.update(SQL_Update,
                entity.getTenKH(),
                entity.getSDT(),
                entity.getEmail(),
                entity.getNgaysinh(),
                entity.isGioiTinh(),
                entity.getMaKH());
    }

    @Override
    public int delete(Integer id) {
        return Xjdbc.update(SQL_Delete, id);
    }

    @Override
    public List<KhachHang> selectBySql(String sql, Object... args) {
        List<KhachHang> list = new ArrayList<>();
        try {
            ResultSet rs = Xjdbc.query(sql, args);
            while (rs.next()) {
                KhachHang entity = new KhachHang();
                entity.setMaKH(rs.getInt(1));
                entity.setTenKH(rs.getString(2));
                entity.setSDT(rs.getString(3));
                entity.setEmail(rs.getString(4));
                entity.setNgaysinh(rs.getDate(5));
                entity.setGioiTinh(rs.getBoolean(6));
                entity.setMaNV(rs.getString(7));
                entity.setTrangThai(rs.getBoolean(8));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<KhachHang> selectPaging(int Status, int pageIndex) {
        return null;
    }

    @Override
    public List<KhachHang> selectByKeyWord(Integer keyWord, int Status) {
        return null;

    }

    @Override
    public KhachHang selectById(Integer id) {
        List<KhachHang> list = this.selectBySql(SQL_SelectID, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public List<KhachHang> selectBySN(String sinhNhat) {
        return selectBySql(SQL_SelectSN, "%" + sinhNhat + "%");
    }

    public KhachHang selectSDT(String SDT) {
        return selectBySql(SQL_SelectSDT, SDT).isEmpty() ? null : selectBySql(SQL_SelectSDT, SDT).get(0);
    }

    public List<KhachHang> selectByAll() {
        return this.selectBySql("Select *from KhachHang");
    }

    public List<KhachHang> selectDSHD(Integer makh) {
        return this.selectBySql(SQL_SelectHD, makh);
    }

    public List<KhachHang> selectByKeyWord(String keyWord, int status, int pageIndex) {
        String sql = "SELECT*FROM KHACHHANG WHERE TrangThai =? AND(MaKH LIKE ? OR TenKH LIKE ? OR SDT LIKE ?) ORDER BY MaKH OFFSET ?*15 ROWS  FETCH NEXT 15 ROWS ONLY";
        return this.selectBySql(sql, status, "%" + keyWord + "%", "%" + keyWord + "%", "%" + keyWord + "%", pageIndex);
    }

    public List<KhachHang> selectPaging(int Status, int pageIndex, String manv) {
        List<KhachHang> list = this.selectBySql(SQL_SelectPaging, Status, "%" + manv + "%", pageIndex);
        return list;
    }

    public List<Object[]> selectByHD(int makh) {
        List<Object[]> obj = new ArrayList<>();
        try {
            ResultSet rs = Xjdbc.query(SQL_SelectHD, makh);
            while (rs.next()) {
                obj.add(new Object[]{
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getInt(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getInt(6)
                });
            }
        } catch (Exception e) {
        }
        return obj;
    }
}
