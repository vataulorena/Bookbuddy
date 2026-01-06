package com.bookbuddy.bookbuddy.controller;

import com.bookbuddy.bookbuddy.dto.user.CreateUserRequest;
import com.bookbuddy.bookbuddy.entity.User;
import com.bookbuddy.bookbuddy.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@Valid @RequestBody CreateUserRequest request) {
        return userService.create(request);
    }

    @GetMapping
    public List<User> list() {
        return userService.list();
    }
}
