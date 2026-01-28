package com.flowershop.service;

import com.flowershop.model.dto.SalesOrderDTO;
import com.flowershop.model.dto.SalesOrderDetailDTO;
import java.util.List;

public interface SalesService {
    boolean createSalesOrder(SalesOrderDTO order, List<SalesOrderDetailDTO> details);
}