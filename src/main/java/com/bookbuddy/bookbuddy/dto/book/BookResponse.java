package com.bookbuddy.bookbuddy.dto.book;

public record BookResponse(
        Long id,
        String title,
        String isbn
) {}
