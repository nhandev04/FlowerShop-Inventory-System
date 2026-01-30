package com.flowershop.service;

import com.flowershop.model.dto.WarehouseDTO;
import java.util.List;

public interface WarehouseService {
    List<WarehouseDTO> getAllWarehouses();
}
