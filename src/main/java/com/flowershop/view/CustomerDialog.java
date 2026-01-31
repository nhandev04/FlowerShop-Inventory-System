package com.flowershop.view;

import com.flowershop.model.dto.CustomerDTO;
import javax.swing.*;
import java.awt.*;
import java.util.regex.Pattern;

public class CustomerDialog extends JDialog {

    private JTextField txtName;
    private JTextField txtPhone;
    private JTextField txtEmail;
    private JTextArea txtAddress;
    private JButton btnSave;
    private JButton btnCancel;

    private boolean saved = false;
    private CustomerDTO customer;

    // Email regex pattern
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@(.+)$");

    // Phone regex pattern (10-11 digits)
    private static final Pattern PHONE_PATTERN = Pattern.compile(
            "^0\\d{9,10}$");

    // Constructor for Add mode
    public CustomerDialog(Frame parent) {
        this(parent, null);
    }

    // Constructor for Edit mode
    public CustomerDialog(Frame parent, CustomerDTO customer) {
        super(parent, customer == null ? "Thêm Khách Hàng" : "Sửa Khách Hàng", true);
        this.customer = customer;

        initComponents();

        if (customer != null) {
            loadCustomerData();
        }

        setSize(450, 400);
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        // Main panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Customer Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        JLabel lblName = new JLabel("Tên khách hàng: *");
        lblName.setFont(new Font("Segoe UI", Font.BOLD, 12));
        mainPanel.add(lblName, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        txtName = new JTextField(20);
        mainPanel.add(txtName, gbc);

        // Phone
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        JLabel lblPhone = new JLabel("Số điện thoại: *");
        lblPhone.setFont(new Font("Segoe UI", Font.BOLD, 12));
        mainPanel.add(lblPhone, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        txtPhone = new JTextField(20);
        mainPanel.add(txtPhone, gbc);

        // Email
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.3;
        mainPanel.add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        txtEmail = new JTextField(20);
        mainPanel.add(txtEmail, gbc);

        // Address
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.3;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        mainPanel.add(new JLabel("Địa chỉ:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        txtAddress = new JTextArea(4, 20);
        txtAddress.setLineWrap(true);
        txtAddress.setWrapStyleWord(true);
        JScrollPane scrollAddress = new JScrollPane(txtAddress);
        mainPanel.add(scrollAddress, gbc);

        add(mainPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        btnSave = new JButton("Lưu");
        btnSave.setBackground(new Color(0, 153, 76));
        btnSave.setForeground(Color.WHITE);
        btnSave.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnSave.setPreferredSize(new Dimension(100, 35));
        btnSave.addActionListener(e -> saveCustomer());

        btnCancel = new JButton("Hủy");
        btnCancel.setBackground(new Color(204, 0, 0));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnCancel.setPreferredSize(new Dimension(100, 35));
        btnCancel.addActionListener(e -> dispose());

        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadCustomerData() {
        txtName.setText(customer.getCustomerName());
        txtPhone.setText(customer.getPhone());
        txtEmail.setText(customer.getEmail());
        txtAddress.setText(customer.getAddress());
    }

    private void saveCustomer() {
        // Validate input
        String name = txtName.getText().trim();
        String phone = txtPhone.getText().trim();
        String email = txtEmail.getText().trim();
        String address = txtAddress.getText().trim();

        // Validate required fields
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng nhập tên khách hàng!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            txtName.requestFocus();
            return;
        }

        if (phone.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng nhập số điện thoại!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            txtPhone.requestFocus();
            return;
        }

        // Validate phone format
        if (!PHONE_PATTERN.matcher(phone).matches()) {
            JOptionPane.showMessageDialog(this,
                    "Số điện thoại không hợp lệ!\nĐịnh dạng: 0xxxxxxxxx (10-11 số)",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            txtPhone.requestFocus();
            return;
        }

        // Validate email if provided
        if (!email.isEmpty() && !EMAIL_PATTERN.matcher(email).matches()) {
            JOptionPane.showMessageDialog(this,
                    "Email không hợp lệ!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            txtEmail.requestFocus();
            return;
        }

        // Create or update customer
        if (customer == null) {
            customer = new CustomerDTO();
        }

        customer.setCustomerName(name);
        customer.setPhone(phone);
        customer.setEmail(email.isEmpty() ? null : email);
        customer.setAddress(address.isEmpty() ? null : address);

        saved = true;
        dispose();
    }

    public boolean isSaved() {
        return saved;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }
}
