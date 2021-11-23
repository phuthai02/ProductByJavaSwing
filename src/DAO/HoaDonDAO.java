/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entity.HoaDon;
import Untils.Xjdbc;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author doanp
 */
public class HoaDonDAO implements SystemDAO<HoaDon, Integer> {

    String SQL_Insert = "insert into HoaDon values(?,?,?,?,?,?,?,?)";
    String SQL_Update = "update HoaDon set MaKH=?, NgayTao=?, GhiChu=?, MaNV=?, MaSKKM=?, MaBan=? where MaHD=?";
    String SQL_Delete = "update HoaDon set TrangThai=0 where MaHD=?";
    String SQL_SelectPaging = "chưa rõ điều kiện";
    String SQL_SelectID = "select * from HoaDon where MaHD=?";

    @Override
    public int insert(HoaDon entity) {
        return Xjdbc.update(SQL_Insert, entity.getMaHD(), entity.getMaKH(), entity.getNgayTao(), entity.getGhiChu(), entity.getMaNV(), entity.getMaSKKM(), entity.getMaBan(), entity.isTrangThai());
    }

    @Override
    public int update(HoaDon entity) {
        return Xjdbc.update(SQL_Update, entity.getMaKH(), entity.getNgayTao(), entity.getGhiChu(), entity.getMaNV(), entity.getMaSKKM(), entity.getMaBan(), entity.isTrangThai(), entity.getMaHD());
    }

    @Override
    public int delete(Integer id) {
        return Xjdbc.update(SQL_Delete, id);
    }

    @Override
    public List<HoaDon> selectBySql(String sql, Object... args) {
        List<HoaDon> list = new ArrayList<>();
        try {
            ResultSet rs = Xjdbc.query(sql, args);
            while (rs.next()) {
                list.add(new HoaDon(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getString(7), rs.getBoolean(8)));
            }

            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<HoaDon> selectPaging(int Status, int pageIndex) {
        return null;
    }

    @Override
    public List<HoaDon> selectByKeyWord(Integer keyWord, int Status) {
        return null;
    }

    @Override
    public HoaDon selectById(Integer id) {
        return selectBySql(SQL_SelectID, id).get(0);
    }
}
