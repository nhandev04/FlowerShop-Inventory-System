package com.flowershop.dao.impl;

import com.flowershop.config.DatabaseConnection;
import com.flowershop.dao.ProductDAO;
import com.flowershop.model.dto.ProductDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {

    private Connection getConnection() throws SQLException {
        return DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public List<ProductDTO> getAll() {
        List<ProductDTO> list = new ArrayList<>();
        // Query to show each product-warehouse combination separately
        String sql = "SELECT p.*, c.CategoryName, " +
                "i.WarehouseID, w.WarehouseName, " +
                "ISNULL(i.QuantityOnHand, 0) AS Stock " +
                "FROM Products p " +
                "LEFT JOIN Categories c ON p.CategoryID = c.CategoryID " +
                "LEFT JOIN Inventory i ON p.ProductID = i.ProductID " +
                "LEFT JOIN Warehouses w ON i.WarehouseID = w.WarehouseID " +
                "WHERE p.IsActive = 1 " +
                "ORDER BY p.ProductID, w.WarehouseName";

        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ProductDTO p = new ProductDTO();
                p.setProductId(rs.getInt("ProductID"));
                p.setProductName(rs.getString("ProductName"));
                p.setCategoryId(rs.getInt("CategoryID"));
                p.setSku(rs.getString("SKU"));
                p.setPrice(rs.getBigDecimal("UnitPrice"));
                p.setReorderLevel(rs.getInt("ReorderLevel"));
                p.setIsActive(rs.getBoolean("IsActive"));
                p.setCreatedAt(rs.getTimestamp("CreatedAt"));
                p.setCategoryName(rs.getString("CategoryName"));

                // Warehouse-specific data (directly from JOIN)
                p.setWarehouseId(rs.getInt("WarehouseID"));
                p.setWarehouseName(rs.getString("WarehouseName"));
                p.setQuantityOnHand(rs.getInt("Stock"));

                // Get purchase price
                p.setPurchasePrice(getLatestPurchasePrice(conn, p.getProductId()));

                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private java.math.BigDecimal getLatestPurchasePrice(Connection conn, int productId) {
        String sql = "SELECT TOP 1 pod.UnitCost " +
                "FROM PurchaseOrderDetails pod " +
                "JOIN PurchaseOrders po ON pod.POID = po.POID " +
                "WHERE pod.ProductID = ? " +
                "ORDER BY po.OrderDate DESC";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getBigDecimal("UnitCost");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new java.math.BigDecimal(0);
    }

    @Override
    public ProductDTO getById(int id) {
        String sql = "SELECT p.*, ISNULL(i.TotalStock, 0) as Stock " +
                "FROM Products p " +
                "LEFT JOIN (" +
                "    SELECT ProductID, SUM(QuantityOnHand) as TotalStock " +
                "    FROM Inventory " +
                "    GROUP BY ProductID" +
                ") i ON p.ProductID = i.ProductID " +
                "WHERE p.ProductID = ? AND p.IsActive = 1";

        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ProductDTO p = new ProductDTO();

                    p.setProductId(rs.getInt("ProductID"));
                    p.setProductName(rs.getString("ProductName"));
                    p.setCategoryId(rs.getInt("CategoryID"));
                    p.setSku(rs.getString("SKU"));

                    p.setPrice(rs.getBigDecimal("UnitPrice"));

                    p.setReorderLevel(rs.getInt("ReorderLevel"));
                    p.setIsActive(rs.getBoolean("IsActive"));

                    p.setQuantityOnHand(rs.getInt("Stock"));

                    return p;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean add(ProductDTO p) {
        Connection conn = null;
        PreparedStatement psProduct = null;
        PreparedStatement psInventory = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            conn.setAutoCommit(false);
            String sqlProduct = "INSERT INTO Products (ProductName, CategoryID, SKU, UnitPrice, ReorderLevel, IsActive) VALUES (?, ?, ?, ?, ?, 1)";
            psProduct = conn.prepareStatement(sqlProduct, Statement.RETURN_GENERATED_KEYS);

            psProduct.setString(1, p.getProductName());
            psProduct.setInt(2, p.getCategoryId());
            psProduct.setString(3, p.getSku());
            psProduct.setBigDecimal(4, p.getPrice());
            psProduct.setInt(5, p.getReorderLevel());

            int rowsAffected = psProduct.executeUpdate();

            if (rowsAffected > 0) {
                rs = psProduct.getGeneratedKeys();
                if (rs.next()) {
                    int newProductId = rs.getInt(1);
                    String sqlInventory = "INSERT INTO Inventory (ProductID, WarehouseID, QuantityOnHand, LastUpdated) VALUES (?, 1, 0, GETDATE())";
                    psInventory = conn.prepareStatement(sqlInventory);
                    psInventory.setInt(1, newProductId);
                    psInventory.executeUpdate();
                }
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (conn != null)
                    conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (psProduct != null)
                    psProduct.close();
                if (psInventory != null)
                    psInventory.close();
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean update(ProductDTO p) {
        String sql = "UPDATE Products SET ProductName=?, CategoryID=?, SKU=?, UnitPrice=?, ReorderLevel=? WHERE ProductID=?";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getProductName());
            ps.setInt(2, p.getCategoryId());
            ps.setString(3, p.getSku());
            ps.setBigDecimal(4, p.getPrice());
            ps.setInt(5, p.getReorderLevel());
            ps.setInt(6, p.getProductId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "UPDATE Products SET IsActive = 0 WHERE ProductID = ?";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}