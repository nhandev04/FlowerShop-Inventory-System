package com.flowershop.dao.impl;

import com.flowershop.config.DatabaseConnection;
import com.flowershop.dao.WarehouseTransferDAO;
import com.flowershop.model.dto.WarehouseTransferDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WarehouseTransferDAOImpl implements WarehouseTransferDAO {

    @Override
    public int createTransfer(WarehouseTransferDTO transfer) {
        String sql = "INSERT INTO WarehouseTransfers (ProductID, FromWarehouseID, ToWarehouseID, " +
                "Quantity, Status, Notes) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, transfer.getProductId());
            ps.setInt(2, transfer.getFromWarehouseId());
            ps.setInt(3, transfer.getToWarehouseId());
            ps.setInt(4, transfer.getQuantity());
            ps.setString(5, "Pending");
            ps.setString(6, transfer.getNotes());

            int affected = ps.executeUpdate();
            if (affected > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public List<WarehouseTransferDTO> getAllTransfers() {
        List<WarehouseTransferDTO> transfers = new ArrayList<>();
        String sql = "SELECT wt.TransferID, wt.ProductID, p.ProductName, " +
                "wt.FromWarehouseID, w1.WarehouseName as FromWarehouse, " +
                "wt.ToWarehouseID, w2.WarehouseName as ToWarehouse, " +
                "wt.Quantity, wt.TransferDate, wt.Status, wt.Notes " +
                "FROM WarehouseTransfers wt " +
                "JOIN Products p ON wt.ProductID = p.ProductID " +
                "JOIN Warehouses w1 ON wt.FromWarehouseID = w1.WarehouseID " +
                "JOIN Warehouses w2 ON wt.ToWarehouseID = w2.WarehouseID " +
                "ORDER BY wt.TransferDate DESC";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                WarehouseTransferDTO transfer = new WarehouseTransferDTO();
                transfer.setTransferId(rs.getInt("TransferID"));
                transfer.setProductId(rs.getInt("ProductID"));
                transfer.setProductName(rs.getString("ProductName"));
                transfer.setFromWarehouseId(rs.getInt("FromWarehouseID"));
                transfer.setFromWarehouseName(rs.getString("FromWarehouse"));
                transfer.setToWarehouseId(rs.getInt("ToWarehouseID"));
                transfer.setToWarehouseName(rs.getString("ToWarehouse"));
                transfer.setQuantity(rs.getInt("Quantity"));
                transfer.setTransferDate(rs.getTimestamp("TransferDate"));
                transfer.setStatus(rs.getString("Status"));
                transfer.setNotes(rs.getString("Notes"));
                transfers.add(transfer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transfers;
    }

    @Override
    public WarehouseTransferDTO getTransferById(int transferId) {
        String sql = "SELECT wt.TransferID, wt.ProductID, p.ProductName, " +
                "wt.FromWarehouseID, w1.WarehouseName as FromWarehouse, " +
                "wt.ToWarehouseID, w2.WarehouseName as ToWarehouse, " +
                "wt.Quantity, wt.TransferDate, wt.Status, wt.Notes " +
                "FROM WarehouseTransfers wt " +
                "JOIN Products p ON wt.ProductID = p.ProductID " +
                "JOIN Warehouses w1 ON wt.FromWarehouseID = w1.WarehouseID " +
                "JOIN Warehouses w2 ON wt.ToWarehouseID = w2.WarehouseID " +
                "WHERE wt.TransferID = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, transferId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                WarehouseTransferDTO transfer = new WarehouseTransferDTO();
                transfer.setTransferId(rs.getInt("TransferID"));
                transfer.setProductId(rs.getInt("ProductID"));
                transfer.setProductName(rs.getString("ProductName"));
                transfer.setFromWarehouseId(rs.getInt("FromWarehouseID"));
                transfer.setFromWarehouseName(rs.getString("FromWarehouse"));
                transfer.setToWarehouseId(rs.getInt("ToWarehouseID"));
                transfer.setToWarehouseName(rs.getString("ToWarehouse"));
                transfer.setQuantity(rs.getInt("Quantity"));
                transfer.setTransferDate(rs.getTimestamp("TransferDate"));
                transfer.setStatus(rs.getString("Status"));
                transfer.setNotes(rs.getString("Notes"));
                return transfer;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateTransferStatus(int transferId, String status) {
        String sql = "UPDATE WarehouseTransfers SET Status = ? WHERE TransferID = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, transferId);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean completeTransfer(int transferId) {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getInstance().getConnection();
            conn.setAutoCommit(false);

            WarehouseTransferDTO transfer = getTransferById(transferId);
            if (transfer == null) {
                return false;
            }

            String updateFromSql = "UPDATE Inventory SET QuantityOnHand = QuantityOnHand - ? " +
                    "WHERE ProductID = ? AND WarehouseID = ?";
            try (PreparedStatement ps = conn.prepareStatement(updateFromSql)) {
                ps.setInt(1, transfer.getQuantity());
                ps.setInt(2, transfer.getProductId());
                ps.setInt(3, transfer.getFromWarehouseId());
                ps.executeUpdate();
            }

            String updateToSql = "UPDATE Inventory SET QuantityOnHand = QuantityOnHand + ? " +
                    "WHERE ProductID = ? AND WarehouseID = ?";
            try (PreparedStatement ps = conn.prepareStatement(updateToSql)) {
                ps.setInt(1, transfer.getQuantity());
                ps.setInt(2, transfer.getProductId());
                ps.setInt(3, transfer.getToWarehouseId());
                ps.executeUpdate();
            }

            String movementSql = "INSERT INTO StockMovements (ProductID, WarehouseID, MovementType, " +
                    "Quantity, ReferenceType, ReferenceID, Notes) " +
                    "VALUES (?, ?, 'TRANSFER', ?, 'WarehouseTransfer', ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(movementSql)) {
                ps.setInt(1, transfer.getProductId());
                ps.setInt(2, transfer.getFromWarehouseId());
                ps.setInt(3, -transfer.getQuantity());
                ps.setInt(4, transferId);
                ps.setString(5, "Transfer to " + transfer.getToWarehouseName());
                ps.executeUpdate();
            }

            try (PreparedStatement ps = conn.prepareStatement(movementSql)) {
                ps.setInt(1, transfer.getProductId());
                ps.setInt(2, transfer.getToWarehouseId());
                ps.setInt(3, transfer.getQuantity());
                ps.setInt(4, transferId);
                ps.setString(5, "Transfer from " + transfer.getFromWarehouseName());
                ps.executeUpdate();
            }

            String statusSql = "UPDATE WarehouseTransfers SET Status = 'Completed' WHERE TransferID = ?";
            try (PreparedStatement ps = conn.prepareStatement(statusSql)) {
                ps.setInt(1, transferId);
                ps.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
}
