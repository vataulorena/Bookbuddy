package com.bookbuddy.bookbuddy.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCategoryRequest(
        @NotBlank @Size(min = 2, max = 100) String name
) {
}
