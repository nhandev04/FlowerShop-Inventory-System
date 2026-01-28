package com.flowershop.view;

import com.flowershop.dao.impl.ReportDAO;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.chart.util.Rotation;
import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class CustomerChartPanel extends JPanel {

    private ReportDAO reportDAO;

    public CustomerChartPanel() {
        reportDAO = new ReportDAO();
        setLayout(new BorderLayout());

        DefaultPieDataset dataset = createDataset();

        JFreeChart chart = ChartFactory.createPieChart3D(
                "PHÂN LOẠI KHÁCH HÀNG THEO DOANH SỐ",
                dataset,
                true,
                true,
                false
        );

        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.8f);
        plot.setSectionPaint("Khách VIP (>10tr)", new Color(255, 215, 0));
        plot.setSectionPaint("Khách Thân Thiết (>2tr)", new Color(0, 191, 255));
        plot.setSectionPaint("Khách Vãng Lai / Mới", new Color(192, 192, 192));

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 500));
        add(chartPanel, BorderLayout.CENTER);

        JButton btnRefresh = new JButton("Cập nhật số liệu");
        btnRefresh.addActionListener(e -> {
            DefaultPieDataset newDataset = createDataset();
            plot.setDataset(newDataset);
        });
        add(btnRefresh, BorderLayout.SOUTH);
    }

    private DefaultPieDataset createDataset() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        Map<String, Integer> data = reportDAO.getCustomerSegmentation();

        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            dataset.setValue(entry.getKey() + " (" + entry.getValue() + ")", entry.getValue());
        }
        return dataset;
    }
}