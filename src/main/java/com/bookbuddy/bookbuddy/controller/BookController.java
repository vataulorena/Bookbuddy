package com.bookbuddy.bookbuddy.controller;

import com.bookbuddy.bookbuddy.dto.book.CreateBookRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createBook(@Valid @RequestBody CreateBookRequest request) {

    }

    @GetMapping
    public List<String> listBooks() {

        return List.of();
    }
}
