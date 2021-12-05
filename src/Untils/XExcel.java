/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Untils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author doanp
 */
public class XExcel {

    public static void exportExcel(String excelFilePath, String nameSheet, String[] header, ResultSet rs) throws SQLException, FileNotFoundException, IOException {
        Workbook workbook = null;
        if (excelFilePath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook();
        } else if (excelFilePath.endsWith("xls")) {
            workbook = new HSSFWorkbook();
        }
        if (workbook != null) {
            Sheet sheet = workbook.createSheet(nameSheet);

            Row row = sheet.createRow(0);
            for (int i = 0; i < header.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(header[i]);
            }

            int rowIndex = 1;
            while (rs.next()) {
                Row rows = sheet.createRow(rowIndex);
                for (int i = 0; i < header.length; i++) {
                    Cell cell = rows.createCell(i);
                    cell.setCellValue(rs.getString(i + 1));
                }
                rowIndex++;
            }

            int countColunm = sheet.getRow(0).getPhysicalNumberOfCells();
            for (int columnIndex = 0; columnIndex < countColunm; columnIndex++) {
                sheet.autoSizeColumn(columnIndex);
            }

            try (OutputStream os = new FileOutputStream(excelFilePath)) {
                workbook.write(os);
            }
        }
    }

}
