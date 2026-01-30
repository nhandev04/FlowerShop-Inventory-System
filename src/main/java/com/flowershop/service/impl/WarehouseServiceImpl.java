package com.flowershop.service.impl;

import com.flowershop.dao.WarehouseDAO;
import com.flowershop.dao.impl.WarehouseDAOImpl;
import com.flowershop.model.dto.WarehouseDTO;
import com.flowershop.service.WarehouseService;

import java.util.List;

public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseDAO warehouseDAO;

    public WarehouseServiceImpl() {
        this.warehouseDAO = new WarehouseDAOImpl();
    }

    @Override
    public List<WarehouseDTO> getAllWarehouses() {
        return warehouseDAO.getAll();
    }
}
