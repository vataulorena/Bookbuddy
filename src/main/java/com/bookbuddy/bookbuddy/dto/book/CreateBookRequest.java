package com.bookbuddy.bookbuddy.dto.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateBookRequest(
        @NotBlank @Size(min = 2, max = 200) String title,
        @NotBlank @Size(min = 10, max = 20) String isbn,
        @Size(max = 1000) String description,
        @NotNull Long authorId,
        @NotNull Long categoryId
) {
}
