package com.flowershop.service.impl;

import com.flowershop.dao.ProductDAO;
import com.flowershop.dao.impl.ProductDAOImpl;
import com.flowershop.model.dto.ProductDTO;
import com.flowershop.service.ProductService;
import java.math.BigDecimal;
import java.util.List;

public class ProductServiceImpl implements ProductService {

    private final ProductDAO productDAO;

    public ProductServiceImpl() {
        this.productDAO = new ProductDAOImpl();
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productDAO.getAll();
    }

    @Override
    public ProductDTO getProductById(int id) {
        return productDAO.getById(id);
    }

    @Override
    public boolean createProduct(ProductDTO product) {
        if (product == null) return false;
        if (product.getProductName() == null || product.getProductName().trim().isEmpty()) {
            System.err.println("Lỗi: Tên sản phẩm không được để trống!");
            return false;
        }
        if (product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            System.err.println("Lỗi: Giá bán phải là số dương!");
            return false;
        }
        return productDAO.add(product);
    }

    @Override
    public boolean updateProduct(ProductDTO product) {
        if (product == null || product.getProductId() == null) return false;
        return productDAO.update(product);
    }

    @Override
    public boolean deleteProduct(int id) {
        return productDAO.delete(id);
    }
}