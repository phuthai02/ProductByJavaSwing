/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

/**
 *
 * @author Admin
 */
public class LoaiThucPham {
    private String maLoaiTP;
    private String tenLoaiTP;

    public LoaiThucPham() {
    }

    public LoaiThucPham(String maLoaiTP, String tenLoaiTP) {
        this.maLoaiTP = maLoaiTP;
        this.tenLoaiTP = tenLoaiTP;
    }

    public String getMaLoaiTP() {
        return maLoaiTP;
    }

    public void setMaLoaiTP(String maLoaiTP) {
        this.maLoaiTP = maLoaiTP;
    }

    public String getTenLoaiTP() {
        return tenLoaiTP;
    }

    public void setTenLoaiTP(String tenLoaiTP) {
        this.tenLoaiTP = tenLoaiTP;
    }
    
}
