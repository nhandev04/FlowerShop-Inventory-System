package com.flowershop.view;

import com.flowershop.model.dto.ProductDTO;
import com.flowershop.model.dto.WarehouseDTO;
import com.flowershop.model.dto.WarehouseTransferDTO;
import com.flowershop.service.ProductService;
import com.flowershop.service.WarehouseService;
import com.flowershop.service.WarehouseTransferService;
import com.flowershop.service.impl.ProductServiceImpl;
import com.flowershop.service.impl.WarehouseServiceImpl;
import com.flowershop.service.impl.WarehouseTransferServiceImpl;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class WarehouseTransferPanel extends JPanel {

    private ProductService productService;
    private WarehouseService warehouseService;
    private WarehouseTransferService transferService;

    private JComboBox<ProductDTO> cboProduct;
    private JComboBox<WarehouseDTO> cboFromWarehouse;
    private JComboBox<WarehouseDTO> cboToWarehouse;
    private JTextField txtQuantity;
    private JTextArea txtNotes;
    private DefaultTableModel tableModel;
    private JTable table;

    public WarehouseTransferPanel() {
        productService = new ProductServiceImpl();
        warehouseService = new WarehouseServiceImpl();
        transferService = new WarehouseTransferServiceImpl();

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel lblTitle = new JLabel("ĐIỀU CHUYỂN KHO", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(new Color(0, 51, 102));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        add(lblTitle, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel formPanel = createFormPanel();
        mainPanel.add(formPanel, BorderLayout.NORTH);

        JPanel tablePanel = createTablePanel();
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);

        loadData();
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(25, 118, 210), 2),
                "Tạo Phiếu Chuyển Kho",
                0, 0,
                new Font("Segoe UI", Font.BOLD, 14),
                new Color(25, 118, 210)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        panel.add(new JLabel("Sản phẩm:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        cboProduct = new JComboBox<>();
        cboProduct.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cboProduct.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof ProductDTO) {
                    ProductDTO p = (ProductDTO) value;
                    setText(p.getProductName());
                }
                return this;
            }
        });
        panel.add(cboProduct, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0;
        panel.add(new JLabel("Từ kho:"), gbc);

        gbc.gridx = 3;
        gbc.weightx = 1.0;
        cboFromWarehouse = new JComboBox<>();
        cboFromWarehouse.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cboFromWarehouse.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof WarehouseDTO) {
                    setText(((WarehouseDTO) value).getWarehouseName());
                }
                return this;
            }
        });
        panel.add(cboFromWarehouse, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        panel.add(new JLabel("Đến kho:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        cboToWarehouse = new JComboBox<>();
        cboToWarehouse.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cboToWarehouse.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof WarehouseDTO) {
                    setText(((WarehouseDTO) value).getWarehouseName());
                }
                return this;
            }
        });
        panel.add(cboToWarehouse, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0;
        panel.add(new JLabel("Số lượng:"), gbc);

        gbc.gridx = 3;
        gbc.weightx = 1.0;
        txtQuantity = new JTextField(10);
        txtQuantity.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        panel.add(txtQuantity, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        panel.add(new JLabel("Ghi chú:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        txtNotes = new JTextArea(2, 20);
        txtNotes.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtNotes.setLineWrap(true);
        txtNotes.setWrapStyleWord(true);
        JScrollPane notesScroll = new JScrollPane(txtNotes);
        panel.add(notesScroll, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        buttonPanel.setBackground(Color.WHITE);

        JButton btnCreate = new JButton("Tạo Phiếu Chuyển");
        btnCreate.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnCreate.setBackground(new Color(25, 118, 210));
        btnCreate.setForeground(Color.WHITE);
        btnCreate.setFocusPainted(false);
        btnCreate.addActionListener(e -> createTransfer());

        JButton btnClear = new JButton("Làm mới");
        btnClear.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnClear.addActionListener(e -> clearForm());

        buttonPanel.add(btnCreate);
        buttonPanel.add(btnClear);
        panel.add(buttonPanel, gbc);

        loadFormData();

        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(25, 118, 210), 2),
                "Danh Sách Phiếu Chuyển Kho",
                0, 0,
                new Font("Segoe UI", Font.BOLD, 14),
                new Color(25, 118, 210)));

        String[] columns = { "Mã PC", "Ngày tạo", "Sản phẩm", "Từ kho → Đến kho", "Số lượng", "Trạng thái",
                "Thao tác" };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(35);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.getColumnModel().getColumn(0).setPreferredWidth(60);
        table.getColumnModel().getColumn(1).setPreferredWidth(130);
        table.getColumnModel().getColumn(2).setPreferredWidth(180);
        table.getColumnModel().getColumn(3).setPreferredWidth(200);
        table.getColumnModel().getColumn(4).setPreferredWidth(80);
        table.getColumnModel().getColumn(5).setPreferredWidth(100);
        table.getColumnModel().getColumn(6).setPreferredWidth(150);
        table.getColumnModel().getColumn(5).setCellRenderer(new StatusCellRenderer());
        table.getColumnModel().getColumn(6).setCellRenderer(new ActionCellRenderer());
        table.getColumnModel().getColumn(6).setCellEditor(new ActionCellEditor());

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void loadFormData() {
        cboProduct.removeAllItems();
        List<ProductDTO> products = productService.getAllProducts();
        for (ProductDTO p : products) {
            cboProduct.addItem(p);
        }

        cboFromWarehouse.removeAllItems();
        cboToWarehouse.removeAllItems();
        List<WarehouseDTO> warehouses = warehouseService.getAllWarehouses();
        for (WarehouseDTO w : warehouses) {
            cboFromWarehouse.addItem(w);
            cboToWarehouse.addItem(w);
        }
    }

    private void loadData() {
        tableModel.setRowCount(0);
        List<WarehouseTransferDTO> transfers = transferService.getAllTransfers();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        for (WarehouseTransferDTO t : transfers) {
            Object[] row = {
                    t.getTransferId(),
                    sdf.format(t.getTransferDate()),
                    t.getProductName(),
                    t.getFromWarehouseName() + " → " + t.getToWarehouseName(),
                    t.getQuantity(),
                    t.getStatus(),
                    t.getTransferId()
            };
            tableModel.addRow(row);
        }
    }

    private void createTransfer() {
        try {
            ProductDTO product = (ProductDTO) cboProduct.getSelectedItem();
            WarehouseDTO fromWarehouse = (WarehouseDTO) cboFromWarehouse.getSelectedItem();
            WarehouseDTO toWarehouse = (WarehouseDTO) cboToWarehouse.getSelectedItem();
            String quantityStr = txtQuantity.getText().trim();
            String notes = txtNotes.getText().trim();

            if (product == null || fromWarehouse == null || toWarehouse == null || quantityStr.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng điền đầy đủ thông tin!",
                        "Thiếu thông tin",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (fromWarehouse.getWarehouseId() == toWarehouse.getWarehouseId()) {
                JOptionPane.showMessageDialog(this,
                        "Kho nguồn và kho đích không thể giống nhau!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            int quantity = Integer.parseInt(quantityStr);
            if (quantity <= 0) {
                JOptionPane.showMessageDialog(this,
                        "Số lượng phải lớn hơn 0!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            WarehouseTransferDTO transfer = new WarehouseTransferDTO();
            transfer.setProductId(product.getProductId());
            transfer.setFromWarehouseId(fromWarehouse.getWarehouseId());
            transfer.setToWarehouseId(toWarehouse.getWarehouseId());
            transfer.setQuantity(quantity);
            transfer.setNotes(notes);

            int transferId = transferService.createTransfer(transfer);
            if (transferId > 0) {
                JOptionPane.showMessageDialog(this,
                        "Tạo phiếu chuyển kho thành công! Mã PC: " + transferId,
                        "Thành công",
                        JOptionPane.INFORMATION_MESSAGE);
                clearForm();
                loadData();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Tạo phiếu chuyển kho thất bại!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Số lượng không hợp lệ!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        if (cboProduct.getItemCount() > 0)
            cboProduct.setSelectedIndex(0);
        if (cboFromWarehouse.getItemCount() > 0)
            cboFromWarehouse.setSelectedIndex(0);
        if (cboToWarehouse.getItemCount() > 0)
            cboToWarehouse.setSelectedIndex(0);
        txtQuantity.setText("");
        txtNotes.setText("");
    }

    class StatusCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            String status = value.toString();
            setFont(new Font("Segoe UI", Font.BOLD, 11));
            setHorizontalAlignment(CENTER);

            if (!isSelected) {
                switch (status) {
                    case "Pending":
                        setBackground(new Color(255, 243, 224));
                        setForeground(new Color(230, 81, 0));
                        setText("Chờ xử lý");
                        break;
                    case "Completed":
                        setBackground(new Color(232, 245, 233));
                        setForeground(new Color(27, 94, 32));
                        setText("Đã hoàn thành");
                        break;
                    case "Cancelled":
                        setBackground(new Color(255, 235, 238));
                        setForeground(new Color(183, 28, 28));
                        setText("Đã hủy");
                        break;
                    default:
                        setBackground(Color.WHITE);
                        setForeground(Color.BLACK);
                }
            }

            return this;
        }
    }

    class ActionCellRenderer extends JPanel implements javax.swing.table.TableCellRenderer {
        private JButton btnComplete;
        private JButton btnCancel;

        public ActionCellRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 5, 2));
            btnComplete = new JButton("Hoàn thành");
            btnComplete.setFont(new Font("Segoe UI", Font.PLAIN, 10));
            btnComplete.setBackground(new Color(76, 175, 80));
            btnComplete.setForeground(Color.WHITE);
            btnComplete.setFocusPainted(false);

            btnCancel = new JButton("Hủy");
            btnCancel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
            btnCancel.setBackground(new Color(244, 67, 54));
            btnCancel.setForeground(Color.WHITE);
            btnCancel.setFocusPainted(false);

            add(btnComplete);
            add(btnCancel);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            String status = (String) table.getValueAt(row, 5);
            btnComplete.setEnabled(status.equals("Pending"));
            btnCancel.setEnabled(status.equals("Pending"));
            return this;
        }
    }

    class ActionCellEditor extends AbstractCellEditor implements javax.swing.table.TableCellEditor {
        private JPanel panel;
        private JButton btnComplete;
        private JButton btnCancel;
        private int transferId;

        public ActionCellEditor() {
            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 2));
            btnComplete = new JButton("Hoàn thành");
            btnComplete.setFont(new Font("Segoe UI", Font.PLAIN, 10));
            btnComplete.setBackground(new Color(76, 175, 80));
            btnComplete.setForeground(Color.WHITE);
            btnComplete.setFocusPainted(false);
            btnComplete.addActionListener(e -> completeTransfer());

            btnCancel = new JButton("Hủy");
            btnCancel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
            btnCancel.setBackground(new Color(244, 67, 54));
            btnCancel.setForeground(Color.WHITE);
            btnCancel.setFocusPainted(false);
            btnCancel.addActionListener(e -> cancelTransfer());

            panel.add(btnComplete);
            panel.add(btnCancel);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            transferId = (int) value;
            String status = (String) table.getValueAt(row, 5);
            btnComplete.setEnabled(status.equals("Pending"));
            btnCancel.setEnabled(status.equals("Pending"));
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return transferId;
        }

        private void completeTransfer() {
            int confirm = JOptionPane.showConfirmDialog(panel,
                    "Xác nhận hoàn thành chuyển kho? Hành động này sẽ cập nhật tồn kho!",
                    "Xác nhận",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                if (transferService.completeTransfer(transferId)) {
                    JOptionPane.showMessageDialog(panel,
                            "Hoàn thành chuyển kho thành công!",
                            "Thành công",
                            JOptionPane.INFORMATION_MESSAGE);
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(panel,
                            "Hoàn thành chuyển kho thất bại!",
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
            fireEditingStopped();
        }

        private void cancelTransfer() {
            int confirm = JOptionPane.showConfirmDialog(panel,
                    "Xác nhận hủy phiếu chuyển kho?",
                    "Xác nhận",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                if (transferService.cancelTransfer(transferId)) {
                    JOptionPane.showMessageDialog(panel,
                            "Hủy phiếu thành công!",
                            "Thành công",
                            JOptionPane.INFORMATION_MESSAGE);
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(panel,
                            "Hủy phiếu thất bại!",
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
            fireEditingStopped();
        }
    }
}
