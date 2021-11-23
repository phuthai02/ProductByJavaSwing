/*
 * To change this license header; choose License Headers in Project Properties.
 * To change this template file; choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

/**
 *
 * @author doanp
 */
public class NhanVien {

    private String maNV;
    private String tenNV;
    private String matKhau;
    private String diaChi;
    private String SDT;
    private String Email;
    private String ngaySinh;
    private boolean gioiTinh;
    private String anhNV;
    private String mauNen;
    private boolean vaiTro;
    private boolean trangThai;

    public NhanVien(String maNV, String tenNV, String matKhau, String diaChi, String SDT, String Email, String ngaySinh, boolean gioiTinh, String anhNV, String mauNen, boolean vaiTro, boolean trangThai) {
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.matKhau = matKhau;
        this.diaChi = diaChi;
        this.SDT = SDT;
        this.Email = Email;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.anhNV = anhNV;
        this.mauNen = mauNen;
        this.vaiTro = vaiTro;
        this.trangThai = trangThai;
    }

    public NhanVien() {
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getAnhNV() {
        return anhNV;
    }

    public void setAnhNV(String anhNV) {
        this.anhNV = anhNV;
    }

    public String getMauNen() {
        return mauNen;
    }

    public void setMauNen(String mauNen) {
        this.mauNen = mauNen;
    }

    public boolean isVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(boolean vaiTro) {
        this.vaiTro = vaiTro;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return this.maNV+" - "+this.tenNV;
    }

   
}
