package com.bookbuddy.bookbuddy.controller;

import com.bookbuddy.bookbuddy.dto.category.CreateCategoryRequest;
import com.bookbuddy.bookbuddy.entity.Category;
import com.bookbuddy.bookbuddy.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Category create(@Valid @RequestBody CreateCategoryRequest request) {
        return categoryService.create(request);
    }

    @GetMapping
    public List<Category> list() {
        return categoryService.list();
    }
}
