package com.bookbuddy.bookbuddy.controller;

import com.bookbuddy.bookbuddy.dto.copy.BookCopyResponse;
import com.bookbuddy.bookbuddy.dto.copy.CreateBookCopyRequest;
import com.bookbuddy.bookbuddy.service.CopyCreateService;
import com.bookbuddy.bookbuddy.service.CopyListService;
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

@WebMvcTest(BookCopyController.class)
class BookCopyControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @MockitoBean private CopyCreateService copyCreateService;
    @MockitoBean private CopyListService copyListService;

    @Test
    void postCopy_shouldReturn201_andId() throws Exception {
        CreateBookCopyRequest req = new CreateBookCopyRequest(true);

        when(copyCreateService.addCopy(eq(1L), any()))
                .thenReturn(new BookCopyResponse(10L, true, 1L));

        mockMvc.perform(post("/api/books/1/copies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.available").value(true))
                .andExpect(jsonPath("$.bookId").value(1));

        verify(copyCreateService).addCopy(eq(1L), any(CreateBookCopyRequest.class));
    }

    @Test
    void getCopies_shouldReturn200() throws Exception {
        when(copyListService.listByBook(1L)).thenReturn(List.of(
                new BookCopyResponse(10L, true, 1L)
        ));

        mockMvc.perform(get("/api/books/1/copies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(10))
                .andExpect(jsonPath("$[0].available").value(true));

        verify(copyListService).listByBook(1L);
    }
}
