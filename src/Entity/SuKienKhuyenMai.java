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
public class SuKienKhuyenMai {
    private int maSKKM;
    private String tenSKKM;
    private double giaTriKM;
    private Date ngayBatDau;
    private Date ngayKetThuc;
    private Date ngayTao;
    private String maNV;
    private boolean trangThai;

    public SuKienKhuyenMai(int maSKKM, String tenSKKM, double giaTriKM, Date ngayBatDau, Date ngayKetThuc, Date ngayTao, String maNV, boolean trangThai) {
        this.maSKKM = maSKKM;
        this.tenSKKM = tenSKKM;
        this.giaTriKM = giaTriKM;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.ngayTao = ngayTao;
        this.maNV = maNV;
        this.trangThai = trangThai;
    }

    public SuKienKhuyenMai() {
    }

    public SuKienKhuyenMai(String tenSKKM, double giaTriKM, Date ngayBatDau, Date ngayKetThuc, Date ngayTao, String maNV, boolean trangThai) {
        this.tenSKKM = tenSKKM;
        this.giaTriKM = giaTriKM;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.ngayTao = ngayTao;
        this.maNV = maNV;
        this.trangThai = trangThai;
    }

    public int getMaSKKM() {
        return maSKKM;
    }

    public void setMaSKKM(int maSKKM) {
        this.maSKKM = maSKKM;
    }

    public String getTenSKKM() {
        return tenSKKM;
    }

    public void setTenSKKM(String tenSKKM) {
        this.tenSKKM = tenSKKM;
    }

    public double getGiaTriKM() {
        return giaTriKM;
    }

    public void setGiaTriKM(double giaTriKM) {
        this.giaTriKM = giaTriKM;
    }

    public Date getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(Date ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public Date getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(Date ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "SuKienKhuyenMai{" + "maSKKM=" + maSKKM + ", tenSKKM=" + tenSKKM + ", giaTriKM=" + giaTriKM + ", ngayBatDau=" + ngayBatDau + ", ngayKetThuc=" + ngayKetThuc + ", ngayTao=" + ngayTao + ", maNV=" + maNV + ", trangThai=" + trangThai + '}';
    }
    
}
