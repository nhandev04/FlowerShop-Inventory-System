package com.flowershop.dao;
import com.flowershop.model.dto.CategoryDTO;
import java.util.List;

public interface CategoryDAO {
    List<CategoryDTO> getAll();
}