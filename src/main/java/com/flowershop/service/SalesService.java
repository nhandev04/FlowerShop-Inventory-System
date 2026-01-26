package com.flowershop.service;

public interface SalesService {
    String sellProduct(int productId, int quantity, String customerName, double price);
}