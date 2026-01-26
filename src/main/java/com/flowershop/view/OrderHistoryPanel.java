package com.flowershop.view;

import com.flowershop.config.DatabaseConnection;
import com.flowershop.view.observer.ShopEventManager;
import com.flowershop.view.observer.ShopObserver;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.text.DecimalFormat;

public class OrderHistoryPanel extends JPanel implements ShopObserver {

    private DefaultTableModel tableModel;

    public OrderHistoryPanel() {
        setLayout(new BorderLayout());
        JLabel lblTitle = new JLabel("LỊCH SỬ ĐƠN HÀNG", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        add(lblTitle, BorderLayout.NORTH);

        String[] cols = {"Mã Đơn", "Ngày Mua", "Khách Hàng", "Tổng Tiền", "Trạng Thái"};
        tableModel = new DefaultTableModel(cols, 0);
        JTable table = new JTable(tableModel);
        table.setRowHeight(25);
        add(new JScrollPane(table), BorderLayout.CENTER);

        loadData();
        ShopEventManager.getInstance().subscribe(this);
    }

    private void loadData() {
        tableModel.setRowCount(0);
        String sql = "SELECT * FROM SalesOrders ORDER BY OrderDate DESC";
        DecimalFormat df = new DecimalFormat("#,### VND");

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Object[] row = {
                        rs.getInt("SalesOrderID"),
                        rs.getTimestamp("OrderDate"),
                        rs.getString("Notes"),
                        df.format(rs.getDouble("TotalAmount")),
                        rs.getString("Status")
                };
                tableModel.addRow(row);
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public void updateData(String eventType) {
        if (eventType.equals("ORDER_CREATED")) loadData();
    }

    @Override
    public void removeNotify() {
        super.removeNotify();
        ShopEventManager.getInstance().unsubscribe(this);
    }
}