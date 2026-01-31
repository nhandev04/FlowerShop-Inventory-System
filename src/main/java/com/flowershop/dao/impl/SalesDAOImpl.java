package com.flowershop.dao.impl;

import com.flowershop.config.DatabaseConnection;
import com.flowershop.dao.SalesDAO;
import com.flowershop.model.dto.SalesOrderDTO;
import com.flowershop.model.dto.SalesOrderDetailDTO;

import java.sql.*;
import java.util.List;

public class SalesDAOImpl implements SalesDAO {

    @Override
    public boolean createOrder(SalesOrderDTO order, List<SalesOrderDetailDTO> details) {
        Connection conn = null;
        PreparedStatement psOrder = null;
        PreparedStatement psDetail = null;
        PreparedStatement psUpdateStock = null;
        PreparedStatement psHistory = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getInstance().getConnection();
            conn.setAutoCommit(false);

            String sqlOrder = "INSERT INTO SalesOrders (CustomerID, WarehouseID, OrderDate, TotalAmount, Status, Notes) VALUES (?, ?, GETDATE(), ?, 'Completed', ?)";
            psOrder = conn.prepareStatement(sqlOrder, Statement.RETURN_GENERATED_KEYS);
            psOrder.setInt(1, order.getCustomerId() > 0 ? order.getCustomerId() : 4);
            psOrder.setInt(2, order.getWarehouseId()); // Use selected warehouse
            psOrder.setBigDecimal(3, order.getTotalAmount());
            psOrder.setString(4, order.getNotes());

            if (psOrder.executeUpdate() == 0)
                throw new SQLException("Tạo hóa đơn thất bại.");

            int orderId = 0;
            rs = psOrder.getGeneratedKeys();
            if (rs.next())
                orderId = rs.getInt(1);

            String sqlDetail = "INSERT INTO SalesOrderDetails (SalesOrderID, ProductID, Quantity, UnitPrice) VALUES (?, ?, ?, ?)";

            // Use selected warehouse in UPDATE query
            String sqlStock = "UPDATE Inventory SET QuantityOnHand = QuantityOnHand - ?, LastUpdated = GETDATE() WHERE ProductID = ? AND WarehouseID = ?";

            // Use selected warehouse in StockMovements
            String sqlHistory = "INSERT INTO StockMovements (ProductID, WarehouseID, MovementType, Quantity, ReferenceType, ReferenceID, MovementDate, CreatedBy, Notes) "
                    +
                    "VALUES (?, ?, 'OUT', ?, 'SalesOrder', ?, GETDATE(), N'Nhân Viên', ?)";

            psDetail = conn.prepareStatement(sqlDetail);
            psUpdateStock = conn.prepareStatement(sqlStock);
            psHistory = conn.prepareStatement(sqlHistory);

            for (SalesOrderDetailDTO detail : details) {
                psDetail.setInt(1, orderId);
                psDetail.setInt(2, detail.getProductId());
                psDetail.setInt(3, detail.getQuantity());
                psDetail.setBigDecimal(4, detail.getUnitPrice());
                psDetail.addBatch();

                // Update stock from selected warehouse
                psUpdateStock.setInt(1, detail.getQuantity());
                psUpdateStock.setInt(2, detail.getProductId());
                psUpdateStock.setInt(3, order.getWarehouseId()); // Use selected warehouse
                psUpdateStock.addBatch();

                // Record movement from selected warehouse
                psHistory.setInt(1, detail.getProductId());
                psHistory.setInt(2, order.getWarehouseId()); // Use selected warehouse
                psHistory.setInt(3, detail.getQuantity());
                psHistory.setInt(4, orderId);
                psHistory.setString(5, "Bán hàng SO #" + orderId);
                psHistory.addBatch();
            }

            psDetail.executeBatch();
            psUpdateStock.executeBatch();
            psHistory.executeBatch();

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
                if (psOrder != null)
                    psOrder.close();
                if (psDetail != null)
                    psDetail.close();
                if (psUpdateStock != null)
                    psUpdateStock.close();
                if (psHistory != null)
                    psHistory.close();
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}