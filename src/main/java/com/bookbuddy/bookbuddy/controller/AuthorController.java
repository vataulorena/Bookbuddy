package com.bookbuddy.bookbuddy.controller;

import com.bookbuddy.bookbuddy.dto.author.AuthorResponse;
import com.bookbuddy.bookbuddy.dto.author.CreateAuthorRequest;
import com.bookbuddy.bookbuddy.entity.Author;
import com.bookbuddy.bookbuddy.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Author create(@Valid @RequestBody CreateAuthorRequest request) {
        return authorService.create(request);
    }

    @GetMapping
    public List<AuthorResponse> list() {
        return authorService.list();
    }
}
