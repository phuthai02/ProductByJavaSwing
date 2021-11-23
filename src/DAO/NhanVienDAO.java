/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entity.NhanVien;
import Untils.Xjdbc;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author doanp
 */
public class NhanVienDAO implements SystemDAO<NhanVien, String> {

    String SQL_Insert = "insert into NhanVien values(?,?,?,?,?,?,?,?,?,?,?,1)";
    String SQL_Update = "update NhanVien set TenNV=?, MatKhau=?, DiaChi=?, SDT=?, Email=?, NgaySinh=?, GioiTinh=?, AnhNV=?,MauNen=?, VaiTro=?, TrangThai=1 where MaNV=?";
    String SQL_Delete = "update NhanVien set TrangThai=0 where MaNV=?";
    String SQL_SelectPaging = "SELECT * FROM dbo.NhanVien WHERE TrangThai = ?  AND (MaNV LIKE ? OR TenNV LIKE ? OR DiaChi LIKE ? OR SDT LIKE ?) AND GioiTinh like ? ORDER BY MaNV OFFSET ?*15 ROWS  FETCH NEXT 15 ROWS ONLY";
    String SQL_SelectID = "select * from NhanVien where MaNV=?";
    String SQL_SelectHoTenNV = "select MaNV,TenNV from NhanVien";

    @Override
    public int insert(NhanVien entity) {
        return Xjdbc.update(SQL_Insert,
                entity.getMaNV(),
                entity.getTenNV(),
                entity.getMatKhau(),
                entity.getDiaChi(),
                entity.getSDT(),
                entity.getEmail(),
                entity.getNgaySinh(),
                entity.isGioiTinh(),
                entity.getAnhNV(),
                entity.getMauNen(),
                entity.isVaiTro());
    }

    @Override
    public int update(NhanVien entity) {
        return Xjdbc.update(SQL_Update,
                entity.getTenNV(),
                entity.getMatKhau(),
                entity.getDiaChi(),
                entity.getSDT(),
                entity.getEmail(),
                entity.getNgaySinh(),
                entity.isGioiTinh(),
                entity.getAnhNV(),
                entity.getMauNen(),
                entity.isVaiTro(),
                entity.getMaNV());

    }

    @Override
    public int delete(String id) {
        return Xjdbc.update(SQL_Delete, id);
    }

    @Override
    public List<NhanVien> selectBySql(String sql, Object... args) {
        List<NhanVien> list = new ArrayList<>();
        try {
            ResultSet rs = Xjdbc.query(sql, args);
            while (rs.next()) {
                list.add(new NhanVien(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getBoolean(8),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getBoolean(11),
                        rs.getBoolean(12)));
            }

            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public NhanVien selectById(String id) {
        List<NhanVien> list = this.selectBySql(SQL_SelectID, id);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    @Override
    public List<NhanVien> selectByKeyWord(String keyWord, int Status) {
        return null;
    }

    public List<NhanVien> selectPagingFull(int Status, int pageIndex, String keyWord, String gioiTinh) {
        List<NhanVien> list = this.selectBySql(SQL_SelectPaging, Status, "%" + keyWord + "%", "%" + keyWord + "%", "%" + keyWord + "%", "%" + keyWord + "%", "%" + gioiTinh + "%", pageIndex);
        return list;
    }

    public Map<String, String> selectHoTenNV() {
        Map<String, String> map = new HashMap<>();
        try {
            ResultSet rs = Xjdbc.query(SQL_SelectHoTenNV);
            while (rs.next()) {
                String key = rs.getString(1);
                String value = rs.getString(2);
                map.put(key, value);
            }
            rs.getStatement().getConnection().close();
            return map;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<NhanVien> selectPaging(int Status, int pageIndex) {
        return null;
    }

    public List<NhanVien> selectByAll() {
        return this.selectBySql("Select *from NhanVien");
    }
}
