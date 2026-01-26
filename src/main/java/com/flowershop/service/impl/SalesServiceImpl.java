package com.flowershop.service.impl;

import com.flowershop.dao.OrderDAO;
import com.flowershop.dao.impl.OrderDAOImpl;
import com.flowershop.service.SalesService;
import com.flowershop.view.observer.ShopEventManager;

public class SalesServiceImpl implements SalesService {

    private final OrderDAO orderDAO;

    public SalesServiceImpl() {
        this.orderDAO = new OrderDAOImpl();
    }

    @Override
    public String sellProduct(int productId, int quantity, String customerName, double price) {
        if (quantity <= 0) return "Số lượng phải lớn hơn 0!";
        if (customerName.isEmpty()) return "Vui lòng nhập tên khách hàng!";

        // 2. Gọi DAO xử lý Transaction
        boolean success = orderDAO.createOrder(productId, quantity, customerName, price);

        if (success) {
            ShopEventManager.getInstance().notify("PRODUCT_CHANGED");
            ShopEventManager.getInstance().notify("ORDER_CREATED");

            return "SUCCESS";
        } else {
            return "Giao dịch thất bại! Kiểm tra tồn kho hoặc lỗi hệ thống.";
        }
    }
}