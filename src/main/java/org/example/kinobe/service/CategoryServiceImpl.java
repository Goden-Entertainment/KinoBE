package org.example.kinobe.service;

import org.example.kinobe.model.Category;
import org.example.kinobe.repository.CategoryRepository;
import org.example.kinobe.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, MovieRepository movieRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> readAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category readCategoryById(int categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryId));
    }

    @Override
    public Category updateCategory(int categoryId, Category updatedCategory) {
        Category categoryExist = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryId));

        categoryExist.setGenre(updatedCategory.getGenre());

        return categoryRepository.save(categoryExist);

    }

    @Override
    public void deleteCategory(int categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}
