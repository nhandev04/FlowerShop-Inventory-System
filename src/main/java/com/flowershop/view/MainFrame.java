package com.flowershop.view;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private final String userRole;

    public MainFrame(String role) {
        this.userRole = role;
        initComponents();
    }

    private void initComponents() {
        setTitle("Hệ thống Quản lý Shop Hoa - " + (userRole.equals("ADMIN") ? "Quản Trị Viên" : "Nhân Viên"));
        setSize(1200, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JMenuBar menuBar = new JMenuBar();

        JMenu menuSystem = new JMenu("Hệ thống");
        JMenuItem itemLogout = new JMenuItem("Đăng xuất");
        JMenuItem itemExit = new JMenuItem("Thoát");

        itemLogout.addActionListener(e -> {
            this.dispose();
            openLogin();
        });

        itemExit.addActionListener(e -> System.exit(0));

        menuSystem.add(itemLogout);
        menuSystem.addSeparator();
        menuSystem.add(itemExit);

        JMenu menuManage = new JMenu("Quản lý");
        JMenuItem itemProduct = new JMenuItem("Sản phẩm & Kho");

        if (userRole.equals("ADMIN")) {
            itemProduct.addActionListener(e -> showPanel(new ProductPanel()));
            menuManage.add(itemProduct);
        } else {
            menuManage.setEnabled(false);
        }

        JMenu menuSales = new JMenu("Bán hàng");
        JMenuItem itemCreateOrder = new JMenuItem("Tạo hóa đơn bán lẻ");
        JMenuItem itemOrderHistory = new JMenuItem("Lịch sử đơn hàng");

        itemCreateOrder.addActionListener(e -> showPanel(new SalesPanel()));
        itemOrderHistory.addActionListener(e -> showPanel(new OrderHistoryPanel()));

        menuSales.add(itemCreateOrder);
        menuSales.add(itemOrderHistory);

        JMenu menuReport = new JMenu("Báo cáo");

        if (userRole.equals("ADMIN")) {
            JMenuItem itemRevenue = new JMenuItem("Báo cáo Doanh thu");
            JMenuItem itemChart = new JMenuItem("Biểu đồ Tồn kho");

            itemRevenue.addActionListener(e -> showPanel(new RevenuePanel()));
            itemChart.addActionListener(e -> showPanel(new StatsPanel()));

            menuReport.add(itemRevenue);
            menuReport.addSeparator();
            menuReport.add(itemChart);
        } else {
            menuReport.setEnabled(false);
        }

        menuBar.add(menuSystem);
        menuBar.add(menuManage);
        menuBar.add(menuSales);
        menuBar.add(menuReport);

        setJMenuBar(menuBar);

        JPanel mainPanel = new JPanel(new BorderLayout());
        JLabel welcomeLabel = new JLabel("XIN CHÀO " + userRole, SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        mainPanel.add(welcomeLabel, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);
    }

    private void showPanel(JPanel panel) {
        getContentPane().removeAll();
        getContentPane().add(panel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public static void openLogin() {
        SwingUtilities.invokeLater(() -> {
            LoginDialog login = new LoginDialog(null);
            login.setVisible(true);

            if (login.isAuthenticated()) {
                new MainFrame(login.getUserRole()).setVisible(true);
            } else {
                System.exit(0);
            }
        });
    }
}