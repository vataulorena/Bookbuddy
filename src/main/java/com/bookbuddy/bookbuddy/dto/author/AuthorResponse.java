package com.bookbuddy.bookbuddy.dto.author;

import java.util.List;

public record AuthorResponse(
        Long id,
        String name,
        List<BookSummary> books
) {
    public record BookSummary(
            Long id,
            String title,
            String isbn
    ) {}
}
