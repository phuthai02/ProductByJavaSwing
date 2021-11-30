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
public class Ban {

    private String maBan;
    private String tenBan;
    private String viTri;
    private boolean trangThai;
    private boolean sanSang;

    public void setMaBan(String maBan) {
        this.maBan = maBan;
    }

    public void setTenBan(String tenBan) {
        this.tenBan = tenBan;
    }

    public void setViTri(String viTri) {
        this.viTri = viTri;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public void setSanSang(boolean sanSang) {
        this.sanSang = sanSang;
    }

    public String getMaBan() {
        return maBan;
    }

    public String getTenBan() {
        return tenBan;
    }

    public String getViTri() {
        return viTri;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public boolean isSanSang() {
        return sanSang;
    }

    public Ban(String maBan, String tenBan, String viTri, boolean trangThai, boolean sanSang) {
        this.maBan = maBan;
        this.tenBan = tenBan;
        this.viTri = viTri;
        this.trangThai = trangThai;
        this.sanSang = sanSang;
    }

    @Override
    public String toString() {
        return "Ban{" + "maBan=" + maBan + ", tenBan=" + tenBan + ", viTri=" + viTri + ", trangThai=" + trangThai + ", sanSang=" + sanSang + '}';
    }
    

}
