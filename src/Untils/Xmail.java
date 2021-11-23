/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Untils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Properties;
import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.*;

/**
 *
 * @author doanp
 */
public class Xmail {

    public static void writeException(Exception ex, Object... args) {
        try {
            File f = new File("bugs/errors.txt");
            FileWriter fw = new FileWriter(f);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            ex.printStackTrace(pw);
            pw.write(Xdate.toString(new Date(), "dd/MM/yyyy hh:mm:ss a"));
            String x = "";
            for (int i = 0; i < args.length; i++) {
                x += args[i];
            }
            pw.write("\nGiá trị nhập vào:\n" + x);
            pw.close();
        } catch (Exception e) {
        }
    }

    public static void sendBugs(String mailTo) {
        Properties props = new Properties();

        props.put("mail.smtp.user", "username");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "25");
        props.put("mail.debug", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.EnableSSL.enable", "true");

        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        //----------------------------
        String accountName = "thaidpph17321@fpt.edu.vn";
        String accountPassword = "thaiad1121@";
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(accountName, accountPassword);
            }
        });
        //----------------------------
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("thaidpph17321@fpt.edu.vn"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(mailTo));
            message.setSubject("Báo lỗi hệ thống quản lý nhà hàng");
            message.setText("Hệ thống quản lý nhà hàng");

            MimeBodyPart contentPart = new MimeBodyPart();
            contentPart.setContent("Exception", "text/html; charser=utf-8");
            MimeBodyPart filePart = new MimeBodyPart();
            File f = new File("bugs/errors.txt");
            FileDataSource fds = new FileDataSource(f);
            filePart.setDataHandler(new DataHandler(fds));
            filePart.setFileName(f.getName());
            MimeMultipart mm = new MimeMultipart();
            mm.addBodyPart(contentPart);
            mm.addBodyPart(filePart);

            message.setContent(mm);
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void sendPassword(String user, String passWord, String mailTo) {
        Properties props = new Properties();

        props.put("mail.smtp.user", "username");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "25");
        props.put("mail.debug", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.EnableSSL.enable", "true");

        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        //----------------------------
        String accountName = "thaidpph17321@fpt.edu.vn";
        String accountPassword = "thaiad1121@";
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(accountName, accountPassword);
            }
        });
        //----------------------------
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("thaidpph17321@fpt.edu.vn"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(mailTo));
            message.setSubject("Tên đăng nhập \"" + user + "\" có mật khẩu là: " + passWord);
            message.setText("Hệ thống quản lý nhà hàng");

            MimeBodyPart contentPart = new MimeBodyPart();
            contentPart.setContent("Password", "text/html; charser=utf-8");
            MimeMultipart mm = new MimeMultipart();
            mm.addBodyPart(contentPart);

            message.setContent(mm);
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void sendCode(int code, String mailTo) {
        Properties props = new Properties();

        props.put("mail.smtp.user", "username");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "25");
        props.put("mail.debug", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.EnableSSL.enable", "true");

        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        //----------------------------
        String accountName = "thaidpph17321@fpt.edu.vn";
        String accountPassword = "thaiad1121@";
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(accountName, accountPassword);
            }
        });
        //----------------------------
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("thaidpph17321@fpt.edu.vn"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(mailTo));
            message.setSubject("Mã xác thực quên mật khẩu của bạn là: " + code);
            message.setText("Hệ thống quản lý nhà hàng");

            MimeBodyPart contentPart = new MimeBodyPart();
            contentPart.setContent("Code", "text/html; charser=utf-8");
            MimeMultipart mm = new MimeMultipart();
            mm.addBodyPart(contentPart);

            message.setContent(mm);
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
