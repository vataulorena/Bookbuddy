package com.bookbuddy.bookbuddy.dto.copy;

public record BookCopyResponse(
        Long id,
        boolean available,
        Long bookId
) {
}
