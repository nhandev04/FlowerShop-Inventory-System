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
    private JComboBox<String> cboPeriod;
    private JLabel lblTotalCustomers;
    private JLabel lblTotalRevenue;

    public CustomerChartPanel() {
        reportDAO = new ReportDAO();
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Title Panel
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));

        JLabel lblTitle = new JLabel("PHÂN LOẠI KHÁCH HÀNG THEO DOANH SỐ");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(new Color(0, 51, 102));
        titlePanel.add(lblTitle, BorderLayout.CENTER);

        // Filter Panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        filterPanel.setBackground(Color.WHITE);
        filterPanel.add(new JLabel("Thời gian:"));

        cboPeriod = new JComboBox<>(new String[] { "Tất cả", "Tháng này", "Quý này", "Năm nay" });
        cboPeriod.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cboPeriod.addActionListener(e -> refreshChart());
        filterPanel.add(cboPeriod);

        titlePanel.add(filterPanel, BorderLayout.EAST);
        add(titlePanel, BorderLayout.NORTH);

        // Chart
        DefaultPieDataset dataset = createDataset();

        JFreeChart chart = ChartFactory.createPieChart(
                null,
                dataset,
                true,
                true,
                false);

        chart.setBackgroundPaint(Color.WHITE);

        plot = (PiePlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setOutlineVisible(false);
        plot.setShadowPaint(null);
        plot.setSectionPaint("Khách VIP (≥10tr)", new Color(255, 193, 7));
        plot.setSectionPaint("Khách Thân Thiết (≥2tr)", new Color(33, 150, 243));
        plot.setSectionPaint("Khách Vãng Lai (<2tr)", new Color(158, 158, 158));

        PieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator(
                "{0}: {2}", // Show segment name and percentage
                new DecimalFormat("0"),
                new DecimalFormat("0.0%"));
        plot.setLabelGenerator(labelGenerator);
        plot.setLabelFont(new Font("Segoe UI", Font.BOLD, 11));
        plot.setLabelBackgroundPaint(new Color(255, 255, 255, 220));
        plot.setLabelOutlinePaint(new Color(200, 200, 200));
        plot.setLabelShadowPaint(null);

        chart.getLegend().setItemFont(new Font("Segoe UI", Font.PLAIN, 12));
        chart.getLegend().setBackgroundPaint(Color.WHITE);
        chart.getLegend().setFrame(org.jfree.chart.block.BlockBorder.NONE);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(900, 500));
        chartPanel.setBackground(Color.WHITE);
        add(chartPanel, BorderLayout.CENTER);

        // Bottom Panel with stats and button
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Stats Panel
        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 5));
        statsPanel.setBackground(Color.WHITE);

        lblTotalCustomers = new JLabel("Tổng KH: 0");
        lblTotalCustomers.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTotalCustomers.setForeground(new Color(0, 102, 204));

        lblTotalRevenue = new JLabel("Tổng DS: 0 VND");
        lblTotalRevenue.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTotalRevenue.setForeground(new Color(0, 153, 76));

        statsPanel.add(lblTotalCustomers);
        statsPanel.add(new JLabel("|"));
        statsPanel.add(lblTotalRevenue);

        bottomPanel.add(statsPanel, BorderLayout.NORTH);

        // Button and info
        JPanel buttonInfoPanel = new JPanel(new BorderLayout());
        buttonInfoPanel.setBackground(Color.WHITE);

        JButton btnRefresh = new JButton("Cập nhật số liệu");
        btnRefresh.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnRefresh.setBackground(new Color(0, 102, 204));
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.setFocusPainted(false);
        btnRefresh.addActionListener(e -> refreshChart());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(btnRefresh);
        buttonInfoPanel.add(buttonPanel, BorderLayout.CENTER);

        JLabel lblInfo = new JLabel("<html><i>Phân loại: VIP ≥10tr | Thân thiết ≥2tr | Vãng lai <2tr</i></html>");
        lblInfo.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblInfo.setForeground(Color.GRAY);
        lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
        buttonInfoPanel.add(lblInfo, BorderLayout.SOUTH);

        bottomPanel.add(buttonInfoPanel, BorderLayout.CENTER);

        add(bottomPanel, BorderLayout.SOUTH);

        updateStats();
    }

    private DefaultPieDataset createDataset() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        String period = getPeriodCode();
        Map<String, double[]> data = reportDAO.getCustomerSegmentationDetailed(period);

        for (Map.Entry<String, double[]> entry : data.entrySet()) {
            int count = (int) entry.getValue()[0];
            dataset.setValue(entry.getKey(), count);
        }
        return dataset;
    }

    private void refreshChart() {
        DefaultPieDataset newDataset = createDataset();
        plot.setDataset(newDataset);
        updateStats();
    }

    private void updateStats() {
        String period = getPeriodCode();
        Map<String, double[]> data = reportDAO.getCustomerSegmentationDetailed(period);

        int totalCustomers = 0;
        double totalRevenue = 0;

        for (double[] values : data.values()) {
            totalCustomers += (int) values[0];
            totalRevenue += values[1];
        }

        DecimalFormat df = new DecimalFormat("#,###");
        lblTotalCustomers.setText("Tổng KH: " + totalCustomers);
        lblTotalRevenue.setText("Tổng DS: " + df.format(totalRevenue) + " VND");
    }

    private String getPeriodCode() {
        String selected = (String) cboPeriod.getSelectedItem();
        switch (selected) {
            case "Tháng này":
                return "month";
            case "Quý này":
                return "quarter";
            case "Năm nay":
                return "year";
            default:
                return "all";
        }
    }
}
