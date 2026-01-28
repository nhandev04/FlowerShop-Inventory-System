package com.flowershop.service;

import com.flowershop.model.dto.ProductDTO;
import java.util.List;

public interface ProductService {
    List<ProductDTO> getAllProducts();
    boolean addProduct(ProductDTO p);
    boolean updateProduct(ProductDTO p);
    boolean deleteProduct(int id);
}