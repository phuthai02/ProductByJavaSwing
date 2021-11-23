/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Untils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author doanp
 */
public class Xjdbc {

    static String user = "sa", pass = "123456";
    static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    static String url = "jdbc:sqlserver://THAIDP\\SQLEXPRESS:1433;databaseName=QuanLyNhaHang";

    static {
        try {
            Class.forName(driver);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static PreparedStatement getStm(String sql, Object... args) throws Exception {
        Connection cn = DriverManager.getConnection(url, user, pass);
        PreparedStatement pst;
        if (sql.trim().startsWith("{")) {
            pst = cn.prepareCall(sql);
            for (int i = 0; i < args.length; i++) {
                pst.setObject(i + 1, args[i]);
            }
        } else {
            pst = cn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                pst.setObject(i + 1, args[i]);
            }
        }
        return pst;
    }

    public static ResultSet query(String sql, Object... args) throws Exception {
        PreparedStatement pst = Xjdbc.getStm(sql, args);
        return pst.executeQuery();
    }

    public static Object value(String sql, Object... args) {
        try {
            ResultSet rs = Xjdbc.query(sql, args);
            if (rs.next()) {
                return rs.getObject(0);
            }
            rs.getStatement().getConnection().close();
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static int update(String sql, Object... args) {
        try {
            PreparedStatement pst = Xjdbc.getStm(sql, args);
            try {
                return pst.executeUpdate();
            } finally {
                pst.getConnection().close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
