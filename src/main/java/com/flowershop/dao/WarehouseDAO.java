package com.flowershop.dao;

import com.flowershop.model.dto.WarehouseDTO;
import java.util.List;

public interface WarehouseDAO {
    List<WarehouseDTO> getAll();
}
