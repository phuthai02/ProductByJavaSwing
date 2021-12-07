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

    private XYDataset createDatasetDT() {

        XYSeries series1 = new XYSeries("TỔNG THU");
        series1.add(18, 530);
        series1.add(20, 580);
        series1.add(25, 740);
        series1.add(30, 901);
        series1.add(40, 1300);
        series1.add(50, 2219);

        XYSeries series2 = new XYSeries("TỔNG CHI");
        series2.add(18, 567);
        series2.add(20, 612);
        series2.add(25, 800);
        series2.add(30, 980);
        series2.add(40, 1210);
        series2.add(50, 2350);

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);
        dataset.addSeries(series2);

        return dataset;
    }

    private JFreeChart createChart(final XYDataset dataset) {

        JFreeChart chart = ChartFactory.createXYLineChart(
                "THỐNG KÊ THU CHI NHÀ HÀNG",
                "THỜI GIAN",
                "SỐ TIỀN",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = chart.getXYPlot();

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));
        renderer.setSeriesPaint(1, Color.BLUE);
        renderer.setSeriesStroke(1, new BasicStroke(2.0f));

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);
        plot.setRangeGridlinesVisible(false);
        plot.setDomainGridlinesVisible(false);

        chart.getLegend().setFrame(BlockBorder.NONE);

        chart.setTitle(new TextTitle("THỐNG KÊ THU CHI NHÀ HÀNG",
                new Font("Arial", Font.BOLD, 24)
        )
        );

        return chart;
    }

}
