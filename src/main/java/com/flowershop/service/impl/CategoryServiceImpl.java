package com.flowershop.service.impl;

import com.flowershop.dao.CategoryDAO;
import com.flowershop.dao.impl.CategoryDAOImpl;
import com.flowershop.model.dto.CategoryDTO;
import com.flowershop.service.CategoryService;
import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    private final CategoryDAO categoryDAO = new CategoryDAOImpl();

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryDAO.getAll();
    }
}