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
        String accountPassword = "thaiad1121";
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

    public static void sendExcelNL(String mailTo, File f, Date d) {
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
        String accountPassword = "thaiad1121";
//        String accountName = "minhdungvipro@gmail.com";
//        String accountPassword = "redsun1803";
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
            message.setSubject("Thông báo mua nguyên liệu ngày " + Xdate.toString(d, "dd/MM/yyyy"));
            message.setText("Hệ thống quản lý nhà hàng");

            MimeBodyPart contentPart = new MimeBodyPart();
            contentPart.setContent("Exception", "text/html; charser=utf-8");
            MimeBodyPart filePart = new MimeBodyPart();
//            File f = new File("excels/nguyenlieu.xlsx");
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
        String accountPassword = "thaiad1121";
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
        String accountPassword = "thaiad1121";
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

    public static void sendThanks(String mailTo, String name) {
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
        String accountPassword = "thaiad1121";
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
            message.setSubject("LỜI CẢM ƠN!");
            message.setText("Chào " + name + "!"
                    + "\n"
                    + "Lời đầu tiên, nhà hàng xin gửi lời cảm ơn chân thành và sâu sắc nhất đến quý khách hàng đã tin tưởng hợp tác và sử dụng dịch vụ của chúng tôi. Sự tin tưởng của quý vị đã góp phần lớn quyết định sự phát triển và thành công của nhà hàng trong thời gian qua.\n"
                    + "\n"
                    + "Với phương châm “Vì sự hài lòng cao nhất của khách hàng”, trong những năm qua, nhà hàng chúng tôi đã và đang không ngừng phấn đấu, nâng cao chất lượng dịch vụ để mang lại nhiều lợi ích hơn và có thể đáp ứng mọi yêu cầu của khách hàng.\n"
                    + "\n"
                    + "Chúng tôi luôn biết rằng, sự ủng hộ và sự tin yêu của quý khách hàng là tài sản vô giá với Nhà hàng chúng tôi, để đạt được điều này chúng tôi luôn nỗ lực không ngừng, hướng tới mục tiêu phát triển bền vững, chú trọng xây dựng các chính sách chăm sóc khách hàng,mang lại những giá trị thiết thực để luôn làm hài lòng khách hàng ở mức cao nhất nhằm đáp lại tình cảm và sự tin yêu của quý khách.\n"
                    + "\n"
                    + "Một lần nữa, nhà hàng chúng tôi xin được gửi lời cảm ơn chân thành đến quý khách hàng đã không ngừng quan tâm và luôn đồng hành cùng nhà hàng trong suốt thời gian qua. Chúng tôi cũng hy vọng trong thời gian tới sẽ tiếp tục nhận được sự quan tâm và tín nhiệm của quý khách.\n"
                    + "\n"
                    + "Xin chúc quý khách hàng và gia đình có nhiều sức khỏe, may mắn và thành công\n"
                    + "\n"
                    + "Trân trọng!");

            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void sendLichDat(String mailTo, String name, String time, String ban) {
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
        String accountPassword = "thaiad1121";
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
            message.setSubject("LỜI NHẮC!");
            message.setText("Chào " + name + "!"
                    + "\n"
                    + "Lời đầu tiên, nhà hàng xin gửi lời cảm ơn chân thành và sâu sắc nhất đến quý khách hàng đã tin tưởng hợp tác và sử dụng dịch vụ của chúng tôi.\n"
                    + "\n"
                    + "Và xin kính nhắc quý khách hàng bỏ lỡ lịch hẹn vào lúc: " + time + " tại bàn " + ban + " của nhà hàng chúng tôi.\n"
                    + "\n"
                    + "Xin chúc quý khách hàng và gia đình có nhiều sức khỏe, may mắn và thành công\n"
                    + "\n"
                    + "Trân trọng!");

            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void sendBirthday(String mailTo, String name) {
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
        String accountPassword = "thaiad1121";
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
            message.setSubject("CHÚC MỪNG SINH NHẬT!");
            message.setText("Chúc mừng sinh nhật " + name + "!");

            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
