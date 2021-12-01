/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entity.SanPham;
import Untils.Xjdbc;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author hoa
 */
public class SanPhamDAO implements SystemDAO<SanPham, String> {

    String SQL_Insert = "INSERT INTO SanPham (MaSP, MaLoaiSP, TenSanPham, DonGia, DonViTinh, AnhSP, MaNV, ChiTiet, TrangThai) VALUES(?,?,?,?,?,?,?,?,1)";
    String SQL_Update = "UPDATE SanPham SET MaLoaiSP =?, TenSanPham =?, DonGia =?, DonViTinh =?, AnhSP =?, MaNV =?, ChiTiet =?, TrangThai =? WHERE MaSP= ?";
    String SQL_Delete = "UPDATE SanPham SET TrangThai=0 where MaSP =?";

    String SQL_SelectPaging = "SELECT * FROM dbo.SanPham WHERE TrangThai = ? AND (MaSP LIKE ? OR TenSanPham LIKE ?) AND MaLoaiSP like ? AND DonGia BETWEEN ? AND ? ORDER BY MaNV OFFSET ?*15 ROWS  FETCH NEXT 15 ROWS ONLY";
    String SQL_SelectByKeyWord = "SELECT *  FROM dbo.SanPham WHERE TrangThai = ? AND (MaSP LIKE ? OR TenSanPham LIKE ? )";
    String SQL_SelectID = "select * from SanPham where MaSP=?";
    String SQL_SelectHoTenNV = "select MaNV,TenNV from NhanVien";
    String SQL_SelectByLoai = "select * from SanPham where MaLoaiSP like ? and TrangThai = 1";

    @Override
    public int insert(SanPham entity) {
        return Xjdbc.update(SQL_Insert,
                entity.getMaSP(),
                entity.getMaLoaiSP(),
                entity.getTenSanPham(),
                entity.getDonGia(),
                entity.getDonViTinh(),
                entity.getAnhSP(),
                entity.getMaNV(),
                entity.getChiTiet()
        );
    }

    @Override
    public int update(SanPham entity) {
        return Xjdbc.update(SQL_Update,
                entity.getMaLoaiSP(),
                entity.getTenSanPham(),
                entity.getDonGia(),
                entity.getDonViTinh(),
                entity.getAnhSP(),
                entity.getMaNV(),
                entity.getChiTiet(),
                entity.isTrangThai(),
                entity.getMaSP()
        );
    }

    @Override
    public int delete(String id) {
        return Xjdbc.update(SQL_Delete, id);
    }

    @Override
    public List<SanPham> selectBySql(String sql, Object... args) {
        List<SanPham> list = new ArrayList<>();
        try {
            ResultSet rs = Xjdbc.query(sql, args);
            while (rs.next()) {
                SanPham entity = new SanPham();
                entity.setMaSP(rs.getString("MASP"));
                entity.setMaLoaiSP(rs.getString("MaLoaiSP"));
                entity.setTenSanPham(rs.getString("TenSanPham"));
                entity.setDonGia(rs.getInt("DonGia"));
                entity.setDonViTinh(rs.getString("DonViTinh"));
                entity.setAnhSP(rs.getString("AnhSP"));
                entity.setMaNV(rs.getString("MaNV"));
                entity.setChiTiet(rs.getString("ChiTiet"));
                entity.setTrangThai(rs.getBoolean("TrangThai"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            System.out.println("" + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<SanPham> selectPaging(int Status, int pageIndex) {
        return null;
    }

    @Override
    public List<SanPham> selectByKeyWord(String keyWord, int Status) {
        return null;
    }

    @Override
    public SanPham selectById(String id) {
        List<SanPham> list = this.selectBySql(SQL_SelectID, id);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    public List<SanPham> selectPagingFull(int Status, int pageIndex, String keyWord, String loaiSP, int giaMin, int giaMax) {
        List<SanPham> list = this.selectBySql(SQL_SelectPaging, Status, "%" + keyWord + "%", "%" + keyWord + "%", "%" + loaiSP + "%", giaMin, giaMax, pageIndex);
        return list;
    }

    public List<SanPham> selectAll() {
        return selectBySql("Select*from sanpham");
    }

    public List<SanPham> selectByLoai(String maLoai) {
        return selectBySql(SQL_SelectByLoai, maLoai);
    }

    public Map<String, String> selectHoTenNV() {
        Map<String, String> map = new HashMap<>();
        try {
            ResultSet rs = Xjdbc.query(SQL_SelectHoTenNV);
            while (rs.next()) {
                String key = rs.getString(1);
                String value = rs.getString(2);
                map.putIfAbsent(key, value);
            }
            rs.getStatement().getConnection().close();
            return map;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
