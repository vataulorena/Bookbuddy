package com.bookbuddy.bookbuddy.controller;

import com.bookbuddy.bookbuddy.dto.category.CategoryResponse;
import com.bookbuddy.bookbuddy.dto.category.CreateCategoryRequest;
import com.bookbuddy.bookbuddy.entity.Category;
import com.bookbuddy.bookbuddy.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @MockitoBean private CategoryService categoryService;

    @Test
    void postCategories_shouldReturn201() throws Exception {
        CreateCategoryRequest req = new CreateCategoryRequest("Fantasy");

        Category saved = new Category();
        saved.setId(1L);
        saved.setName("Fantasy");

        when(categoryService.create(any())).thenReturn(saved);

        mockMvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Fantasy"));

        verify(categoryService).create(any(CreateCategoryRequest.class));
    }

    @Test
    void getCategories_shouldReturn200() throws Exception {
        when(categoryService.list()).thenReturn(List.of(new CategoryResponse(1L, "Fantasy")));

        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Fantasy"));

        verify(categoryService).list();
    }
}
