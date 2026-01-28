package com.flowershop.view;

import com.flowershop.factory.ReportExporter;
import com.flowershop.factory.ReportFactory;
import com.flowershop.model.dto.CategoryDTO;
import com.flowershop.model.dto.ProductDTO;
import com.flowershop.service.CategoryService;
import com.flowershop.service.ProductService;
import com.flowershop.service.impl.CategoryServiceImpl;
import com.flowershop.service.impl.ProductServiceImpl;
import com.flowershop.view.observer.ShopEventManager;
import com.flowershop.view.observer.ShopObserver;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.text.DecimalFormat;
import java.util.List;

public class ProductPanel extends JPanel implements ShopObserver {

    private JTable table;
    private DefaultTableModel tableModel;
    private JComboBox<CategoryDTO> cboCategoryFilter;
    private JTextField txtSearch;

    private final ProductService productService;
    private final CategoryService categoryService;
    private final DecimalFormat formatter = new DecimalFormat("#,### VND");

    public ProductPanel() {
        this.productService = new ProductServiceImpl();
        this.categoryService = new CategoryServiceImpl();

        initComponents();
        loadCategories();
        loadDataToTable();

        ShopEventManager.getInstance().subscribe(this);
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(Color.WHITE);

        JPanel pnlHeader = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlHeader.setBackground(Color.WHITE);

        pnlHeader.add(new JLabel("Tìm kiếm:"));
        txtSearch = new JTextField(15);
        pnlHeader.add(txtSearch);

        JButton btnSearch = new JButton("Tìm");
        pnlHeader.add(btnSearch);

        pnlHeader.add(Box.createHorizontalStrut(20));

        pnlHeader.add(new JLabel("Danh mục:"));
        cboCategoryFilter = new JComboBox<>();
        cboCategoryFilter.setPreferredSize(new Dimension(150, 25));
        pnlHeader.add(cboCategoryFilter);

        cboCategoryFilter.addActionListener(e -> filterByCategory());
        btnSearch.addActionListener(e -> loadDataToTable());

        add(pnlHeader, BorderLayout.NORTH);

        String[] columns = {"ID", "Tên Sản Phẩm", "Danh Mục", "Giá Bán", "Tồn Kho", "Đơn Vị"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlButtons.setBackground(Color.WHITE);

        JButton btnAdd = new JButton("Thêm Mới");
        JButton btnEdit = new JButton("Sửa");
        JButton btnDelete = new JButton("Xóa");
        JButton btnRefresh = new JButton("Làm Mới");

        JButton btnExport = new JButton("Xuất Báo Cáo");
        try {
            btnExport.setIcon(new ImageIcon(getClass().getResource("/icons/excel.png")));
        } catch (Exception e) {}

        btnExport.addActionListener(e -> {
            String[] options = {"Excel", "PDF", "CSV"};
            int choice = JOptionPane.showOptionDialog(this, "Chọn định dạng xuất file:", "Xuất Báo Cáo",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            if (choice != -1) {
                String format = options[choice].toLowerCase();
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Lưu file báo cáo");

                if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                    String path = fileChooser.getSelectedFile().getAbsolutePath();
                    if (!path.endsWith("." + format)) {
                        path += "." + format;
                    }

                    try {
                        List<ProductDTO> listData = productService.getAllProducts();
                        ReportExporter exporter = ReportFactory.getExporter(format);

                        if (exporter != null) {
                            exporter.export(listData, path);
                            JOptionPane.showMessageDialog(this, "Xuất file thành công!\n" + path);
                            try {
                                Desktop.getDesktop().open(new File(path));
                            } catch (Exception ex) {}
                        } else {
                            JOptionPane.showMessageDialog(this, "Chưa hỗ trợ định dạng này!");
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Lỗi khi xuất file: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        btnAdd.setBackground(new Color(0, 153, 76)); btnAdd.setForeground(Color.WHITE);
        btnEdit.setBackground(new Color(0, 102, 204)); btnEdit.setForeground(Color.WHITE);
        btnDelete.setBackground(new Color(204, 0, 0)); btnDelete.setForeground(Color.WHITE);
        btnExport.setBackground(new Color(255, 102, 0)); btnExport.setForeground(Color.WHITE);

        pnlButtons.add(btnAdd);
        pnlButtons.add(btnEdit);
        pnlButtons.add(btnDelete);
        pnlButtons.add(btnRefresh);
        pnlButtons.add(btnExport);

        add(pnlButtons, BorderLayout.SOUTH);
        btnRefresh.addActionListener(e -> loadDataToTable());
    }

    private void loadCategories() {
        cboCategoryFilter.removeAllItems();
        CategoryDTO all = new CategoryDTO();
        all.setCategoryId(0);
        all.setCategoryName("--- Tất cả ---");
        cboCategoryFilter.addItem(all);

        List<CategoryDTO> list = categoryService.getAllCategories();
        for (CategoryDTO c : list) {
            cboCategoryFilter.addItem(c);
        }
    }

    private void loadDataToTable() {
        tableModel.setRowCount(0);
        List<ProductDTO> list = productService.getAllProducts();

        for (ProductDTO p : list) {
            Object[] row = new Object[]{
                    p.getProductId(),
                    p.getProductName(),
                    p.getCategoryName(),
                    formatter.format(p.getPrice() != null ? p.getPrice().doubleValue() : 0),
                    p.getQuantityOnHand(),
                    "Cái/Bông"
            };
            tableModel.addRow(row);
        }
    }

    private void filterByCategory() {
        CategoryDTO selected = (CategoryDTO) cboCategoryFilter.getSelectedItem();
        if (selected != null && selected.getCategoryId() == 0) {
            loadDataToTable();
        } else if (selected != null) {
            tableModel.setRowCount(0);
            List<ProductDTO> list = productService.getAllProducts();
            for (ProductDTO p : list) {
                if(p.getCategoryId() == selected.getCategoryId()) {
                    Object[] row = {
                            p.getProductId(),
                            p.getProductName(),
                            p.getCategoryName(),
                            formatter.format(p.getPrice() != null ? p.getPrice().doubleValue() : 0),
                            p.getQuantityOnHand(),
                            "Cái/Bông"
                    };
                    tableModel.addRow(row);
                }
            }
        }
    }

    @Override
    public void updateData(String eventType) {
        if (eventType.equals("PRODUCT_CHANGED")) {
            loadDataToTable();
        }
    }

    @Override
    public void removeNotify() {
        super.removeNotify();
        ShopEventManager.getInstance().unsubscribe(this);
    }
}