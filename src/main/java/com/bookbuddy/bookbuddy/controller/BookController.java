package com.bookbuddy.bookbuddy.controller;

import com.bookbuddy.bookbuddy.dto.book.CreateBookRequest;
import com.bookbuddy.bookbuddy.service.BookCreateService;
import com.bookbuddy.bookbuddy.service.BookListService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookCreateService bookCreateService;
    private final BookListService bookListService;

    public BookController(BookCreateService bookCreateService, BookListService bookListService) {
        this.bookCreateService = bookCreateService;
        this.bookListService = bookListService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createBook(@Valid @RequestBody CreateBookRequest request) {
        bookCreateService.create(request);
    }

    @GetMapping
    public List<String> listBooks() {
        return bookListService.listTitles();
    }
}
