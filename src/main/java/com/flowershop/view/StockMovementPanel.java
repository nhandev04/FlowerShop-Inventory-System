package com.flowershop.view;

import com.flowershop.config.DatabaseConnection;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;

public class StockMovementPanel extends JPanel {

    private DefaultTableModel tableModel;
    private JTable table;
    private JComboBox<String> cboFilter;

    public StockMovementPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel lblTitle = new JLabel("QUẢN LÝ KHO - LỊCH SỬ GIAO DỊCH", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(new Color(0, 51, 102));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        add(lblTitle, BorderLayout.NORTH);

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.setBackground(Color.WHITE);
        filterPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        filterPanel.add(new JLabel("Lọc theo loại:"));
        String[] filterOptions = { "Tất cả", "Nhập kho", "Xuất kho", "Điều chỉnh", "Chuyển kho" };
        cboFilter = new JComboBox<>(filterOptions);
        cboFilter.addActionListener(e -> loadData());
        filterPanel.add(cboFilter);

        JButton btnRefresh = new JButton("Làm mới");
        btnRefresh.addActionListener(e -> loadData());
        filterPanel.add(btnRefresh);

        add(filterPanel, BorderLayout.NORTH);

        String[] columns = { "Mã GD", "Ngày giờ", "Sản phẩm", "Kho", "Loại", "Số lượng", "Người thực hiện", "Ghi chú" };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(28);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        table.getColumnModel().getColumn(0).setPreferredWidth(60);
        table.getColumnModel().getColumn(1).setPreferredWidth(140);
        table.getColumnModel().getColumn(2).setPreferredWidth(200);
        table.getColumnModel().getColumn(3).setPreferredWidth(120);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
        table.getColumnModel().getColumn(5).setPreferredWidth(90);
        table.getColumnModel().getColumn(6).setPreferredWidth(120);
        table.getColumnModel().getColumn(7).setPreferredWidth(200);

        table.getColumnModel().getColumn(4).setCellRenderer(new TransactionTypeCellRenderer());

        table.getColumnModel().getColumn(5).setCellRenderer(new QuantityCellRenderer());

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        loadData();
    }

    private void loadData() {
        tableModel.setRowCount(0);

        String filterType = getFilterType();
        String sql = "SELECT sm.MovementID, sm.MovementDate, p.ProductName, w.WarehouseName, " +
                "sm.MovementType, sm.Quantity, ISNULL(sm.CreatedBy, N'Hệ thống') as CreatedBy, " +
                "ISNULL(sm.Notes, N'') as Notes " +
                "FROM StockMovements sm " +
                "JOIN Products p ON sm.ProductID = p.ProductID " +
                "JOIN Warehouses w ON sm.WarehouseID = w.WarehouseID ";

        if (filterType != null) {
            sql += "WHERE sm.MovementType = ? ";
        }

        sql += "ORDER BY sm.MovementDate DESC";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            if (filterType != null) {
                ps.setString(1, filterType);
            }

            ResultSet rs = ps.executeQuery();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

            while (rs.next()) {
                Object[] row = {
                        rs.getInt("MovementID"),
                        sdf.format(rs.getTimestamp("MovementDate")),
                        rs.getString("ProductName"),
                        rs.getString("WarehouseName"),
                        translateMovementType(rs.getString("MovementType")),
                        formatQuantity(rs.getString("MovementType"), rs.getInt("Quantity")),
                        rs.getString("CreatedBy"),
                        rs.getString("Notes")
                };
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi tải dữ liệu: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private String getFilterType() {
        String selected = (String) cboFilter.getSelectedItem();
        switch (selected) {
            case "Nhập kho":
                return "IN";
            case "Xuất kho":
                return "OUT";
            case "Điều chỉnh":
                return "ADJUSTMENT";
            case "Chuyển kho":
                return "TRANSFER";
            default:
                return null;
        }
    }

    private String translateMovementType(String type) {
        switch (type) {
            case "IN":
                return "Nhập kho";
            case "OUT":
                return "Xuất kho";
            case "ADJUSTMENT":
                return "Điều chỉnh";
            case "TRANSFER":
                return "Chuyển kho";
            default:
                return type;
        }
    }

    private String formatQuantity(String type, int quantity) {
        switch (type) {
            case "IN":
            case "ADJUSTMENT":
                return "+" + quantity;
            case "OUT":
                return "-" + quantity;
            case "TRANSFER":
                return "→ " + quantity;
            default:
                return String.valueOf(quantity);
        }
    }

    private class TransactionTypeCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            String type = value.toString();
            setFont(new Font("Segoe UI", Font.BOLD, 12));

            if (!isSelected) {
                switch (type) {
                    case "Nhập kho":
                        setBackground(new Color(232, 245, 233));
                        setForeground(new Color(27, 94, 32));
                        break;
                    case "Xuất kho":
                        setBackground(new Color(255, 235, 238));
                        setForeground(new Color(183, 28, 28));
                        break;
                    case "Điều chỉnh":
                        setBackground(new Color(255, 243, 224));
                        setForeground(new Color(230, 81, 0));
                        break;
                    case "Chuyển kho":
                        setBackground(new Color(227, 242, 253));
                        setForeground(new Color(13, 71, 161));
                        break;
                    default:
                        setBackground(Color.WHITE);
                        setForeground(Color.BLACK);
                }
            }

            setHorizontalAlignment(CENTER);
            return this;
        }
    }

    private class QuantityCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            setFont(new Font("Segoe UI", Font.BOLD, 12));
            setHorizontalAlignment(CENTER);

            String qtyStr = value.toString();
            if (!isSelected) {
                if (qtyStr.startsWith("+")) {
                    setForeground(new Color(27, 94, 32));
                } else if (qtyStr.startsWith("-")) {
                    setForeground(new Color(183, 28, 28));
                } else if (qtyStr.startsWith("→")) {
                    setForeground(new Color(13, 71, 161));
                } else {
                    setForeground(Color.BLACK);
                }
            }

            return this;
        }
    }
}
