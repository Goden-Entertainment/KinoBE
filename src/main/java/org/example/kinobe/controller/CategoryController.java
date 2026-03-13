package org.example.kinobe.controller;

import org.apache.coyote.Response;
import org.example.kinobe.model.Category;
import org.example.kinobe.service.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@CrossOrigin("*")
public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryServiceImpl;

    @PostMapping("/create")
    public ResponseEntity<Category> createCategory(@RequestBody Category category){
        Category savedCategory = categoryServiceImpl.createCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> readAllCategories(){

        return ResponseEntity.ok(categoryServiceImpl.readAllCategories());
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> readCategoryById(@PathVariable int categoryId){
        Category category = categoryServiceImpl.readCategoryById(categoryId);
        return ResponseEntity.ok(category);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<Category> updateCategory(@PathVariable int categoryId, @RequestBody Category updatedCategory){
        Category category = categoryServiceImpl.updateCategory(categoryId, updatedCategory);
        return ResponseEntity.ok(category);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Category> deleteCategory(@PathVariable int categoryId){
        categoryServiceImpl.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }
}
