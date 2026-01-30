package com.flowershop.service.impl;

import com.flowershop.dao.WarehouseTransferDAO;
import com.flowershop.dao.impl.WarehouseTransferDAOImpl;
import com.flowershop.model.dto.WarehouseTransferDTO;
import com.flowershop.service.WarehouseTransferService;
import java.util.List;

public class WarehouseTransferServiceImpl implements WarehouseTransferService {

    private WarehouseTransferDAO transferDAO;

    public WarehouseTransferServiceImpl() {
        this.transferDAO = new WarehouseTransferDAOImpl();
    }

    @Override
    public int createTransfer(WarehouseTransferDTO transfer) {
        return transferDAO.createTransfer(transfer);
    }

    @Override
    public List<WarehouseTransferDTO> getAllTransfers() {
        return transferDAO.getAllTransfers();
    }

    @Override
    public WarehouseTransferDTO getTransferById(int transferId) {
        return transferDAO.getTransferById(transferId);
    }

    @Override
    public boolean completeTransfer(int transferId) {
        return transferDAO.completeTransfer(transferId);
    }

    @Override
    public boolean cancelTransfer(int transferId) {
        return transferDAO.updateTransferStatus(transferId, "Cancelled");
    }
}
