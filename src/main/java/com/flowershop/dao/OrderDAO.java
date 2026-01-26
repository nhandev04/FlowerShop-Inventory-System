package com.flowershop.dao;

public interface OrderDAO {
    boolean createOrder(int productId, int quantity, String customerName, double finalPrice);
}