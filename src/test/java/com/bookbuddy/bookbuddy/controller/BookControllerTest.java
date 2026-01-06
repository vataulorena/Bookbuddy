package com.bookbuddy.bookbuddy.controller;

import com.bookbuddy.bookbuddy.dto.book.CreateBookRequest;
import com.bookbuddy.bookbuddy.service.BookCreateService;
import com.bookbuddy.bookbuddy.service.BookListService;
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

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @MockitoBean private BookCreateService bookCreateService;
    @MockitoBean private BookListService bookListService;

    @Test
    void postBooks_shouldReturn201() throws Exception {
        CreateBookRequest req = new CreateBookRequest(
                "1984",
                "9780451524935",
                "Roman distopic",
                1L,
                1L
        );

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated());

        verify(bookCreateService).create(any(CreateBookRequest.class));
    }

    @Test
    void getBooks_shouldReturn200() throws Exception {
        when(bookListService.listTitles()).thenReturn(List.of("1984"));

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("1984"));

        verify(bookListService).listTitles();
    }
}
