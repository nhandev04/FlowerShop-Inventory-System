package com.flowershop.service;
import com.flowershop.model.dto.CategoryDTO;
import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getAllCategories();
}