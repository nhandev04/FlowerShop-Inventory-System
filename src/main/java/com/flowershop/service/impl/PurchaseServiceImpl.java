package com.flowershop.service.impl;

import com.flowershop.dao.PurchaseDAO;
import com.flowershop.dao.impl.PurchaseDAOImpl;
import com.flowershop.model.dto.PurchaseOrderDTO;
import com.flowershop.model.dto.PurchaseOrderDetailDTO;
import com.flowershop.service.PurchaseService;
import com.flowershop.view.observer.ShopEventManager;

import java.util.List;

public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseDAO purchaseDAO;

    public PurchaseServiceImpl() {
        this.purchaseDAO = new PurchaseDAOImpl();
    }

    @Override
    public boolean createPurchaseOrder(PurchaseOrderDTO order, List<PurchaseOrderDetailDTO> details) {
        boolean success = purchaseDAO.save(order, details);

        if (success) {
            ShopEventManager.getInstance().notify("PRODUCT_CHANGED");
        }

        return success;
    }
}