package com.flowershop.service;

import com.flowershop.model.dto.WarehouseTransferDTO;
import java.util.List;

public interface WarehouseTransferService {
    int createTransfer(WarehouseTransferDTO transfer);

    List<WarehouseTransferDTO> getAllTransfers();

    WarehouseTransferDTO getTransferById(int transferId);

    boolean completeTransfer(int transferId);

    boolean cancelTransfer(int transferId);
}
