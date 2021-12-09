/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entity.Ban;
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
public class BanDAO implements SystemDAO<Ban, String> {

    String SQL_Insert = "insert into Ban values(?,?,?,?,?)";
    String SQL_Update = "update Ban set TenBan=?, ViTri=?, TrangThai=?, SanSang=? WHERE MaBan=?";
    String SQL_Delete = "update Ban set TrangThai=0 where MaBan=?";
    String SQL_SelectPaging = "SELECT * FROM dbo.Ban WHERE TrangThai=? AND ViTri LIKE ? AND (TenBan LIKE ? OR MaBan LIKE ?) AND SanSang LIKE ? AND MaBan != 'MV' ORDER BY MaBan OFFSET 11*? ROWS FETCH NEXT 11 ROWS ONLY";
    String SQL_SelectID = "select * from Ban where MaBan=?";
    String SQL_SelectTang = "SELECT DISTINCT ViTri FROM dbo.Ban where TrangThai = 1";
    String SQL_Count = "SELECT COUNT(*) FROM dbo.BangCho WHERE MaBan = ?";
    String SQL_SelectALL = "SELECT * FROM BAN WHERE ViTri LIKE ? and TrangThai = ?";

    @Override
    public int insert(Ban entity) {
        return Xjdbc.update(SQL_Insert, entity.getMaBan(), entity.getTenBan(), entity.getViTri(), entity.isTrangThai(), entity.isSanSang());
    }

    @Override
    public int update(Ban entity) {
        return Xjdbc.update(SQL_Update, entity.getTenBan(), entity.getViTri(), entity.isTrangThai(), entity.isSanSang(), entity.getMaBan());
    }

    @Override
    public int delete(String id) {
        return Xjdbc.update(SQL_Delete, id);
    }

    @Override
    public List<Ban> selectBySql(String sql, Object... args) {
        List<Ban> list = new ArrayList<>();
        try {
            ResultSet rs = Xjdbc.query(sql, args);
            while (rs.next()) {
                list.add(new Ban(rs.getString(1), rs.getString(2), rs.getString(3), rs.getBoolean(4), rs.getBoolean(5)));
            }

            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Ban> selectPaging(int Status, int pageIndex) {
        return null;
    }

    public List<Ban> selectALL(String tang, String stt) {
        return selectBySql(SQL_SelectALL, "%" + tang + "%", stt);
    }

    @Override
    public List<Ban> selectByKeyWord(String keyWord, int Status) {
        return null;
    }

    @Override
    public Ban selectById(String id) {
        return selectBySql(SQL_SelectID, id).isEmpty() ? null : selectBySql(SQL_SelectID, id).get(0);
    }

    public List<Ban> selectPagingFull(int Status, String viTri, String keyWord, String SanSang, int pageIndex) {
        return selectBySql(SQL_SelectPaging, Status, "%" + viTri + "%", "%" + keyWord + "%", "%" + keyWord + "%", "%" + SanSang + "%", pageIndex);
    }

    public List<String> selectTang() {
        List<String> lst = new ArrayList<>();
        try {
            ResultSet rs = Xjdbc.query(SQL_SelectTang);
            while (rs.next()) {
                lst.add(rs.getString(1));
            }
            return lst;
        } catch (Exception ex) {
            Logger.getLogger(BanDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public int selectCount(String maBan) {
        try {
            ResultSet rs = Xjdbc.query(SQL_Count, maBan);
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception ex) {
            Logger.getLogger(BanDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

}
