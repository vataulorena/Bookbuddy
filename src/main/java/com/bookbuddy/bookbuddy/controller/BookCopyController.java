package com.bookbuddy.bookbuddy.controller;

import com.bookbuddy.bookbuddy.dto.copy.CreateBookCopyRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books/{bookId}/copies")
public class BookCopyController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addCopy(@PathVariable Long bookId,
                        @Valid @RequestBody CreateBookCopyRequest request) {

    }
}
