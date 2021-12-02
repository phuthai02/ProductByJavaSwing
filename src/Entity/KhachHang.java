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
public class KhachHang {

    private int maKH;
    private String tenKH;
    private String SDT;
    private String Email;
    private Date ngaysinh;
    private boolean gioiTinh;
    private String maNV;    
    private boolean trangThai;

    public KhachHang(String tenKH, String SDT, String Email, Date ngaysinh, boolean gioiTinh, String maNV, boolean trangThai) {
        this.tenKH = tenKH;
        this.SDT = SDT;
        this.Email = Email;
        this.ngaysinh = ngaysinh;
        this.gioiTinh = gioiTinh;
        this.maNV = maNV;
        this.trangThai = trangThai;
    }

    public KhachHang(int maKH, String tenKH, String SDT, String Email, Date ngaysinh, boolean gioiTinh, String maNV, boolean trangThai) {
        this.maKH = maKH;
        this.tenKH = tenKH;
        this.SDT = SDT;
        this.Email = Email;
        this.ngaysinh = ngaysinh;
        this.gioiTinh = gioiTinh;
        this.maNV = maNV;
        this.trangThai = trangThai;
    }

    public KhachHang() {
    }
    
    public int getMaKH() {
        return maKH;
    }

    public void setMaKH(int maKH) {
        this.maKH = maKH;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
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

    public Date getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(Date ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
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
        return "KhachHang{" + "maKH=" + maKH + ", tenKH=" + tenKH + ", SDT=" + SDT + ", Email=" + Email + ", ngaysinh=" + ngaysinh + ", gioiTinh=" + gioiTinh + ", maNV=" + maNV + ", trangThai=" + trangThai + '}';
    }
}
