/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entity.BanCho;
import Untils.Xjdbc;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author doanp
 */
public class BanChoDAO {

    String SQL_Insert = "insert into BangCho values(?,?,?,?,?,?,?)";
    String SQL_Update = "UPDATE dbo.BangCho SET HoanThanh = ? WHERE MaBan  = ? AND MaSP = ?";
    String SQL_Delete = "DELETE FROM dbo.BangCho WHERE MaBan=?";
    String SQL_SelectByBan = "SELECT * FROM dbo.BangCho WHERE MaBan = ?";
    String SQL_SelectByID = "SELECT * FROM dbo.BangCho WHERE MaBan = ? AND MaSP = ?";
    String SQL_SelectStatusTable = "SELECT DISTINCT MaBan FROM dbo.BangCho";
    String SQL_UpdateALL = "UPDATE dbo.Ban SET SanSang = 1";
    String SQL_GioVao = "SELECT DISTINCT GioVao FROM dbo.BangCho WHERE MaBan = ?";
    String SQL_NV = "SELECT DISTINCT MaNV FROM dbo.BangCho WHERE MaBan = ?";
    String SQL_MASP = "select *from BangCho where MASP=?";

    public void chuyenBan(String toBan, List<BanCho> lst) {
        lst.forEach((entity) -> {
            Xjdbc.update(SQL_Insert, toBan, entity.getMaSP(), entity.getSoLuong(), entity.getGia(), entity.getGioVao(), entity.getMaNV(), entity.isHoanThanh());
            delete(entity.getMaBan());
        });

    }

    public String getTimeIn(String maBan) {
        try {
            ResultSet rs = Xjdbc.query(SQL_GioVao, maBan);
            while (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception ex) {
            Logger.getLogger(BanChoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String getNV(String maBan) {
        try {
            ResultSet rs = Xjdbc.query(SQL_NV, maBan);
            while (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception ex) {
            Logger.getLogger(BanChoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public int insert(BanCho entity) {
        return Xjdbc.update(SQL_Insert, entity.getMaBan(), entity.getMaSP(), entity.getSoLuong(), entity.getGia(), entity.getGioVao(), entity.getMaNV(), entity.isHoanThanh());
    }

    public List<String> selectStatusTable() {
        List<String> lst = new ArrayList<>();
        Xjdbc.update(SQL_UpdateALL);
        try {
            ResultSet rs = Xjdbc.query(SQL_SelectStatusTable);
            while (rs.next()) {
                lst.add(rs.getString(1));
            }
            return lst;
        } catch (Exception ex) {
            Logger.getLogger(BanDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public int update(BanCho entity) {
        return Xjdbc.update(SQL_Update, entity.isHoanThanh(), entity.getMaBan(), entity.getMaSP());
    }

    public int delete(String id) {
        return Xjdbc.update(SQL_Delete, id);
    }

    public List<BanCho> selectByBan(String MaBan) {
        return selectBySql(SQL_SelectByBan, MaBan);
    }

    public BanCho selectByID(String maBAN, String maSP) {
        return selectBySql(SQL_SelectByID, maBAN, maSP).isEmpty() ? null : selectBySql(SQL_SelectByID, maBAN, maSP).get(0);
    }

    public List<BanCho> selectBySql(String sql, Object... args) {
        List<BanCho> list = new ArrayList<>();
        try {
            ResultSet rs = Xjdbc.query(sql, args);
            while (rs.next()) {
                list.add(new BanCho(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getString(5), rs.getString(6), rs.getBoolean(7)));
            }

            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public BanCho selectById(String id) {
        List<BanCho> list = this.selectBySql(SQL_MASP, id);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }
}
