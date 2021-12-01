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
public class BanCho {

    private String maBan;
    private String maSP;
    private int soLuong;
    private int gia;
    private String gioVao;
    private String maNV;
    private boolean hoanThanh;

    public BanCho(String maBan, String maSP, int soLuong, int gia, String gioVao, String maNV, boolean hoanThanh) {
        this.maBan = maBan;
        this.maSP = maSP;
        this.soLuong = soLuong;
        this.gia = gia;
        this.gioVao = gioVao;
        this.maNV = maNV;
        this.hoanThanh = hoanThanh;
    }

    public String getMaBan() {
        return maBan;
    }

    public void setMaBan(String maBan) {
        this.maBan = maBan;
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

    public String getGioVao() {
        return gioVao;
    }

    public void setGioVao(String gioVao) {
        this.gioVao = gioVao;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public boolean isHoanThanh() {
        return hoanThanh;
    }

    public void setHoanThanh(boolean hoanThanh) {
        this.hoanThanh = hoanThanh;
    }

    @Override
    public String toString() {
        return "BanCho{" + "maBan=" + maBan + ", maSP=" + maSP + ", soLuong=" + soLuong + ", gia=" + gia + ", gioVao=" + gioVao + ", maNV=" + maNV + ", hoanThanh=" + hoanThanh + '}';
    }

   
}
