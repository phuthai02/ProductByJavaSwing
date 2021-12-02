/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entity.HoaDonChiTiet;
import Untils.Xjdbc;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author doanp
 */
public class HoaDonChiTietDAO implements SystemDAO<HoaDonChiTiet, Integer> {
    
    String SQL_Insert = "insert into HoaDonChiTiet values(?,?,?,?,1)";
    String SQL_Update = "update HoaDonChiTiet set SoLuong=?, Gia=?, TrangThai=? where MaHD=? and MaSP=?";
    String SQL_Delete = "update HoaDon set TrangThai=0 where MaHD=? and MaSP=?";
    String SQL_SelectPaging = "chưa rõ điều kiện";
    String SQL_SelectID = "select * from HoaDonChiTiet where MaHD=? AND MaSP=?";
String SQL_SelectPagingCT = "SELECT * FROM dbo.HoaDon WHERE TrangThai = ?ORDER BY MaNV OFFSET ? *15 ROWS  FETCH NEXT 15 ROWS ONLY"; 
    @Override
    public int insert(HoaDonChiTiet entity) {
        return Xjdbc.update(SQL_Insert, entity.getMaHD(), entity.getMaSP(), entity.getSoLuong(), entity.getGia());
    }
    
    @Override
    public int update(HoaDonChiTiet entity) {
        return Xjdbc.update(SQL_Update, entity.getSoLuong(), entity.getGia(), entity.getSoLuong(), entity.getMaHD(), entity.getMaSP());
    }
    
    @Override
    public int delete(Integer id) {
        return 0;
    }
    
    public int delete(Integer id1, String id2) {
        return Xjdbc.update(SQL_Delete, id1, id2);
    }
    
    @Override
    public List<HoaDonChiTiet> selectBySql(String sql, Object... args) {
        List<HoaDonChiTiet> list = new ArrayList<>();
        try {
            ResultSet rs = Xjdbc.query(sql, args);
            while (rs.next()) {
                list.add(new HoaDonChiTiet(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getBoolean(5)));
            }
            
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public List<HoaDonChiTiet> selectPaging(int Status, int pageIndex) {
        return null;
    }
    
    @Override
    public List<HoaDonChiTiet> selectByKeyWord(Integer keyWord, int Status) {
        return null;
    }
    
    @Override
    public HoaDonChiTiet selectById(Integer id) {
         List<HoaDonChiTiet> list = this.selectBySql(SQL_SelectID, id);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }
    
    public HoaDonChiTiet selectById(Integer id1, String id2) {
        return selectBySql(SQL_SelectID, id1, id2).get(0);
    }

 public List<HoaDonChiTiet> selectByAll(Integer maHD) {
        return this.selectBySql("Select *from HoaDonChiTiet where MAHD=?",maHD);
    }
}
