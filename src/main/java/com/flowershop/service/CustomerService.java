package com.flowershop.service;

import com.flowershop.model.dto.CustomerDTO;
import java.util.List;

public interface CustomerService {
    List<CustomerDTO> getAllCustomers();
}
