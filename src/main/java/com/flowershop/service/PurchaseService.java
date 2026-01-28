package com.flowershop.service;

import com.flowershop.model.dto.PurchaseOrderDTO;
import com.flowershop.model.dto.PurchaseOrderDetailDTO;
import java.util.List;

public interface PurchaseService {
    boolean createPurchaseOrder(PurchaseOrderDTO order, List<PurchaseOrderDetailDTO> details);
}