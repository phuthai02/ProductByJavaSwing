/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Untils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author doanp
 */
public class Xjfreechart {

    public static JFreeChart createChart(CategoryDataset dataset, String titel, String titleX, String titleY) {
        JFreeChart barChart = ChartFactory.createBarChart(
                titel,
                titleX, titleY,
                dataset, PlotOrientation.VERTICAL, false, false, false);
        return barChart;
    }

    public static CategoryDataset createDataset(List<Object[]> lst, int dataX, int dataY, String typeData) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        lst.forEach((obj) -> {
            dataset.addValue((int) obj[dataY], typeData, obj[dataX].toString());
        });
        return dataset;
    }

    public static ChartPanel chartBaoCao(List<Object[]> lst, String title, String titleX, String titleY, int dataX, int dataY, String typeData) {
        JFreeChart pieChart = createChart(createDataset(lst, dataX, dataY, typeData), title, titleX, titleY);
        ChartPanel chartPanel = new ChartPanel(pieChart);
        return chartPanel;
    }
    

}
