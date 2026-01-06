package com.bookbuddy.bookbuddy.dto.copy;

import jakarta.validation.constraints.NotNull;

public record CreateBookCopyRequest(
        @NotNull Boolean available
) {
}
