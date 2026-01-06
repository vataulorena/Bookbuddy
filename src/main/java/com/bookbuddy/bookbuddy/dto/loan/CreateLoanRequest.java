package com.bookbuddy.bookbuddy.dto.loan;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateLoanRequest(
        @NotNull Long userId,
        @NotNull Long bookCopyId,
        @NotNull @FutureOrPresent LocalDate dueDate
) {
}
