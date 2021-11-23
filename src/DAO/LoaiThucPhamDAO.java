/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entity.LoaiThucPham;
import Untils.Xjdbc;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class LoaiThucPhamDAO {
    String SQL_SelectID = "SELECT * FROM LoaiThucPham WHERE MaLoaiTP=?";
    
    public List<LoaiThucPham> selectBySql(String sql, Object... args) {
        try {
            List<LoaiThucPham> list = new ArrayList<>();
            ResultSet rs = Xjdbc.query(sql, args);
            while (rs.next()) {
                list.add(new LoaiThucPham(rs.getString("MaLoaiTP"), rs.getString("TenLoaiTP")));
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            System.out.println(e.toString());
            throw new RuntimeException(e);
        }
    }
    public LoaiThucPham selectById(String id) {
        List<LoaiThucPham> list = this.selectBySql(SQL_SelectID, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    
    public List<LoaiThucPham> selectByAll() {
        return this.selectBySql("Select *from LoaiThucPham");
    }

    
}
