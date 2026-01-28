package com.flowershop.dao.impl;

import com.flowershop.config.DatabaseConnection;
import com.flowershop.dao.PurchaseDAO;
import com.flowershop.model.dto.PurchaseOrderDTO;
import com.flowershop.model.dto.PurchaseOrderDetailDTO;

import java.sql.*;
import java.util.List;

public class PurchaseDAOImpl implements PurchaseDAO {

    @Override
    public boolean save(PurchaseOrderDTO order, List<PurchaseOrderDetailDTO> details) {
        Connection conn = null;
        PreparedStatement psOrder = null;
        PreparedStatement psDetail = null;
        PreparedStatement psUpdateStock = null;
        PreparedStatement psHistory = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getInstance().getConnection();
            conn.setAutoCommit(false);

            String sqlOrder = "INSERT INTO PurchaseOrders (SupplierID, WarehouseID, OrderDate, TotalAmount, Status, Notes) VALUES (?, ?, GETDATE(), ?, 'Received', ?)";
            psOrder = conn.prepareStatement(sqlOrder, Statement.RETURN_GENERATED_KEYS);
            psOrder.setInt(1, order.getSupplierId());
            psOrder.setInt(2, 1);
            psOrder.setBigDecimal(3, order.getTotalAmount());
            psOrder.setString(4, order.getNotes());

            int affected = psOrder.executeUpdate();
            if (affected == 0) throw new SQLException("Tạo đơn nhập hàng thất bại.");

            int purchaseId = 0;
            rs = psOrder.getGeneratedKeys();
            if (rs.next()) {
                purchaseId = rs.getInt(1);
            }

            String sqlDetail = "INSERT INTO PurchaseOrderDetails (POID, ProductID, Quantity, UnitCost, ReceivedQuantity) VALUES (?, ?, ?, ?, ?)";

            String sqlStock = "UPDATE Inventory SET QuantityOnHand = QuantityOnHand + ?, LastUpdated = GETDATE() WHERE ProductID = ? AND WarehouseID = 1";

            String sqlHistory = "INSERT INTO StockMovements (ProductID, WarehouseID, MovementType, Quantity, ReferenceType, ReferenceID, MovementDate, CreatedBy, Notes) " +
                    "VALUES (?, 1, 'IN', ?, 'PurchaseOrder', ?, GETDATE(), N'Thủ Kho', ?)";

            psDetail = conn.prepareStatement(sqlDetail);
            psUpdateStock = conn.prepareStatement(sqlStock);
            psHistory = conn.prepareStatement(sqlHistory);

            for (PurchaseOrderDetailDTO detail : details) {
                psDetail.setInt(1, purchaseId);
                psDetail.setInt(2, detail.getProductId());
                psDetail.setInt(3, detail.getQuantity());
                psDetail.setBigDecimal(4, detail.getUnitCost());
                psDetail.setInt(5, detail.getQuantity());
                psDetail.addBatch();

                psUpdateStock.setInt(1, detail.getQuantity());
                psUpdateStock.setInt(2, detail.getProductId());
                psUpdateStock.addBatch();

                psHistory.setInt(1, detail.getProductId());
                psHistory.setInt(2, detail.getQuantity());
                psHistory.setInt(3, purchaseId);
                psHistory.setString(4, "Nhập hàng PO #" + purchaseId);
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
                if (conn != null) conn.rollback();
            } catch (SQLException ex) { ex.printStackTrace(); }
            return false;
        } finally {
            try {
                if (rs != null) rs.close();
                if (psOrder != null) psOrder.close();
                if (psDetail != null) psDetail.close();
                if (psUpdateStock != null) psUpdateStock.close();
                if (psHistory != null) psHistory.close();
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) { e.printStackTrace(); }
        }
    }
}