package com.flowershop.view;

import com.flowershop.model.dto.CustomerDTO;
import com.flowershop.service.CustomerService;
import com.flowershop.service.impl.CustomerServiceImpl;
import com.flowershop.view.observer.ShopEventManager;
import com.flowershop.view.observer.ShopObserver;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CustomerPanel extends JPanel implements ShopObserver {

    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtSearch;
    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDelete;
    private JButton btnRefresh;

    private final CustomerService customerService;

    public CustomerPanel() {
        this.customerService = new CustomerServiceImpl();

        initComponents();
        loadDataToTable();

        ShopEventManager.getInstance().subscribe(this);
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(15, 15, 15, 15));
        setBackground(Color.WHITE);

        // Header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);

        JLabel lblTitle = new JLabel("QUẢN LÝ KHÁCH HÀNG");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(new Color(0, 102, 204));
        headerPanel.add(lblTitle, BorderLayout.WEST);

        add(headerPanel, BorderLayout.NORTH);

        // Search and action panel
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(Color.WHITE);

        topPanel.add(new JLabel("Tìm kiếm:"));
        txtSearch = new JTextField(20);
        topPanel.add(txtSearch);

        JButton btnSearch = new JButton("Tìm");
        btnSearch.setBackground(new Color(0, 102, 204));
        btnSearch.setForeground(Color.WHITE);
        btnSearch.addActionListener(e -> searchCustomers());
        topPanel.add(btnSearch);

        topPanel.add(Box.createHorizontalStrut(20));

        btnAdd = new JButton("+ Thêm Khách Hàng");
        btnAdd.setBackground(new Color(0, 153, 76));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnAdd.addActionListener(e -> addCustomer());
        topPanel.add(btnAdd);

        add(topPanel, BorderLayout.BEFORE_FIRST_LINE);

        // Table
        String[] columns = { "ID", "Tên Khách Hàng", "Số Điện Thoại", "Email", "Địa Chỉ" };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(0, 102, 204));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Center align ID column
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);

        // Set column widths
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        table.getColumnModel().getColumn(2).setPreferredWidth(120);
        table.getColumnModel().getColumn(3).setPreferredWidth(180);
        table.getColumnModel().getColumn(4).setPreferredWidth(250);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Color.WHITE);

        btnEdit = new JButton("Sửa");
        btnEdit.setBackground(new Color(255, 153, 0));
        btnEdit.setForeground(Color.WHITE);
        btnEdit.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnEdit.setPreferredSize(new Dimension(100, 35));
        btnEdit.addActionListener(e -> editCustomer());

        btnDelete = new JButton("Xóa");
        btnDelete.setBackground(new Color(204, 0, 0));
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnDelete.setPreferredSize(new Dimension(100, 35));
        btnDelete.addActionListener(e -> deleteCustomer());

        btnRefresh = new JButton("Làm Mới");
        btnRefresh.setBackground(new Color(102, 102, 102));
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnRefresh.setPreferredSize(new Dimension(100, 35));
        btnRefresh.addActionListener(e -> loadDataToTable());

        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnRefresh);

        add(buttonPanel, BorderLayout.SOUTH);

        // Search on text change
        txtSearch.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                searchCustomers();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                if (txtSearch.getText().trim().isEmpty()) {
                    loadDataToTable();
                }
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                searchCustomers();
            }
        });
    }

    private void loadDataToTable() {
        tableModel.setRowCount(0);
        List<CustomerDTO> customers = customerService.getAllCustomers();

        for (CustomerDTO c : customers) {
            Object[] row = {
                    c.getCustomerId(),
                    c.getCustomerName(),
                    c.getPhone() != null ? c.getPhone() : "",
                    c.getEmail() != null ? c.getEmail() : "",
                    c.getAddress() != null ? c.getAddress() : ""
            };
            tableModel.addRow(row);
        }
    }

    private void searchCustomers() {
        String keyword = txtSearch.getText().trim().toLowerCase();

        if (keyword.isEmpty()) {
            loadDataToTable();
            return;
        }

        tableModel.setRowCount(0);
        List<CustomerDTO> customers = customerService.getAllCustomers();

        for (CustomerDTO c : customers) {
            boolean matches = c.getCustomerName().toLowerCase().contains(keyword) ||
                    (c.getPhone() != null && c.getPhone().contains(keyword)) ||
                    (c.getEmail() != null && c.getEmail().toLowerCase().contains(keyword));

            if (matches) {
                Object[] row = {
                        c.getCustomerId(),
                        c.getCustomerName(),
                        c.getPhone() != null ? c.getPhone() : "",
                        c.getEmail() != null ? c.getEmail() : "",
                        c.getAddress() != null ? c.getAddress() : ""
                };
                tableModel.addRow(row);
            }
        }
    }

    private void addCustomer() {
        CustomerDialog dialog = new CustomerDialog((Frame) SwingUtilities.getWindowAncestor(this));
        dialog.setVisible(true);

        if (dialog.isSaved()) {
            CustomerDTO customer = dialog.getCustomer();
            boolean success = customerService.addCustomer(customer);

            if (success) {
                JOptionPane.showMessageDialog(this,
                        "Thêm khách hàng thành công!",
                        "Thành công",
                        JOptionPane.INFORMATION_MESSAGE);
                loadDataToTable();
                ShopEventManager.getInstance().notify("CUSTOMER_CHANGED");
            } else {
                JOptionPane.showMessageDialog(this,
                        "Thêm khách hàng thất bại!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editCustomer() {
        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn khách hàng cần sửa!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int customerId = (int) tableModel.getValueAt(selectedRow, 0);
        CustomerDTO customer = customerService.getCustomerById(customerId);

        if (customer == null) {
            JOptionPane.showMessageDialog(this,
                    "Không tìm thấy khách hàng!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        CustomerDialog dialog = new CustomerDialog(
                (Frame) SwingUtilities.getWindowAncestor(this),
                customer);
        dialog.setVisible(true);

        if (dialog.isSaved()) {
            boolean success = customerService.updateCustomer(dialog.getCustomer());

            if (success) {
                JOptionPane.showMessageDialog(this,
                        "Cập nhật khách hàng thành công!",
                        "Thành công",
                        JOptionPane.INFORMATION_MESSAGE);
                loadDataToTable();
                ShopEventManager.getInstance().notify("CUSTOMER_CHANGED");
            } else {
                JOptionPane.showMessageDialog(this,
                        "Cập nhật khách hàng thất bại!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteCustomer() {
        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn khách hàng cần xóa!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int customerId = (int) tableModel.getValueAt(selectedRow, 0);
        String customerName = (String) tableModel.getValueAt(selectedRow, 1);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn xóa khách hàng: " + customerName + "?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = customerService.deleteCustomer(customerId);

            if (success) {
                JOptionPane.showMessageDialog(this,
                        "Xóa khách hàng thành công!",
                        "Thành công",
                        JOptionPane.INFORMATION_MESSAGE);
                loadDataToTable();
                ShopEventManager.getInstance().notify("CUSTOMER_CHANGED");
            } else {
                JOptionPane.showMessageDialog(this,
                        "Xóa khách hàng thất bại!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void updateData(String eventType) {
        if ("CUSTOMER_CHANGED".equals(eventType)) {
            loadDataToTable();
        }
    }
}
