package com.flowershop.view;

import com.flowershop.model.dto.ProductDTO;
import com.flowershop.service.ProductService;
import com.flowershop.service.SalesService;
import com.flowershop.service.impl.ProductServiceImpl;
import com.flowershop.service.impl.SalesServiceImpl;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.List;

public class SalesPanel extends JPanel {

    private JComboBox<ProductDTO> cboProduct;
    private JTextField txtQuantity;
    private JTextField txtCustomer;
    private JLabel lblPricePreview;
    private JButton btnSubmit;

    private final ProductService productService;
    private final SalesService salesService;

    public SalesPanel() {
        this.productService = new ProductServiceImpl();
        this.salesService = new SalesServiceImpl();
        initComponents();
        loadProducts();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        JLabel lblTitle = new JLabel("TẠO ĐƠN HÀNG BÁN LẺ", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(new Color(0, 102, 204));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(lblTitle, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Chọn Sản Phẩm:"), gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        cboProduct = new JComboBox<>();
        cboProduct.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof ProductDTO) {
                    ProductDTO p = (ProductDTO) value;
                    setText(p.getProductName() + " - " + new DecimalFormat("#,###").format(p.getPrice()) + " VND");
                }
                return this;
            }
        });
        formPanel.add(cboProduct, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        formPanel.add(new JLabel("Số Lượng Bán:"), gbc);

        gbc.gridx = 1;
        txtQuantity = new JTextField();
        formPanel.add(txtQuantity, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Tên Khách Hàng:"), gbc);

        gbc.gridx = 1;
        txtCustomer = new JTextField("Khách vãng lai");
        formPanel.add(txtCustomer, gbc);

        add(formPanel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnSubmit = new JButton("THANH TOÁN & XUẤT KHO");
        btnSubmit.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSubmit.setBackground(new Color(0, 153, 76));
        btnSubmit.setForeground(Color.WHITE);
        btnSubmit.addActionListener(e -> processSales());

        btnPanel.add(btnSubmit);
        btnPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 50, 0));
        add(btnPanel, BorderLayout.SOUTH);
    }

    private void loadProducts() {
        List<ProductDTO> products = productService.getAllProducts();
        for (ProductDTO p : products) {
            cboProduct.addItem(p);
        }
    }

    private void processSales() {
        ProductDTO selectedProduct = (ProductDTO) cboProduct.getSelectedItem();
        String qtyStr = txtQuantity.getText().trim();
        String customer = txtCustomer.getText().trim();

        if (selectedProduct == null || qtyStr.isEmpty() || customer.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int quantity = Integer.parseInt(qtyStr);

            String result = salesService.sellProduct(
                    selectedProduct.getProductId(),
                    quantity,
                    customer,
                    selectedProduct.getPrice().doubleValue()
            );

            if ("SUCCESS".equals(result)) {
                JOptionPane.showMessageDialog(this, "Thanh toán thành công!\nĐã trừ kho và tạo hóa đơn.");
                txtQuantity.setText("");
                txtCustomer.setText("Khách vãng lai");
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi: " + result, "Thất bại", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Số lượng phải là số nguyên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}