package com.bookbuddy.bookbuddy.dto.author;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateAuthorRequest(
        @NotBlank @Size(min = 2, max = 100) String name
) {
}
