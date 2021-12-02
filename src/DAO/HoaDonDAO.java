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

    String SQL_Insert = "insert into HoaDon(MaKH,NgayTao,GhiChu, MaNV, MaSKKM, MaBan,TrangThai) values(?,?,?,?,?,?,1)";
    String SQL_Update = "update HoaDon set MaKH=?, NgayTao=?, GhiChu=?, MaNV=?, MaSKKM=?, MaBan=? where MaHD=?";
    String SQL_Delete = "update HoaDon set TrangThai=0 where MaHD=?";
    String SQL_SelectPaging = "SELECT * FROM dbo.HoaDon WHERE TrangThai = ? AND \n"
            + "(NgayTao BETWEEN ? AND ?)  ORDER BY MaHD DESC OFFSET ? *15 ROWS  FETCH NEXT 15 ROWS ONLY";

    String SQL_SelectID = "select * from HoaDon where MaHD=?";
    String SQL_SelectMaHD = "SELECT TOP 1 * FROM dbo.HoaDon WHERE MaBan = ? ORDER BY MaHD DESC";

    @Override
    public int insert(HoaDon entity) {
        return Xjdbc.update(SQL_Insert, entity.getMaKH(), entity.getNgayTao(), entity.getGhiChu(), entity.getMaNV(), entity.getMaSKKM(), entity.getMaBan());
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
    public HoaDon selectById(Integer id) {
//        return selectBySql(SQL_SelectID, id).get(0);
        List<HoaDon> list = this.selectBySql(SQL_SelectID, id);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    public HoaDon selectMaHD(String maBan) {
        List<HoaDon> list = this.selectBySql(SQL_SelectMaHD, maBan);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    @Override
    public List<HoaDon> selectBySql(String sql, Object... args) {
        List<HoaDon> list = new ArrayList<>();
        try {
            ResultSet rs = Xjdbc.query(sql, args);
            while (rs.next()) {
                HoaDon entity = new HoaDon();
                entity.setMaHD(rs.getInt("MaHD"));
                entity.setMaKH(rs.getInt("MaKH"));
                entity.setNgayTao(rs.getString("NgayTao"));
                entity.setGhiChu(rs.getString("GhiChu"));
                entity.setMaNV(rs.getString("MaNV"));
                entity.setMaSKKM(rs.getInt("MaSKKM"));
                entity.setMaBan(rs.getString("MaBan"));
                entity.setTrangThai(rs.getBoolean("TrangThai"));
                list.add(entity);
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

    public List<HoaDon> selectPagingFull(int Status, int pageIndex, String ngayBD, String ngayKT) {
        List<HoaDon> list = this.selectBySql(SQL_SelectPaging, Status, ngayBD, ngayKT, pageIndex);
        return list;
    }

    public List<HoaDon> selectByAll() {
        return this.selectBySql("Select *from HoaDon");
    }
}
