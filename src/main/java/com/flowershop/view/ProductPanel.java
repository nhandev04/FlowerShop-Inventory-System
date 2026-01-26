package com.flowershop.view;

import com.flowershop.factory.ReportExporter;
import com.flowershop.factory.ReportFactory;
import com.flowershop.model.dto.ProductDTO;
import com.flowershop.service.ProductService;
import com.flowershop.service.impl.ProductServiceImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.text.DecimalFormat;
import java.util.List;

public class ProductPanel extends JPanel implements com.flowershop.view.observer.ShopObserver {

    private final ProductService productService;
    private DefaultTableModel tableModel;
    private JTable table;

    public ProductPanel() {
        this.productService = new ProductServiceImpl();
        initComponents();
        loadData();
        com.flowershop.view.observer.ShopEventManager.getInstance().subscribe(this);
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);

        JButton btnAdd = new JButton("Thêm Mới");
        btnAdd.addActionListener(e -> {
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            ProductDialog dialog = new ProductDialog(parentFrame);
            dialog.setVisible(true);
            if (dialog.isSaved()) loadData();
        });

        JButton btnEdit = new JButton("Sửa");
        btnEdit.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int productId = (int) tableModel.getValueAt(selectedRow, 0);
            ProductDTO product = productService.getProductById(productId);

            if (product != null) {
                JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                ProductDialog dialog = new ProductDialog(parentFrame, product);
                dialog.setVisible(true);
                if (dialog.isSaved()) loadData();
            }
        });

        JButton btnDelete = new JButton("Xóa");
        btnDelete.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int productId = (int) tableModel.getValueAt(selectedRow, 0);
            String productName = (String) tableModel.getValueAt(selectedRow, 1);

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc muốn xóa sản phẩm: " + productName + "?",
                    "Xác nhận xóa",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                if (productService.deleteProduct(productId)) {
                    JOptionPane.showMessageDialog(this, "Đã xóa thành công!");
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton btnRefresh = new JButton("Làm mới");
        btnRefresh.addActionListener(e -> {
            com.flowershop.view.observer.ShopEventManager.getInstance().notify("PRODUCT_CHANGED");
        });
        JButton btnExport = new JButton("Xuất Báo Cáo");
        btnExport.addActionListener(e -> {
            String[] options = {"Excel (.xlsx)", "PDF (.pdf)", "CSV (.csv)"};
            int choice = JOptionPane.showOptionDialog(this,
                    "Chọn định dạng báo cáo muốn xuất:",
                    "Xuất Báo Cáo",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);

            if (choice == -1) return;
            String type = "";
            String extension = "";
            if (choice == 0) { type = "EXCEL"; extension = ".xlsx"; }
            else if (choice == 1) { type = "PDF"; extension = ".pdf"; }
            else if (choice == 2) { type = "CSV"; extension = ".csv"; }

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Lưu file " + type);
            fileChooser.setSelectedFile(new File("DanhSachSanPham" + extension));

            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                try {
                    String path = fileChooser.getSelectedFile().getAbsolutePath();
                    if (!path.toLowerCase().endsWith(extension)) {
                        path += extension;
                    }
                    List<ProductDTO> data = productService.getAllProducts();
                    ReportExporter exporter = ReportFactory.getExporter(type);
                    exporter.export(data, path);

                    JOptionPane.showMessageDialog(this, "Xuất file thành công!\nĐường dẫn: " + path);
                    Desktop.getDesktop().open(new File(path));

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi Xuất File", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });

        toolBar.add(btnAdd);
        toolBar.add(btnEdit);
        toolBar.add(btnDelete);
        toolBar.addSeparator();
        toolBar.add(btnRefresh);
        toolBar.addSeparator();
        toolBar.add(btnExport);

        add(toolBar, BorderLayout.NORTH);

        String[] columnNames = {"ID", "Tên Sản Phẩm", "Danh Mục", "Mã Kho", "Giá Bán", "Tồn Kho"};

        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(25);
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(0).setMaxWidth(50);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void loadData() {
        tableModel.setRowCount(0);

        List<ProductDTO> products = productService.getAllProducts();
        DecimalFormat df = new DecimalFormat("#,### VND");

        for (ProductDTO p : products) {
            Object[] row = {
                    p.getProductId(),
                    p.getProductName(),
                    p.getCategoryName(),
                    p.getSku(),
                    df.format(p.getPrice()),
                    p.getReorderLevel()
            };
            tableModel.addRow(row);
        }
    }
    @Override
    public void updateData(String eventType) {
        if (eventType.equals("PRODUCT_CHANGED") || eventType.equals("ORDER_CREATED")) {
            System.out.println("ProductPanel nhận tín hiệu update: " + eventType);
            loadData();
        }
    }

    @Override
    public void removeNotify() {
        super.removeNotify();
        com.flowershop.view.observer.ShopEventManager.getInstance().unsubscribe(this);
    }
}
