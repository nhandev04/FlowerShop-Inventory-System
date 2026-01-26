package com.flowershop.dao.impl;

import com.flowershop.config.DatabaseConnection;
import com.flowershop.dao.OrderDAO;

import java.sql.*;

public class OrderDAOImpl implements OrderDAO {

    @Override
    public boolean createOrder(int productId, int quantity, String customerName, double finalPrice) {
        Connection conn = null;
        PreparedStatement psOrder = null;
        PreparedStatement psDetail = null;
        PreparedStatement psUpdateStock = null;
        ResultSet rsKey = null;

        try {
            conn = DatabaseConnection.getInstance().getConnection();
            conn.setAutoCommit(false);
            String sqlOrder = "INSERT INTO SalesOrders (CustomerName, OrderDate, TotalAmount, Status) VALUES (?, GETDATE(), ?, 'Completed')";
            String sqlOrderReal = "INSERT INTO SalesOrders (CustomerID, OrderDate, TotalAmount, Status, Notes) VALUES (4, GETDATE(), ?, 'Completed', ?)";
            psOrder = conn.prepareStatement(sqlOrderReal, Statement.RETURN_GENERATED_KEYS);
            psOrder.setDouble(1, finalPrice * quantity);
            psOrder.setString(2, "Khách hàng: " + customerName);
            psOrder.executeUpdate();
            rsKey = psOrder.getGeneratedKeys();
            int orderId = 0;
            if (rsKey.next()) {
                orderId = rsKey.getInt(1);
            }

            String sqlDetail = "INSERT INTO SalesOrderDetails (SalesOrderID, ProductID, Quantity, UnitPrice) VALUES (?, ?, ?, ?)";
            psDetail = conn.prepareStatement(sqlDetail);
            psDetail.setInt(1, orderId);
            psDetail.setInt(2, productId);
            psDetail.setInt(3, quantity);
            psDetail.setDouble(4, finalPrice);
            psDetail.executeUpdate();

            String sqlStock = "UPDATE Inventory SET QuantityOnHand = QuantityOnHand - ? WHERE ProductID = ? AND WarehouseID = 1";
            psUpdateStock = conn.prepareStatement(sqlStock);
            psUpdateStock.setInt(1, quantity);
            psUpdateStock.setInt(2, productId);
            int rows = psUpdateStock.executeUpdate();

            if (rows == 0) {
                throw new SQLException("Sản phẩm chưa được nhập vào Kho số 1 (Inventory)!");
            }

            conn.commit();
            System.out.println("Giao dịch thành công! OrderID: " + orderId);
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    System.err.println("Lỗi Transaction! Đang Rollback...");
                    conn.rollback();
                } catch (SQLException ex) { ex.printStackTrace(); }
            }
            return false;
        } finally {
            try {
                if (conn != null) conn.setAutoCommit(true);
                if (rsKey != null) rsKey.close();
                if (psOrder != null) psOrder.close();
                if (psDetail != null) psDetail.close();
                if (psUpdateStock != null) psUpdateStock.close();
            } catch (SQLException e) { e.printStackTrace(); }
        }
    }
}