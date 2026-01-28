package com.flowershop.view;

import com.flowershop.model.dto.ProductDTO;
import com.flowershop.model.dto.SalesOrderDTO;
import com.flowershop.model.dto.SalesOrderDetailDTO;
import com.flowershop.service.ProductService;
import com.flowershop.service.SalesService;
import com.flowershop.service.impl.ProductServiceImpl;
import com.flowershop.service.impl.SalesServiceImpl;
import com.flowershop.view.observer.ShopEventManager;
import com.flowershop.view.observer.ShopObserver;
import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class SalesPanel extends JPanel implements ShopObserver {

    private JComboBox<ProductDTO> cboProduct;
    private JTextField txtQuantity;
    private JTextField txtCustomer;
    private JButton btnSubmit;

    private final ProductService productService;
    private final SalesService salesService;

    public SalesPanel() {
        this.productService = new ProductServiceImpl();
        this.salesService = new SalesServiceImpl();
        initComponents();
        loadProducts();

        ShopEventManager.getInstance().subscribe(this);
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
                    setText(p.getProductName() + " | Tồn: " + p.getQuantityOnHand() + " | Giá: " + new DecimalFormat("#,###").format(p.getPrice()));
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
        cboProduct.removeAllItems();
        List<ProductDTO> products = productService.getAllProducts();
        for (ProductDTO p : products) {
            cboProduct.addItem(p);
        }
    }

    private void processSales() {
        ProductDTO selectedProduct = (ProductDTO) cboProduct.getSelectedItem();
        String qtyStr = txtQuantity.getText().trim();
        String customerName = txtCustomer.getText().trim();

        if (selectedProduct == null || qtyStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm và nhập số lượng!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int quantity = Integer.parseInt(qtyStr);

            if (quantity > selectedProduct.getQuantityOnHand()) {
                JOptionPane.showMessageDialog(this, "Số lượng tồn kho không đủ! (Còn: " + selectedProduct.getQuantityOnHand() + ")", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            BigDecimal price = selectedProduct.getPrice();
            BigDecimal totalAmount = price.multiply(BigDecimal.valueOf(quantity));

            SalesOrderDTO order = new SalesOrderDTO();
            order.setCustomerId(4);
            order.setTotalAmount(totalAmount);
            order.setNotes("Khách mua: " + customerName);

            List<SalesOrderDetailDTO> details = new ArrayList<>();
            SalesOrderDetailDTO detail = new SalesOrderDetailDTO();
            detail.setProductId(selectedProduct.getProductId());
            detail.setQuantity(quantity);
            detail.setUnitPrice(price);

            details.add(detail);

            boolean success = salesService.createSalesOrder(order, details);

            if (success) {
                JOptionPane.showMessageDialog(this,
                        "Thanh toán thành công!\n" +
                                "Sản phẩm: " + selectedProduct.getProductName() + "\n" +
                                "Số lượng: " + quantity + "\n" +
                                "Tổng tiền: " + new DecimalFormat("#,###").format(totalAmount) + " VND"
                );
                txtQuantity.setText("");
                txtCustomer.setText("Khách vãng lai");
                loadProducts();
            } else {
                JOptionPane.showMessageDialog(this, "Thanh toán thất bại! Vui lòng kiểm tra lại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Số lượng phải là số nguyên hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi hệ thống: " + ex.getMessage());
        }
    }

    @Override
    public void updateData(String eventType) {
        if ("PRODUCT_CHANGED".equals(eventType)) {
            loadProducts();
        }
    }
}