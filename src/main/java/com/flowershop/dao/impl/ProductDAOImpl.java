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
        String sql = "SELECT p.*, c.CategoryName FROM Products p " +
                "LEFT JOIN Categories c ON p.CategoryID = c.CategoryID " +
                "WHERE p.IsActive = 1";

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

                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public ProductDTO getById(int id) {
        String sql = "SELECT * FROM Products WHERE ProductID = ? AND IsActive = 1";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return ProductDTO.builder()
                            .productId(rs.getInt("ProductID"))
                            .productName(rs.getString("ProductName"))
                            .categoryId(rs.getInt("CategoryID"))
                            .sku(rs.getString("SKU"))
                            .price(rs.getBigDecimal("UnitPrice"))
                            .reorderLevel(rs.getInt("ReorderLevel"))
                            .isActive(rs.getBoolean("IsActive"))
                            .build();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean add(ProductDTO p) {
        String sql = "INSERT INTO Products (ProductName, CategoryID, SKU, UnitPrice, ReorderLevel, IsActive) VALUES (?, ?, ?, ?, ?, 1)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getProductName());
            ps.setInt(2, p.getCategoryId());
            ps.setString(3, p.getSku());
            ps.setBigDecimal(4, p.getPrice());
            ps.setInt(5, p.getReorderLevel());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi database khi thêm sản phẩm: " + e.getMessage());
            e.printStackTrace();
            return false;
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