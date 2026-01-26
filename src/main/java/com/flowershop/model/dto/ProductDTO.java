package com.flowershop.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {
    private Integer productId;
    private String productName;
    private Integer categoryId;
    private String sku;
    private BigDecimal price;
    private Integer reorderLevel;
    private Boolean isActive;
    private Timestamp createdAt;

    private String categoryName;
}