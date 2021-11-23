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
public class ThucPhamDAO implements SystemDAO<ThucPham, Integer>{
    String SQL_Insert = "INSERT INTO ThucPham values(?,?,?,?,?,?,?,?,?,1)";
    String SQL_Update = "UPDATE ThucPham SET MaLoaiTP=?, TenTP=?, NgayNhap=?, GiaNhap=?, SoLuong=?,DonViTinh=?,MaNV=?,NCC=?,GhiChu=?, TrangThai=? where MaTP=?";
    String SQL_Delete = "update ThucPham set TrangThai=0 where MaTP=?";
    String SQL_SelectPaging = "SELECT * FROM dbo.ThucPham WHERE TrangThai = ? AND (MaTP LIKE ? OR TenTP LIKE ? OR NCC LIKE ?) ORDER BY MaTP OFFSET ?*15 ROWS  FETCH NEXT 15 ROWS ONLY";
    String SQL_SelectTenLoaiTP = "select MaLoaiTP,TenLoaiTP from LoaiThucPham ";
    String SQL_SelectID = "SELECT * FROM ThucPham WHERE MaTP=?";
    
    @Override
    public int insert(ThucPham entity) {
        return Xjdbc.update(SQL_Insert,
                entity.getMaLoaiTP(),
                entity.getTenTP(),
                entity.getNgayNhap(),
                entity.getGiaNhap(),
                entity.getSoLuong(),
                entity.getDonViTinh(),
                entity.getMaNV(),
                entity.getNCC(),
                entity.getGhiChu());
//                entity.isTrangThai());
    }

    @Override
    public int update(ThucPham entity) {
        return Xjdbc.update(SQL_Update,
                entity.getMaLoaiTP(),
                entity.getTenTP(),
                entity.getNgayNhap(),
                entity.getGiaNhap(),
                entity.getSoLuong(),
                entity.getDonViTinh(),
                entity.getMaNV(),
                entity.getNCC(),
                entity.getGhiChu(),
                entity.isTrangThai(),
                entity.getMaTP());
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
                tp.setMaTP(rs.getInt("MaTP"));
                tp.setMaLoaiTP(rs.getString("MaLoaiTP"));
                tp.setTenTP(rs.getString("TenTP"));
                tp.setNgayNhap(rs.getString("NgayNhap"));
                tp.setGiaNhap(rs.getInt("GiaNhap"));
                tp.setSoLuong(rs.getInt("SoLuong"));
                tp.setDonViTinh(rs.getString("DonViTinh"));
                tp.setGhiChu(rs.getString("GhiChu"));
                tp.setMaNV(rs.getString("MaNV"));
                tp.setNCC(rs.getString("NCC"));
                tp.setTrangThai(rs.getBoolean("TrangThai"));
                list.add(tp);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw  new RuntimeException(e);
        }
    }

    @Override
    public List<ThucPham> selectPaging(int Status, int pageIndex) {
        return null;
    }
    public List<ThucPham> selectPagingFull(int Status, int pageIndex, String keyWord, String gioiTinh) {
        List<ThucPham> list = this.selectBySql(SQL_SelectPaging, Status, "%" + keyWord + "%", "%" + keyWord + "%", "%" + keyWord + "%", pageIndex);
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
