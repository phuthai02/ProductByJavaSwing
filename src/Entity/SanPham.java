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
public class SanPham {
    private String maSP;
    private String maLoaiSP;
    private String tenSanPham;
    private int donGia;
    private String donViTinh;
    private String anhSP;
    private String maNV;
    private String chiTiet;
    private boolean trangThai;

    public SanPham(String maSP, String maLoaiSP, String tenSanPham, int donGia, String donViTinh, String anhSP, String maNV, String chiTiet, boolean trangThai) {
        this.maSP = maSP;
        this.maLoaiSP = maLoaiSP;
        this.tenSanPham = tenSanPham;
        this.donGia = donGia;
        this.donViTinh = donViTinh;
        this.anhSP = anhSP;
        this.maNV = maNV;
        this.chiTiet = chiTiet;
        this.trangThai = trangThai;
    }

    public SanPham() {
    }
    
    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getMaLoaiSP() {
        return maLoaiSP;
    }

    public void setMaLoaiSP(String taLoaiSP) {
        this.maLoaiSP = taLoaiSP;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public int getDonGia() {
        return donGia;
    }

    public void setDonGia(int donGia) {
        this.donGia = donGia;
    }

    public String getDonViTinh() {
        return donViTinh;
    }

    public void setDonViTinh(String donViTinh) {
        this.donViTinh = donViTinh;
    }

    public String getAnhSP() {
        return anhSP;
    }

    public void setAnhSP(String anhSP) {
        this.anhSP = anhSP;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getChiTiet() {
        return chiTiet;
    }

    public void setChiTiet(String chiTiet) {
        this.chiTiet = chiTiet;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }
    
    @Override
    public String toString() {
        return "SanPham{" + "maSP=" + maSP + ", taLoaiSP=" + maLoaiSP + ", tenSanPham=" + tenSanPham + ", donGia=" + donGia + ", donViTinh=" + donViTinh + ", anhSP=" + anhSP + ", maNV=" + maNV + ", chiTiet=" + chiTiet + ", trangThai=" + trangThai + '}';
    }
    
}
