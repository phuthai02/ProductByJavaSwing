/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Untils;

import java.util.Random;

/**
 *
 * @author doanp
 */
public class Xpassword {

    private static final Random random = new Random();
    private static final String lib = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789#?!@$%^&*-";

    public static String checkLevelPass(String pass) {
        String pUpper = "^.*[A-Z]+.*$";
        String pLower = "^.*[a-z]+.*$";
        String pDigit = "^.*[0-9]+.*$";
        String pSpecial = "^.*[#?!@$%^&*-]+.*$";
        int level = 0;
        if (pass.matches(pSpecial)) {
            level++;
        }
        if (pass.matches(pLower)) {
            level++;
        }
        if (pass.matches(pUpper)) {
            level++;
        }
        if (pass.matches(pDigit)) {
            level++;
        }

        switch (level) {
            case 1:
                return "Yếu";
            case 2:
                return "Trung bình";
            case 3:
                return "Khá mạnh";
            case 4:
                return "Mạnh";
        }
        return null;
    }

    public static int randomNumber(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }

    public static String randomPassword(int numberOfCharactor) {
        String str = "";
        for (int i = 0; i < numberOfCharactor; i++) {
            int number = randomNumber(0, lib.length() - 1);
            char ch = lib.charAt(number);
            str += ch;
        }
        return str;
    }

}
