package com.flowershop.dao;

import com.flowershop.model.dto.WarehouseTransferDTO;
import java.util.List;

public interface WarehouseTransferDAO {
    int createTransfer(WarehouseTransferDTO transfer);

    List<WarehouseTransferDTO> getAllTransfers();

    WarehouseTransferDTO getTransferById(int transferId);

    boolean updateTransferStatus(int transferId, String status);

    boolean completeTransfer(int transferId);
}
