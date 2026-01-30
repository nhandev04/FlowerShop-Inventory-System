package com.flowershop.view;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class MainFrame extends JFrame {

    private final String userRole;
    private JLabel currentPageLabel;
    private JMenu lastActiveMenu = null;

    public MainFrame(String role) {
        this.userRole = role;
        initComponents();
    }

    private void initComponents() {
        setTitle("");
        setSize(1200, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(25, 118, 210));
        menuBar.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, new Color(13, 71, 161)));
        menuBar.setPreferredSize(new Dimension(0, 45));

        try {
            URL logoUrl = getClass().getClassLoader().getResource("icons/flower_logo.png");
            if (logoUrl != null) {
                ImageIcon originalIcon = new ImageIcon(logoUrl);
                Image scaledImage = originalIcon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
                ImageIcon logoIcon = new ImageIcon(scaledImage);

                JLabel logoLabel = new JLabel(logoIcon);
                logoLabel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
                menuBar.add(logoLabel);
            }
        } catch (Exception e) {
            System.err.println("Could not load flower logo: " + e.getMessage());
        }

        JMenu menuSystem = new JMenu("Hệ thống");
        styleMenu(menuSystem);
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
        styleMenu(menuManage);
        if (userRole.equals("ADMIN") || userRole.equals("STAFF")) {
            JMenuItem itemProduct = new JMenuItem("Sản phẩm & Kho");
            itemProduct.addActionListener(e -> {
                showPanel(new ProductPanel(), "Quản lý Sản phẩm & Kho", menuManage);
            });

            JMenuItem itemImport = new JMenuItem("Nhập Hàng");
            itemImport.addActionListener(e -> {
                showPanel(new ImportPanel(), "Nhập Hàng", menuManage);
            });

            JMenuItem itemStockHistory = new JMenuItem("Lịch Sử Kho");
            itemStockHistory.addActionListener(e -> {
                showPanel(new com.flowershop.view.StockMovementPanel(), "Lịch Sử Kho", menuManage);
            });

            JMenuItem itemTransfer = new JMenuItem("Điều Chuyển Kho");
            itemTransfer.addActionListener(e -> {
                showPanel(new com.flowershop.view.WarehouseTransferPanel(), "Điều Chuyển Kho", menuManage);
            });

            menuManage.add(itemProduct);
            menuManage.add(itemImport);
            menuManage.add(itemStockHistory);
            menuManage.add(itemTransfer);
        } else {
            menuManage.setEnabled(false);
        }

        JMenu menuSales = new JMenu("Bán hàng");
        styleMenu(menuSales);
        JMenuItem itemCreateOrder = new JMenuItem("Tạo hóa đơn bán lẻ");
        JMenuItem itemOrderHistory = new JMenuItem("Lịch sử đơn hàng");

        itemCreateOrder.addActionListener(e -> {
            showPanel(new SalesPanel(), "Tạo Hóa Đơn Bán Lẻ", menuSales);
        });
        itemOrderHistory.addActionListener(e -> {
            showPanel(new OrderHistoryPanel(), "Lịch Sử Đơn Hàng", menuSales);
        });

        menuSales.add(itemCreateOrder);
        menuSales.add(itemOrderHistory);

        JMenu menuReport = new JMenu("Báo Cáo");
        styleMenu(menuReport);
        if (userRole.equals("ADMIN")) {
            JMenuItem itemRevenue = new JMenuItem("Báo cáo Doanh thu");
            itemRevenue.addActionListener(e -> {
                showPanel(new com.flowershop.view.RevenuePanel(), "Báo Cáo Doanh Thu", menuReport);
            });
            menuReport.add(itemRevenue);

            JMenuItem itemInventoryChart = new JMenuItem("Thống Kê Tồn Kho");
            itemInventoryChart.addActionListener(e -> {
                showPanel(new com.flowershop.view.InventoryChartPanel(), "Thống Kê Tồn Kho", menuReport);
            });
            menuReport.add(itemInventoryChart);

            JMenuItem itemCustomerChart = new JMenuItem("Phân Loại Khách Hàng");
            itemCustomerChart.addActionListener(e -> {
                showPanel(new com.flowershop.view.CustomerChartPanel(), "Phân Loại Khách Hàng", menuReport);
            });
            menuReport.add(itemCustomerChart);
        } else {
            menuReport.setEnabled(false);
        }

        menuBar.add(menuSystem);
        menuBar.add(menuManage);
        menuBar.add(menuSales);
        menuBar.add(menuReport);

        setJMenuBar(menuBar);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(245, 245, 250));
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(220, 220, 230)),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)));

        currentPageLabel = new JLabel("Trang Chủ");
        currentPageLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        currentPageLabel.setForeground(new Color(30, 30, 50));
        headerPanel.add(currentPageLabel, BorderLayout.WEST);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(80, 50, 80, 50));

        JLabel titleLabel = new JLabel("HỆ THỐNG QUẢN LÝ KHO HÀNG");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 42));
        titleLabel.setForeground(new Color(25, 118, 210));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("SHOP HOA");
        subtitleLabel.setFont(new Font("Segoe UI", Font.BOLD, 38));
        subtitleLabel.setForeground(new Color(255, 152, 0));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel linePanel = new JPanel();
        linePanel.setMaximumSize(new Dimension(300, 3));
        linePanel.setBackground(new Color(25, 118, 210));
        linePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        String roleDisplay = userRole.equals("ADMIN") ? "Quản Trị Viên" : "Nhân Viên";
        JLabel welcomeLabel = new JLabel("Chào mừng - " + roleDisplay);
        welcomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        welcomeLabel.setForeground(new Color(100, 100, 100));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel infoLabel = new JLabel("Vui lòng chọn chức năng từ menu phía trên");
        infoLabel.setFont(new Font("Segoe UI", Font.ITALIC, 16));
        infoLabel.setForeground(new Color(150, 150, 150));
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(titleLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        centerPanel.add(subtitleLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        centerPanel.add(linePanel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        centerPanel.add(welcomeLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        centerPanel.add(infoLabel);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel contentWrapper = new JPanel(new BorderLayout());
        contentWrapper.add(headerPanel, BorderLayout.NORTH);
        contentWrapper.add(mainPanel, BorderLayout.CENTER);
        add(contentWrapper, BorderLayout.CENTER);

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
        showPanel(panel, "Trang Chủ", null);
    }

    private void showPanel(JPanel panel, String pageTitle, JMenu activeMenu) {
        currentPageLabel.setText(pageTitle);

        if (lastActiveMenu != null) {
            lastActiveMenu.setForeground(UIManager.getColor("Menu.foreground"));
            lastActiveMenu.setOpaque(false);
            lastActiveMenu.setFont(lastActiveMenu.getFont().deriveFont(Font.PLAIN));
            lastActiveMenu.setBorder(null);
        }

        if (activeMenu != null) {
            activeMenu.setOpaque(true);
            activeMenu.setBackground(new Color(41, 166, 185));
            activeMenu.setForeground(Color.WHITE);
            activeMenu.setFont(activeMenu.getFont().deriveFont(Font.BOLD));
            activeMenu.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 3, 0, new Color(52, 152, 219)),
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)));
            lastActiveMenu = activeMenu;
        }

        Container contentPane = getContentPane();
        Component[] components = contentPane.getComponents();

        for (Component comp : components) {
            if (comp instanceof JPanel) {
                JPanel wrapper = (JPanel) comp;
                Component[] wrapperComps = wrapper.getComponents();
                for (int i = 0; i < wrapperComps.length; i++) {
                    if (wrapperComps[i] instanceof JPanel && i > 0) {
                        wrapper.remove(wrapperComps[i]);
                        wrapper.add(panel, BorderLayout.CENTER);
                        wrapper.revalidate();
                        wrapper.repaint();
                        return;
                    }
                }
            }
        }
    }

    private void styleMenu(JMenu menu) {
        menu.setFont(new Font("Segoe UI", Font.BOLD, 14));
        menu.setForeground(Color.WHITE);
        menu.setOpaque(false);
        menu.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 30));
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