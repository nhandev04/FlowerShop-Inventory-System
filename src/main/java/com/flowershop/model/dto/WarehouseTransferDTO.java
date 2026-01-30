package com.flowershop.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseTransferDTO {
    private int transferId;
    private int productId;
    private String productName;
    private int fromWarehouseId;
    private String fromWarehouseName;
    private int toWarehouseId;
    private String toWarehouseName;
    private int quantity;
    private Date transferDate;
    private String status;
    private String notes;
}
