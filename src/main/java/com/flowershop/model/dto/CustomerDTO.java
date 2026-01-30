package com.flowershop.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
    private int customerId;
    private String customerName;
    private String phone;
    private String email;
    private String address;

    @Override
    public String toString() {
        return customerName;
    }
}
