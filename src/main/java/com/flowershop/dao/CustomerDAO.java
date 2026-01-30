package com.flowershop.dao;

import com.flowershop.model.dto.CustomerDTO;
import java.util.List;

public interface CustomerDAO {
    List<CustomerDTO> getAll();
}
