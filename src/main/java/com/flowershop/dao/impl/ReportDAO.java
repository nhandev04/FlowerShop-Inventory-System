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

    /**
     * Enhanced customer segmentation with revenue data
     * 
     * @param period "month", "quarter", "year", or "all"
     * @return Map of segment name to [count, revenue]
     */
    public Map<String, double[]> getCustomerSegmentationDetailed(String period) {
        Map<String, double[]> stats = new HashMap<>();
        stats.put("Khách VIP (≥10tr)", new double[] { 0, 0 }); // [count, revenue]
        stats.put("Khách Thân Thiết (≥2tr)", new double[] { 0, 0 });
        stats.put("Khách Vãng Lai (<2tr)", new double[] { 0, 0 });

        String dateFilter = getDateFilter(period);

        String sql = "SELECT CustomerID, SUM(TotalAmount) as TongChiTieu " +
                "FROM SalesOrders " +
                "WHERE (Status = 'Completed' OR Status = 'Delivered') " +
                dateFilter +
                "GROUP BY CustomerID";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                double total = rs.getDouble("TongChiTieu");

                String segment;
                if (total >= 10000000) {
                    segment = "Khách VIP (≥10tr)";
                } else if (total >= 2000000) {
                    segment = "Khách Thân Thiết (≥2tr)";
                } else {
                    segment = "Khách Vãng Lai (<2tr)";
                }

                double[] data = stats.get(segment);
                data[0]++; // Increment count
                data[1] += total; // Add revenue
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stats;
    }

    private String getDateFilter(String period) {
        switch (period.toLowerCase()) {
            case "month":
                return "AND MONTH(OrderDate) = MONTH(GETDATE()) AND YEAR(OrderDate) = YEAR(GETDATE()) ";
            case "quarter":
                return "AND DATEPART(QUARTER, OrderDate) = DATEPART(QUARTER, GETDATE()) AND YEAR(OrderDate) = YEAR(GETDATE()) ";
            case "year":
                return "AND YEAR(OrderDate) = YEAR(GETDATE()) ";
            case "all":
            default:
                return "";
        }
    }
}