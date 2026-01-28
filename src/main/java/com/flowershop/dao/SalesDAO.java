package com.flowershop.dao;

import com.flowershop.model.dto.SalesOrderDTO;
import com.flowershop.model.dto.SalesOrderDetailDTO;
import java.util.List;

public interface SalesDAO {
    boolean createOrder(SalesOrderDTO order, List<SalesOrderDetailDTO> details);
}