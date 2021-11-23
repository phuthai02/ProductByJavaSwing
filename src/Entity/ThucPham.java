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
public class ThucPham {
    private int maTP;
    private String maLoaiTP;
    private String tenTP;
    private String ngayNhap;
    private int giaNhap;
    private int soLuong;
    private String donViTinh;
    private String maNV;
    private String NCC;
    private String ghiChu;
    private boolean trangThai;

    public ThucPham(int maTP, String maLoaiTP, String tenTP, String ngayNhap, int giaNhap, int soLuong, String donViTinh, String maNV, String NCC, String ghiChu, boolean trangThai) {
        this.maTP = maTP;
        this.maLoaiTP = maLoaiTP;
        this.tenTP = tenTP;
        this.ngayNhap = ngayNhap;
        this.giaNhap = giaNhap;
        this.soLuong = soLuong;
        this.donViTinh = donViTinh;
        this.maNV = maNV;
        this.NCC = NCC;
        this.ghiChu = ghiChu;
        this.trangThai = trangThai;
    }

    public ThucPham() {
    }

    public int getMaTP() {
        return maTP;
    }

    public void setMaTP(int maTP) {
        this.maTP = maTP;
    }

    public String getMaLoaiTP() {
        return maLoaiTP;
    }

    public void setMaLoaiTP(String maLoaiTP) {
        this.maLoaiTP = maLoaiTP;
    }

    public String getTenTP() {
        return tenTP;
    }

    public void setTenTP(String tenTP) {
        this.tenTP = tenTP;
    }

    public String getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(String ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public int getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(int giaNhap) {
        this.giaNhap = giaNhap;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getDonViTinh() {
        return donViTinh;
    }

    public void setDonViTinh(String donViTinh) {
        this.donViTinh = donViTinh;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getNCC() {
        return NCC;
    }

    public void setNCC(String NCC) {
        this.NCC = NCC;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "ThucPham{" + "maTP=" + maTP + ", maLoaiTP=" + maLoaiTP + ", tenTP=" + tenTP + ", ngayNhap=" + ngayNhap + ", giaNhap=" + giaNhap + ", soLuong=" + soLuong + ", donViTinh=" + donViTinh + ", maNV=" + maNV + ", NCC=" + NCC + ", ghiChu=" + ghiChu + ", trangThai=" + trangThai + '}';
    }

    
}
