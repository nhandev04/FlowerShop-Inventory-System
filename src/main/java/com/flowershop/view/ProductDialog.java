package com.flowershop.view;

import com.flowershop.model.dto.CategoryDTO;
import com.flowershop.model.dto.ProductDTO;
import com.flowershop.service.CategoryService;
import com.flowershop.service.ProductService;
import com.flowershop.service.impl.CategoryServiceImpl;
import com.flowershop.service.impl.ProductServiceImpl;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

public class ProductDialog extends JDialog {

    private JTextField txtName, txtSku, txtPrice, txtReorder;
    private JComboBox<CategoryDTO> cboCategory;
    private JButton btnSave, btnCancel;
    private boolean isSaved = false;
    private ProductDTO productToEdit = null;
    private final ProductService productService = new ProductServiceImpl();
    private final CategoryService categoryService = new CategoryServiceImpl();

    public ProductDialog(Frame parent) {
        super(parent, "Thêm Sản Phẩm Mới", true);
        initCommon();
    }

    public ProductDialog(Frame parent, ProductDTO product) {
        super(parent, "Cập Nhật Sản Phẩm", true);
        this.productToEdit = product;
        initCommon();
        fillData();
    }

    private void initCommon() {
        setSize(400, 350);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout());
        initComponents();
        loadCategories();
    }

    private void initComponents() {
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        formPanel.add(new JLabel("Tên Sản Phẩm (*):"));
        txtName = new JTextField();
        formPanel.add(txtName);

        formPanel.add(new JLabel("Danh Mục:"));
        cboCategory = new JComboBox<>();
        formPanel.add(cboCategory);

        formPanel.add(new JLabel("Mã Kho (SKU) (*):"));
        txtSku = new JTextField();
        formPanel.add(txtSku);

        formPanel.add(new JLabel("Giá Bán (VND) (*):"));
        txtPrice = new JTextField();
        formPanel.add(txtPrice);

        formPanel.add(new JLabel("Mức Báo Động (SL):"));
        txtReorder = new JTextField("10");
        formPanel.add(txtReorder);

        add(formPanel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnSave = new JButton("Lưu");
        btnCancel = new JButton("Hủy");

        btnSave.addActionListener(e -> saveProduct());
        btnCancel.addActionListener(e -> dispose());

        btnPanel.add(btnSave);
        btnPanel.add(btnCancel);
        add(btnPanel, BorderLayout.SOUTH);
    }

    private void loadCategories() {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        for (CategoryDTO c : categories) {
            cboCategory.addItem(c);
        }
    }

    private void fillData() {
        if (productToEdit == null) return;

        txtName.setText(productToEdit.getProductName());
        txtSku.setText(productToEdit.getSku());
        txtPrice.setText(productToEdit.getPrice().toString());
        txtReorder.setText(String.valueOf(productToEdit.getReorderLevel()));

        for (int i = 0; i < cboCategory.getItemCount(); i++) {
            CategoryDTO item = cboCategory.getItemAt(i);
            if (item.getCategoryId().equals(productToEdit.getCategoryId())) {
                cboCategory.setSelectedIndex(i);
                break;
            }
        }
    }

    private void saveProduct() {
        String name = txtName.getText().trim();
        String sku = txtSku.getText().trim();
        String priceStr = txtPrice.getText().trim();
        CategoryDTO selectedCat = (CategoryDTO) cboCategory.getSelectedItem();

        if (name.isEmpty() || sku.isEmpty() || priceStr.isEmpty() || selectedCat == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            BigDecimal price = new BigDecimal(priceStr);
            int reorder = Integer.parseInt(txtReorder.getText().trim());

            ProductDTO dto = new ProductDTO();

            if (productToEdit != null) {
                dto.setProductId(productToEdit.getProductId());
            }

            dto.setProductName(name);
            dto.setCategoryId(selectedCat.getCategoryId());
            dto.setSku(sku);
            dto.setPrice(price);
            dto.setReorderLevel(reorder);
            dto.setIsActive(true);

            boolean success;
            if (productToEdit == null) {
                success = productService.addProduct(dto);
            } else {
                success = productService.updateProduct(dto);
            }

            if (success) {
                this.isSaved = true;
                JOptionPane.showMessageDialog(this, "Lưu thành công!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Lưu thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số hợp lệ!", "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isSaved() {
        return isSaved;
    }
}