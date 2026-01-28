package com.flowershop.dao.impl;

import com.flowershop.config.DatabaseConnection;
import com.flowershop.model.dto.StockMovementDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StockMovementDAOImpl {

    public List<StockMovementDTO> getAllMovements() {
        List<StockMovementDTO> list = new ArrayList<>();
        String sql = "SELECT m.MovementID, p.ProductName, w.WarehouseName, m.MovementType, " +
                "m.Quantity, m.MovementDate, m.CreatedBy, m.Notes " +
                "FROM StockMovements m " +
                "JOIN Products p ON m.ProductID = p.ProductID " +
                "JOIN Warehouses w ON m.WarehouseID = w.WarehouseID " +
                "ORDER BY m.MovementDate DESC";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                StockMovementDTO dto = new StockMovementDTO();
                dto.setMovementId(rs.getInt("MovementID"));
                dto.setProductName(rs.getString("ProductName"));
                dto.setWarehouseName(rs.getString("WarehouseName"));
                dto.setType(rs.getString("MovementType"));
                dto.setQuantity(rs.getInt("Quantity"));
                dto.setMovementDate(rs.getTimestamp("MovementDate"));
                dto.setCreatedBy(rs.getString("CreatedBy"));
                dto.setNotes(rs.getString("Notes"));
                list.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}