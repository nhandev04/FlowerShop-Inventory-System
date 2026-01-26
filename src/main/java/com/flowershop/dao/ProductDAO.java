package com.flowershop.dao;

import com.flowershop.model.dto.ProductDTO;
import java.util.List;

public interface ProductDAO {
    List<ProductDTO> getAll();
    ProductDTO getById(int id);
    boolean add(ProductDTO product);
    boolean update(ProductDTO product);
    boolean delete(int id);
}