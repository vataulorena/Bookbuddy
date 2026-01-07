package com.bookbuddy.bookbuddy.service;

import com.bookbuddy.bookbuddy.dto.category.CategoryResponse;
import com.bookbuddy.bookbuddy.dto.category.CreateCategoryRequest;
import com.bookbuddy.bookbuddy.entity.Category;
import com.bookbuddy.bookbuddy.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category create(CreateCategoryRequest request) {
        Category category = new Category();
        category.setName(request.name());
        return categoryRepository.save(category);
    }

    public List<CategoryResponse> list() {
        return categoryRepository.findAll()
                .stream()
                .map(c -> new CategoryResponse(c.getId(), c.getName()))
                .toList();
    }
}
