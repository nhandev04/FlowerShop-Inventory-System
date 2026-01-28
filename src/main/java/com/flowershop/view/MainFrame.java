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
        setTitle("Hệ thống Quản lý Kho Hàng Shop Hoa - Đăng nhập bằng tài khoản " + (userRole.equals("ADMIN") ? "Quản Trị Viên" : "Nhân Viên"));
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
        if (userRole.equals("ADMIN")) {
            JMenuItem itemProduct = new JMenuItem("Sản phẩm & Kho");
            itemProduct.addActionListener(e -> showPanel(new ProductPanel()));

            JMenuItem itemImport = new JMenuItem("Nhập Hàng");
            itemImport.addActionListener(e -> showPanel(new ImportPanel()));

            menuManage.add(itemProduct);
            menuManage.add(itemImport);
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

        JMenu menuReport = new JMenu("Báo Cáo");
        if (userRole.equals("ADMIN")) {
            JMenuItem itemRevenue = new JMenuItem("Báo cáo Doanh thu");
            itemRevenue.addActionListener(e -> showPanel(new com.flowershop.view.RevenuePanel()));
            menuReport.add(itemRevenue);
            JMenuItem itemInventoryChart = new JMenuItem("Thống Kê Tồn Kho");
            itemInventoryChart.addActionListener(e -> showPanel(new com.flowershop.view.InventoryChartPanel()));
            menuReport.add(itemInventoryChart);
            menuReport.addSeparator();
            JMenuItem itemHistory = new JMenuItem("Lịch Sử Kho");
            itemHistory.addActionListener(e -> showPanel(new com.flowershop.view.StockMovementPanel()));
            menuReport.add(itemHistory);
        } else {
            menuReport.setEnabled(false);
        }
        menuBar.add(menuReport);
        menuBar.add(menuSystem);
        menuBar.add(menuManage);
        menuBar.add(menuSales);
        menuBar.add(menuReport);
        setJMenuBar(menuBar);

        JMenuItem itemCustomerChart = new JMenuItem("Phân Loại Khách Hàng");
        itemCustomerChart.addActionListener(e -> showPanel(new com.flowershop.view.CustomerChartPanel()));
        menuReport.add(itemCustomerChart);

        menuReport.addSeparator();

        JPanel mainPanel = new JPanel(new BorderLayout());
        JLabel welcomeLabel = new JLabel("XIN CHÀO " + userRole + "!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        welcomeLabel.setForeground(new Color(100, 100, 100));
        mainPanel.add(welcomeLabel, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);

        JMenu menuClock = new JMenu();
        menuClock.setEnabled(false);
        menuClock.setForeground(new java.awt.Color(0, 102, 204));
        menuBar.add(javax.swing.Box.createHorizontalGlue());
        menuBar.add(menuClock);

        Thread clockThread = new Thread(() -> {
            try {
                while (true) {
                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    String time = sdf.format(new java.util.Date());

                    javax.swing.SwingUtilities.invokeLater(() -> menuClock.setText(time));

                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        clockThread.start();
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