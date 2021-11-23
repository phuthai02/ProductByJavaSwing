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
public class HoaDonChiTiet {
    private int maHD;
    private String maSP;
    private int soLuong;
    private int gia;
    private boolean trangThai;

    public HoaDonChiTiet(int maHD, String maSP, int soLuong, int gia, boolean trangThai) {
        this.maHD = maHD;
        this.maSP = maSP;
        this.soLuong = soLuong;
        this.gia = gia;
        this.trangThai = trangThai;
    }

    public HoaDonChiTiet() {
    }
    
    public int getMaHD() {
        return maHD;
    }

    public void setMaHD(int maHD) {
        this.maHD = maHD;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "HoaDonChiTiet{" + "maHD=" + maHD + ", maSP=" + maSP + ", soLuong=" + soLuong + ", gia=" + gia + ", trangThai=" + trangThai + '}';
    }

    
}
