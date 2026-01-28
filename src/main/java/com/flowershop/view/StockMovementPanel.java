package com.flowershop.view;

import com.flowershop.dao.impl.StockMovementDAOImpl;
import com.flowershop.model.dto.StockMovementDTO;
import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class StockMovementPanel extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;
    private StockMovementDAOImpl dao;

    public StockMovementPanel() {
        this.dao = new StockMovementDAOImpl();
        initComponents();
        loadData();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblTitle = new JLabel("LỊCH SỬ BIẾN ĐỘNG KHO (STOCK CARD)");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(new Color(0, 102, 204));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblTitle, BorderLayout.NORTH);

        String[] columns = {"ID", "Sản Phẩm", "Kho", "Loại", "Số Lượng", "Ngày Giờ", "Người Thực Hiện", "Ghi Chú"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override // Không cho sửa trực tiếp trên bảng
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String type = (String) value;
                if ("IN".equals(type)) {
                    c.setForeground(new Color(0, 153, 51));
                    setText("NHẬP (+)");
                } else if ("OUT".equals(type)) {
                    c.setForeground(Color.RED);
                    setText("XUẤT (-)");
                } else {
                    c.setForeground(Color.BLACK);
                }
                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JButton btnRefresh = new JButton("Làm Mới Dữ Liệu");
        btnRefresh.addActionListener(e -> loadData());
        JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlBottom.add(btnRefresh);
        add(pnlBottom, BorderLayout.SOUTH);
    }

    private void loadData() {
        tableModel.setRowCount(0);
        List<StockMovementDTO> list = dao.getAllMovements();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        for (StockMovementDTO m : list) {
            Object[] row = {
                    m.getMovementId(),
                    m.getProductName(),
                    m.getWarehouseName(),
                    m.getType(),
                    m.getQuantity(),
                    sdf.format(m.getMovementDate()),
                    m.getCreatedBy(),
                    m.getNotes()
            };
            tableModel.addRow(row);
        }
    }
}