package org.example.kinobe.service;

import org.example.kinobe.model.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(Category category);
    List<Category> readAllCategories();
    Category readCategoryById(int categoryId);
    Category updateCategory(int categoryId, Category updatedCategory);
    void deleteCategory(int categoryId);

}
