package com.flowershop.model.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class PurchaseOrderDTO {
    private int purchaseId;
    private int supplierId;
    private int warehouseId;
    private Timestamp orderDate;
    private BigDecimal totalAmount;
    private String status;
    private String notes;

    public PurchaseOrderDTO() {}

    public PurchaseOrderDTO(int supplierId, int warehouseId, BigDecimal totalAmount, String notes) {
        this.supplierId = supplierId;
        this.warehouseId = warehouseId;
        this.totalAmount = totalAmount;
        this.notes = notes;
    }

    public int getPurchaseId() { return purchaseId; }
    public void setPurchaseId(int purchaseId) { this.purchaseId = purchaseId; }

    public int getSupplierId() { return supplierId; }
    public void setSupplierId(int supplierId) { this.supplierId = supplierId; }

    public int getWarehouseId() { return warehouseId; }
    public void setWarehouseId(int warehouseId) { this.warehouseId = warehouseId; }

    public Timestamp getOrderDate() { return orderDate; }
    public void setOrderDate(Timestamp orderDate) { this.orderDate = orderDate; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}