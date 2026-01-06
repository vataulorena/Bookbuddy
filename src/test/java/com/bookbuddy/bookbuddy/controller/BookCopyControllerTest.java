package com.bookbuddy.bookbuddy.controller;

import com.bookbuddy.bookbuddy.dto.copy.CreateBookCopyRequest;
import com.bookbuddy.bookbuddy.service.CopyCreateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookCopyController.class)
class BookCopyControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @MockitoBean private CopyCreateService copyCreateService;

    @Test
    void postCopy_shouldReturn201() throws Exception {
        CreateBookCopyRequest req = new CreateBookCopyRequest(true);

        mockMvc.perform(post("/api/books/1/copies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated());

        verify(copyCreateService).addCopy(eq(1L), any(CreateBookCopyRequest.class));
    }
}
