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
public class HoaDon {

    private int maHD;
    private int maKH;
    private String ngayTao;
    private String ghiChu;
    private String maNV;
    private int maSKKM;
    private String maBan;
    boolean trangThai;

    public HoaDon(int maKH, String ngayTao, String ghiChu, String maNV, int maSKKM, String maBan, boolean trangThai) {
        this.maKH = maKH;
        this.ngayTao = ngayTao;
        this.ghiChu = ghiChu;
        this.maNV = maNV;
        this.maSKKM = maSKKM;
        this.maBan = maBan;
        this.trangThai = trangThai;
    }

    public HoaDon(int maHD, int maKH, String ngayTao, String ghiChu, String maNV, int maSKKM, String maBan, boolean trangThai) {
        this.maHD = maHD;
        this.maKH = maKH;
        this.ngayTao = ngayTao;
        this.ghiChu = ghiChu;
        this.maNV = maNV;
        this.maSKKM = maSKKM;
        this.maBan = maBan;
        this.trangThai = trangThai;
    }

    public int getMaHD() {
        return maHD;
    }

    public void setMaHD(int maHD) {
        this.maHD = maHD;
    }

    public int getMaKH() {
        return maKH;
    }

    public void setMaKH(int maKH) {
        this.maKH = maKH;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public int getMaSKKM() {
        return maSKKM;
    }

    public void setMaSKKM(int maSKKM) {
        this.maSKKM = maSKKM;
    }

    public String getMaBan() {
        return maBan;
    }

    public void setMaBan(String maBan) {
        this.maBan = maBan;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "HoaDon{" + "maHD=" + maHD + ", maKH=" + maKH + ", ngayTao=" + ngayTao + ", ghiChu=" + ghiChu + ", maNV=" + maNV + ", maSKKM=" + maSKKM + ", maBan=" + maBan + ", trangThai=" + trangThai + '}';
    }

    public HoaDon() {
    }

  
}
