package com.flowershop.service.impl;

import com.flowershop.dao.SalesDAO;
import com.flowershop.dao.impl.SalesDAOImpl;
import com.flowershop.model.dto.SalesOrderDTO;
import com.flowershop.model.dto.SalesOrderDetailDTO;
import com.flowershop.service.SalesService;
import com.flowershop.view.observer.ShopEventManager;

import java.util.List;

public class SalesServiceImpl implements SalesService {

    private final SalesDAO salesDAO;

    public SalesServiceImpl() {
        this.salesDAO = new SalesDAOImpl();
    }

    @Override
    public boolean createSalesOrder(SalesOrderDTO order, List<SalesOrderDetailDTO> details) {
        boolean success = salesDAO.createOrder(order, details);

        if (success) {
            ShopEventManager.getInstance().notify("PRODUCT_CHANGED");
        }
        return success;
    }
}