/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entity.LoaiSp;
import Untils.Xjdbc;
import java.sql.ResultSet;
import java.util.ArrayList;

import java.util.List;

/**
 *
 * @author one
 */
public class LoaiSanPhamDAO {

    String SELECT_ALL_SQL = "SELECT * FROM LoaiSanPham";
    String SELECT_ID = "SELECT * FROM LoaiSanPham where MaLoaiSP = ?";

    public List<LoaiSp> selectBySql(String sql, Object... args) {
        try {
            List<LoaiSp> list = new ArrayList<>();
            ResultSet rs = Xjdbc.query(sql, args);
            while (rs.next()) {
                list.add(new LoaiSp(rs.getString(1), rs.getString(2)));
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            System.out.println(e.toString());
            throw new RuntimeException(e);
        }
    }
    
    public List<LoaiSp> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }
    
     public LoaiSp selectByID(String ma) {
        return selectBySql(SELECT_ID,ma).get(0);
    }

   

}
