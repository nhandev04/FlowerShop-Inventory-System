package com.flowershop.view;

import com.flowershop.config.DatabaseConnection;
import com.flowershop.view.observer.ShopEventManager;
import com.flowershop.view.observer.ShopObserver;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class RevenuePanel extends JPanel implements ShopObserver {

    private JPanel cardsPanel;
    private JPanel chartsPanel;
    private JLabel lblTotalRevenue, lblTotalOrders, lblTotalProducts;

    public RevenuePanel() {
        initComponents();
        loadDashboardData();
        ShopEventManager.getInstance().subscribe(this);
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(new Color(245, 245, 245)); // Màu nền xám nhẹ hiện đại

        JLabel lblTitle = new JLabel("TỔNG QUAN KINH DOANH (DASHBOARD)");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(new Color(50, 50, 50));
        add(lblTitle, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(0, 20));
        centerPanel.setOpaque(false);

        cardsPanel = new JPanel(new GridLayout(1, 3, 20, 0)); // 1 hàng, 3 cột, cách nhau 20px
        cardsPanel.setOpaque(false);

        lblTotalRevenue = createCard(cardsPanel, "TỔNG DOANH THU", new Color(0, 153, 76));
        lblTotalOrders = createCard(cardsPanel, "TỔNG ĐƠN HÀNG", new Color(0, 102, 204));
        lblTotalProducts = createCard(cardsPanel, "SẢN PHẨM ĐÃ BÁN", new Color(204, 102, 0));

        centerPanel.add(cardsPanel, BorderLayout.NORTH);

        chartsPanel = new JPanel(new GridLayout(1, 2, 20, 0)); // 1 hàng, 2 biểu đồ
        chartsPanel.setOpaque(false);
        centerPanel.add(chartsPanel, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        JButton btnRefresh = new JButton("Cập nhật dữ liệu");
        btnRefresh.addActionListener(e -> loadDashboardData());
        add(btnRefresh, BorderLayout.SOUTH);
    }

    private JLabel createCard(JPanel parent, String title, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createMatteBorder(0, 0, 4, 0, color)); // Viền màu dưới đáy

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblTitle.setForeground(Color.GRAY);
        lblTitle.setBorder(new EmptyBorder(10, 20, 5, 20));

        JLabel lblValue = new JLabel("Loading...");
        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblValue.setForeground(color);
        lblValue.setBorder(new EmptyBorder(0, 20, 20, 20));

        card.add(lblTitle, BorderLayout.NORTH);
        card.add(lblValue, BorderLayout.CENTER);
        parent.add(card);

        return lblValue;
    }

    private void loadDashboardData() {
        loadSummaryCards();
        loadCharts();
    }

    private void loadSummaryCards() {
        String sql = "SELECT " +
                "(SELECT SUM(TotalAmount) FROM SalesOrders WHERE Status = 'Completed') as TotalRev, " +
                "(SELECT COUNT(*) FROM SalesOrders) as TotalOrd, " +
                "(SELECT ISNULL(SUM(Quantity), 0) FROM SalesOrderDetails) as TotalProd";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                DecimalFormat df = new DecimalFormat("#,### VND");
                lblTotalRevenue.setText(df.format(rs.getDouble("TotalRev")));
                lblTotalOrders.setText(rs.getInt("TotalOrd") + " Đơn");
                lblTotalProducts.setText(rs.getInt("TotalProd") + " Sản phẩm");
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void loadCharts() {
        chartsPanel.removeAll();

        DefaultCategoryDataset lineDataset = new DefaultCategoryDataset();
        String sqlLine = "SELECT CAST(OrderDate AS DATE) as SaleDate, SUM(TotalAmount) as Revenue " +
                "FROM SalesOrders WHERE Status = 'Completed' " +
                "GROUP BY CAST(OrderDate AS DATE) ORDER BY SaleDate ASC";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlLine);
             ResultSet rs = ps.executeQuery()) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");
            while (rs.next()) {
                lineDataset.addValue(rs.getDouble("Revenue"), "Doanh Thu", sdf.format(rs.getDate("SaleDate")));
            }
        } catch (Exception e) { e.printStackTrace(); }

        JFreeChart lineChart = ChartFactory.createLineChart(
                "Xu Hướng Doanh Thu", "Ngày", "VND",
                lineDataset, PlotOrientation.VERTICAL, false, true, false);
        customizeChart(lineChart);

        CategoryPlot plot = lineChart.getCategoryPlot();
        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
        renderer.setSeriesShapesVisible(0, true);
        renderer.setSeriesPaint(0, new Color(0, 153, 76));

        DefaultPieDataset pieDataset = new DefaultPieDataset();
        String sqlPie = "SELECT c.CategoryName, SUM(d.Quantity * d.UnitPrice) as Total " +
                "FROM SalesOrderDetails d " +
                "JOIN Products p ON d.ProductID = p.ProductID " +
                "JOIN Categories c ON p.CategoryID = c.CategoryID " +
                "GROUP BY c.CategoryName";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlPie);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                pieDataset.setValue(rs.getString("CategoryName"), rs.getDouble("Total"));
            }
        } catch (Exception e) { e.printStackTrace(); }

        JFreeChart pieChart = ChartFactory.createPieChart("Tỷ Trọng Doanh Thu", pieDataset, true, true, false);
        customizePieChart(pieChart);

        chartsPanel.add(new ChartPanel(lineChart));
        chartsPanel.add(new ChartPanel(pieChart));
        chartsPanel.validate();
        chartsPanel.repaint();
    }

    private void customizeChart(JFreeChart chart) {
        chart.setBackgroundPaint(Color.WHITE);
        chart.setBorderVisible(false);
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);
        plot.setOutlineVisible(false);
    }

    private void customizePieChart(JFreeChart chart) {
        chart.setBackgroundPaint(Color.WHITE);
        chart.setBorderVisible(false);
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setOutlineVisible(false);
        plot.setLabelBackgroundPaint(Color.WHITE);
    }

    @Override
    public void updateData(String eventType) {
        if (eventType.equals("ORDER_CREATED")) {
            loadDashboardData();
        }
    }

    @Override
    public void removeNotify() {
        super.removeNotify();
        ShopEventManager.getInstance().unsubscribe(this);
    }
}