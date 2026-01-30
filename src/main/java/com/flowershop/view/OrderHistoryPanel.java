package com.flowershop.view;

import com.flowershop.config.DatabaseConnection;
import com.flowershop.view.observer.ShopEventManager;
import com.flowershop.view.observer.ShopObserver;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class OrderHistoryPanel extends JPanel implements ShopObserver {

    private DefaultTableModel tableModel;
    private JTable table;
    private Map<Integer, String> originalStatuses = new HashMap<>();

    public OrderHistoryPanel() {
        setLayout(new BorderLayout());
        JLabel lblTitle = new JLabel("LỊCH SỬ ĐƠN HÀNG", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        add(lblTitle, BorderLayout.NORTH);

        String[] cols = { "Mã Đơn", "Ngày Mua", "Khách Hàng", "Tổng Tiền", "Trạng Thái" };
        tableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(25);

        String[] statuses = { "Pending", "Processing", "Shipped", "Delivered", "Completed", "Cancelled", "Returned" };
        JComboBox<String> statusCombo = new JComboBox<>(statuses);
        table.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(statusCombo));

        table.getColumnModel().getColumn(4).setCellRenderer(new StatusCellRenderer());

        table.getModel().addTableModelListener(e -> {
            if (e.getType() == javax.swing.event.TableModelEvent.UPDATE && e.getColumn() == 4) {
                int row = e.getFirstRow();
                int orderId = (int) tableModel.getValueAt(row, 0);
                String newStatus = (String) tableModel.getValueAt(row, 4);
                String oldStatus = originalStatuses.get(orderId);

                if (oldStatus != null && !oldStatus.equals(newStatus)) {
                    updateOrderStatus(orderId, newStatus);
                    originalStatuses.put(orderId, newStatus);
                }
            }
        });

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
                int orderId = rs.getInt("SalesOrderID");
                String status = rs.getString("Status");

                Object[] row = {
                        orderId,
                        rs.getTimestamp("OrderDate"),
                        rs.getString("Notes"),
                        df.format(rs.getDouble("TotalAmount")),
                        status
                };
                tableModel.addRow(row);

                originalStatuses.put(orderId, status);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateOrderStatus(int orderId, String newStatus) {
        String sql = "UPDATE SalesOrders SET Status = ? WHERE SalesOrderID = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newStatus);
            ps.setInt(2, orderId);
            int updated = ps.executeUpdate();
            if (updated > 0) {
                JOptionPane.showMessageDialog(this,
                        "Cập nhật trạng thái thành công!",
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Lỗi cập nhật: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void updateData(String eventType) {
        if (eventType.equals("ORDER_CREATED"))
            loadData();
    }

    @Override
    public void removeNotify() {
        super.removeNotify();
        ShopEventManager.getInstance().unsubscribe(this);
    }

    private static class StatusCellRenderer extends JLabel implements TableCellRenderer {
        public StatusCellRenderer() {
            setOpaque(true);
            setHorizontalAlignment(CENTER);
            setFont(new Font("Segoe UI", Font.BOLD, 12));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus,
                int row, int column) {
            String status = value != null ? value.toString() : "";
            setText(status);

            switch (status) {
                case "Completed":
                    setBackground(new Color(46, 204, 113));
                    setForeground(Color.WHITE);
                    break;
                case "Delivered":
                    setBackground(new Color(22, 160, 133));
                    setForeground(Color.WHITE);
                    break;
                case "Pending":
                    setBackground(new Color(241, 196, 15));
                    setForeground(Color.BLACK);
                    break;
                case "Processing":
                    setBackground(new Color(155, 89, 182));
                    setForeground(Color.WHITE);
                    break;
                case "Shipped":
                    setBackground(new Color(52, 152, 219));
                    setForeground(Color.WHITE);
                    break;
                case "Cancelled":
                    setBackground(new Color(149, 165, 166));
                    setForeground(Color.WHITE);
                    break;
                case "Returned":
                    setBackground(new Color(231, 76, 60));
                    setForeground(Color.WHITE);
                    break;
                default:
                    setBackground(Color.LIGHT_GRAY);
                    setForeground(Color.BLACK);
            }

            if (isSelected) {
                setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
            } else {
                setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
            }

            return this;
        }
    }
}