package com.bookbuddy.bookbuddy.controller;

import com.bookbuddy.bookbuddy.dto.author.AuthorResponse;
import com.bookbuddy.bookbuddy.dto.author.CreateAuthorRequest;
import com.bookbuddy.bookbuddy.entity.Author;
import com.bookbuddy.bookbuddy.service.AuthorService;
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

@WebMvcTest(AuthorController.class)
class AuthorControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @MockitoBean private AuthorService authorService;

    @Test
    void postAuthors_shouldReturn201() throws Exception {
        CreateAuthorRequest req = new CreateAuthorRequest("George Orwell");

        Author saved = new Author();
        saved.setId(1L);
        saved.setName("George Orwell");

        when(authorService.create(any(CreateAuthorRequest.class))).thenReturn(saved);

        mockMvc.perform(post("/api/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("George Orwell"));

        verify(authorService).create(any(CreateAuthorRequest.class));
    }

    @Test
    void getAuthors_shouldReturn200() throws Exception {
        AuthorResponse resp = new AuthorResponse(1L, "George Orwell", List.of());

        when(authorService.list()).thenReturn(List.of(resp));

        mockMvc.perform(get("/api/authors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("George Orwell"))
                .andExpect(jsonPath("$[0].books").isArray());

        verify(authorService).list();
    }
}
