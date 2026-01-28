package com.flowershop.dao.impl;

import com.flowershop.config.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ReportDAO {
    public double[] getMonthlyRevenue(int year) {
        double[] data = new double[12];
        Arrays.fill(data, 0);

        String sql = "SELECT MONTH(OrderDate) as Thang, SUM(TotalAmount) as TongTien " +
                "FROM SalesOrders " +
                "WHERE YEAR(OrderDate) = ? " +
                "AND (Status = 'Completed' OR Status = 'Delivered') " +
                "GROUP BY MONTH(OrderDate)";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, year);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int month = rs.getInt("Thang");
                double amount = rs.getDouble("TongTien");

                if (month >= 1 && month <= 12) {
                    data[month - 1] = amount;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public Map<String, Integer> getCustomerSegmentation() {
        Map<String, Integer> stats = new HashMap<>();
        stats.put("Khách VIP (>10tr)", 0);
        stats.put("Khách Thân Thiết (>2tr)", 0);
        stats.put("Khách Vãng Lai / Mới", 0);

        String sql = "SELECT CustomerID, SUM(TotalAmount) as TongChiTieu " +
                "FROM SalesOrders " +
                "WHERE Status = 'Completed' OR Status = 'Delivered' " +
                "GROUP BY CustomerID";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                double total = rs.getDouble("TongChiTieu");

                if (total >= 10000000) {
                    stats.put("Khách VIP (>10tr)", stats.get("Khách VIP (>10tr)") + 1);
                } else if (total >= 2000000) {
                    stats.put("Khách Thân Thiết (>2tr)", stats.get("Khách Thân Thiết (>2tr)") + 1);
                } else {
                    stats.put("Khách Vãng Lai / Mới", stats.get("Khách Vãng Lai / Mới") + 1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stats;
    }
}