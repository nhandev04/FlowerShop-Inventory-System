package com.flowershop.view;

import com.flowershop.model.dto.ProductDTO;
import com.flowershop.service.ProductService;
import com.flowershop.service.impl.ProductServiceImpl;
import com.flowershop.view.observer.ShopObserver;
import com.flowershop.view.observer.ShopEventManager;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StatsPanel extends JPanel implements ShopObserver {

    private final ProductService productService;
    private JPanel chartContainer;

    public StatsPanel() {
        this.productService = new ProductServiceImpl();
        initComponents();
        refreshChart();
        ShopEventManager.getInstance().subscribe(this);
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        JLabel lblTitle = new JLabel("THỐNG KÊ TỒN KHO THỜI GIAN THỰC", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(lblTitle, BorderLayout.NORTH);

        chartContainer = new JPanel(new BorderLayout());
        add(chartContainer, BorderLayout.CENTER);

        JButton btnRefresh = new JButton("Làm mới biểu đồ");
        btnRefresh.addActionListener(e -> refreshChart());
        add(btnRefresh, BorderLayout.SOUTH);
    }

    private void refreshChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<ProductDTO> products = productService.getAllProducts();

        for (ProductDTO p : products) {
            dataset.addValue(p.getReorderLevel(), "Tồn kho", p.getSku());
        }

        JFreeChart barChart = ChartFactory.createBarChart(
                "Biểu đồ Số Lượng Tồn Kho Theo Mã Sản Phẩm",
                "Mã Sản Phẩm",
                "Số Lượng (Cái/Bó)",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        CategoryPlot plot = barChart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);

        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(0, 102, 204));
        renderer.setDrawBarOutline(false);

        chartContainer.removeAll();
        ChartPanel chartPanel = new ChartPanel(barChart);
        chartContainer.add(chartPanel, BorderLayout.CENTER);
        chartContainer.validate();
    }

    @Override
    public void updateData(String eventType) {
        if (eventType.equals("PRODUCT_CHANGED") || eventType.equals("ORDER_CREATED")) {
            refreshChart();
        }
    }

    @Override
    public void removeNotify() {
        super.removeNotify();
        ShopEventManager.getInstance().unsubscribe(this);
    }
}