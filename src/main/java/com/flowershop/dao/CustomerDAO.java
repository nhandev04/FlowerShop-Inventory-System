package com.flowershop.dao;

import com.flowershop.model.dto.CustomerDTO;
import java.util.List;

public interface CustomerDAO {
    List<CustomerDTO> getAll();

    CustomerDTO getById(int id);

    boolean add(CustomerDTO customer);

    boolean update(CustomerDTO customer);

    boolean delete(int id);
}
