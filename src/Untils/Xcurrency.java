/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Untils;

import java.text.NumberFormat;
import java.util.Locale;

/**
 *
 * @author doanp
 */
public class Xcurrency {

    public static String toCurrency(int tienTe) {
        Locale lc = new Locale("vi", "VN");
        NumberFormat nf = NumberFormat.getCurrencyInstance(lc);
        return nf.format(tienTe);
    }

    public static int toInt(String tienTe) {
        String x = "";
        for (int i = 0; i < tienTe.length(); i++) {
            if (!tienTe.substring(i, i + 1).equals(".") && !tienTe.substring(i, i + 1).equals(" ") && !tienTe.substring(i, i + 1).equals("đ")) {
                x += tienTe.substring(i, i + 1);
            }
        }
        return Integer.parseInt(x);
    }
}
