package com.flowershop.service;

import com.flowershop.model.dto.CustomerDTO;
import java.util.List;

public interface CustomerService {
    List<CustomerDTO> getAllCustomers();

    CustomerDTO getCustomerById(int id);

    boolean addCustomer(CustomerDTO customer);

    boolean updateCustomer(CustomerDTO customer);

    boolean deleteCustomer(int id);
}
