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
public class BanDat {

    private String maBan;
    private String gioDat;
    private int maKH;
    private String maNV;

    public BanDat(String maBan, String gioDat, int maKH, String maNV) {
        this.maBan = maBan;
        this.gioDat = gioDat;
        this.maKH = maKH;
        this.maNV = maNV;
    }

    public String getMaBan() {
        return maBan;
    }

    public void setMaBan(String maBan) {
        this.maBan = maBan;
    }

    public String getGioDat() {
        return gioDat;
    }

    public void setGioDat(String gioDat) {
        this.gioDat = gioDat;
    }

    public int getMaKH() {
        return maKH;
    }

    public void setMaKH(int maKH) {
        this.maKH = maKH;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    @Override
    public String toString() {
        return "BanDat{" + "maBan=" + maBan + ", gioDat=" + gioDat + ", maKH=" + maKH + ", maNV=" + maNV + '}';
    }

}
