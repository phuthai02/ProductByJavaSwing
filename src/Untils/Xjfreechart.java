/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Untils;

import java.util.List;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

/**
 *
 * @author doanp
 */
public class Xjfreechart {

//    static BaoCaoDAO dao=new BaoCaoDAO();
    public static JFreeChart createChart(CategoryDataset dataset) {
        JFreeChart barChart = ChartFactory.createBarChart(
                "BIỂU ĐỒ DÂN SỐ VIỆT NAM",
                "Thành viên", "Doanh thu",
                dataset, PlotOrientation.VERTICAL, false, false, false);
        return barChart;
    }

    public static CategoryDataset createDataset(String date) {
//        DefaultPieDataset dataset = new DefaultPieDataset();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//        List<Object[]> list = dao.baoCaoSanPham(date);
//        for (Object[] x : list) {
//            dataset.setValue(x[0] + " - " + x[1], Integer.parseInt(x[2] + ""));
//        }
        dataset.addValue(100000, "Thái", "Hoa");
        dataset.addValue(100020, "Hoa", "Thái");
        dataset.addValue(300000, "Dương", "Dũng");
        dataset.addValue(900000, "Dũng", "Dương");
        dataset.addValue(900000, "Bích", "Bích");

        return dataset;
    }

    public static void chartBaoCao(String date) {
        JFreeChart pieChart = createChart(createDataset(date));
        ChartPanel chartPanel = new ChartPanel(pieChart);
        JFrame frame = new JFrame();
        frame.add(chartPanel);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        chartBaoCao("ccc");
    }

}
