package com.bookbuddy.bookbuddy.controller;

import com.bookbuddy.bookbuddy.dto.copy.BookCopyResponse;
import com.bookbuddy.bookbuddy.dto.copy.CreateBookCopyRequest;
import com.bookbuddy.bookbuddy.service.CopyCreateService;
import com.bookbuddy.bookbuddy.service.CopyListService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books/{bookId}/copies")
public class BookCopyController {

    private final CopyCreateService copyCreateService;
    private final CopyListService copyListService;

    public BookCopyController(CopyCreateService copyCreateService, CopyListService copyListService) {
        this.copyCreateService = copyCreateService;
        this.copyListService = copyListService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookCopyResponse addCopy(@PathVariable Long bookId,
                                    @Valid @RequestBody CreateBookCopyRequest request) {
        return copyCreateService.addCopy(bookId, request);
    }

    @GetMapping
    public List<BookCopyResponse> listCopies(@PathVariable Long bookId) {
        return copyListService.listByBook(bookId);
    }
}
