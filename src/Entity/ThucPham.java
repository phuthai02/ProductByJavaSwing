/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.util.Date;

/**
 *
 * @author doanp
 */
public class ThucPham {

    private int maNL;
    private String maLoaiTP;
    private String tenNL;
    private Date ngaymua;
    private Date ngaynhap;
    private int soLuong;
    private String maNV;
    private String moTa;
    private boolean trangThai;

    public ThucPham() {
    }

    public ThucPham(int maNL, String maLoaiTP, String tenNL, Date ngaymua, Date ngaynhap, int soLuong, String maNV, String moTa, boolean trangThai) {
        this.maNL = maNL;
        this.maLoaiTP = maLoaiTP;
        this.tenNL = tenNL;
        this.ngaymua = ngaymua;
        this.ngaynhap = ngaynhap;
        this.soLuong = soLuong;
        this.maNV = maNV;
        this.moTa = moTa;
        this.trangThai = trangThai;
    }

    public int getMaNL() {
        return maNL;
    }

    public void setMaNL(int maNL) {
        this.maNL = maNL;
    }

    public String getMaLoaiTP() {
        return maLoaiTP;
    }

    public void setMaLoaiTP(String maLoaiTP) {
        this.maLoaiTP = maLoaiTP;
    }

    public String getTenNL() {
        return tenNL;
    }

    public void setTenNL(String tenNL) {
        this.tenNL = tenNL;
    }

    public Date getNgaymua() {
        return ngaymua;
    }

    public void setNgaymua(Date ngaymua) {
        this.ngaymua = ngaymua;
    }

    public Date getNgaynhap() {
        return ngaynhap;
    }

    public void setNgaynhap(Date ngaynhap) {
        this.ngaynhap = ngaynhap;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "ThucPham{" + "maNL=" + maNL + ", maLoaiTP=" + maLoaiTP + ", tenNL=" + tenNL + ", ngaymua=" + ngaymua + ", ngaynhap=" + ngaynhap + ", soLuong=" + soLuong + ", maNV=" + maNV + ", moTa=" + moTa + ", trangThai=" + trangThai + '}';
    }



}
