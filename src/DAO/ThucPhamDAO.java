/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entity.ThucPham;
import Untils.Xjdbc;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class ThucPhamDAO implements SystemDAO<ThucPham, Integer> {

    String SQL_Insert = "INSERT INTO NguyenLieu values(?,?,?,?,?,?,?,1)";
    String SQL_Update = "UPDATE NguyenLieu SET MaLoaiTP=?, TenNL=?, Ngaymua=?, NgayNhap=?, SoLuong=?,MaNV=?,MoTa=?, TrangThai=? where MaTP=?";
    String SQL_Delete = "update NguyenLieu set TrangThai=0 where MaNL=?";
    String SQL_SelectPaging = "SELECT * FROM dbo.NguyenLieu WHERE TrangThai = ? AND (TenNL Like ?) ORDER BY MaNL OFFSET ?*15 ROWS  FETCH NEXT 15 ROWS ONLY";
    String SQL_SelectTenLoaiTP = "select MaLoaiTP,TenLoaiTP from LoaiThucPham ";
    String SQL_SelectID = "SELECT * FROM NguyenLieu WHERE MaNL=?";

    @Override
    public int insert(ThucPham entity) {
        return Xjdbc.update(SQL_Insert,
                entity.getMaLoaiTP(),
                entity.getTenNL(),
                entity.getNgaymua(),
                entity.getNgaynhap(),
                entity.getSoLuong(),
                entity.getMaNV(),
                entity.getMoTa());
    }

    @Override
    public int update(ThucPham entity) {
        return Xjdbc.update(SQL_Update,
                entity.getMaLoaiTP(),
                entity.getTenNL(),
                entity.getNgaymua(),
                entity.getNgaynhap(),
                entity.getSoLuong(),
                entity.getMaNV(),
                entity.getMoTa(),
                entity.isTrangThai(),      
                entity.getMaNL());
    }

    @Override
    public int delete(Integer id) {
        return Xjdbc.update(SQL_Delete, id);
    }

    @Override
    public List<ThucPham> selectBySql(String sql, Object... args) {
        List<ThucPham> list = new ArrayList<>();
        try {
            ResultSet rs = Xjdbc.query(sql, args);
            while (rs.next()) {
                ThucPham tp = new ThucPham();
                tp.setMaNL(rs.getInt(1));
                tp.setMaLoaiTP(rs.getString(2));
                tp.setTenNL(rs.getString(3));
                tp.setNgaymua(rs.getDate(4));
                tp.setNgaynhap(rs.getDate(5));
                tp.setSoLuong(rs.getInt(6));
                tp.setMoTa(rs.getString(8));
                tp.setMaNV(rs.getString(7));
                tp.setTrangThai(rs.getBoolean(9));
                list.add(tp);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ThucPham> selectPaging(int Status, int pageIndex) {
        return null;
    }

    public List<ThucPham> selectPagingFull(int Status, int pageIndex, String keyWord) {
        List<ThucPham> list = this.selectBySql(SQL_SelectPaging, Status, "%" + keyWord + "%", pageIndex);
        return list;
    }

    public Map<String, String> selectTenLoaiTP() {
        Map<String, String> map = new HashMap<>();
        try {
            ResultSet rs = Xjdbc.query(SQL_SelectTenLoaiTP);
            while (rs.next()) {
                String key = rs.getString("MaLoaiTP");
                String value = rs.getString("TenLoaiTP");
                map.put(key, value);
            }
            rs.getStatement().getConnection().close();
            return map;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ThucPham> selectByKeyWord(Integer keyWord, int Status) {
        return null;
    }

    @Override
    public ThucPham selectById(Integer id) {
        List<ThucPham> list = this.selectBySql(SQL_SelectID, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

}
