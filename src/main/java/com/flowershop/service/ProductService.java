package com.flowershop.service;

import com.flowershop.model.dto.ProductDTO;
import java.util.List;

public interface ProductService {
    List<ProductDTO> getAllProducts();
    ProductDTO getProductById(int id);
    boolean createProduct(ProductDTO product);
    boolean updateProduct(ProductDTO product);
    boolean deleteProduct(int id);
}