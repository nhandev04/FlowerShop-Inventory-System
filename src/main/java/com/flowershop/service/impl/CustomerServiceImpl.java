package com.flowershop.service.impl;

import com.flowershop.dao.CustomerDAO;
import com.flowershop.dao.impl.CustomerDAOImpl;
import com.flowershop.model.dto.CustomerDTO;
import com.flowershop.service.CustomerService;
import java.util.List;

public class CustomerServiceImpl implements CustomerService {

    private final CustomerDAO customerDAO;

    public CustomerServiceImpl() {
        this.customerDAO = new CustomerDAOImpl();
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerDAO.getAll();
    }
}
