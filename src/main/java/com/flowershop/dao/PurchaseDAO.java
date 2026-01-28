package com.flowershop.dao;

import com.flowershop.model.dto.PurchaseOrderDTO;
import com.flowershop.model.dto.PurchaseOrderDetailDTO;
import java.util.List;

public interface PurchaseDAO {
    boolean save(PurchaseOrderDTO order, List<PurchaseOrderDetailDTO> details);
}