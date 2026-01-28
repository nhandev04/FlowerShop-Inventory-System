package com.flowershop.model.dto;

import java.math.BigDecimal;

public class PurchaseOrderDetailDTO {
    private int detailId;
    private int purchaseId;
    private int productId;
    private int quantity;
    private BigDecimal unitCost;

    public PurchaseOrderDetailDTO() {}

    public PurchaseOrderDetailDTO(int productId, int quantity, BigDecimal unitCost) {
        this.productId = productId;
        this.quantity = quantity;
        this.unitCost = unitCost;
    }

    public int getDetailId() { return detailId; }
    public void setDetailId(int detailId) { this.detailId = detailId; }

    public int getPurchaseId() { return purchaseId; }
    public void setPurchaseId(int purchaseId) { this.purchaseId = purchaseId; }

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public BigDecimal getUnitCost() { return unitCost; }
    public void setUnitCost(BigDecimal unitCost) { this.unitCost = unitCost; }
}