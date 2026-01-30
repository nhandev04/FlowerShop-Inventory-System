package com.flowershop.view;

import com.flowershop.dao.impl.ReportDAO;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.Map;

public class CustomerChartPanel extends JPanel {

    private ReportDAO reportDAO;
    private PiePlot plot;

    public CustomerChartPanel() {
        reportDAO = new ReportDAO();
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.WHITE);
        JLabel lblTitle = new JLabel("PHÂN LOẠI KHÁCH HÀNG THEO DOANH SỐ");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(new Color(0, 51, 102));
        titlePanel.add(lblTitle);
        add(titlePanel, BorderLayout.NORTH);

        DefaultPieDataset dataset = createDataset();

        JFreeChart chart = ChartFactory.createPieChart(
                null,
                dataset,
                true,
                true,
                false
        );

        chart.setBackgroundPaint(Color.WHITE);

        plot = (PiePlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setOutlineVisible(false);
        plot.setShadowPaint(null);
        plot.setSectionPaint("Khách VIP (>10tr)", new Color(255, 193, 7));
        plot.setSectionPaint("Khách Thân Thiết (>2tr)", new Color(33, 150, 243));
        plot.setSectionPaint("Khách Vãng Lai / Mới", new Color(158, 158, 158));

        PieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator(
                "{0}: {2} ({1} khách)",
                new DecimalFormat("0"),
                new DecimalFormat("0.0%"));
        plot.setLabelGenerator(labelGenerator);
        plot.setLabelFont(new Font("Segoe UI", Font.PLAIN, 12));
        plot.setLabelBackgroundPaint(new Color(255, 255, 255, 200));
        plot.setLabelOutlinePaint(null);
        plot.setLabelShadowPaint(null);

        chart.getLegend().setItemFont(new Font("Segoe UI", Font.PLAIN, 12));
        chart.getLegend().setBackgroundPaint(Color.WHITE);
        chart.getLegend().setFrame(org.jfree.chart.block.BlockBorder.NONE);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(900, 550));
        chartPanel.setBackground(Color.WHITE);
        add(chartPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton btnRefresh = new JButton("Cập nhật số liệu");
        btnRefresh.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnRefresh.setBackground(new Color(0, 102, 204));
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.setFocusPainted(false);
        btnRefresh.addActionListener(e -> {
            DefaultPieDataset newDataset = createDataset();
            plot.setDataset(newDataset);
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(btnRefresh);
        bottomPanel.add(buttonPanel, BorderLayout.CENTER);

        JLabel lblInfo = new JLabel("<html><i>Phân loại dựa trên tổng doanh số mua hàng của khách hàng</i></html>");
        lblInfo.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblInfo.setForeground(Color.GRAY);
        lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
        bottomPanel.add(lblInfo, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private DefaultPieDataset createDataset() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        Map<String, Integer> data = reportDAO.getCustomerSegmentation();

        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue());
        }
        return dataset;
    }
}