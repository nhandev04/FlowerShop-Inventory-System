package com.flowershop.dao.impl;

import com.flowershop.config.DatabaseConnection;
import com.flowershop.dao.CustomerDAO;
import com.flowershop.model.dto.CustomerDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public List<CustomerDTO> getAll() {
        List<CustomerDTO> customers = new ArrayList<>();
        String sql = "SELECT CustomerID, CustomerName, Phone, Email, Address " +
                "FROM Customers WHERE IsActive = 1 ORDER BY CustomerName";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                CustomerDTO customer = new CustomerDTO();
                customer.setCustomerId(rs.getInt("CustomerID"));
                customer.setCustomerName(rs.getString("CustomerName"));
                customer.setPhone(rs.getString("Phone"));
                customer.setEmail(rs.getString("Email"));
                customer.setAddress(rs.getString("Address"));
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customers;
    }

    @Override
    public CustomerDTO getById(int id) {
        String sql = "SELECT CustomerID, CustomerName, Phone, Email, Address " +
                "FROM Customers WHERE CustomerID = ? AND IsActive = 1";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                CustomerDTO customer = new CustomerDTO();
                customer.setCustomerId(rs.getInt("CustomerID"));
                customer.setCustomerName(rs.getString("CustomerName"));
                customer.setPhone(rs.getString("Phone"));
                customer.setEmail(rs.getString("Email"));
                customer.setAddress(rs.getString("Address"));
                return customer;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean add(CustomerDTO customer) {
        String sql = "INSERT INTO Customers (CustomerName, Phone, Email, Address, IsActive) " +
                "VALUES (?, ?, ?, ?, 1)";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, customer.getCustomerName());
            ps.setString(2, customer.getPhone());
            ps.setString(3, customer.getEmail());
            ps.setString(4, customer.getAddress());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(CustomerDTO customer) {
        String sql = "UPDATE Customers SET CustomerName = ?, Phone = ?, Email = ?, Address = ? " +
                "WHERE CustomerID = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, customer.getCustomerName());
            ps.setString(2, customer.getPhone());
            ps.setString(3, customer.getEmail());
            ps.setString(4, customer.getAddress());
            ps.setInt(5, customer.getCustomerId());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        // Soft delete: set IsActive = 0
        String sql = "UPDATE Customers SET IsActive = 0 WHERE CustomerID = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
