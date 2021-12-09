/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entity.BanDat;
import Untils.Xjdbc;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author doanp
 */
public class BanDatDAO implements SystemDAO<BanDat, String> {

    String SQL_SelectBanDat = "Select * from bandat";
    String SQL_SelectByID = "Select * from bandat where MaBan = ? and GioDat like ?";
    String SQL_Insert = "insert into BanDat values(?,?,?,?)";
    String SQL_Update = "UPDATE dbo.BanDat SET MaBan = ?, GioDat = ?,MaKH = ?,MaNV =? WHERE MaBan = ? AND GioDat = ?";
    String SQL_Delete = "DELETE FROM dbo.BanDat WHERE MaBan  = ? AND GioDat = ?";
    String SQL_SelectByGioDat = "Select * from bandat where GioDat like ?";
    String SQL_SelectByMaKH = "Select * from bandat where MaKH like ?";

    @Override
    public List<BanDat> selectBySql(String sql, Object... args) {
        List<BanDat> list = new ArrayList<>();
        try {
            ResultSet rs = Xjdbc.query(sql, args);
            while (rs.next()) {
                list.add(new BanDat(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getString(4)));
            }

            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public BanDat selectByID(String maBan, String gioDat) {
        return selectBySql(SQL_SelectByID, maBan, "%" + gioDat + "%").isEmpty() ? null : selectBySql(SQL_SelectByID, maBan, "%" + gioDat + "%").get(0);
    }

    public BanDat selectByGioDat(String gioDat) {
        return selectBySql(SQL_SelectByGioDat, "%" + gioDat + "%").isEmpty() ? null : selectBySql(SQL_SelectByGioDat, "%" + gioDat + "%").get(0);
    }

    public List<BanDat> selectByDay(String maBan, String gioDat) {
        return selectBySql(SQL_SelectByID, maBan, "%" + gioDat + "%");
    }

    public List<BanDat> selectByMaKH(int MaKH) {
        return selectBySql(SQL_SelectByMaKH, MaKH);
    }

    @Override
    public int insert(BanDat entity) {
        return Xjdbc.update(SQL_Insert, entity.getMaBan(), entity.getGioDat(), entity.getMaKH(), entity.getMaNV());
    }

    @Override
    public int update(BanDat entity) {
        return Xjdbc.update(SQL_Update, entity.getMaBan(), entity.getGioDat(), entity.getMaKH(), entity.getMaNV(), entity.getMaBan(), entity.getGioDat());
    }

    public int updateDB(BanDat entity, String maBanCu, String gioDatCu) {
        return Xjdbc.update(SQL_Update, entity.getMaBan(), entity.getGioDat(), entity.getMaKH(), entity.getMaNV(), maBanCu, gioDatCu);
    }

    public void deleteDB(String maBan, String gioDat) {
        Xjdbc.update(SQL_Delete, maBan, gioDat);
    }

    @Override
    public int delete(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<BanDat> selectPaging(int Status, int pageIndex) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<BanDat> selectByKeyWord(String keyWord, int Status) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BanDat selectById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<BanDat> selectALL() {
        return selectBySql(SQL_SelectBanDat);
    }
}
