package com.bookbuddy.bookbuddy.controller;

import com.bookbuddy.bookbuddy.dto.copy.CreateBookCopyRequest;
import com.bookbuddy.bookbuddy.service.CopyCreateService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books/{bookId}/copies")
public class BookCopyController {

    private final CopyCreateService copyCreateService;

    public BookCopyController(CopyCreateService copyCreateService) {
        this.copyCreateService = copyCreateService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addCopy(@PathVariable Long bookId,
                        @Valid @RequestBody CreateBookCopyRequest request) {
        copyCreateService.addCopy(bookId, request);
    }
}
