package com.flowershop.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerSegmentDTO {
    private int customerCount;
    private double totalRevenue;
}
