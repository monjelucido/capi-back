package com.capitravel.Capitravel.service;

import com.capitravel.Capitravel.model.Category;
import com.capitravel.Capitravel.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    Category getCategoryById(Long id);

    List<Category> getAllCategories();

    Category createCategory(CategoryDTO categoryDTO);

    Category updateCategory(Long id, CategoryDTO categoryDTO);

    void deleteCategory(Long id);
}
