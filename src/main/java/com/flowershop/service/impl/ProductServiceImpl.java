package com.flowershop.service.impl;

import com.flowershop.dao.ProductDAO;
import com.flowershop.dao.impl.ProductDAOImpl;
import com.flowershop.model.dto.ProductDTO;
import com.flowershop.service.ProductService;
import com.flowershop.view.observer.ShopEventManager;

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
    public boolean addProduct(ProductDTO p) {
        boolean success = productDAO.add(p);
        if (success) {
            ShopEventManager.getInstance().notify("PRODUCT_CHANGED");
        }
        return success;
    }

    @Override
    public boolean updateProduct(ProductDTO p) {
        boolean success = productDAO.update(p);
        if (success) {
            ShopEventManager.getInstance().notify("PRODUCT_CHANGED");
        }
        return success;
    }

    @Override
    public boolean deleteProduct(int id) {
        boolean success = productDAO.delete(id);
        if (success) {
            ShopEventManager.getInstance().notify("PRODUCT_CHANGED");
        }
        return success;
    }
}