/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Untils;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author doanp
 */
public class XBill {

    public static void exportBill(List<Object[]> lst) throws BadElementException, IOException {
        Document document = new Document();

        try {
            BaseFont bf = BaseFont.createFont("C:/Windows/Fonts/Arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            PdfWriter.getInstance(document, new FileOutputStream("hoaDon/" + lst.get(0)[0].toString() + ".pdf"));
            document.open();

            Image image = Image.getInstance("src/icon/daubep2.png");
            image.setAlignment(Element.ALIGN_CENTER);
            document.add(image);

            Paragraph heaDer = new Paragraph("HỆ THỐNG QUẢN LÝ NHÀ HÀNG\n", new Font(bf, 12, Font.ITALIC));
            heaDer.setAlignment(Element.ALIGN_CENTER);
            document.add(heaDer);
            Paragraph heaDer2 = new Paragraph("NHÓM 1 - BÍCH LÍT ĐỜ\n", new Font(bf, 10, Font.ITALIC));
            heaDer2.setAlignment(Element.ALIGN_CENTER);
            document.add(heaDer2);
            Paragraph heaDer3 = new Paragraph("PHIẾU THANH TOÁN\n", new Font(bf, 25, Font.BOLD));
            heaDer3.setAlignment(Element.ALIGN_CENTER);
            document.add(heaDer3);
            Paragraph heaDer4 = new Paragraph("(" + lst.get(0)[1].toString() + ")\n", new Font(bf, 15, Font.BOLD));
            heaDer4.setAlignment(Element.ALIGN_CENTER);
            document.add(heaDer4);

            Paragraph hr = new Paragraph("--------------------------------------------------------------------------------------------------------", new Font(bf, 15, Font.BOLD));
            hr.setAlignment(Element.ALIGN_CENTER);
            document.add(hr);
            Paragraph ban = new Paragraph("Bàn: " + lst.get(0)[2].toString(), new Font(bf, 15));
            document.add(ban);
            Paragraph tenKH = new Paragraph("Tên khách hàng: " + lst.get(0)[3].toString(), new Font(bf, 15));
            document.add(tenKH);
            Paragraph gioVao = new Paragraph("Giờ vào: " + lst.get(0)[4].toString(), new Font(bf, 15));
            document.add(gioVao);
            Paragraph gioRa = new Paragraph("Giờ ra: " + lst.get(0)[5].toString(), new Font(bf, 15));
            document.add(gioRa);
            Paragraph thuNgan = new Paragraph("Thu ngân: " + lst.get(0)[6].toString(), new Font(bf, 15));
            document.add(thuNgan);
            Paragraph phucVu = new Paragraph("Phục vụ: " + lst.get(0)[7].toString(), new Font(bf, 15));
            document.add(phucVu);
            Paragraph hr2 = new Paragraph("--------------------------------------------------------------------------------------------------------\n\n", new Font(bf, 15, Font.BOLD));
            hr2.setAlignment(Element.ALIGN_CENTER);
            document.add(hr2);

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);

            PdfPCell header1 = new PdfPCell(new Paragraph("STT", new Font(bf, 15, Font.BOLD)));
            PdfPCell header2 = new PdfPCell(new Paragraph("Tên sản phẩm", new Font(bf, 15, Font.BOLD)));
            PdfPCell header3 = new PdfPCell(new Paragraph("Số lượng", new Font(bf, 15, Font.BOLD)));
            PdfPCell header4 = new PdfPCell(new Paragraph("Đơn giá", new Font(bf, 15, Font.BOLD)));

            table.addCell(header1);
            table.addCell(header2);
            table.addCell(header3);
            table.addCell(header4);
            for (int i = 1; i < lst.size(); i++) {
                PdfPCell data1 = new PdfPCell(new Paragraph(i + "", new Font(bf, 15)));
                PdfPCell data2 = new PdfPCell(new Paragraph(lst.get(i)[0].toString(), new Font(bf, 15)));
                PdfPCell data3 = new PdfPCell(new Paragraph(lst.get(i)[1].toString(), new Font(bf, 15)));
                PdfPCell data4 = new PdfPCell(new Paragraph(lst.get(i)[2].toString(), new Font(bf, 15)));
                table.addCell(data1);
                table.addCell(data2);
                table.addCell(data3);
                table.addCell(data4);
            }
            document.add(table);
            Paragraph tienHang = new Paragraph("Cộng tiền hàng: " + lst.get(0)[8].toString(), new Font(bf, 15));
            document.add(tienHang);
            Paragraph tienGiam = new Paragraph("Giảm: " + lst.get(0)[9].toString(), new Font(bf, 15));
            document.add(tienGiam);
            Paragraph tongCong = new Paragraph("Thành tiền: " + lst.get(0)[10].toString(), new Font(bf, 15, Font.BOLD));
            document.add(tongCong);
            Paragraph thanhChu = new Paragraph("Thành chữ: " + lst.get(0)[11].toString(), new Font(bf, 15, Font.BOLD));
            document.add(thanhChu);
            document.add(hr);
            Paragraph thks = new Paragraph("Thank you!\n", new Font(bf, 15, Font.BOLD));
            thks.setAlignment(Element.ALIGN_CENTER);
            document.add(thks);
            document.close();
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
