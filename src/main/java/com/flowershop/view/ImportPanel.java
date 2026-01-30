package com.flowershop.view;

import com.flowershop.model.dto.ProductDTO;
import com.flowershop.model.dto.PurchaseOrderDTO;
import com.flowershop.model.dto.PurchaseOrderDetailDTO;
import com.flowershop.model.dto.WarehouseDTO;
import com.flowershop.service.ProductService;
import com.flowershop.service.PurchaseService;
import com.flowershop.service.WarehouseService;
import com.flowershop.service.impl.ProductServiceImpl;
import com.flowershop.service.impl.PurchaseServiceImpl;
import com.flowershop.service.impl.WarehouseServiceImpl;
import com.flowershop.view.observer.ShopEventManager;
import com.flowershop.view.observer.ShopObserver;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ImportPanel extends JPanel implements ShopObserver {

    private JComboBox<ProductDTO> cboProduct;
    private JComboBox<WarehouseDTO> cboWarehouse;
    private JTextField txtQuantity;
    private JTextField txtCost;
    private JButton btnImport;

    private final ProductService productService;
    private final PurchaseService purchaseService;
    private final WarehouseService warehouseService;

    public ImportPanel() {
        this.productService = new ProductServiceImpl();
        this.purchaseService = new PurchaseServiceImpl();
        this.warehouseService = new WarehouseServiceImpl();

        initComponents();
        loadProducts();
        loadWarehouses();

        ShopEventManager.getInstance().subscribe(this);
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitle = new JLabel("NHẬP HÀNG VÀO KHO (PO)");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(new Color(0, 102, 204));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(lblTitle, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(new JLabel("Chọn Sản Phẩm:"), gbc);

        cboProduct = new JComboBox<>();
        gbc.gridx = 1;
        add(cboProduct, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Số Lượng Nhập:"), gbc);

        txtQuantity = new JTextField(15);
        gbc.gridx = 1;
        add(txtQuantity, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Giá Nhập (VND):"), gbc);

        txtCost = new JTextField(15);
        gbc.gridx = 1;
        add(txtCost, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Kho nhập:"), gbc);

        cboWarehouse = new JComboBox<>();
        gbc.gridx = 1;
        add(cboWarehouse, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;

        btnImport = new JButton("NHẬP KHO (+)");
        btnImport.setBackground(new Color(0, 153, 76));
        btnImport.setForeground(Color.WHITE);
        btnImport.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnImport.setPreferredSize(new Dimension(150, 40));

        btnImport.addActionListener(e -> processImport());
        add(btnImport, gbc);
    }

    private void loadProducts() {
        cboProduct.removeAllItems();
        List<ProductDTO> list = productService.getAllProducts();
        for (ProductDTO p : list) {
            cboProduct.addItem(p);
        }
    }

    private void loadWarehouses() {
        cboWarehouse.removeAllItems();
        List<WarehouseDTO> list = warehouseService.getAllWarehouses();
        for (WarehouseDTO w : list) {
            cboWarehouse.addItem(w);
        }
    }

    private void processImport() {
        ProductDTO selectedProduct = (ProductDTO) cboProduct.getSelectedItem();
        WarehouseDTO selectedWarehouse = (WarehouseDTO) cboWarehouse.getSelectedItem();
        String qtyStr = txtQuantity.getText().trim();
        String costStr = txtCost.getText().trim();

        if (selectedProduct == null || selectedWarehouse == null || qtyStr.isEmpty() || costStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int qty = Integer.parseInt(qtyStr);
            double costVal = Double.parseDouble(costStr);
            BigDecimal unitCost = BigDecimal.valueOf(costVal);
            BigDecimal totalAmount = unitCost.multiply(BigDecimal.valueOf(qty));

            PurchaseOrderDTO order = new PurchaseOrderDTO();
            order.setSupplierId(1);
            order.setWarehouseId(selectedWarehouse.getWarehouseId());
            order.setTotalAmount(totalAmount);
            order.setNotes("Nhập nhanh từ ImportPanel - Kho: " + selectedWarehouse.getWarehouseName());

            List<PurchaseOrderDetailDTO> details = new ArrayList<>();
            PurchaseOrderDetailDTO detail = new PurchaseOrderDetailDTO();
            detail.setProductId(selectedProduct.getProductId());
            detail.setQuantity(qty);
            detail.setUnitCost(unitCost);

            details.add(detail);
            boolean success = purchaseService.createPurchaseOrder(order, details);

            if (success) {
                JOptionPane.showMessageDialog(this,
                        "Nhập kho thành công!\nSản phẩm: " + selectedProduct.getProductName() + "\n+ " + qty,
                        "Message", JOptionPane.INFORMATION_MESSAGE);

                txtQuantity.setText("");
                txtCost.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Nhập kho thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Số lượng hoặc giá không hợp lệ!", "Lỗi nhập liệu",
                    JOptionPane.ERROR_MESSAGE);
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