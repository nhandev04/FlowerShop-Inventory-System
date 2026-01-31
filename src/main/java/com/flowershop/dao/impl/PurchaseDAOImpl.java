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
            psOrder.setInt(2, order.getWarehouseId()); // Use selected warehouse
            psOrder.setBigDecimal(3, order.getTotalAmount());
            psOrder.setString(4, order.getNotes());

            int affected = psOrder.executeUpdate();
            if (affected == 0)
                throw new SQLException("Tạo đơn nhập hàng thất bại.");

            int purchaseId = 0;
            rs = psOrder.getGeneratedKeys();
            if (rs.next()) {
                purchaseId = rs.getInt(1);
            }

            String sqlDetail = "INSERT INTO PurchaseOrderDetails (POID, ProductID, Quantity, UnitCost, ReceivedQuantity) VALUES (?, ?, ?, ?, ?)";

            // Use selected warehouse in UPDATE query
            String sqlStock = "UPDATE Inventory SET QuantityOnHand = QuantityOnHand + ?, LastUpdated = GETDATE() WHERE ProductID = ? AND WarehouseID = ?";

            // Use selected warehouse in StockMovements
            String sqlHistory = "INSERT INTO StockMovements (ProductID, WarehouseID, MovementType, Quantity, ReferenceType, ReferenceID, MovementDate, CreatedBy, Notes) "
                    +
                    "VALUES (?, ?, 'IN', ?, 'PurchaseOrder', ?, GETDATE(), N'Thủ Kho', ?)";

            // Check/create inventory record if not exists
            String sqlCheckInventory = "SELECT COUNT(*) FROM Inventory WHERE ProductID = ? AND WarehouseID = ?";
            String sqlInsertInventory = "INSERT INTO Inventory (ProductID, WarehouseID, QuantityOnHand, LastUpdated) VALUES (?, ?, 0, GETDATE())";

            psDetail = conn.prepareStatement(sqlDetail);
            psUpdateStock = conn.prepareStatement(sqlStock);
            psHistory = conn.prepareStatement(sqlHistory);
            PreparedStatement psCheckInventory = conn.prepareStatement(sqlCheckInventory);
            PreparedStatement psInsertInventory = conn.prepareStatement(sqlInsertInventory);

            for (PurchaseOrderDetailDTO detail : details) {
                // Check if inventory record exists for this product-warehouse combination
                psCheckInventory.setInt(1, detail.getProductId());
                psCheckInventory.setInt(2, order.getWarehouseId());
                ResultSet rsCheck = psCheckInventory.executeQuery();
                rsCheck.next();
                int count = rsCheck.getInt(1);
                rsCheck.close();

                // Create inventory record if it doesn't exist
                if (count == 0) {
                    psInsertInventory.setInt(1, detail.getProductId());
                    psInsertInventory.setInt(2, order.getWarehouseId());
                    psInsertInventory.executeUpdate();
                }

                // Insert purchase order detail
                psDetail.setInt(1, purchaseId);
                psDetail.setInt(2, detail.getProductId());
                psDetail.setInt(3, detail.getQuantity());
                psDetail.setBigDecimal(4, detail.getUnitCost());
                psDetail.setInt(5, detail.getQuantity());
                psDetail.addBatch();

                // Update stock with selected warehouse
                psUpdateStock.setInt(1, detail.getQuantity());
                psUpdateStock.setInt(2, detail.getProductId());
                psUpdateStock.setInt(3, order.getWarehouseId()); // Use selected warehouse
                psUpdateStock.addBatch();

                // Insert stock movement with selected warehouse
                psHistory.setInt(1, detail.getProductId());
                psHistory.setInt(2, order.getWarehouseId()); // Use selected warehouse
                psHistory.setInt(3, detail.getQuantity());
                psHistory.setInt(4, purchaseId);
                psHistory.setString(5, "Nhập hàng PO #" + purchaseId);
                psHistory.addBatch();
            }

            psDetail.executeBatch();
            psUpdateStock.executeBatch();
            psHistory.executeBatch();

            // Close extra statements
            psCheckInventory.close();
            psInsertInventory.close();

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