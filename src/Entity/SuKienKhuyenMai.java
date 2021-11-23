/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

/**
 *
 * @author doanp
 */
public class SuKienKhuyenMai {
    private int maSKKM;
    private String tenSKKM;
    private double giaTriKM;
    private String ngayBatDau;
    private String ngayKetThuc;
    private String ngayTao;
    private String maNV;
    private boolean trangThai;

    public SuKienKhuyenMai(int maSKKM, String tenSKKM, double giaTriKM, String ngayBatDau, String ngayKetThuc, String ngayTao, String maNV, boolean trangThai) {
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

    public String getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(String ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public String getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(String ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(String ngayTao) {
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
