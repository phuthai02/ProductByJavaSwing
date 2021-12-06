/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entity.SuKienKhuyenMai;
import Untils.Xjdbc;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author duong
 */
public class SKKMDAO implements SystemDAO<SuKienKhuyenMai, String> {

    String SQL_Insert = "insert into SuKienKhuyenMai values(?,?,?,?,?,?,1)";
    String SQL_Update = "update SuKienKhuyenMai set TenSKKM = ?,GiaTriKM = ?,NgayBatDau = ?,NgayKetThuc = ?,NgayTao = ?,TrangThai = 1 where MaSKKM = ?";
    String SQL_Delete = "update SuKienKhuyenMai set TrangThai = 0 where MaSKKM = ?";
    String SQL_SelectID = "select * from SuKienKhuyenMai where MaSKKM = ?";
    String SQL_SelectAll = "select * from SuKienKhuyenMai";
    String SQL_SelectDDR = "select * from dbo.SuKienKhuyenMai where TrangThai = ? and  (MaSKKM like ? or TenSKKM like ? ) and ? between NgayBatDau and NgayKetThuc ORDER BY MaSKKM OFFSET ?*15 ROWS  FETCH NEXT 15 ROWS ONLY";
    String SQL_SelectKT = "select * from dbo.SuKienKhuyenMai where TrangThai = ? and  (MaSKKM like ? or TenSKKM like ? ) and ? > NgayKetThuc ORDER BY MaSKKM OFFSET ?*15 ROWS  FETCH NEXT 15 ROWS ONLY";
    String SQL_SelectSDR = "select * from dbo.SuKienKhuyenMai where TrangThai = ? and  (MaSKKM like ? or TenSKKM like ? ) and ? < NgayBatDau ORDER BY MaSKKM OFFSET ?*15 ROWS  FETCH NEXT 15 ROWS ONLY";
    String SQL_SelectSKDDR = "select * from dbo.SuKienKhuyenMai where TrangThai = ? and ? between NgayBatDau and NgayKetThuc ORDER BY GiaTriKM DESC";

    @Override
    public int insert(SuKienKhuyenMai entity) {
        return Xjdbc.update(SQL_Insert,
                entity.getTenSKKM(),
                entity.getGiaTriKM(),
                entity.getNgayBatDau(),
                entity.getNgayKetThuc(),
                entity.getNgayTao(),
                entity.getMaNV()
        );
    }

    @Override
    public int update(SuKienKhuyenMai entity) {
        return Xjdbc.update(SQL_Update,
                entity.getTenSKKM(),
                entity.getGiaTriKM(),
                entity.getNgayBatDau(),
                entity.getNgayKetThuc(),
                entity.getNgayTao(),
                entity.getMaSKKM()
        );
    }

    @Override
    public int delete(String id) {
        return Xjdbc.update(SQL_Delete, id);
    }

    @Override
    public List<SuKienKhuyenMai> selectBySql(String sql, Object... args) {
        List<SuKienKhuyenMai> list = new ArrayList<>();
        try {
            ResultSet rs = Xjdbc.query(sql, args);
            while (rs.next()) {
                list.add(new SuKienKhuyenMai(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getDouble(3),
                        rs.getDate(4),
                        rs.getDate(5),
                        rs.getDate(6),
                        rs.getString(7),
                        rs.getBoolean(8)
                ));
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public List<SuKienKhuyenMai> selectPaging(int Status, int pageIndex) {
        return null;
    }

    @Override
    public List<SuKienKhuyenMai> selectByKeyWord(String keyWord, int Status) {
        return null;
    }

    @Override
    public SuKienKhuyenMai selectById(String id) {
        List<SuKienKhuyenMai> list = this.selectBySql(SQL_SelectID, id);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    public List<SuKienKhuyenMai> selectAll() {
        return selectBySql(SQL_SelectAll);
    }

    public List<SuKienKhuyenMai> selectPagingFull(int SQL, int Status, String keyWord, String nowDate, int pageIndex) {
        switch (SQL) {
            case 0:
                return selectBySql(SQL_SelectDDR, Status, "%" + keyWord + "%", "%" + keyWord + "%", nowDate, pageIndex);
            case 1:
                return selectBySql(SQL_SelectSDR, Status, "%" + keyWord + "%", "%" + keyWord + "%", nowDate, pageIndex);
            case 2:
                return selectBySql(SQL_SelectKT, Status, "%" + keyWord + "%", "%" + keyWord + "%", nowDate, pageIndex);
        }
        return null;
    }

    public List<SuKienKhuyenMai> selectSKDDR(int Status, String nowDate) {
        return selectBySql(SQL_SelectSKDDR, Status, nowDate);

    }

    public SuKienKhuyenMai selectById1(int id) {
        List<SuKienKhuyenMai> list = this.selectBySql(SQL_SelectID, id);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }
}
