package com.bookbuddy.bookbuddy.controller;

import com.bookbuddy.bookbuddy.dto.user.CreateUserRequest;
import com.bookbuddy.bookbuddy.dto.user.UserResponse;
import com.bookbuddy.bookbuddy.entity.User;
import com.bookbuddy.bookbuddy.service.UserService;
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

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    @Test
    void postUsers_shouldReturn201_whenValid() throws Exception {
        CreateUserRequest req = new CreateUserRequest("Ana", "ana@example.com");

        User saved = new User();
        saved.setId(1L);
        saved.setName("Ana");
        saved.setEmail("ana@example.com");

        when(userService.create(any())).thenReturn(saved);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated());

        verify(userService).create(any(CreateUserRequest.class));
    }

    @Test
    void getUsers_shouldReturn200() throws Exception {
        List<UserResponse> resp = List.of(
                new UserResponse(1L, "Ana", "ana@example.com", List.of())
        );

        when(userService.listWithLoans()).thenReturn(resp);

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));

        verify(userService).listWithLoans();
    }
}
