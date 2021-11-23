/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Untils;

import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author doanp
 */
public class MsgBox {
    
    public static void alert(Component parent, String mess) {
        JOptionPane.showMessageDialog(parent, mess, "Hệ thống quản lý nhà hàng", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/boxIcon.png"));
    }

    public static boolean confirm(Component parent, String mess) {
        int result = JOptionPane.showConfirmDialog(parent, mess, "Hệ thống quản lý nhà hàng", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon("images/boxIcon.png"));
        return result == JOptionPane.YES_OPTION;
    }

    public static String promt(Component parent, String mess) {
        return (String) JOptionPane.showInputDialog(parent, mess, "Hệ thống quản lý nhà hàng", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("images/boxIcon.png"),null,null);
    }
}
