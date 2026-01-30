package com.flowershop.dao.impl;

import com.flowershop.config.DatabaseConnection;
import com.flowershop.dao.CategoryDAO;
import com.flowershop.model.dto.CategoryDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAOImpl implements CategoryDAO {
    @Override
    public List<CategoryDTO> getAll() {
        List<CategoryDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM Categories WHERE IsActive = 1";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new CategoryDTO(
                        rs.getInt("CategoryID"),
                        rs.getInt("CategoryID") + " - " + rs.getString("CategoryName"),
                        rs.getString("Description")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}