package com.bookbuddy.bookbuddy.service;

import com.bookbuddy.bookbuddy.dto.category.CreateCategoryRequest;
import com.bookbuddy.bookbuddy.entity.Category;
import com.bookbuddy.bookbuddy.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

    private final CategoryRepository categoryRepository = mock(CategoryRepository.class);
    private final CategoryService service = new CategoryService(categoryRepository);

    @Test
    void create_shouldSaveCategoryWithName() {
        CreateCategoryRequest request = new CreateCategoryRequest("Fantasy");

        service.create(request);

        ArgumentCaptor<Category> captor = ArgumentCaptor.forClass(Category.class);
        verify(categoryRepository).save(captor.capture());

        assertEquals("Fantasy", captor.getValue().getName());
    }

    @Test
    void list_shouldReturnAllCategories() {
        Category c = new Category();
        c.setId(1L);
        c.setName("Fantasy");

        when(categoryRepository.findAll()).thenReturn(List.of(c));

        List<Category> result = service.list();

        assertEquals(1, result.size());
        assertEquals("Fantasy", result.get(0).getName());
        verify(categoryRepository).findAll();
    }
}
