package com.bookbuddy.bookbuddy.controller;

import com.bookbuddy.bookbuddy.dto.loan.CreateLoanRequest;
import com.bookbuddy.bookbuddy.service.LoanCreateService;
import com.bookbuddy.bookbuddy.service.LoanReturnService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LoanController.class)
class LoanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private LoanCreateService loanCreateService;

    @MockitoBean
    private LoanReturnService loanReturnService;

    @Test
    void createLoan_shouldReturn201() throws Exception {
        CreateLoanRequest req = new CreateLoanRequest(1L, 2L, LocalDate.now().plusDays(7));

        mockMvc.perform(post("/api/loans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated());

        verify(loanCreateService).create(any(CreateLoanRequest.class));
    }

    @Test
    void returnLoan_shouldReturn200() throws Exception {
        mockMvc.perform(post("/api/loans/1/return"))
                .andExpect(status().isOk());

        verify(loanReturnService).returnLoan(1L);
    }
}
