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
        public static String toCurrency(int tienTe){
            Locale lc = new Locale("vi","VN");
            NumberFormat nf = NumberFormat.getCurrencyInstance(lc);
            return  nf.format(tienTe);       
        }
}
